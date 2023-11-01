package ink.honp.sample.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * JSON 工具类
 * @author jeff chen
 * @since 1.0.0
 */
@Slf4j
public abstract class JsonUtil {


    private static final ObjectMapper MAPPER = new ObjectMapper();
    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 取消默认转换timestamps形式
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        // 忽略空Bean转json的错误
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        // 忽略在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        // 支持 java.time.LocalDateTime 类型
        MAPPER.registerModule(new JavaTimeModule());
    }

    private JsonUtil() {

    }

    /**
     * json 字符内容转对象
     * @param text  字符内容
     * @param clz   对象 Class
     * @return 转换失败返回 null
     */
    public static <T> T toBean(final String text, @NonNull Class<T> clz) {
        if (StringUtils.isNotBlank(text)) {
            try {
                return MAPPER.readValue(text, clz);
            } catch (JsonProcessingException e) {
                log.warn("json 解析失败: {}", text, e);
            }
        }

        return null;
    }

    /**
     * 字符串转对象，适用含泛型类型的对象转换
     * <p>e.g. toBean("[1,2,3,4]", List.class, Integer.class)</p>
     * @param jsonText   json 字符串
     * @param parametrized    基类
     * @param parameterClz 泛型元素类
     * @return 转换失败返回 Optional.empty()
     * @param <T> -
     * @param <E> -
     */
    public static <T,E> T toBean(final String jsonText, Class<T> parametrized, Class<E> parameterClz) {

        if (StringUtils.isNotBlank(jsonText)) {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(parametrized, parameterClz);
            try {
                return MAPPER.readValue(jsonText, javaType);
            } catch (JsonProcessingException e) {
                log.warn("json 解析失败: {}", jsonText, e);
            }
        }

        return null;
    }

    /**
     * 对象转字符串
     * @param obj 对象实例
     * @return 错误返回空字符串 “”
     */
    public static String toStr(@NonNull Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("对象转字符串错误", e);
        }

        return StringUtils.EMPTY;
    }


    /**
     * json 字符串转List数组
     * @param text   json 字符串
     * @param clz    元素类型
     * @return List<T> 转换失败返回 null
     */
    public static <T> List<T> toList(String text, Class<T> clz) {
        if (StringUtils.isNotBlank(text)) {
            JavaType listJavaType = MAPPER.getTypeFactory().constructParametricType(List.class, clz);
            try {
                return MAPPER.readValue(text, listJavaType);
            } catch (JsonProcessingException e) {
                log.warn("json 字符内容转 List 数组错误:{}", e.getMessage(), e);
            }
        }

        return Collections.emptyList();
    }


    /**
     * json 字符串转为json节点对象
     * @param text json 字符串
     * @return json节点对象
     */
    public static JsonNode toNode(@NonNull String text) {
        try {
            return MAPPER.readTree(text);
        } catch (JsonProcessingException e) {
            log.warn("json 转 node 失败: {}", text, e);
        }
        return null;
    }



}

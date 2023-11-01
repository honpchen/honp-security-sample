package ink.honp.sample.common.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jeff chen
 * @since 1.0.0
 */
public abstract class CollectionUtil {

    private CollectionUtil() {

    }

    /**
     * 默认的 Map 初始化大小
     */
    private static final Integer DEFAULT_INIT_MAP_SIZE = 2;

    /**
     * 判断集合是否为空
     * <pre>
     *     CollectionUtil.isEmpty(null)              --> true
     *     CollectionUtil.isEmpty(new ArrayList<>()) --> true
     * </pre>
     * @param coll 集合实例
     * @return {@code true} 集合实例 null 或 空时返回
     */
    public static boolean isEmpty(Collection<?> coll) {

        return null == coll || coll.isEmpty();
    }

    /**
     * 判断集合是否为空
     * <pre>
     *     CollectionUtil.isNotEmpty(null)              --> false
     *     CollectionUtil.isNotEmpty(new ArrayList<>()) --> false
     * </pre>
     * @param coll 集合实例
     * @return -
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * 创建默认大小的Map, 默认大小是 2
     * @param <K>  Key
     * @param <V>  Value
     * @return Map
     */
    public static <K,V>Map<K, V> newHashMap() {
        return new HashMap<>(DEFAULT_INIT_MAP_SIZE);
    }

    /**
     * 创建 Map
     * @param size 初始化大小
     * @param <K>  Key
     * @param <V>  Value
     * @return Map
     */
    public static <K,V>Map<K, V> newHashMap(Integer size) {

        return new HashMap<>(size);
    }
}

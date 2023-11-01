package ink.honp.sample.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据封装
 * @author jeff chen
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
public class PageInfo<T> {

    /** 当前页 **/
    private Long page;

    /** 每页大小 **/
    private Long size;

    /** 总页数 **/
    private Long totalPages;

    /** 总数 **/
    private Long totalCount;

    /** 数据 **/
    private List<T> content;


    public static <T> PageInfo<T> create(long page, long size, long totalPages, long totalCount, List<T> content) {
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setPage(page);
        pageInfo.setSize(size);
        pageInfo.setTotalPages(totalPages);
        pageInfo.setTotalCount(totalCount);
        pageInfo.setContent(content);

        return pageInfo;
    }

    public static <T> PageInfo<T> create(long page, long size, long totalPages, long totalCount) {
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setPage(page);
        pageInfo.setSize(size);
        pageInfo.setTotalPages(totalPages);
        pageInfo.setTotalCount(totalCount);
        pageInfo.setContent(new ArrayList<>());

        return pageInfo;
    }
}

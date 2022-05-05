package site.wetsion.framework.mitosis.common;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author <a href="mailto:shuanghua@fordeal.com">霜华</a>
 * @date 2022-05-04 14:50
 */
@Data
public class Pagination implements Serializable {
    private static final long serialVersionUID = 5789046414875699283L;

    private Long page;
    private Long size;
    private Long count;
    private String sort;
    private static Long DEFAULT_SIZE = 10L;
    private static Long DEFAULT_PAGE = 1L;
    private static String DEFAULT_SORT = "desc";

    public Pagination(Long page, Long size, Long count) {
        this.setPage(page);
        this.setCount(count);
        this.setSize(size);
    }

    public Pagination(Long page, Long size, Long count, String sort) {
        this(page, size, count);
        this.setSort(sort);
    }

    public Pagination(Long page, Long size) {
        this(page, size, 0L);
    }

    public Pagination() {
        this.page = DEFAULT_PAGE;
        this.size = DEFAULT_SIZE;
    }

    public static void setDefaultSize(Long size) {
        DEFAULT_SIZE = size;
    }

    public static void setDefaultPage(Long page) {
        DEFAULT_PAGE = page;
    }

    public static void setDefaultSort(String sort) {
        DEFAULT_SORT = sort;
    }

    public Long getPage() {
        return this.page;
    }

    public void setPage(Long page) {
        if (page == null || page <= 0L) {
            page = DEFAULT_PAGE;
        }

        this.page = page;
    }

    public Long getSize() {
        return this.size;
    }

    public void setSize(Long size) {
        if (size == null || size <= 0L) {
            size = DEFAULT_SIZE;
        }

        this.size = size;
    }

    public Long getCount() {
        return this.count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getSort() {
        if (StringUtils.isBlank(this.sort)) {
            this.sort = DEFAULT_SORT;
        }

        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Long getSqlOffset() {
        return this.getSize() * (this.getPage() - 1L);
    }

    public Long getSqlLimit() {
        return this.getSize();
    }

    public Long getTotal() {
        return (long)Math.ceil((double)this.getCount() * 1.0D / (double)this.getSize());
    }

    public HashMap<String, Long> getPaginationInfo() {
        HashMap<String, Long> result = new HashMap(3);
        result.put("size", this.getSize());
        result.put("page", this.getPage());
        result.put("total", this.getCount());
        return result;
    }

    public void goToNextPage() {
        this.setPage(this.getPage() + 1L);
    }

    public Boolean hasContentOnThisPage() {
        return this.getTotal() >= this.getPage();
    }
}

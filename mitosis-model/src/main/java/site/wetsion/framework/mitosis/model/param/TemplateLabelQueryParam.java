package site.wetsion.framework.mitosis.model.param;

import lombok.Data;

import java.util.Objects;

/**
 * @author wetsion
 * @date 4/18/22
 */
@Data
public class TemplateLabelQueryParam {

    private String name;

    private String code;

    private Integer dataType;

    private Long page;

    private Long pageSize;

    public Long getPage() {
        if (Objects.isNull(page)) {
            return 1L;
        }
        return page;
    }

    public Long getPageSize() {
        if (Objects.isNull(page)) {
            return 10L;
        }
        return pageSize;
    }
}

package site.wetsion.framework.mitosis.model.entity;

import lombok.Data;

/**
 * @author wetsion
 * @date 4/17/22
 */
@Data
public class TemplateDO {

    private Long id;

    private String title;

    private String content;

    private Integer created;

    private Integer updated;

    private Integer deleted;

}

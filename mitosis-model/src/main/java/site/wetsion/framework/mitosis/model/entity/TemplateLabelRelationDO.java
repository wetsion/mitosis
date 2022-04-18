package site.wetsion.framework.mitosis.model.entity;

import lombok.Data;

/**
 * @author wetsion
 * @date 2022-04-18 15:10
 */
@Data
public class TemplateLabelRelationDO {

    private Long id;

    private Long templateId;

    private Long labelId;

    private String code;

    private Integer created;

    private Integer updated;

    private Integer deleted;
}

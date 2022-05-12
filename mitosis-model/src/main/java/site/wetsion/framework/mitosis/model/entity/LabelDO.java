package site.wetsion.framework.mitosis.model.entity;

import lombok.Data;

/**
 * @author wetsion
 * @date 4/17/22
 */
@Data
public class LabelDO {

    private Long id;

    private String code;

    private String name;

    private Integer dataType;

    private String config;

    private Integer created;

    private Integer updated;

    private Integer deleted;
}

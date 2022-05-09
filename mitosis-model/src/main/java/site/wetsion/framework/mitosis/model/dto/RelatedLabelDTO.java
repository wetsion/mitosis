package site.wetsion.framework.mitosis.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wetsion
 * @date 2022-05-09 12:04
 */
@Data
public class RelatedLabelDTO implements Serializable {
    private static final long serialVersionUID = -7315625045445271227L;

    private Long id;

    private String code;

    private String name;

    private Integer dataType;

    private Long locationPk;

    private Long created;
}

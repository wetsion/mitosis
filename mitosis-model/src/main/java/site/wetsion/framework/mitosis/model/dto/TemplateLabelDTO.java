package site.wetsion.framework.mitosis.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wetsion
 * @date 4/17/22
 */
@Data
public class TemplateLabelDTO implements Serializable {

    private Long id;

    private String name;

    private Integer dataType;
}

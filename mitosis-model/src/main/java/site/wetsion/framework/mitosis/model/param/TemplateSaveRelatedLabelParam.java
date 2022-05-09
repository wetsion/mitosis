package site.wetsion.framework.mitosis.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wetsion
 * @date 2022-05-09 11:52
 */
@Data
public class TemplateSaveRelatedLabelParam implements Serializable {
    private static final long serialVersionUID = -5591173760800226626L;

    private Long labelId;

    private Long locationPk;
}

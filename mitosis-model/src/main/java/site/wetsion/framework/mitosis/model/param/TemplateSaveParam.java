package site.wetsion.framework.mitosis.model.param;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wetsion
 * @date 2022-05-04 16:25
 */
@Data
public class TemplateSaveParam implements Serializable {
    private static final long serialVersionUID = 5808511028688896373L;

    private Long templateId;

    private String title;

    private String content;

    private List<Long> relatedLabels;
}

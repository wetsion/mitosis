package site.wetsion.framework.mitosis.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wetsion
 * @date 2022-05-04 17:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateWrapperDTO implements Serializable {
    private static final long serialVersionUID = 4042325550717900995L;

    private TemplateDTO template;

    private List<LabelDTO> relatedLabels;
}

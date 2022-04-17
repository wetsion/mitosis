package site.wetsion.framework.mitosis.service;

import org.springframework.stereotype.Service;
import site.wetsion.framework.datasource.mapper.TemplateLabelMapper;
import site.wetsion.framework.mitosis.core.converter.TemplateConverter;
import site.wetsion.framework.mitosis.model.dto.TemplateLabelDTO;
import site.wetsion.framework.mitosis.model.entity.TemplateLabelDO;
import site.wetsion.framework.mitosis.model.param.TemplateLabelQueryParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wetsion
 * @date 4/18/22
 */
@Service
public class TemplateService {

    @Resource
    private TemplateLabelMapper templateLabelMapper;

    public List<TemplateLabelDTO> queryLabels(TemplateLabelQueryParam param) {
        List<TemplateLabelDO> templateLabels = templateLabelMapper.listByCondition(param);
        return TemplateConverter.INSTANCE.templateLabelDoListToDtoList(templateLabels);
    }
}

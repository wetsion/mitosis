package site.wetsion.framework.mitosis.service;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import site.wetsion.framework.mitosis.datasource.mapper.LabelMapper;
import site.wetsion.framework.mitosis.datasource.mapper.TemplateLabelRelationMapper;
import site.wetsion.framework.mitosis.common.constant.ExceptionConstant;
import site.wetsion.framework.mitosis.core.converter.TemplateConverter;
import site.wetsion.framework.mitosis.model.dto.LabelDTO;
import site.wetsion.framework.mitosis.model.entity.LabelDO;
import site.wetsion.framework.mitosis.model.entity.TemplateLabelRelationDO;
import site.wetsion.framework.mitosis.model.param.TemplateLabelQueryParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wetsion
 * @date 4/18/22
 */
@Service
public class TemplateService {

    @Resource
    private LabelMapper labelMapper;
    @Resource
    private TemplateLabelRelationMapper templateLabelRelationMapper;

    public List<LabelDTO> queryLabels(TemplateLabelQueryParam param) {
        List<LabelDO> templateLabels = labelMapper.listByCondition(param);
        return TemplateConverter.INSTANCE.templateLabelDoListToDtoList(templateLabels);
    }

    /**
     * 查询模版上已关联的标签
     * @param templateId 模版ID
     * @return
     */
    public List<LabelDTO> queryLabelsOnTemplat(Long templateId) {
        Objects.requireNonNull(templateId, ExceptionConstant.TEMPLATE_UNKNOWN);
        List<TemplateLabelRelationDO> relations = templateLabelRelationMapper.listByTemplateId(templateId);
        if (CollectionUtils.isEmpty(relations)) {
            return null;
        }
        List<Long> labelIds = relations.stream().map(TemplateLabelRelationDO::getLabelId).collect(Collectors.toList());
        List<LabelDO> labelDOS = labelMapper.listByIdList(labelIds);
        return TemplateConverter.INSTANCE.templateLabelDoListToDtoList(labelDOS);
    }
}

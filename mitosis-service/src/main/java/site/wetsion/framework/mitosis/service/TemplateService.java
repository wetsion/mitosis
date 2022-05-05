package site.wetsion.framework.mitosis.service;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import site.wetsion.framework.mitosis.common.Pagination;
import site.wetsion.framework.mitosis.core.render.wkhtml.ComplexWkHtmlToPdfRender;
import site.wetsion.framework.mitosis.datasource.mapper.LabelMapper;
import site.wetsion.framework.mitosis.datasource.mapper.TemplateLabelRelationMapper;
import site.wetsion.framework.mitosis.common.constant.ExceptionConstant;
import site.wetsion.framework.mitosis.core.converter.TemplateConverter;
import site.wetsion.framework.mitosis.datasource.mapper.TemplateMapper;
import site.wetsion.framework.mitosis.model.dto.LabelDTO;
import site.wetsion.framework.mitosis.model.dto.TemplateDTO;
import site.wetsion.framework.mitosis.model.dto.TemplateWrapperDTO;
import site.wetsion.framework.mitosis.model.entity.LabelDO;
import site.wetsion.framework.mitosis.model.entity.TemplateDO;
import site.wetsion.framework.mitosis.model.entity.TemplateLabelRelationDO;
import site.wetsion.framework.mitosis.model.param.TemplateLabelQueryParam;
import site.wetsion.framework.mitosis.model.param.TemplateQueryParam;
import site.wetsion.framework.mitosis.model.param.TemplateSaveParam;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private TemplateMapper templateMapper;
    @Resource
    private TemplateLabelRelationMapper templateLabelRelationMapper;

    public String renderTemplate(Long templateId) throws IOException {
        TemplateDO templateDO = templateMapper.getById(templateId);
        Objects.requireNonNull(templateDO, "template not exist");
        ComplexWkHtmlToPdfRender complexWkHtmlToPdfRender = new ComplexWkHtmlToPdfRender();
        return complexWkHtmlToPdfRender.renderFromHtmlString(templateDO.getContent(),
                templateDO.getTitle(), "--encoding utf8 --page-height 200 --page-width 100 --margin-left 1 " +
                        "--margin-top 3 --margin-top 3");
    }

    public TemplateWrapperDTO previewTemplate(Long templateId) {
        TemplateDO templateDO = templateMapper.getById(templateId);
        Objects.requireNonNull(templateDO, "template not exist");
        List<TemplateLabelRelationDO> templateLabelRelationDOS =
                templateLabelRelationMapper.listByTemplateId(templateId);
        List<Long> labelIds =
                templateLabelRelationDOS.stream().map(TemplateLabelRelationDO::getLabelId).collect(Collectors.toList());
        List<LabelDTO> labels = null;
        if (CollectionUtils.isNotEmpty(labelIds)) {
            List<LabelDO> labelDOS = labelMapper.listByIdList(labelIds);
            labels = TemplateConverter.INSTANCE.templateLabelDoListToDtoList(labelDOS);
        }
        return TemplateWrapperDTO.builder().relatedLabels(labels)
                .template(TemplateConverter.INSTANCE.templateDoToDto(templateDO))
                .build();
    }

    public Boolean createTemplate(TemplateSaveParam param) {
        TemplateDO create = new TemplateDO();
        create.setTitle(param.getTitle());
        templateMapper.create(create);
        return Boolean.TRUE;
    }

    public Boolean saveTemplate(TemplateSaveParam param) {
        TemplateDO save = new TemplateDO();
        save.setTitle(param.getTitle());
        save.setContent(param.getContent());
        save.setId(param.getTemplateId());
        templateMapper.update(save);
        if (CollectionUtils.isNotEmpty(param.getRelatedLabels())) {
            List<LabelDO> labelDOS = labelMapper.listByIdList(param.getRelatedLabels());
            Map<Long, String> labelCodeMap = labelDOS.stream().collect(Collectors.toMap(LabelDO::getId,
                    LabelDO::getCode));
            List<TemplateLabelRelationDO> relations = new ArrayList<>();
            for (LabelDO labelDO : labelDOS) {
                TemplateLabelRelationDO t = new TemplateLabelRelationDO();
                t.setCode(labelCodeMap.get(labelDO.getId()));
                t.setLabelId(labelDO.getId());
                t.setTemplateId(param.getTemplateId());
                relations.add(t);
            }
            if (CollectionUtils.isNotEmpty(relations)) {
                templateLabelRelationMapper.deleteRelatedLabels(param.getTemplateId());
                templateLabelRelationMapper.insertRelatedLabels(relations);
            }
        }
        return Boolean.TRUE;
    }

    public List<TemplateDTO> queryTemplates(TemplateQueryParam param, Pagination pagination) {
        List<TemplateDO> tds = templateMapper.getByTitle(param.getTitle(), pagination);
        return TemplateConverter.INSTANCE.templateDoListToDtoList(tds);
    }

    public Long countTemplates(TemplateQueryParam param) {
        return templateMapper.countByTitle(param.getTitle());
    }

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

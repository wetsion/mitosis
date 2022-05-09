package site.wetsion.framework.mitosis.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import site.wetsion.framework.mitosis.common.Pagination;
import site.wetsion.framework.mitosis.core.parser.jsoup.JsoupTemplateParser;
import site.wetsion.framework.mitosis.core.render.wkhtml.ComplexWkHtmlToPdfRender;
import site.wetsion.framework.mitosis.datasource.mapper.LabelMapper;
import site.wetsion.framework.mitosis.datasource.mapper.TemplateLabelRelationMapper;
import site.wetsion.framework.mitosis.common.constant.ExceptionConstant;
import site.wetsion.framework.mitosis.core.converter.TemplateConverter;
import site.wetsion.framework.mitosis.datasource.mapper.TemplateMapper;
import site.wetsion.framework.mitosis.model.dto.LabelDTO;
import site.wetsion.framework.mitosis.model.dto.RelatedLabelDTO;
import site.wetsion.framework.mitosis.model.dto.TemplateDTO;
import site.wetsion.framework.mitosis.model.dto.TemplateWrapperDTO;
import site.wetsion.framework.mitosis.model.entity.LabelDO;
import site.wetsion.framework.mitosis.model.entity.TemplateDO;
import site.wetsion.framework.mitosis.model.entity.TemplateLabelRelationDO;
import site.wetsion.framework.mitosis.model.param.TemplateLabelQueryParam;
import site.wetsion.framework.mitosis.model.param.TemplateQueryParam;
import site.wetsion.framework.mitosis.model.param.TemplateSaveParam;
import site.wetsion.framework.mitosis.model.param.TemplateSaveRelatedLabelParam;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
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
        return renderHtmlToPdf(templateDO.getContent(), templateDO.getTitle());
    }

    private String renderHtmlToPdf(String html, String fileName) throws IOException {
        ComplexWkHtmlToPdfRender complexWkHtmlToPdfRender = new ComplexWkHtmlToPdfRender();
        return complexWkHtmlToPdfRender.renderFromHtmlString(html,
                fileName, "--encoding utf8 --page-height 200 --page-width 100 --margin-left 1 " +
                        "--margin-top 3 --margin-top 3");
    }

    public String renderTemplateWithReplacement(JSONObject replacement) throws IOException {
        TemplateDO template = templateMapper.getById(replacement.getLong("templateId"));
        List<LabelDTO> relatedLabels = queryLabelsOnTemplate(template.getId());
        String result = template.getContent();
        if (CollectionUtils.isNotEmpty(relatedLabels)) {
            Map<String, Object> replace = Maps.newHashMap();
            for (LabelDTO labelDTO : relatedLabels) {
                String key = "span.customLabel[data-value=\"" + labelDTO.getId() + "\"]";
                String value = replacement.getString(labelDTO.getCode());
                replace.put(key, value);
            }
            JsoupTemplateParser parser = new JsoupTemplateParser();
            result = parser.parse(template.getContent(), replace);
        }
        return renderHtmlToPdf(result, template.getTitle());
    }

    /**
     * 编辑预览模版
     * @param templateId
     * @return
     */
    public TemplateWrapperDTO previewTemplate(Long templateId) {
        TemplateDO templateDO = templateMapper.getById(templateId);
        Objects.requireNonNull(templateDO, "template not exist");

        List<TemplateLabelRelationDO> templateLabelRelationDOS =
                templateLabelRelationMapper.listByTemplateId(templateId);

        List<Long> labelIds = new ArrayList<>(templateLabelRelationDOS.stream()
                .map(TemplateLabelRelationDO::getLabelId).collect(Collectors.toSet()));

        List<RelatedLabelDTO> labels = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(labelIds)) {
            List<LabelDO> labelDOS = labelMapper.listByIdList(labelIds);
            Map<Long, LabelDO> labelIdMap = labelDOS.stream().collect(Collectors.toMap(LabelDO::getId, Function.identity()));
            for (TemplateLabelRelationDO item : templateLabelRelationDOS) {
                RelatedLabelDTO relatedLabelDTO =
                        TemplateConverter.INSTANCE.templateLabelDoToRelatedLabelDto(labelIdMap.get(item.getLabelId()));
                relatedLabelDTO.setLocationPk(item.getLabelLocationPk());
                labels.add(relatedLabelDTO);
            }
        }
        return TemplateWrapperDTO.builder().relatedLabels(labels)
                .template(TemplateConverter.INSTANCE.templateDoToDto(templateDO))
                .build();
    }

    /**
     * 创建模版
     * @param param
     * @return
     */
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
        if (CollectionUtils.isNotEmpty(param.getRelatedLabelList())) {
            List<Long> relatedLabelIds = new ArrayList<>(param.getRelatedLabelList().stream()
                    .map(TemplateSaveRelatedLabelParam::getLabelId).collect(Collectors.toSet()));
            List<LabelDO> labelDOS = labelMapper.listByIdList(relatedLabelIds);

            Map<Long, LabelDO> labelIdMap = labelDOS.stream().collect(Collectors.toMap(LabelDO::getId,
                    Function.identity()));
            List<TemplateLabelRelationDO> relations = new ArrayList<>();
            for (TemplateSaveRelatedLabelParam item : param.getRelatedLabelList()) {
                TemplateLabelRelationDO t = new TemplateLabelRelationDO();
                t.setCode(labelIdMap.get(item.getLabelId()).getCode());
                t.setLabelId(item.getLabelId());
                t.setTemplateId(param.getTemplateId());
                t.setLabelLocationPk(item.getLocationPk());
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

    /**
     * 查询模版上已关联的标签
     * @param templateId 模版ID
     * @return
     */
    public List<LabelDTO> queryLabelsOnTemplate(Long templateId) {
        Objects.requireNonNull(templateId, ExceptionConstant.TEMPLATE_UNKNOWN);
        List<TemplateLabelRelationDO> relations = templateLabelRelationMapper.listByTemplateId(templateId);
        if (CollectionUtils.isEmpty(relations)) {
            return null;
        }
        List<Long> labelIds = new ArrayList<>(relations.stream().map(TemplateLabelRelationDO::getLabelId)
                .collect(Collectors.toSet()));
        List<LabelDO> labelDOS = labelMapper.listByIdList(labelIds);
        return TemplateConverter.INSTANCE.templateLabelDoListToDtoList(labelDOS);
    }
}

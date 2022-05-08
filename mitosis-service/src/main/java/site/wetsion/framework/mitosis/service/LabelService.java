package site.wetsion.framework.mitosis.service;

import org.springframework.stereotype.Service;
import site.wetsion.framework.mitosis.common.Pagination;
import site.wetsion.framework.mitosis.core.converter.TemplateConverter;
import site.wetsion.framework.mitosis.datasource.mapper.LabelMapper;
import site.wetsion.framework.mitosis.model.dto.LabelDTO;
import site.wetsion.framework.mitosis.model.entity.LabelDO;
import site.wetsion.framework.mitosis.model.param.LabelSaveParam;
import site.wetsion.framework.mitosis.model.param.TemplateLabelQueryParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wetsion
 * @date 2022-05-08 15:38
 */
@Service
public class LabelService {

    @Resource
    private LabelMapper labelMapper;

    public List<LabelDTO> queryLabels(TemplateLabelQueryParam param) {
        List<LabelDO> templateLabels = labelMapper.listByCondition(param);
        return TemplateConverter.INSTANCE.templateLabelDoListToDtoList(templateLabels);
    }

    public List<LabelDTO> pageLabel(TemplateLabelQueryParam param) {
        Pagination pagination = new Pagination();
        pagination.setPage(param.getPage());
        pagination.setSize(param.getPageSize());
        List<LabelDO> labelDOS = labelMapper.pageByCondition(param, pagination);
        return TemplateConverter.INSTANCE.templateLabelDoListToDtoList(labelDOS);
    }

    public Long countLabel(TemplateLabelQueryParam param) {
        return labelMapper.countByCondition(param);
    }

    public void createLabel(LabelSaveParam param) {
        LabelDO labelDO = new LabelDO();
        labelDO.setCode(param.getCode());
        labelDO.setName(param.getName());
        labelDO.setDataType(param.getDataType());
        labelMapper.create(labelDO);
    }

}

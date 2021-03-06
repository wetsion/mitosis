package site.wetsion.framework.mitosis.core.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import site.wetsion.framework.mitosis.model.dto.LabelDTO;
import site.wetsion.framework.mitosis.model.dto.RelatedLabelDTO;
import site.wetsion.framework.mitosis.model.dto.TemplateDTO;
import site.wetsion.framework.mitosis.model.entity.LabelDO;
import site.wetsion.framework.mitosis.model.entity.TemplateDO;

import java.util.List;

/**
 * @author wetsion
 * @date 4/17/22
 */
@Mapper(componentModel = "spring")
public interface TemplateConverter {

    TemplateConverter INSTANCE = Mappers.getMapper(TemplateConverter.class);

    LabelDTO templateLabelDoToDto(LabelDO labelDO);

    RelatedLabelDTO templateLabelDoToRelatedLabelDto(LabelDO labelDO);

    List<LabelDTO> templateLabelDoListToDtoList(List<LabelDO> labelDO);

    List<RelatedLabelDTO> templateLabelDoListToRelatedLabelDtoList(List<LabelDO> labelDO);

    TemplateDTO templateDoToDto(TemplateDO templateDO);

    List<TemplateDTO> templateDoListToDtoList(List<TemplateDO> templateDOList);
}

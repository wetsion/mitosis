package site.wetsion.framework.mitosis.core.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import site.wetsion.framework.mitosis.model.dto.TemplateLabelDTO;
import site.wetsion.framework.mitosis.model.entity.TemplateLabelDO;

import java.util.List;

/**
 * @author wetsion
 * @date 4/17/22
 */
@Mapper(componentModel = "spring")
public interface TemplateConverter {

    TemplateConverter INSTANCE = Mappers.getMapper(TemplateConverter.class);

    TemplateLabelDTO templateLabelDoToDto(TemplateLabelDO templateLabelDO);

    List<TemplateLabelDTO> templateLabelDoListToDtoList(List<TemplateLabelDO> templateLabelDO);
}

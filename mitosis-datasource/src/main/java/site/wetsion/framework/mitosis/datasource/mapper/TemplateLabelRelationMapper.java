package site.wetsion.framework.mitosis.datasource.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.wetsion.framework.mitosis.model.entity.TemplateLabelRelationDO;

import java.util.List;

/**
 * @author wetsion
 * @date 2022-04-18 15:17
 */
@Mapper
public interface TemplateLabelRelationMapper {

    List<TemplateLabelRelationDO> listByTemplateId(@Param("templateId") Long templateId);

    int deleteRelatedLabels(@Param("templateId") Long templateId);

    int insertRelatedLabels(@Param("relations") List<TemplateLabelRelationDO> relations);
}

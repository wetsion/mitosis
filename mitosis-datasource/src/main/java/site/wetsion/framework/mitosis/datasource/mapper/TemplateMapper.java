package site.wetsion.framework.mitosis.datasource.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.wetsion.framework.mitosis.common.Pagination;
import site.wetsion.framework.mitosis.model.entity.TemplateDO;

import java.util.List;

/**
 * @author wetsion
 * @date 4/18/22
 */
@Mapper
public interface TemplateMapper {

    TemplateDO getById(@Param("id") Long id);


    List<TemplateDO> getByTitle(@Param("title") String title, @Param("page") Pagination pagination);

    Long countByTitle(@Param("title") String title);

    int create(@Param("data") TemplateDO templateDO);

    int update(@Param("data") TemplateDO templateDO);
}

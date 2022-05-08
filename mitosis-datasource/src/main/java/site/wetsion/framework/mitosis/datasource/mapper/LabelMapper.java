package site.wetsion.framework.mitosis.datasource.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.wetsion.framework.mitosis.common.Pagination;
import site.wetsion.framework.mitosis.model.entity.LabelDO;
import site.wetsion.framework.mitosis.model.param.TemplateLabelQueryParam;

import java.util.List;

/**
 * @author wetsion
 * @date 4/17/22
 */
@Mapper
public interface LabelMapper {

    List<LabelDO> listByCondition(@Param("param") TemplateLabelQueryParam param);

    Long countByCondition(@Param("param") TemplateLabelQueryParam param);

    List<LabelDO> pageByCondition(@Param("param") TemplateLabelQueryParam param,  @Param("page") Pagination pagination);

    List<LabelDO> listByIdList(@Param("ids") List<Long> ids);

    int create(@Param("data") LabelDO labelDO);
}

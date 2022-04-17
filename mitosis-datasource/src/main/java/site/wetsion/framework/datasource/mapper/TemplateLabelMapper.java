package site.wetsion.framework.datasource.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.wetsion.framework.mitosis.model.entity.TemplateLabelDO;
import site.wetsion.framework.mitosis.model.param.TemplateLabelQueryParam;

import java.util.List;

/**
 * @author wetsion
 * @date 4/17/22
 */
@Mapper
public interface TemplateLabelMapper {

    List<TemplateLabelDO> listByCondition(@Param("param") TemplateLabelQueryParam param);
}

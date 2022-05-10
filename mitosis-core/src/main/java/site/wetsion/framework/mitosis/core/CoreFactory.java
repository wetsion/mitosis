package site.wetsion.framework.mitosis.core;

import org.springframework.stereotype.Component;
import site.wetsion.framework.mitosis.common.constant.LabelTypeEnum;
import site.wetsion.framework.mitosis.common.exception.UnsupportedException;
import site.wetsion.framework.mitosis.core.resolver.ILabelResolver;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wetsion
 * @date 2022-05-11 01:10
 */
@Component
public class CoreFactory {

    @Resource
    private List<ILabelResolver> resolvers;

    public ILabelResolver buildResolver(Integer code) {
        return resolvers.stream().filter(r -> r.type().equals(LabelTypeEnum.getByCode(code)))
                .findFirst().orElseThrow(() -> new UnsupportedException("unsupported label type"));
    }
}

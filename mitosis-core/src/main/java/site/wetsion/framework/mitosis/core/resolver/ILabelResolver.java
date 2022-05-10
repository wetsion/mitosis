package site.wetsion.framework.mitosis.core.resolver;

import site.wetsion.framework.mitosis.common.constant.LabelTypeEnum;
import site.wetsion.framework.mitosis.model.dto.label.ILabelData;

import javax.annotation.Nullable;

/**
 * @author wetsion
 * @date 2022-05-11 00:55
 */
public interface ILabelResolver<T extends ILabelData> {

    /**
     * 标签类型
     * @return
     */
    LabelTypeEnum type();

    /**
     * 标签值转换成实体对象
     *
     * @param data 输入的值
     * @return
     */
    T convertData(String data);

    /**
     * 标签值处理成html文本
     *
     * @param data 标签值
     * @return html文本
     */
    String resolveToHtml(@Nullable T data);

    String resolveToHtml(@Nullable String data);

}

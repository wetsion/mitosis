package site.wetsion.framework.mitosis.core.resolver.impl;

import org.springframework.stereotype.Component;
import site.wetsion.framework.mitosis.common.constant.LabelTypeEnum;
import site.wetsion.framework.mitosis.core.resolver.ILabelResolver;
import site.wetsion.framework.mitosis.model.dto.label.TextLabelData;
import site.wetsion.framework.mitosis.model.dto.label.TextLabelDef;

import javax.annotation.Nullable;

/**
 * 文本标签处理器
 *
 * @author wetsion
 * @date 2022-05-11 00:56
 */
@Component
public class TextLabelResolver implements ILabelResolver<TextLabelData, TextLabelDef> {
    @Override
    public LabelTypeEnum type() {
        return LabelTypeEnum.TEXT;
    }

    @Override
    public TextLabelData convertData(String data) {
        TextLabelData r = new TextLabelData();
        r.setValue(data);
        return r;
    }

    @Override
    public String resolveToHtml(TextLabelData data, TextLabelDef def) {
        return data.getValue();
    }

    @Override
    public String resolveToHtml(@Nullable String data, TextLabelDef def) {
        return resolveToHtml(convertData(data), def);
    }
}

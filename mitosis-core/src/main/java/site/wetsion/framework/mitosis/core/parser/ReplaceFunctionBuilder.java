package site.wetsion.framework.mitosis.core.parser;

import site.wetsion.framework.mitosis.common.constant.LabelTypeEnum;
import site.wetsion.framework.mitosis.common.exception.UnsupportedException;
import site.wetsion.framework.mitosis.core.parser.functions.TextLabelSelectionReplaceFunction;
import site.wetsion.framework.mitosis.model.dto.LabelDTO;

/**
 * @author wetsion
 * @date 2022-05-11 01:47
 */
public class ReplaceFunctionBuilder {

    public static TemplateSelectionReplaceFunction buildFunction(LabelDTO label, String targetValue) {
        Integer type = label.getDataType();
        if (LabelTypeEnum.TEXT.getCode().equals(type)) {
            return new TextLabelSelectionReplaceFunction(label, targetValue);
        } else {
            throw new UnsupportedException("unsupprted label type");
        }
    }
}

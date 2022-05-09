package site.wetsion.framework.mitosis.core.parser;

import java.util.List;
import java.util.Map;

/**
 * @author wetsion
 * @date 2022-05-10 00:08
 */
public interface TemplateParser {

    /**
     * 解析html
     *
     * @param origin 原html片段
     * @param selectorReplacement 选择器要替换的目标值
     * @return 处理后的html片段
     */
    String parse(String origin, Map<String, Object> selectorReplacement);

    String parse(String origin, List<TemplateSelectionReplaceFunction> replaceFunctions);
}

package site.wetsion.framework.mitosis.core.parser.functions;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import site.wetsion.framework.mitosis.core.ComponentTaker;
import site.wetsion.framework.mitosis.core.parser.TemplateSelectionReplaceFunction;
import site.wetsion.framework.mitosis.core.resolver.impl.TextLabelResolver;
import site.wetsion.framework.mitosis.model.dto.LabelDTO;
import site.wetsion.framework.mitosis.model.dto.label.TextLabelDef;

import java.util.Objects;

/**
 * 针对文本类型标签的 html选择器 选择替换内容
 *
 * @author wetsion
 * @date 2022-05-11 01:32
 */
public class TextLabelSelectionReplaceFunction implements TemplateSelectionReplaceFunction {

    private LabelDTO label;

    private String targetValue;

    public TextLabelSelectionReplaceFunction(LabelDTO label, String targetValue) {
        this.label = label;
        this.targetValue = targetValue;
    }

    @Override
    public void replace(Document document) {
        Element element = document.select(buildSelection()).first();
        if (Objects.isNull(element)) {
            return;
        }
        element.removeClass("customLabel");
        element.removeClass("highlight");
        TextLabelResolver resolver = ComponentTaker.getBean(TextLabelResolver.class);
        element.html(resolver.resolveToHtml(targetValue, buildTextLabelDef()));
    }

    private String buildSelection() {
        return "span.customLabel[data-value=\"" + label.getId() + "\"]";
    }

    private TextLabelDef buildTextLabelDef() {
        if (StringUtils.isBlank(label.getConfig())) {
            return new TextLabelDef();
        }
        return JSON.parseObject(label.getConfig(), TextLabelDef.class);
    }

}

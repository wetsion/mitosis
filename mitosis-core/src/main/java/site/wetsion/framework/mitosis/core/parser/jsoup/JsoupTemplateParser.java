package site.wetsion.framework.mitosis.core.parser.jsoup;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import site.wetsion.framework.mitosis.core.parser.TemplateParser;
import site.wetsion.framework.mitosis.core.parser.TemplateSelectionReplaceFunction;

import java.util.List;
import java.util.Map;

/**
 * @author wetsion
 * @date 2022-05-10 00:09
 */
public class JsoupTemplateParser implements TemplateParser {
    @Override
    public String parse(String origin, Map<String, Object> selectorReplacement) {
        if (MapUtils.isEmpty(selectorReplacement)) {
            return origin;
        }
        String wrapperOrigin = "<div>" + origin + "</div>";
        Document document = Jsoup.parseBodyFragment(wrapperOrigin);
        for (Map.Entry<String, Object> item : selectorReplacement.entrySet()) {
            String selector = item.getKey();
            Elements elements = document.select(selector);
            Element element = elements.first();
            element.removeClass("customLabel");
            element.removeClass("highlight");
            element.html(item.getValue().toString());
        }
        return document.html();
    }

    @Override
    public String parse(String origin, List<TemplateSelectionReplaceFunction> replaceFunctions) {
        if (CollectionUtils.isEmpty(replaceFunctions)) {
            return origin;
        }
        String wrapperOrigin = "<div>" + origin + "</div>";
        Document document = Jsoup.parseBodyFragment(wrapperOrigin);
        for (TemplateSelectionReplaceFunction function : replaceFunctions) {
            function.replace(document);
        }
        return document.html();
    }
}

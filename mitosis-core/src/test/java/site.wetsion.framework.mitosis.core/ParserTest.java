package site.wetsion.framework.mitosis.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import org.jsoup.nodes.Element;
import org.junit.Test;
import site.wetsion.framework.mitosis.core.parser.TemplateSelectionReplaceFunction;
import site.wetsion.framework.mitosis.core.parser.jsoup.JsoupTemplateParser;

import java.util.List;
import java.util.Map;

/**
 * @author wetsion
 * @date 2022-05-10 00:38
 */
public class ParserTest {

    @Test
    public void testParse() {
        String html = "<div><span data-value=\"1\" class=\"customLabel highlight\">#label#</span></div>";
        JsoupTemplateParser parser = new JsoupTemplateParser();
        Map<String, Object> replace = Maps.newHashMap();
        replace.put("span[data-value=\"1\"]", "131241244");
        String result = parser.parse(html, replace);
        System.out.println(result);
    }

    @Test
    public void testParse2() {
        String html = "<div><span data-value=\"1\" class=\"customLabel " +
                "highlight\">#label#</span><span>2313</span><span data-value=\"1\">23133344443</span></div>";
        JsoupTemplateParser parser = new JsoupTemplateParser();
        List<TemplateSelectionReplaceFunction> functions = ImmutableList.of(document -> {
            Element element = document.select("span.customLabel[data-value=\"1\"]").first();
            element.removeClass("customLabel");
            element.removeClass("highlight");
            element.html("12312324");
        });
        String result = parser.parse(html, functions);
        System.out.println(result);
    }
}

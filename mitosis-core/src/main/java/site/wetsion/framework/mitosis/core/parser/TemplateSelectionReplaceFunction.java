package site.wetsion.framework.mitosis.core.parser;


import org.jsoup.nodes.Document;

/**
 * @author wetsion
 * @date 2022-05-10 00:48
 */
@FunctionalInterface
public interface TemplateSelectionReplaceFunction {

    void replace(Document document);
}

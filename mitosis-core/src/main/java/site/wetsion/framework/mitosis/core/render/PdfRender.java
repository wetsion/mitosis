package site.wetsion.framework.mitosis.core.render;

/**
 * @author wetsion
 * @date 2022-05-04 21:53
 */
public interface PdfRender {

    String renderFromHtmlString(String html, String pdfFileName) throws Exception;
}

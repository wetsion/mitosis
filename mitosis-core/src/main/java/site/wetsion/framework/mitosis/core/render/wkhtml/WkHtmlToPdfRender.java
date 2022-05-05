package site.wetsion.framework.mitosis.core.render.wkhtml;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import site.wetsion.framework.mitosis.common.util.ShellUtil;
import site.wetsion.framework.mitosis.core.render.PdfRender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wetsion
 * @date 2022-05-04 21:54
 */
@Slf4j
public class WkHtmlToPdfRender implements PdfRender {

    protected static String commad = "/usr/local/bin/wkhtmltopdf";

    protected String outBaseDir = "/tmp/wkhtmltopdf/";

    protected Map<String, String> commadArgs = new HashMap<>();

    protected String getTmpFile(String pdfFileName) throws IOException {
        String filePath = outBaseDir + LocalDate.now().toString() + "/" + pdfFileName + ".pdf";
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            FileUtils.forceMkdir(file.getParentFile());
        }
        return filePath;
    }

    public WkHtmlToPdfRender addCommandArg(String arg, String value) {
        commadArgs.put(arg, value);
        return this;
    }

    public static String getSystemCommand() {
        if(SystemUtils.IS_OS_WINDOWS) {
            return "wkhtmltopdf";
        } else {
            return commad;
        }
    }

    protected String getCommand(String htmlFile, String pdfFile) {
        StringBuilder sb = new StringBuilder();
        sb.append(getSystemCommand()).append(" ");
        //在页脚中心放置页码
        //sb.append("--footer-center [page]/[topage] ");
        //设置页面左边距
        sb.append("--margin-left ").append("5mm ");
        //设置页面上边距
        sb.append("--margin-top ").append("5mm ");
        //sb.append("--cache-dir ").append(PathUtil.getRootPath() + "static/").append(" ");
        //sb.append("--load-error-handling ");
        for (Map.Entry<String, String> entry : commadArgs.entrySet()) {
            sb.append(entry.getKey()).append(" ").append(entry.getValue()).append(" ");
        }
        sb.append(htmlFile).append(" ");
        sb.append(pdfFile);
        return sb.toString();
    }

    public String renderFromHtmlFile(String htmlFile, String outFileName) throws IOException {
        String outFile = this.getTmpFile(outFileName);
        String commad = this.getCommand(htmlFile, outFile);
        int result = ShellUtil.exec(commad);
        if (0 == result) {
            return outFile;
        }
        return null;
    }

    @Override
    public String renderFromHtmlString(String html, String pdfFileName) throws Exception {
        // 写入临时文件
        String tmpHtmlFile = outBaseDir + LocalDate.now().toString() + "/" + pdfFileName + ".html";
        File file = new File(tmpHtmlFile);
        if (!file.getParentFile().exists()) {
            FileUtils.forceMkdir(file.getParentFile());
        }
        FileWriter writer = new FileWriter(tmpHtmlFile);
        writer.append(html);
        writer.close();

        return this.renderFromHtmlFile(tmpHtmlFile, pdfFileName);
    }
}

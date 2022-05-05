package site.wetsion.framework.mitosis.core.render.wkhtml;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import site.wetsion.framework.mitosis.common.util.ShellUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 * @author wetsion
 * @date 2022-05-04 22:02
 */
@Slf4j
public class ComplexWkHtmlToPdfRender extends WkHtmlToPdfRender {

    private static final String command = getSystemCommand();

    private String getCommand(String htmlFile, String pdfFile, String extra) {
        return command + " " +
                extra + " " +
                htmlFile + " " + pdfFile;
    }

    private String renderFromHtmlFile(String htmlFile, String pdfFileName, String append) throws IOException {
        String pdfFile = getTmpFile(pdfFileName);
        String command = getCommand(htmlFile, pdfFile, append);
        int result = ShellUtil.exec(command);
        if (0 == result) {
            return pdfFile;
        }
        return null;
    }

    public String renderFromHtmlString(String html, String pdfFileName, String append) throws IOException {
        // 写入临时文件
        String tmpHtmlFile = outBaseDir + LocalDate.now().toString() + "/" + pdfFileName + ".html";
        File file = new File(tmpHtmlFile);
        if (!file.getParentFile().exists()) {
            FileUtils.forceMkdir(file.getParentFile());
        }
        FileWriter writer = new FileWriter(tmpHtmlFile);
        writer.append(html);
        writer.close();

        return this.renderFromHtmlFile(tmpHtmlFile, pdfFileName, append);
    }
}

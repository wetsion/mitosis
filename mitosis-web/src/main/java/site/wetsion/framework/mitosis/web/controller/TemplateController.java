package site.wetsion.framework.mitosis.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import site.wetsion.framework.mitosis.common.PageR;
import site.wetsion.framework.mitosis.common.Pagination;
import site.wetsion.framework.mitosis.common.R;
import site.wetsion.framework.mitosis.common.util.FileUtil;
import site.wetsion.framework.mitosis.model.dto.TemplateDTO;
import site.wetsion.framework.mitosis.model.dto.TemplateWrapperDTO;
import site.wetsion.framework.mitosis.model.param.TemplateQueryParam;
import site.wetsion.framework.mitosis.model.param.TemplateSaveParam;
import site.wetsion.framework.mitosis.service.TemplateService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author wetsion
 * @date 2022-05-04 14:33
 */
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/template")
public class TemplateController {

    @Resource
    private TemplateService templateService;

    @GetMapping("/page")
    public R<PageR<TemplateDTO>> pageQuery(@RequestParam("title") String title,
                                           @RequestParam(value = "page", defaultValue = "1") Long page,
                                           @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize) {
        Pagination pagination = new Pagination();
        pagination.setPage(page);
        pagination.setSize(pageSize);
        TemplateQueryParam param = new TemplateQueryParam();
        param.setTitle(title);
        return R.success(PageR.<TemplateDTO>builder().data(templateService.queryTemplates(param, pagination))
                .page(page)
                .pageSize(pageSize)
                .total(templateService.countTemplates(param))
                .build());
    }


    @PostMapping("/create")
    public R createTemplate(@RequestBody TemplateSaveParam param) {
        return R.success(templateService.createTemplate(param));
    }

    @PostMapping("/save")
    public R saveTemplate(@RequestBody TemplateSaveParam param) {
        return R.success(templateService.saveTemplate(param));
    }

    @GetMapping("/preview")
    public R<TemplateWrapperDTO> preview(@RequestParam("templateId") Long templateId) {
        return R.success(templateService.previewTemplate(templateId));
    }

    @GetMapping(value = "/renderPdf", consumes = MediaType.ALL_VALUE)
    public void renderPdf(@RequestParam("templateId") Long templateId, HttpServletResponse response) {
        String fileStr = null;
        try {
            fileStr = templateService.renderTemplate(templateId);
        } catch (IOException e) {
            return;
        }
        File file = new File(fileStr);
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("filename", file.getName());
        response.setHeader("Access-Control-Expose-Headers", "filename");
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            log.error("renderPdf fail",e);
        }
    }
}

package site.wetsion.framework.mitosis.web.controller;

import org.springframework.web.bind.annotation.*;
import site.wetsion.framework.mitosis.common.PageR;
import site.wetsion.framework.mitosis.common.Pagination;
import site.wetsion.framework.mitosis.common.R;
import site.wetsion.framework.mitosis.model.dto.LabelDTO;
import site.wetsion.framework.mitosis.model.param.LabelSaveParam;
import site.wetsion.framework.mitosis.model.param.TemplateLabelQueryParam;
import site.wetsion.framework.mitosis.service.LabelService;
import site.wetsion.framework.mitosis.service.TemplateService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author wetsion
 * @date 4/17/22
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/label")
public class LabelController {

    @Resource
    private TemplateService templateService;
    @Resource
    private LabelService labelService;

    @PostMapping("/page")
    public R<PageR<LabelDTO>> pageLabel(@RequestBody TemplateLabelQueryParam param) {
        return R.success(PageR.<LabelDTO>builder()
                .total(labelService.countLabel(param))
                .page(param.getPage())
                .pageSize(param.getPageSize())
                .data(labelService.pageLabel(param))
                .build());
    }

    @PostMapping("/create")
    public R createLabel(@RequestBody LabelSaveParam param) {
        Objects.requireNonNull(param.getCode(), "code is empty");
        Objects.requireNonNull(param.getName(), "name is empty");
        Objects.requireNonNull(param.getDataType(), "dataType is empty");
        labelService.createLabel(param);
        return R.success(Boolean.TRUE);
    }

    @PostMapping("/query")
    public R<List<LabelDTO>> query(@RequestBody TemplateLabelQueryParam param) {
        return R.success(labelService.queryLabels(param));
    }

    @GetMapping("/templateRelated")
    public R<List<LabelDTO>> templateRelated(@RequestParam("templateId") Long templateId) {
        return R.success(templateService.queryLabelsOnTemplate(templateId));
    }
}

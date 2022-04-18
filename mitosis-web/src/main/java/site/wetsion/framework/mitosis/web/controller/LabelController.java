package site.wetsion.framework.mitosis.web.controller;

import org.springframework.web.bind.annotation.*;
import site.wetsion.framework.mitosis.common.R;
import site.wetsion.framework.mitosis.model.dto.LabelDTO;
import site.wetsion.framework.mitosis.model.param.TemplateLabelQueryParam;
import site.wetsion.framework.mitosis.service.TemplateService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wetsion
 * @date 4/17/22
 */
@RestController
@RequestMapping("/api/label")
public class LabelController {

    @Resource
    private TemplateService templateService;

    @PostMapping("/query")
    public R<List<LabelDTO>> query(@RequestBody TemplateLabelQueryParam param) {
        return R.success(templateService.queryLabels(param));
    }

    @GetMapping("/templateRelated")
    public R<List<LabelDTO>> templateRelated(@RequestParam("templateId") Long templateId) {
        return R.success(templateService.queryLabelsOnTemplat(templateId));
    }
}

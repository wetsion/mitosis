package site.wetsion.framework.mitosis.model.dto.label;

import lombok.Data;

import java.util.List;

/**
 * 表格标签定义
 *
 * @author wetsion
 * @date 2022-05-13 00:32
 */
@Data
public class TableLabelDef implements ILabelDef {

    /**
     * 表头定义
     */
    private List<String> head;

    /**
     * 行数
     */
    private Integer row;

    /**
     * 列数
     */
    private Integer col;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 高度
     */
    private Integer height;

    private int border = 1;

    /**
     * 样式
     */
    private String style;
}

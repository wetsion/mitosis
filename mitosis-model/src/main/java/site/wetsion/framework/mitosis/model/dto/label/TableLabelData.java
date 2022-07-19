package site.wetsion.framework.mitosis.model.dto.label;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 表格标签数据实体
 *
 * @author wetsion
 * @date 2022-05-13 00:33
 */
@Data
public class TableLabelData implements ILabelData {

    private static final long serialVersionUID = 7355591953533957846L;

    private List<TableRowData> value;


    @Data
    public static class TableRowData implements Serializable {

        private static final long serialVersionUID = 272990866238520605L;

        private List<TableCellData> cells;
    }

    /**
     * 表格单元格数据
     */
    @Data
    public static class TableCellData implements Serializable {

        private static final long serialVersionUID = -6137700403405395374L;
        /**
         * 跨列
         */
        private Integer colspan;
        /**
         * 跨行
         */
        private Integer rowspan;
        /**
         * 单元格的值
         */
        private String value;

    }
}

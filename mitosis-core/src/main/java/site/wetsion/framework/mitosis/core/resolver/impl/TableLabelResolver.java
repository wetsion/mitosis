package site.wetsion.framework.mitosis.core.resolver.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import site.wetsion.framework.mitosis.common.constant.ExceptionConstant;
import site.wetsion.framework.mitosis.common.constant.HtmlRenderConstant;
import site.wetsion.framework.mitosis.common.constant.LabelTypeEnum;
import site.wetsion.framework.mitosis.common.exception.InvalidDataException;
import site.wetsion.framework.mitosis.core.resolver.ILabelResolver;
import site.wetsion.framework.mitosis.model.dto.label.TableLabelData;
import site.wetsion.framework.mitosis.model.dto.label.TableLabelDef;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

/**
 * @author wetsion
 * @date 2022-05-13 00:38
 */
@Component
public class TableLabelResolver implements ILabelResolver<TableLabelData, TableLabelDef> {


    @Override
    public LabelTypeEnum type() {
        return LabelTypeEnum.TABLE;
    }

    @Override
    public TableLabelData convertData(String data) {
        return JSON.parseObject(data, TableLabelData.class);
    }

    @Override
    public String resolveToHtml(@Nullable TableLabelData data, TableLabelDef def) {
        validData(data);
        StringBuffer tableSb = new StringBuffer();
        tableSb.append(buildTableTag(def));
        tableSb.append(buildTableBody(data));
        tableSb.append(HtmlRenderConstant.TABLE_TAG_SUFFIX);
        return tableSb.toString();
    }

    @Override
    public String resolveToHtml(@Nullable String data, TableLabelDef def) {
        if (StringUtils.isBlank(data)) {
            throw new InvalidDataException(ExceptionConstant.INVALID_LABEL_DATA);
        }
        TableLabelData tableLabelData = convertData(data);
        return resolveToHtml(tableLabelData, def);
    }

    private void validData(TableLabelData tableLabelData) {
        Objects.requireNonNull(tableLabelData, ExceptionConstant.INVALID_LABEL_DATA);
    }

    private String buildTableBody(TableLabelData data) {
        List<TableLabelData.TableRowData> rows = data.getValue();
        StringBuffer body = new StringBuffer();
        for (TableLabelData.TableRowData row : rows) {
            StringBuffer rowSb = new StringBuffer();
            rowSb.append("<tr>");
            for (TableLabelData.TableCellData cellData : row.getCells()) {
                String colspan = StringUtils.EMPTY;
                String rowspan = StringUtils.EMPTY;
                if (Objects.nonNull(cellData.getColspan())) {
                    colspan = String.format("colspan=\"%d\"", cellData.getColspan());
                }
                if (Objects.nonNull(cellData.getRowspan())) {
                    rowspan = String.format("rowspan=\"%d\"", cellData.getRowspan());
                }
                rowSb.append(String.format(HtmlRenderConstant.TABLE_COL_TAG_PREFIX, rowspan, colspan));
                rowSb.append(cellData.getValue());
                rowSb.append(HtmlRenderConstant.TABLE_COL_TAG_SUFFIX);
            }
            rowSb.append("</tr>");
        }
        return body.toString();
    }


    private String buildTableTag(TableLabelDef def) {
        StringBuffer stringBuffer = new StringBuffer();
        String border = "border = \"" + def.getBorder() + "\"";
        stringBuffer.append(border);
        if (StringUtils.isNotBlank(def.getStyle())) {
            String style = "style = \"" + def.getStyle() + "\"";
            stringBuffer.append(HtmlRenderConstant.SPACE);
            stringBuffer.append(style);
        }
        return String.format(HtmlRenderConstant.TABLE_TAG_PREFIX, stringBuffer.toString());
    }
}

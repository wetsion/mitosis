package site.wetsion.framework.mitosis.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.wetsion.framework.mitosis.common.exception.UnsupportedException;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 标签类型枚举
 *
 * @author wetsion
 * @date 2022-05-11 00:45
 */
@Getter
@AllArgsConstructor
public enum LabelTypeEnum {

    /**
     * 文本
     */
    TEXT(0, "文本"),

    /**
     * 图片
     */
    PICTURE(1, "图片"),

    /**
     * 表格
     */
    TABLE(2, "表格"),

    ;

    private final Integer code;

    private final String desc;

    private final static Map<Integer, LabelTypeEnum> MAP;

    static {
        MAP = Arrays.stream(values()).collect(Collectors.toMap(LabelTypeEnum::getCode, Function.identity()));
    }

    public static LabelTypeEnum getByCode(Integer code) {
        return Optional.ofNullable(MAP.getOrDefault(code, null)).orElseThrow(() -> new UnsupportedException(
                "unsupported label type"));
    }
}

package tech.xs.doc.constant;

/**
 * 约束类型
 *
 * @author 沈家文
 * @date 2021-31-17 9:31
 */
public class ConstraintTypeConstant {


    public static final int NOT_NULL = 1;
    public static final String NOT_NULL_MSG = "不能为null";

    public static final int MIN = 2;
    public static final String MIN_MSG = "最小值为";

    public static final int MAX = 3;
    public static final String MAX_MSG = "最大值为";

    public static final int LENGTH_MIN = 4;
    public static final String LENGTH_MIN_MSG = "最小长度为";

    public static final int LENGTH_MAX = 5;
    public static final String LENGTH_MAX_MSG = "最大长度为";

    public static final int NOT_BLANK = 6;
    public static final String NOT_BLANK_MSG = "不能为null且不能为空字符串";

    public static final int NOT_EMPTY = 7;
    public static final String NOT_EMPTY_MSG = "不能为null且不能为空列表";

    public static final int SIZE_MIN = 8;
    public static final String SIZE_MIN_MSG = "列表长度必须大于等于";

    public static final int SIZE_MAX = 9;
    public static final String SIZE_MAX_MSG = "列表长度必须小于等于";

    public static final int CUSTOM = 10;
}

package tech.xs.framework.constant;

/**
 * 分页查询参数检测常量
 *
 * @author 沈家文
 * @date 2020/11/5 11:38
 */
public class ParamCheckConstant {

    /**
     * 分页查询参数,页下标的最小值
     */
    public static final int PAGE_MIN_INDEX = 1;

    /**
     * 分页查询每页个数,的最小值
     */
    public static final int PAGE_MIN_SIZE = 1;

    /**
     * 分页查询每页个数,的最大值
     */
    public static final int PAGE_MAX_SIZE = 1000;

    /**
     * 列表最大长度
     * 常用于根据ID列表删除时使用
     */
    public static final int LIST_MAX_SIZE = 100;

    /**
     * 手机号长度
     */
    public static final int PHONE_LENGTH = 11;

    /**
     * 验证码长度
     */
    public static final int VERIFICATION_CODE_LENGTH = 6;

    /**
     * 验证码最大输入次数
     */
    public static final int VERIFICATION_CODE_MAX_INPUT_NUM = 6;


}

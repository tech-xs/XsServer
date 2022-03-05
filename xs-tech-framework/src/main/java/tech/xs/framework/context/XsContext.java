package tech.xs.framework.context;

/**
 * 上下文
 *
 * @author 沈家文
 * @date 2020/12/3 14:45
 */
public class XsContext {

    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<Long> COMPANY_ID = new ThreadLocal<>();
    private static final ThreadLocal<Object> AUTH = new ThreadLocal<>();

    /**
     * 设置当前组织id
     *
     * @param companyId 公司ID
     */
    public static void setCompanyId(Long companyId) {
        COMPANY_ID.set(companyId);
    }

    /**
     * 获取当前组织id
     *
     * @return 公司ID
     */
    public static Long getCompanyId() {
        return COMPANY_ID.get();
    }

    /**
     * 设置当前用户ID
     *
     * @param userId 用户ID
     */
    public static void setUserId(Long userId) {
        USER_ID.set(userId);
    }

    /**
     * 获取当前用户ID
     *
     * @return 用户ID
     */
    public static Long getUserId() {
        return USER_ID.get();
    }

    /**
     * 设置当前授权
     *
     * @param auth 授权信息
     */
    public static void setAuth(Object auth) {
        AUTH.set(auth);
    }

    /**
     * 获取当前授权
     *
     * @return 授权信息
     */
    public static Object getAuth() {
        return AUTH.get();
    }

    /**
     * 移除上下文中的值
     */
    public static void remove() {
        USER_ID.remove();
        AUTH.remove();
        COMPANY_ID.remove();
    }

}

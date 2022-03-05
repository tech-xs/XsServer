package tech.xs.auth.context;

import tech.xs.auth.domain.entity.AuthToken;
import tech.xs.framework.context.XsContext;

/**
 * 授权上下文
 *
 * @author 沈家文
 * @date 2020/12/3 13:50
 */
public class AuthContext {

    public static void setAuthToken(AuthToken authToken, Long companyId) {
        XsContext.setAuth(authToken);
        if (authToken != null) {
            XsContext.setUserId(authToken.getUserId());
            XsContext.setCompanyId(companyId);
        } else {
            XsContext.setUserId(null);
            XsContext.setCompanyId(null);
        }
    }

    public static AuthToken getAuthToken() {
        return (AuthToken) XsContext.getAuth();
    }

}

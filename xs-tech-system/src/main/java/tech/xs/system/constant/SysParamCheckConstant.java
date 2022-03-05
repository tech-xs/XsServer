package tech.xs.system.constant;

/**
 * 参数常量
 *
 * @author 沈家文
 * @date 2020/11/3 13:35
 */
public class SysParamCheckConstant {

    public static class SysPage {
        public static final int NAME_MIN_LENGTH = 1;
        public static final int NAME_MAX_LENGTH = 128;
        public static final int URL_MIN_LENGTH = 1;
        public static final int URL_MAX_LENGTH = 1024;
        public static final int REMARK_MAX_LENGTH = 1024;
    }

    public static class SysPagePermission {
        public static final int NAME_MIN_LENGTH = 1;
        public static final int NAME_MAX_LENGTH = 128;
        public static final int CODE_MIN_LENGTH = 1;
        public static final int CODE_MAX_LENGTH = 128;
        public static final int REMARK_MAX_LENGTH = 1024;
    }

    public static class SysPageApi {
        public static final int REMARK_MAX_LENGTH = 1024;
    }

    public static class SysApi {
        public static final int NAME_MAX_LENGTH = 128;
        public static final int URI_MAX_LENGTH = 2048;
        public static final int REMARK_MAX_LENGTH = 1024;
    }

    public static class SysDict {
        public static final int CODE_MIN_LENGTH = 1;
        public static final int CODE_MAX_LENGTH = 128;
        public static final int NAME_MAX_LENGTH = 128;
        public static final int NAME_MIN_LENGTH = 1;
        public static final int REMARK_MAX_LENGTH = 1024;
    }

    public static class SysDictValue {
        public static final int NAME_MIN_LENGTH = 1;
        public static final int NAME_MAX_LENGTH = 128;
        public static final int VALUE_MIN_LENGTH = 1;
        public static final int VALUE_MAX_LENGTH = 128;
        public static final int REMARK_MAX_LENGTH = 1024;
        public static final int SORT_MIN = 0;
    }

    public static class SysRole {
        public static final int CODE_MIN_LENGTH = 1;
        public static final int CODE_MAX_LENGTH = 128;
        public static final int NAME_MIN_LENGTH = 1;
        public static final int NAME_MAX_LENGTH = 128;
        public static final int REMARK_MAX_LENGTH = 1024;
    }

    public static class SysUser {
        public static final int USER_NAME_MIN_LENGTH = 1;
        public static final int USER_NAME_MAX_LENGTH = 32;
        public static final int NICK_NAME_MAX_LENGTH = 32;
        public static final int PHONE_MAX_LENGTH = 32;
        public static final int EMAIL_MAX_LENGTH = 128;
        public static final int PASSWORD_MAX_LENGTH = 1024;
    }

    public static class SysMenu {
        public static final int NAME_MAX_LENGTH = 128;
        public static final int CODE_MAX_LENGTH = 128;
        public static final int ICON_MAX_LENGTH = 128;
        public static final int SORT_MIN = 0;
        public static final int SORT_MAX = 1000000;
    }

    public static class SysRoleMenu {
        public static final int SELECT_TYPE_MIN = 1;
        public static final int SELECT_TYPE_MAN = 3;
    }

    public static class SysRolePagePermission {
        public static final int SELECT_TYPE_MIN = 1;
        public static final int SELECT_TYPE_MAN = 3;
    }

    public static class SysCompany {
        public static final int SHORT_NAME_MAX_LENGTH = 128;
        public static final int FULL_NAME_MAX_LENGTH = 128;
    }

    public static class SysConfig {
        public static final int MODULE_MAX_LENGTH = 128;
        public static final int KEY_MAX_LENGTH = 128;
        public static final int SUB_KEY_MAX_LENGTH = 128;
        public static final int VALUE_MAX_LENGTH = 1024;
        public static final int SUB_VALUE_MAX_LENGTH = 1024;
    }


}

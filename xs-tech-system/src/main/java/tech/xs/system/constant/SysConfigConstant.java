package tech.xs.system.constant;

/**
 * 配置常量
 *
 * @author 沈家文
 * @date 2021/9/8 19:50
 */
public class SysConfigConstant {

    public static class Type {

        /**
         * 公开网页初始化配置
         */
        public static final String PUBLIC_WEB_INIT_CONFIG = "public-web-init-config";

    }

    /**
     * 模块
     */
    public static class Module {

        /**
         * Sys模块
         */
        public static final String SYS = "sys";
        /**
         * 授权模块
         */
        public static final String AUTH = "auth";
        /**
         * 存储模块
         */
        public static final String STORAGE = "storage";
        /**
         * 消息通知模块
         */
        public static final String NOTICE = "notice";
    }

    /**
     * 配置名称
     */
    public static class ConfigName {

        /**
         * 网站名称
         */
        public static final String WEB_NAME = "web:name";

        /**
         * 网站icon
         */
        public static final String WEB_ICON = "web:icon";

        /**
         * 账号注册功能
         */
        public static final String ACCOUNT_REGISTER_ENABLE = "account:register";

        /**
         * 阿里云授权
         */
        public static final String ALI_YUN_ACCESS_KEY = "ali-yun:access-key";
        public static final String ALI_YUN_SECRET_KEY = "ali-yun:secret-key";

        /**
         * 存储类型
         */
        public static final String STORAGE_TYPE = "storage:type";
        /**
         * minio存储
         */
        public static final String STORAGE_MINIO = "storage:minio";
        /**
         * 外部资源访问根路径
         */
        public static final String STORAGE_WEB_ROOT = "storage:web:root";
        /**
         * 邮件通知,邮件服务器类型
         */
        public static final String NOTICE_MAIL_TYPE = "notice:mail:type";
        /**
         * 阿里云企业邮箱
         */
        public static final String NOTICE_MAIL_ALIYUN_ENTERPRISE = "notice:mail:aliyun:enterprise";
        /**
         * QQ邮箱
         */
        public static final String NOTICE_MAIL_QQ = "notice:mail:qq";
        /**
         * 网易
         */
        public static final String NOTICE_MAIL_NETEASE = "notice:mail:netease";
    }

    /**
     * 配置键
     */
    public static class ConfigKey {
        /**
         * 网站名称
         */
        public static final String WEB_NAME = "web:name";
        /**
         * 网站icon
         */
        public static final String WEB_ICON = "web:icon";
        /**
         * 是否开启通过邮箱注册账号
         */
        public static final String ACCOUNT_REGISTER_EMAIL_ENABLE = "account:register:email:enable";
        /**
         * 是否开启通过手机号注册账号
         */
        public static final String ACCOUNT_REGISTER_PHONE_ENABLE = "account:register:phone:enable";

        /**
         * 阿里云授权
         */
        public static final String ALI_YUN_ACCESS_KEY = "ali-yun:access-key";
        public static final String ALI_YUN_SECRET_KEY = "ali-yun:secret-key";

        /**
         * 存储类型
         */
        public static final String STORAGE_TYPE = "storage:type";

        /**
         * minio accessKey
         */
        public static final String STORAGE_MINIO_ACCESS_KEY = "storage.minio.accessKey";
        /**
         * minio secretKey
         */
        public static final String STORAGE_MINIO_SECRET_KEY = "storage.minio.secretKey";
        /**
         * minio endpoint
         */
        public static final String STORAGE_MINIO_ENDPOINT = "storage.minio.endpoint";
        /**
         * minio bucketName
         */
        public static final String STORAGE_MINIO_BUCKET_NAME = "storage.minio.bucketName";
        /**
         * 外部资源访问根路径
         */
        public static final String STORAGE_WEB_ROOT = "storage:web:root";
        /**
         * 邮件通知,邮件服务器类型
         */
        public static final String NOTICE_MAIL_TYPE = "notice:mail:type";
        /**
         * 阿里云企业邮箱
         */
        public static final String NOTICE_MAIL_ALIYUN_ENTERPRISE_HOST = "notice:mail:aliyun:enterprise:host";
        public static final String NOTICE_MAIL_ALIYUN_ENTERPRISE_USER_NAME = "notice:mail:aliyun:enterprise:userName";
        public static final String NOTICE_MAIL_ALIYUN_ENTERPRISE_PASSWORD = "notice:mail:aliyun:enterprise:password";
        public static final String NOTICE_MAIL_ALIYUN_ENTERPRISE_FORM_NAME = "notice:mail:aliyun:enterprise:formName";
        public static final String NOTICE_MAIL_ALIYUN_ENTERPRISE_MAIL = "notice:mail:aliyun:enterprise:mail";
        /**
         * QQ邮箱
         */
        public static final String NOTICE_MAIL_QQ_USER_NAME = "notice:mail:qq:userName";
        public static final String NOTICE_MAIL_QQ_PASSWORD = "notice:mail:qq:password";
        /**
         * 网易邮箱
         */
        public static final String NOTICE_MAIL_NETEASE_USER_NAME = "notice:mail:netease:userName";
        public static final String NOTICE_MAIL_NETEASE_PASSWORD = "notice:mail:netease:password";
    }


}

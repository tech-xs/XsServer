package tech.xs.admin.config;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONTokener;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import tech.xs.auth.constant.ClientTypeConstant;
import tech.xs.auth.domain.entity.AuthClientType;
import tech.xs.auth.service.AuthClientTypeService;
import tech.xs.auth.service.AuthUriService;
import tech.xs.common.collections.CollectionUtils;
import tech.xs.common.constant.Symbol;
import tech.xs.common.constant.Text;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.constant.*;
import tech.xs.framework.enums.BooleanEnum;
import tech.xs.framework.enums.HttpMethodEnum;
import tech.xs.framework.enums.SexEnum;
import tech.xs.framework.util.SpringUtil;
import tech.xs.system.constant.SysCompanyConstant;
import tech.xs.system.constant.SysConfigConstant;
import tech.xs.system.constant.SysRoleConstant;
import tech.xs.system.dao.SysUserDao;
import tech.xs.system.domain.entity.*;
import tech.xs.system.enmus.DictStructureTypeEnum;
import tech.xs.system.enmus.DictValueTypeEnum;
import tech.xs.system.service.*;

import javax.annotation.Resource;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据初始化
 *
 * @author 沈家文
 * @date 2021-58-25 11:58
 */
@Component
@Slf4j
@Order(11000)
public class InitCommonData implements ApplicationRunner {

    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysCompanyService sysCompanyService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysPageService sysPageService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private SysConfigService sysConfigService;
    @Resource
    private SysApiService sysApiService;
    @Resource
    private SysPageApiService sysPageApiService;
    @Resource
    private SysDictService sysDictService;
    @Resource
    private SysDictValueService sysDictValueService;
    @Resource
    private SysRoleApiService sysRoleApiService;
    @Resource
    private SysPagePermissionService sysPagePermissionService;
    @Resource
    private SysRoleMenuPagePermissionService sysRoleMenuPermissionService;

    @Resource
    private AuthClientTypeService authClientTypeService;
    @Resource
    private AuthUriService authUriService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始通用数据初始化");
        sysRoleApiService.clearCacheRoleUri();
        initAuthUri();
        initDictList();
        initConfigList();
        setWebResourceRootUrl();
        initPageList();
        initMenuList();
        initRoleList();
        initSysCompany();
        initSysUser();
        initUserRole();
        initAuthClientType();
        log.info("通用数据初始化结束");
    }

    private void initAuthUri() throws Exception {
        List<URL> urlList = SpringUtil.getDepthFileResource("admin/auth/auth.json");
        for (URL url : urlList) {
            String content = SpringUtil.getResourceStringContent(url);
            if (StringUtils.isBlank(content)) {
                continue;
            }
            JSONObject json = JSONObject.parseObject(content);
            JSONArray whiteList = json.getJSONArray("whiteList");
            if (whiteList != null && !whiteList.isEmpty()) {
                for (Object uriObj : whiteList) {
                    JSONObject uriJson = (JSONObject) uriObj;
                    String method = uriJson.getString("method");
                    if (StringUtils.isBlank(method)) {
                        throw new Exception("请求方式不能为空 filePath:" + url.getPath());
                    }
                    HttpMethodEnum httpMethodEnum = HttpMethodEnum.getByCode(method);
                    String uri = uriJson.getString("uri");
                    if (StringUtils.isBlank(uri)) {
                        throw new Exception("uri不能为空 filePath:" + url.getPath());
                    }
                    authUriService.addUriWhite(httpMethodEnum, uri);
                }
            }
        }
    }

    private void initDictList() throws Exception {
        log.info("开始初始化字典");
        List<URL> urlList = SpringUtil.getDepthFileResource("admin/dict");
        for (URL url : urlList) {
            String content = SpringUtil.getResourceStringContent(url);
            if (StringUtils.isBlank(content)) {
                continue;
            }
            JSONArray dictJsonArray = JSON.parseArray(content);
            for (Object dictObj : dictJsonArray) {
                JSONObject dictJson = (JSONObject) dictObj;
                String code = dictJson.getString("code");
                if (StringUtils.isBlank(code)) {
                    throw new Exception("字典code不能为空 filePath:" + url.getPath());
                }
                String typeStr = dictJson.getString("type");
                DictStructureTypeEnum type = null;
                if (StringUtils.isNotBlank(typeStr)) {
                    type = DictStructureTypeEnum.getByCode(typeStr);
                }
                if (type == null) {
                    type = DictStructureTypeEnum.LIST;
                }
                String dictName = dictJson.getString("name");
                if (StringUtils.isBlank(dictName)) {
                    throw new Exception("字典名 不能为空 filePath:" + url.getPath());
                }
                String valueTypeStr = dictJson.getString("valueType");
                if (StringUtils.isBlank(valueTypeStr)) {
                    throw new Exception("字典值类型不能为空 filePath:" + url.getPath());
                }
                DictValueTypeEnum valueType = DictValueTypeEnum.getByCode(valueTypeStr);
                if (valueType == null) {
                    throw new Exception("字典值类型错误 filePath:" + url.getPath() + " valueType:" + valueTypeStr);
                }

                List<SysDict> dictList = sysDictService.list(Wrappers.<SysDict>lambdaQuery()
                        .eq(SysDict::getCode, code)
                        .eq(SysDict::getStructureType, type));
                SysDict dict = null;
                if (CollectionUtils.isEmpty(dictList)) {
                    dict = new SysDict();
                    dict.setCode(code);
                    dict.setStructureType(type);
                    dict.setName(dictName);
                    dict.setValueType(valueType);
                    sysDictService.add(dict);
                } else {
                    dict = dictList.get(0);
                }
                Long dictId = dict.getId();

                JSONArray valueListJson = dictJson.getJSONArray("valueList");
                for (Object valueObj : valueListJson) {
                    JSONObject valueJson = (JSONObject) valueObj;
                    String value = valueJson.getString("value");
                    if (StringUtils.isBlank(value)) {
                        throw new Exception("字典值不能为空 filePath:" + url.getPath() + " dict:" + dict);
                    }
                    String valueName = valueJson.getString("name");
                    if (StringUtils.isBlank(valueName)) {
                        throw new Exception("字典值名称不能为空 filePath:" + url.getPath() + " dict:" + dict);
                    }
                    Integer sort = valueJson.getInteger("sort");
                    if (sort == null) {
                        sort = 10;
                    }
                    if (!sysDictValueService.exist(Wrappers.<SysDictValue>lambdaQuery()
                            .eq(SysDictValue::getDictId, dictId)
                            .eq(SysDictValue::getValue, value))) {
                        SysDictValue dictValue = new SysDictValue();
                        dictValue.setDictId(dictId);
                        dictValue.setValue(value);
                        dictValue.setName(valueName);
                        dictValue.setSort(sort);
                        sysDictValueService.add(dictValue);
                    }
                }
            }
        }
        log.info("字典初始化完毕");
    }

    private void initConfigList() {
        // 网站名称配置
        initConfigItem(SysConfigConstant.Type.PUBLIC_WEB_INIT_CONFIG, SysConfigConstant.Module.SYS, SysConfigConstant.ConfigName.WEB_NAME, SysConfigConstant.ConfigKey.WEB_NAME, "网站名称");
        // 网站图标
        initConfigItem(SysConfigConstant.Type.PUBLIC_WEB_INIT_CONFIG, SysConfigConstant.Module.SYS, SysConfigConstant.ConfigName.WEB_ICON, SysConfigConstant.ConfigKey.WEB_ICON, Text.EMPTY);
        // 存储类型
        initConfigItem(null, SysConfigConstant.Module.STORAGE, SysConfigConstant.ConfigName.STORAGE_TYPE, SysConfigConstant.ConfigKey.STORAGE_TYPE, StorageConstant.MINIO_TYPE);
        // minio存储
        initConfigItem(null, SysConfigConstant.Module.STORAGE, SysConfigConstant.ConfigName.STORAGE_MINIO, SysConfigConstant.ConfigKey.STORAGE_MINIO_ACCESS_KEY, Text.EMPTY);
        initConfigItem(null, SysConfigConstant.Module.STORAGE, SysConfigConstant.ConfigName.STORAGE_MINIO, SysConfigConstant.ConfigKey.STORAGE_MINIO_SECRET_KEY, Text.EMPTY);
        initConfigItem(null, SysConfigConstant.Module.STORAGE, SysConfigConstant.ConfigName.STORAGE_MINIO, SysConfigConstant.ConfigKey.STORAGE_MINIO_ENDPOINT, Text.EMPTY);
        initConfigItem(null, SysConfigConstant.Module.STORAGE, SysConfigConstant.ConfigName.STORAGE_MINIO, SysConfigConstant.ConfigKey.STORAGE_MINIO_BUCKET_NAME, Text.EMPTY);
        // 邮件服务器类型
        initConfigItem(null, SysConfigConstant.Module.NOTICE, SysConfigConstant.ConfigName.NOTICE_MAIL_TYPE, SysConfigConstant.ConfigKey.NOTICE_MAIL_TYPE, NoticeConstant.MailType.ALI_YUN_ENTERPRISE);
        // 阿里云企业邮箱
        initConfigItem(null, SysConfigConstant.Module.NOTICE, SysConfigConstant.ConfigName.NOTICE_MAIL_ALIYUN_ENTERPRISE, SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_HOST, Text.EMPTY);
        initConfigItem(null, SysConfigConstant.Module.NOTICE, SysConfigConstant.ConfigName.NOTICE_MAIL_ALIYUN_ENTERPRISE, SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_USER_NAME, Text.EMPTY);
        initConfigItem(null, SysConfigConstant.Module.NOTICE, SysConfigConstant.ConfigName.NOTICE_MAIL_ALIYUN_ENTERPRISE, SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_PASSWORD, Text.EMPTY);
        initConfigItem(null, SysConfigConstant.Module.NOTICE, SysConfigConstant.ConfigName.NOTICE_MAIL_ALIYUN_ENTERPRISE, SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_FORM_NAME, Text.EMPTY);
        initConfigItem(null, SysConfigConstant.Module.NOTICE, SysConfigConstant.ConfigName.NOTICE_MAIL_ALIYUN_ENTERPRISE, SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_MAIL, Text.EMPTY);
        // QQ邮箱
        initConfigItem(null, SysConfigConstant.Module.NOTICE, SysConfigConstant.ConfigName.NOTICE_MAIL_QQ, SysConfigConstant.ConfigKey.NOTICE_MAIL_QQ_USER_NAME, Text.EMPTY);
        initConfigItem(null, SysConfigConstant.Module.NOTICE, SysConfigConstant.ConfigName.NOTICE_MAIL_QQ, SysConfigConstant.ConfigKey.NOTICE_MAIL_QQ_PASSWORD, Text.EMPTY);
        // 网易邮箱
        initConfigItem(null, SysConfigConstant.Module.NOTICE, SysConfigConstant.ConfigName.NOTICE_MAIL_NETEASE, SysConfigConstant.ConfigKey.NOTICE_MAIL_NETEASE_USER_NAME, Text.EMPTY);
        initConfigItem(null, SysConfigConstant.Module.NOTICE, SysConfigConstant.ConfigName.NOTICE_MAIL_NETEASE, SysConfigConstant.ConfigKey.NOTICE_MAIL_NETEASE_PASSWORD, Text.EMPTY);
        // 是否开启邮箱注册
        initConfigItem(SysConfigConstant.Type.PUBLIC_WEB_INIT_CONFIG, SysConfigConstant.Module.SYS, SysConfigConstant.ConfigName.ACCOUNT_REGISTER_ENABLE, SysConfigConstant.ConfigKey.ACCOUNT_REGISTER_EMAIL_ENABLE, BooleanConstant.FALSE + Text.EMPTY);
        // 是否开启手机号注册
        initConfigItem(SysConfigConstant.Type.PUBLIC_WEB_INIT_CONFIG, SysConfigConstant.Module.SYS, SysConfigConstant.ConfigName.ACCOUNT_REGISTER_ENABLE, SysConfigConstant.ConfigKey.ACCOUNT_REGISTER_PHONE_ENABLE, BooleanConstant.FALSE + Text.EMPTY);
        // 阿里云Key
        initConfigItem(null, SysConfigConstant.Module.SYS, SysConfigConstant.ConfigName.ALI_YUN_ACCESS_KEY, SysConfigConstant.ConfigKey.ALI_YUN_ACCESS_KEY, Text.EMPTY);
        initConfigItem(null, SysConfigConstant.Module.SYS, SysConfigConstant.ConfigName.ALI_YUN_SECRET_KEY, SysConfigConstant.ConfigKey.ALI_YUN_SECRET_KEY, Text.EMPTY);
    }

    private void initConfigItem(String configType, String module, String configName, String configKey, String configValue) {
        if (!sysConfigService.exist(Wrappers.<SysConfig>lambdaQuery()
                .eq(StringUtils.isNotBlank(configType), SysConfig::getConfigType, configType)
                .eq(StringUtils.isNotBlank(module), SysConfig::getConfigModule, module)
                .eq(StringUtils.isNotBlank(configName), SysConfig::getConfigName, configName)
                .eq(StringUtils.isNotBlank(configKey), SysConfig::getConfigKey, configKey))) {
            SysConfig config = new SysConfig();
            config.setConfigType(configType);
            config.setConfigModule(module);
            config.setConfigName(configName);
            config.setConfigKey(configKey);
            config.setConfigValue(configValue);
            sysConfigService.add(config);
        }
    }

    /**
     * 设置前端访问资源根路径
     */
    private void setWebResourceRootUrl() {
        SysConfig storageTypeConfig = sysConfigService.getOne(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.STORAGE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.STORAGE_TYPE)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.STORAGE_TYPE));
        String storageType = storageTypeConfig.getConfigValue();
        String rootUrl = Text.EMPTY;
        switch (storageType) {
            case StorageConstant.MINIO_TYPE: {
                SysConfig endpointConfig = sysConfigService.getOne(Wrappers.<SysConfig>lambdaQuery()
                        .eq(SysConfig::getConfigModule, SysConfigConstant.Module.STORAGE)
                        .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.STORAGE_MINIO)
                        .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.STORAGE_MINIO_ENDPOINT));
                SysConfig bucketNameConfig = sysConfigService.getOne(Wrappers.<SysConfig>lambdaQuery()
                        .eq(SysConfig::getConfigModule, SysConfigConstant.Module.STORAGE)
                        .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.STORAGE_MINIO)
                        .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.STORAGE_MINIO_BUCKET_NAME));
                if (endpointConfig == null || bucketNameConfig == null) {
                    return;
                }
                String endpoint = endpointConfig.getConfigValue();
                String bucketName = bucketNameConfig.getConfigValue();
                if (StringUtils.isAnyBlank(endpoint, bucketName)) {
                    return;
                }
                if (endpoint.endsWith(Symbol.SLASH)) {
                    rootUrl = endpoint + bucketName;
                } else {
                    rootUrl = endpoint + Symbol.SLASH + bucketName;
                }
                break;
            }
            default: {
                return;
            }
        }
        if (!sysConfigService.exist(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.STORAGE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.STORAGE_WEB_ROOT)
                .eq(SysConfig::getConfigType, SysConfigConstant.Type.PUBLIC_WEB_INIT_CONFIG)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.STORAGE_WEB_ROOT))) {
            SysConfig config = new SysConfig();
            config.setConfigType(SysConfigConstant.Type.PUBLIC_WEB_INIT_CONFIG);
            config.setConfigModule(SysConfigConstant.Module.STORAGE);
            config.setConfigName(SysConfigConstant.ConfigName.STORAGE_WEB_ROOT);
            config.setConfigKey(SysConfigConstant.ConfigKey.STORAGE_WEB_ROOT);
            config.setConfigValue(rootUrl);
            sysConfigService.add(config);
        } else {
            sysConfigService.update(new SysConfig(), Wrappers.<SysConfig>lambdaUpdate()
                    .set(SysConfig::getConfigValue, rootUrl)
                    .eq(SysConfig::getConfigModule, SysConfigConstant.Module.STORAGE)
                    .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.STORAGE_WEB_ROOT)
                    .eq(SysConfig::getConfigType, SysConfigConstant.Type.PUBLIC_WEB_INIT_CONFIG)
                    .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.STORAGE_WEB_ROOT));
        }
    }

    /**
     * 初始化菜单
     */
    private void initMenuList() throws Exception {
        log.info("初始化菜单数据");
        String menuJsonFilePath = "admin/menu/webSidebarMenu.json";
        String menuJsonStr = SpringUtil.getResourceStringContent(menuJsonFilePath);
        if (StringUtils.isBlank(menuJsonStr)) {
            return;
        }
        JSONArray jsonArray = JSON.parseArray(menuJsonStr);
        saveMenu(null, jsonArray);

//
//        SysMenu dictMgr = new SysMenu();
//        dictMgr.setName("字典管理");
//        dictMgr.setIcon("profile");
//        dictMgr.setFatherId(1L);
//        dictMgr.setSort(30);
//        dictMgr.setPageId(sysPageService.getOne(Wrappers.<SysPage>lambdaQuery()
//                .eq(SysPage::getUri, "/system/dictMgr")).getId());
//        menuList.add(dictMgr);
//
//
//        SysMenu apiMgr = new SysMenu();
//        apiMgr.setName("接口管理");
//        apiMgr.setIcon("api");
//        apiMgr.setFatherId(1L);
//        apiMgr.setSort(60);
//        apiMgr.setPageId(sysPageService.getOne(Wrappers.<SysPage>lambdaQuery()
//                .eq(SysPage::getUri, "/system/apiMgr")).getId());
//        menuList.add(apiMgr);
//
//        SysMenu companyMgr = new SysMenu();
//        companyMgr.setName("公司管理");
//        companyMgr.setIcon("deployment-unit");
//        companyMgr.setFatherId(1L);
//        companyMgr.setSort(70);
//        companyMgr.setPageId(sysPageService.getOne(Wrappers.<SysPage>lambdaQuery()
//                .eq(SysPage::getUri, "/system/companyMgr")).getId());
//        menuList.add(companyMgr);
//
//
//        SysMenu sysInfo = new SysMenu();
//        sysInfo.setName("系统信息");
//        sysInfo.setIcon("area-chart");
//        sysInfo.setFatherId(1L);
//        sysInfo.setSort(90);
//        sysInfo.setPageId(sysPageService.getOne(Wrappers.<SysPage>lambdaQuery()
//                .eq(SysPage::getUri, "/system/os/info")).getId());
//        menuList.add(sysInfo);

        log.info("菜单数据初始化完毕");
    }

    private void saveMenu(SysMenu father, JSONArray menuListJson) throws Exception {
        if (CollectionUtils.isEmpty(menuListJson)) {
            return;
        }
        // 校验菜单编码和名称是否为空
        for (Object jsonObj : menuListJson) {
            JSONObject menuJson = (JSONObject) jsonObj;
            String code = menuJson.getString("code");
            if (StringUtils.isBlank(code)) {
                throw new Exception("菜单编码不能为空");
            }
            String menuName = menuJson.getString("name");
            if (StringUtils.isBlank(menuName)) {
                throw new Exception("菜单名称不能为空");
            }
        }
        // 校验是否存在重复编码和名称
        for (Object jsonObj : menuListJson) {
            JSONObject menuJson = (JSONObject) jsonObj;
            String code = menuJson.getString("code");
            String name = menuJson.getString("name");
            for (Object checkObj : menuListJson) {
                JSONObject checkJson = (JSONObject) checkObj;
                String checkCode = checkJson.getString("code");
                String checkName = checkJson.getString("name");
                if (checkObj != jsonObj) {
                    if (code.equals(checkCode)) {
                        throw new Exception("菜单节点中存在重复编码 code:" + code);
                    }
                    if (name.equals(checkName)) {
                        throw new Exception("菜单节点中存在重复名称 name:" + name);
                    }
                }
            }
        }
        //添加或更新菜单
        for (Object jsonObj : menuListJson) {
            JSONObject menuJson = (JSONObject) jsonObj;

            String menuName = menuJson.getString("name");
            String menuIcon = menuJson.getString("icon");
            Integer sort = menuJson.getInteger("sort");
            if (sort == null) {
                sort = 10;
            }
            String code = menuJson.getString("code");
            SysMenu menu = new SysMenu();
            menu.setName(menuName);
            menu.setCode(code);
            menu.setIcon(menuIcon);
            menu.setSort(sort);
            if (father != null) {
                menu.setFatherId(father.getId());
            }
            String pageUri = menuJson.getString("pageUri");
            if (StringUtils.isNotBlank(pageUri)) {
                SysPage page = sysPageService.getOne(Wrappers.<SysPage>lambdaQuery()
                        .eq(SysPage::getUri, pageUri));
                if (page == null) {
                    throw new Exception("菜单绑定的页面不存在 页面URI:" + pageUri);
                }
                menu.setPageId(page.getId());
            }
            saveMenu(menu);
            saveMenu(menu, menuJson.getJSONArray("childList"));
        }
    }

    private void saveMenu(SysMenu menu) {
        SysMenu dbMenu = sysMenuService.getOne(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getName, menu.getName())
                .eq(menu.getFatherId() != null, SysMenu::getFatherId, menu.getFatherId())
                .isNull(menu.getFatherId() == null, SysMenu::getFatherId));
        if (dbMenu == null) {
            sysMenuService.add(menu);
        } else {
            menu.setId(dbMenu.getId());
            sysMenuService.updateById(menu);
        }
    }


    /**
     * 初始化页面
     */
    private void initPageList() throws Exception {
        log.info("开始初始化页面数据");
        List<SysPage> pageList = getPageList();
        //校验页面
        for (SysPage page : pageList) {
            if (page.getFather() != null && page.getUri().equals(page.getFather().getUri())) {
                throw new Exception("页面的父页面不能是自身 pageUri:" + page.getUri());
            }
            // 判断父页面是否存在
            if (page.getFather() != null && StringUtils.isNotBlank(page.getFather().getUri())) {
                boolean fatherExist = false;
                for (SysPage fatherPage : pageList) {
                    if (page.getUri().equals(fatherPage.getUri())) {
                        fatherExist = true;
                        break;
                    }
                }
                if (!fatherExist) {
                    throw new Exception("页面的父页面不存在 pageUri:" + page.getUri());
                }
            }
            for (SysPageApi api : page.getPageApiList()) {
                SysPagePermission permission = api.getPermission();
                if (permission == null) {
                    continue;
                }
                if (StringUtils.isBlank(permission.getPermissionCode())) {
                    throw new Exception("权限编码不能为空 pageUri:" + page.getUri());
                }
                boolean isExistPermission = false;
                for (SysPagePermission item : page.getPagePermissionList()) {
                    if (permission.getPermissionCode().equals(item.getPermissionCode())) {
                        isExistPermission = true;
                        break;
                    }
                }
                if (!isExistPermission) {
                    throw new Exception("页面接口对应的权限不存在 pageUri:" + page.getUri());
                }
            }
        }
        for (int i = 0; i < pageList.size(); i++) {
            SysPage page = pageList.get(i);
            if (page.getFather() == null) {
                initPage(page);
                pageList.remove(i);
                if (i >= pageList.size()) {
                    i = 0;
                }
                i--;
                continue;
            }
            SysPage father = sysPageService.getOne(Wrappers.<SysPage>lambdaQuery()
                    .eq(SysPage::getUri, page.getFather().getUri()));
            if (father == null) {
                continue;
            }
            page.setFather(father);
            page.setFatherId(father.getId());
            initPage(page);
            pageList.remove(i);
            if (i >= pageList.size()) {
                i = 0;
            }
            i--;
        }
        if (CollectionUtils.isNotEmpty(pageList)) {
            throw new Exception("页面初始化异常" + pageList);
        }

//        List<SysPage> pageList = new ArrayList<>();
//
//        SysPage dictMgr = new SysPage();
//        dictMgr.setName("字典管理");
//        dictMgr.setUri("/system/dictMgr");
//        pageList.add(dictMgr);
//
//        SysPage apiMgr = new SysPage();
//        apiMgr.setName("接口管理");
//        apiMgr.setUri("/system/apiMgr");
//        pageList.add(apiMgr);
//
//        SysPage companyMgr = new SysPage();
//        companyMgr.setName("公司管理");
//        companyMgr.setUri("/system/companyMgr");
//        pageList.add(companyMgr);
//
//        SysPage osInfo = new SysPage();
//        osInfo.setName("系统信息");
//        osInfo.setUri("/system/os/info");
//        pageList.add(osInfo);

//        sysPageService.add(pageList);
        log.info("页面数据初始化完毕");
    }

    private List<SysPage> getPageList() throws Exception {
        List<URL> urlList = SpringUtil.getDepthFileResource("admin/page");
        List<SysPage> pageList = new ArrayList<>();
        for (URL url : urlList) {
            String content = SpringUtil.getResourceStringContent(url);
            if (StringUtils.isBlank(content)) {
                continue;
            }
            Object obj = new JSONTokener(content).nextValue();
            if (obj.getClass().equals(org.json.JSONArray.class)) {
                org.json.JSONArray jsonArray = (org.json.JSONArray) obj;
                for (int i = 0; i < jsonArray.length(); i++) {
                    SysPage page = getPageItem(jsonArray.getString(i));
                    if (page != null) {
                        pageList.add(page);
                    }
                }
            } else {
                SysPage page = getPageItem(content);
                if (page != null) {
                    pageList.add(page);
                }
            }
        }
        return pageList;
    }

    private SysPage getPageItem(String content) throws Exception {
        JSONObject json = JSON.parseObject(content);
        String pageName = json.getString("name");
        if (StringUtils.isBlank(pageName)) {
            throw new Exception("页面名称不能为空 content:" + content);
        }
        String pageUri = json.getString("uri");
        if (StringUtils.isBlank(pageUri)) {
            throw new Exception("页面URI不能为空 content:" + content);
        }
        String fatherUri = json.getString("fatherUri");
        if (fatherUri == "") {
            throw new Exception("父页面URI不能为空字符串 content:" + content);
        }
        SysPage page = new SysPage();
        page.setUri(pageUri);
        page.setName(pageName);
        if (StringUtils.isNotBlank(fatherUri)) {
            SysPage father = new SysPage();
            father.setUri(fatherUri);
            page.setFather(father);
        }
        page.setPageApiList(getPageHttpApiList(json));
        page.setPagePermissionList(getPagePermissionList(json));
        return page;
    }

    private List<SysPageApi> getPageHttpApiList(JSONObject json) throws Exception {
        JSONArray httpApiArray = json.getJSONArray("httpApiList");
        List<SysPageApi> pageApiList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(httpApiArray)) {
            for (Object item : httpApiArray) {
                JSONObject apiJson = (JSONObject) item;
                String method = apiJson.getString("method");
                if (StringUtils.isBlank(method)) {
                    throw new Exception("接口Method不能为空");
                }
                String uri = apiJson.getString("uri");
                if (StringUtils.isBlank(uri)) {
                    throw new Exception("接口URI不能为空");
                }
                SysApi api = sysApiService.getOne(Wrappers.<SysApi>lambdaQuery()
                        .eq(SysApi::getMethod, HttpMethodEnum.getByCode(method))
                        .eq(SysApi::getUri, uri));
                if (api == null) {
                    throw new Exception("接口不存在 apiUri:" + uri);
                }
                SysPageApi pageApi = new SysPageApi();
                String permissionCode = apiJson.getString("permission");
                if (StringUtils.isNotBlank(permissionCode)) {
                    SysPagePermission permission = new SysPagePermission();
                    permission.setPermissionCode(permissionCode);
                    pageApi.setPermission(permission);
                }
                pageApi.setApiId(api.getId());
                pageApiList.add(pageApi);
            }
        }
        return pageApiList;
    }

    private List<SysPagePermission> getPagePermissionList(JSONObject json) throws Exception {
        List<SysPagePermission> permissionList = new ArrayList<>();
        JSONArray permissionArray = json.getJSONArray("permissionList");
        if (CollectionUtils.isNotEmpty(permissionArray)) {
            for (Object permissionObj : permissionArray) {
                JSONObject permissionJson = (JSONObject) permissionObj;
                String permissionName = permissionJson.getString("name");
                if (StringUtils.isBlank(permissionName)) {
                    throw new Exception("权限名称不能为空");
                }
                String permissionCode = permissionJson.getString("code");
                if (StringUtils.isBlank(permissionCode)) {
                    throw new Exception("权限编码不能为空");
                }
                SysPagePermission permission = new SysPagePermission();
                permission.setPermissionName(permissionName);
                permission.setPermissionCode(permissionCode);
                permissionList.add(permission);
            }
        }
        return permissionList;
    }

    private void initPage(SysPage page) throws Exception {
        SysPage dbPage = sysPageService.getOne(Wrappers.<SysPage>lambdaUpdate()
                .eq(SysPage::getUri, page.getUri()));
        if (dbPage == null) {
            sysPageService.add(page);
        } else {
            page.setId(dbPage.getId());
            sysPageService.updateById(page);
        }
        for (SysPagePermission permission : page.getPagePermissionList()) {
            SysPagePermission dbPermission = sysPagePermissionService.getOne(Wrappers.<SysPagePermission>lambdaQuery()
                    .eq(SysPagePermission::getPageId, page.getId())
                    .eq(SysPagePermission::getPermissionCode, permission.getPermissionCode()));
            permission.setPageId(page.getId());
            if (dbPermission == null) {
                sysPagePermissionService.add(permission);
            } else {
                permission.setId(dbPermission.getId());
                sysPagePermissionService.updateById(permission);
            }
        }
        for (SysPageApi pageApi : page.getPageApiList()) {
            pageApi.setPageId(page.getId());
            SysPagePermission permission = pageApi.getPermission();
            if (permission != null && StringUtils.isNotBlank(permission.getPermissionCode())) {
                permission = sysPagePermissionService.getOne(Wrappers.<SysPagePermission>lambdaQuery()
                        .eq(SysPagePermission::getPageId, page.getId())
                        .eq(SysPagePermission::getPermissionCode, permission.getPermissionCode()));
                if (permission == null) {
                    throw new Exception("页面接口对应的权限编码不存在 page:" + page);
                }
                pageApi.setPermissionId(permission.getId());
                pageApi.setPermission(permission);
            }
            SysPageApi dbPageApi = sysPageApiService.getOne(Wrappers.<SysPageApi>lambdaQuery()
                    .eq(SysPageApi::getPageId, page.getId())
                    .eq(SysPageApi::getApiId, pageApi.getApi()));
            if (dbPageApi == null) {
                sysPageApiService.add(pageApi);
            } else {
                pageApi.setId(dbPageApi.getId());
                sysPageApiService.updateById(pageApi);
            }
        }
    }

    /**
     * 初始化用户角色
     */
    private void initUserRole() {
        log.info("开始初始化用户角色数据");
        if (sysUserRoleService.exist(Wrappers.lambdaQuery())) {
            log.info("用户角色数据无需初始化");
            return;
        }
        List<SysUserRole> userRoleList = new ArrayList<>();

        SysUser superAdminUser = sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, "admin"));
        SysRole superAdminRole = sysRoleService.getOne(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getCode, SysRoleConstant.Code.SUPER_ADMIN));
        SysUserRole superAdminUserRole = new SysUserRole();
        superAdminUserRole.setUserId(superAdminUser.getId());
        superAdminUserRole.setRoleId(superAdminRole.getId());
        userRoleList.add(superAdminUserRole);

        sysUserRoleService.add(userRoleList);
        log.info("用户角色数据初始化完毕");
    }

    /**
     * 授权客户端类型
     */
    private void initAuthClientType() {
        log.info("开始初始化授权客户端类型");
        if (authClientTypeService.exist(Wrappers.lambdaQuery())) {
            log.info("客户端类型数据无需初始化");
            return;
        }
        List<AuthClientType> clientTypeList = new ArrayList<>();

        AuthClientType browserClientType = new AuthClientType();
        browserClientType.setId(ClientTypeConstant.BROWSER);
        browserClientType.setName("浏览器");
        browserClientType.setAccessTokenValidity(10800000L);
        browserClientType.setAccessTokenRefreshInterval(3600000L);
        browserClientType.setRefreshTokenValidity(2592000000L);
        browserClientType.setStatus(BooleanEnum.TRUE);
        clientTypeList.add(browserClientType);

        AuthClientType serverClientType = new AuthClientType();
        serverClientType.setId(ClientTypeConstant.SERVER);
        serverClientType.setName("服务端");
        serverClientType.setAccessTokenValidity(3153600000000L);
        serverClientType.setAccessTokenRefreshInterval(315360000000L);
        serverClientType.setRefreshTokenValidity(2592000000L);
        serverClientType.setStatus(BooleanEnum.TRUE);
        clientTypeList.add(serverClientType);

        AuthClientType androidClientType = new AuthClientType();
        androidClientType.setId(ClientTypeConstant.ANDROID);
        androidClientType.setName("安卓端");
        androidClientType.setAccessTokenValidity(172800000L);
        androidClientType.setAccessTokenRefreshInterval(86400000L);
        androidClientType.setRefreshTokenValidity(2592000000L);
        androidClientType.setStatus(BooleanEnum.TRUE);
        clientTypeList.add(androidClientType);

        AuthClientType iosClientType = new AuthClientType();
        iosClientType.setId(ClientTypeConstant.IOS);
        iosClientType.setName("IOS");
        iosClientType.setAccessTokenValidity(172800000L);
        iosClientType.setAccessTokenRefreshInterval(86400000L);
        iosClientType.setRefreshTokenValidity(2592000000L);
        iosClientType.setStatus(BooleanEnum.TRUE);
        clientTypeList.add(iosClientType);

        authClientTypeService.add(clientTypeList);
        log.info("客户端类型数据初始化完毕");
    }

    private void initSysUser() {
        log.info("开始初始化用户数据");
        if (sysUserService.exist(Wrappers.lambdaQuery())) {
            log.info("用户数据无需初始化");
            return;
        }

        List<SysUser> userList = new ArrayList<>();


        SysUser superUser = new SysUser();
        superUser.setUserName("admin");
        superUser.setNickName("管理员");
        superUser.setCompanyId(SysCompanyConstant.SUPER_COMPANY_ID);
        superUser.setAccountStatus(BooleanEnum.TRUE);
        superUser.setSex(SexEnum.UNKNOWN);
        userList.add(superUser);

        sysUserService.add(userList);
        setUserPassword(userList);
        log.info("用户数据初始化完毕");
    }

    private void setUserPassword(List<SysUser> userList) {
        String webSalt = "1j3gokcAIvEjhKzeneMiodjg1rnReDOk";
        String userInitPassword = "111111";
        String webPassword = SecureUtil.md5(userInitPassword + webSalt);
        String dbPasswrd = sysUserService.loginPasswordEncode(webPassword);
        for (SysUser user : userList) {
            sysUserDao.updateLoginPassword(user.getId(), dbPasswrd);
        }
    }

    private void initSysCompany() {
        log.info("开始初始化机构数据");
        if (sysCompanyService.exist(Wrappers.lambdaQuery())) {
            log.info("机构数据无需初始化");
            return;
        }
        List<SysCompany> companyList = new ArrayList<>();

        SysCompany superCompany = new SysCompany();
        superCompany.setId(SysCompanyConstant.SUPER_COMPANY_ID);
        superCompany.setFullName("总公司");
        superCompany.setShortName("总公司");
        companyList.add(superCompany);

        sysCompanyService.add(companyList);
        log.info("机构数据初始化完毕");
    }

    /**
     * 初始化角色数据
     */
    private void initRoleList() throws Exception {
        log.info("开始初始化角色数据");
        List<URL> urlList = SpringUtil.getDepthFileResource("admin/role");
        for (URL url : urlList) {
            String content = SpringUtil.getResourceStringContent(url);
            if (StringUtils.isBlank(content)) {
                continue;
            }
            JSONObject roleJson = JSON.parseObject(content);
            String code = roleJson.getString("code");
            if (StringUtils.isBlank(code)) {
                throw new Exception("角色编码不能为空 filePath:" + url.getPath());
            }
            String name = roleJson.getString("name");
            if (StringUtils.isBlank(name)) {
                throw new Exception("角色名称不能为空 filePath:" + url.getPath());
            }
            BooleanEnum status = null;
            String statusStr = roleJson.getString("status");
            if (StringUtils.isNotBlank(statusStr)) {
                status = BooleanEnum.getByCode(statusStr);
            }
            if (status == null) {
                status = BooleanEnum.TRUE;
            }
            String remark = roleJson.getString("remark");
            SysRole role = sysRoleService.getOne(Wrappers.<SysRole>lambdaQuery()
                    .eq(SysRole::getCode, code));
            if (role == null) {
                role = new SysRole();
                role.setCode(code);
                role.setName(name);
                role.setStatus(status);
                role.setRemark(remark);
                sysRoleService.add(role);
            }
            // 初始化角色菜单
            JSONArray menuTree = roleJson.getJSONArray("menuTree");
            if (menuTree != null) {
                initRoleMenu(role, null, menuTree);
            }
        }
        log.info("角色数据初始化完毕");
    }

    private void initRoleMenu(SysRole role, Long fatherMenuId, JSONArray menuTree) throws Exception {
        for (Object menuObj : menuTree) {
            JSONObject menuJson = (JSONObject) menuObj;
            String code = menuJson.getString("code");
            if (StringUtils.isBlank(code)) {
                throw new Exception("角色拥有的菜单编码不能为空 role:" + role);
            }
            SysMenu menu = sysMenuService.getOne(Wrappers.<SysMenu>lambdaQuery()
                    .eq(fatherMenuId != null, SysMenu::getFatherId, fatherMenuId)
                    .isNull(fatherMenuId == null, SysMenu::getFatherId)
                    .eq(SysMenu::getCode, code));
            if (menu == null) {
                throw new Exception("角色拥有的菜单不存在 role:" + role);
            }
            if (!sysRoleMenuService.exist(Wrappers.<SysRoleMenu>lambdaQuery()
                    .eq(SysRoleMenu::getMenuId, menu.getId())
                    .eq(SysRoleMenu::getRoleId, role.getId()))) {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setMenuId(menu.getId());
                roleMenu.setRoleId(role.getId());
                sysRoleMenuService.add(roleMenu);
            }
            if (menu.getPageId() != null) {
                JSONArray permissionJsonList = menuJson.getJSONArray("permissionList");
                if (CollectionUtils.isNotEmpty(permissionJsonList)) {
                    for (Object permissionCode : permissionJsonList) {
                        SysPagePermission permission = sysPagePermissionService.getOne(Wrappers.<SysPagePermission>lambdaQuery()
                                .eq(SysPagePermission::getPageId, menu.getPageId())
                                .eq(SysPagePermission::getPermissionCode, permissionCode));
                        if (permission == null) {
                            throw new Exception("角色菜单拥有的页面权限不存在 role:" + role);
                        }
                        if (!sysRoleMenuPermissionService.exist(Wrappers.<SysRoleMenuPagePermission>lambdaQuery()
                                .eq(SysRoleMenuPagePermission::getRoleId, role.getId())
                                .eq(SysRoleMenuPagePermission::getMenuId, menu.getId())
                                .eq(SysRoleMenuPagePermission::getPageId, menu.getPageId())
                                .eq(SysRoleMenuPagePermission::getPermissionId, permission.getId()))) {
                            SysRoleMenuPagePermission menuPermission = new SysRoleMenuPagePermission();
                            menuPermission.setMenuId(menu.getId());
                            menuPermission.setRoleId(role.getId());
                            menuPermission.setPageId(menu.getPageId());
                            menuPermission.setPermissionId(permission.getId());
                            sysRoleMenuPermissionService.add(menuPermission);
                        }
                    }
                }
            }
            JSONArray childList = menuJson.getJSONArray("childList");
            if (childList != null) {
                initRoleMenu(role, menu.getId(), childList);
            }
        }
    }

}

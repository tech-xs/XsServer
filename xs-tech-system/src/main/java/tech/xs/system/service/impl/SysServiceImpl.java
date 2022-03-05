package tech.xs.system.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.xs.common.constant.Symbol;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.core.storage.Storage;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.system.constant.SysConfigConstant;
import tech.xs.system.constant.SysConstant;
import tech.xs.system.constant.SysUserConstant;
import tech.xs.system.domain.entity.SysConfig;
import tech.xs.system.service.*;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 系统Service实现类
 *
 * @author 沈家文
 * @date 2020/10/6
 */
@Slf4j
@Service
public class SysServiceImpl implements SysService {

    @Resource
    private Storage storage;

    @Resource
    private SysConfigService sysConfigService;

    @Override
    public ApiResult<String> modifyIcon(MultipartFile iconFile) throws Exception {
        String originalFilename = iconFile.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)) {
            return ApiResult.error(1000, "文件不存在");
        }
        String extName = FileUtil.extName(originalFilename);
        if (StringUtils.isBlank(extName)) {
            return ApiResult.error(1001, "文件类型错误");
        }
        String filePath = SysConstant.ICON_ROOT_PATH + Symbol.SLASH + IdUtil.simpleUUID() + Symbol.POINT + extName;
        storage.upload(filePath, iconFile.getInputStream());

        SysConfig config = sysConfigService.getOne(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigType, SysConfigConstant.Type.PUBLIC_WEB_INIT_CONFIG)
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.SYS)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.WEB_ICON)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.WEB_ICON));

        if (StringUtils.isNotBlank(config.getConfigValue())) {
            try {
                storage.delete(config.getConfigValue());
            } catch (Exception e) {
                log.error("网站icon删除失败:" + " filePath:" + config.getConfigValue());
                e.printStackTrace();
            }
        }

        config.setConfigValue(filePath);
        sysConfigService.updateById(config);
        return ApiResult.success(filePath);
    }

    @Override
    public void modifyWebName(String webName) {
        sysConfigService.update(new SysConfig(), Wrappers.<SysConfig>lambdaUpdate()
                .set(SysConfig::getConfigValue, webName)
                .eq(SysConfig::getConfigType, SysConfigConstant.Type.PUBLIC_WEB_INIT_CONFIG)
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.SYS)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.WEB_NAME)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.WEB_NAME));
    }

}

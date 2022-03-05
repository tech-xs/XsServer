package tech.xs.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.annotation.doc.Api;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.constant.ParamCheckConstant;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.framework.domain.model.PageResult;
import tech.xs.system.constant.SysParamCheckConstant;
import tech.xs.system.domain.bo.config.GetMailConfigBo;
import tech.xs.system.domain.bo.config.SetMailConfigBo;
import tech.xs.system.domain.entity.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import java.util.Arrays;
import java.util.List;

/**
 * 系统设置Controller
 *
 * @author 沈家文
 * @date 2021/6/15 10:20
 */
@Validated
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends BaseSysController {

    @Api(name = "获取公开的网页初始化配置", describe = "首次进入网页所需要的初始化参数获取")
    @GetMapping("/public/init/web")
    public ApiResult<List<SysConfig>> getPublicInitWebConfig() {
        return ApiResult.success(sysConfigService.getPublicInitWebConfig());
    }

    /**
     * 设置邮件服务器
     *
     * @return
     */
    @PostMapping("/modify/mail")
    public ApiResult modifyMailConfig(@RequestBody SetMailConfigBo config) {
        return sysConfigService.modifyMailConfig(config);
    }

    /**
     * 获取邮箱配置
     *
     * @return
     */
    @GetMapping("/mail")
    public ApiResult<GetMailConfigBo> getMailConfig() {
        return ApiResult.success(sysConfigService.getMailConfig());
    }

    /**
     * 修改网站名称
     *
     * @param webName
     * @return
     */
    @PostMapping("/modify/web/name")
    public ApiResult<String> modifyWebName(@NotBlank String webName) {
        sysService.modifyWebName(webName);
        return ApiResult.success();
    }

    /**
     * 修改网站Icon
     *
     * @param iconFile
     * @return
     */
    @PostMapping(value = "/modify/icon", headers = "content-type=multipart/form-data")
    public ApiResult<String> modifyIcon(@RequestPart(value = "iconFile", required = false) MultipartFile iconFile) throws Exception {
        return sysService.modifyIcon(iconFile);
    }


    @PutMapping("/add")
    public ApiResult<SysConfig> add(
            @NotBlank @Length(max = SysParamCheckConstant.SysConfig.MODULE_MAX_LENGTH) String module,
            @NotBlank @Length(max = SysParamCheckConstant.SysConfig.KEY_MAX_LENGTH) String configKey,
            @Length(max = SysParamCheckConstant.SysConfig.SUB_KEY_MAX_LENGTH) String configSubKey,
            @NotBlank @Length(max = SysParamCheckConstant.SysConfig.VALUE_MAX_LENGTH) String configValue,
            @Length(max = SysParamCheckConstant.SysConfig.SUB_VALUE_MAX_LENGTH) String configSubValue) {
        SysConfig config = new SysConfig();
        config.setConfigModule(module);
        config.setConfigKey(configKey);
        config.setConfigSubKey(configSubKey);
        config.setConfigValue(configValue);
        config.setConfigSubValue(configSubValue);
        sysConfigService.add(config);
        return ApiResult.success(config);
    }

    @PostMapping("/delete/list/id")
    public ApiResult<Object> deleteByIdList(@NotEmpty @Size(max = ParamCheckConstant.LIST_MAX_SIZE) List<Long> idList) {
        sysConfigService.deleteByIdList(idList);
        return ApiResult.success();
    }

    @PostMapping("/modify/id")
    public ApiResult<Object> modifyById(
            @NotNull Long id,
            @Length(max = SysParamCheckConstant.SysConfig.MODULE_MAX_LENGTH) String module,
            @Length(max = SysParamCheckConstant.SysConfig.KEY_MAX_LENGTH) String configKey,
            @Length(max = SysParamCheckConstant.SysConfig.SUB_KEY_MAX_LENGTH) String configSubKey,
            @Length(max = SysParamCheckConstant.SysConfig.VALUE_MAX_LENGTH) String configValue,
            @Length(max = SysParamCheckConstant.SysConfig.SUB_VALUE_MAX_LENGTH) String configSubValue
    ) {
        SysConfig config = sysConfigService.getById(id);
        if (config == null) {
            return ApiResult.error(1000, "配置不存在");
        }
        config = new SysConfig();
        config.setId(id);
        config.setConfigModule(module);
        config.setConfigKey(configKey);
        config.setConfigSubKey(configSubKey);
        config.setConfigValue(configValue);
        config.setConfigSubValue(configSubValue);
        sysConfigService.updateById(config);
        return ApiResult.success();
    }


    @GetMapping("/list/page")
    public ApiResult<List<SysConfig>> listPage(
            @NotNull @Min(ParamCheckConstant.PAGE_MIN_INDEX) Long pageIndex,
            @NotNull @Min(ParamCheckConstant.PAGE_MIN_SIZE) @Max(ParamCheckConstant.PAGE_MAX_SIZE) Long pageSize,
            @Length(max = SysParamCheckConstant.SysConfig.MODULE_MAX_LENGTH) String module,
            @Length(max = SysParamCheckConstant.SysConfig.KEY_MAX_LENGTH) String configKey,
            @Length(max = SysParamCheckConstant.SysConfig.SUB_KEY_MAX_LENGTH) String configSubKey,
            @Length(max = SysParamCheckConstant.SysConfig.VALUE_MAX_LENGTH) String configValue,
            @Length(max = SysParamCheckConstant.SysConfig.SUB_VALUE_MAX_LENGTH) String configSubValue) {
        Page<SysConfig> page = new Page<>(pageIndex, pageSize);
        LambdaQueryWrapper<SysConfig> wrapper = Wrappers.<SysConfig>lambdaQuery()
                .orderByAsc(Arrays.asList(SysConfig::getConfigModule, BaseEntity::getCreateTime));
        wrapper.like(StringUtils.isNotBlank(module), SysConfig::getConfigModule, module);
        wrapper.like(StringUtils.isNotBlank(configKey), SysConfig::getConfigKey, configKey);
        wrapper.like(StringUtils.isNotBlank(configSubKey), SysConfig::getConfigSubKey, configSubKey);
        wrapper.like(StringUtils.isNotBlank(configValue), SysConfig::getConfigValue, configValue);
        wrapper.like(StringUtils.isNotBlank(configSubValue), SysConfig::getConfigSubValue, configSubValue);
        sysConfigService.page(page, wrapper);
        return PageResult.success(page);
    }

}

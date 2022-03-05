package tech.xs.system.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.xs.framework.annotation.doc.Api;
import tech.xs.system.domain.bo.config.SetMailConfigBo;
import tech.xs.system.service.SysService;
import org.springframework.validation.annotation.Validated;
import tech.xs.framework.domain.model.ApiResult;

import javax.validation.constraints.NotBlank;

/**
 * 系统通用Controller
 *
 * @author 沈家文
 * @date 2020/10/6
 */
@Validated
@RestController
@RequestMapping("/sys")
public class SysController extends BaseSysController {

    /**
     * 健康监测接口
     *
     * @return 始终返回ok
     */
    @GetMapping("/health")
    public ApiResult<Object> health() {
        return ApiResult.success();
    }




}

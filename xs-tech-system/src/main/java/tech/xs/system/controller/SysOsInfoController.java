package tech.xs.system.controller;

import tech.xs.framework.domain.model.ApiResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统信息Controller
 *
 * @author 沈家文
 * @date 2021/7/1 16:33
 */
@Validated
@RestController
@RequestMapping("/sys/os/info")
public class SysOsInfoController extends BaseSysController {

    /**
     * 获取当前系统信息
     *
     * @return
     */
    @GetMapping("/current")
    public ApiResult current() {
        return ApiResult.success(sysOsInfoService.current());
    }
}

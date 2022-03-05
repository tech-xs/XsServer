package tech.xs.doc.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.xs.doc.domain.entity.DocApiResponseCode;
import tech.xs.framework.annotation.doc.Api;
import tech.xs.framework.domain.model.ApiResult;

import java.util.List;

/**
 * 文档接口项目码Controller
 *
 * @author 沈家文
 * @date 2021/7/8 17:16
 */
@Validated
@RestController
@RequestMapping("/doc/api/response/code")
public class DocApiResponseCodeController extends BaseDocController {

    /**
     * 获取全局通用响应
     *
     * @return 返回全局响应码列表
     */
    @GetMapping("/list/global")
    @Api(name = "获取全局通用响应码")
    public ApiResult<List<DocApiResponseCode>> listGlobalResponseCode() {
        return ApiResult.success(docApiResponseCodeService.listGlobalResponseCode());
    }

}

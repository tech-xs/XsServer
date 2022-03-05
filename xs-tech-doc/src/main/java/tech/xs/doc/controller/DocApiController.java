package tech.xs.doc.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.xs.common.lang.StringUtils;
import tech.xs.doc.domain.entity.*;
import tech.xs.doc.enums.HttpSourceType;
import tech.xs.framework.annotation.doc.Api;
import tech.xs.framework.annotation.doc.Param;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.system.domain.entity.SysApi;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 接口文档Controller
 *
 * @author 沈家文
 * @date 2021/5/25 11:59
 */
@Validated
@RestController
@RequestMapping("/doc/api")
public class DocApiController extends BaseDocController {

    /**
     * 根据接口id获取文档详情
     *
     * @param resourceId 接口ID
     * @return 返回文档详情
     */
    @GetMapping("/details/resourceId")
    @Api(name = "根据接口ID查询文档详情")
    public ApiResult<DocApi> getDetailsByResourceId(
            @Param(title = "接口ID") @NotNull Long resourceId) {
        return ApiResult.success(docApiService.getDetailsByResourceId(resourceId));
    }

    /**
     * 保存接口文档
     *
     * @return 返回成功或失败
     */
    @PostMapping("/save")
    @Api(name = "保存文档")
    public ApiResult<Object> save(@RequestBody DocApi doc) {
        SysApi resource = doc.getApi();
        if (doc.getApiId() == null) {
            if (resource == null) {
                return ApiResult.invalidParameter("resourceId");
            } else if (resource.getId() == null) {
                return ApiResult.invalidParameter("resourceId");
            }
            doc.setApiId(resource.getId());
        }
        if (!sysUriResourceService.existById(doc.getApiId())) {
            return ApiResult.error(1000, "接口不存在");
        }
        if (doc.getId() == null) {
            boolean exist = docApiService.exist(Wrappers.<DocApi>lambdaQuery()
                    .eq(DocApi::getApiId, doc.getApiId()));
            if (exist) {
                return ApiResult.error(1001, "接口文档已经存在");
            }
        } else {
            Long docId = doc.getId();
            if (!docApiService.existById(docId)) {
                return ApiResult.error(1002, "接口文档不存在");
            }
        }

        ApiResult<Object> result = checkResponseCodeList(doc);
        if (result != null) {
            return result;
        }
        result = checkRequestParameterList(doc);
        if (result != null) {
            return result;
        }
        result = checkResponseParameterList(doc);
        if (result != null) {
            return result;
        }

        result = checkBodyList(doc);
        if (result != null) {
            return result;
        }

        docApiService.save(doc);
        return ApiResult.success();
    }

    private ApiResult<Object> checkBodyList(DocApi doc) {
        List<DocApiBody> bodyList = doc.getBodyList();
        if (CollectionUtils.isEmpty(bodyList)) {
            return null;
        }
        if (doc.getId() != null) {
            List<Long> bodyIdList = new ArrayList<>();
            for (DocApiBody body : bodyList) {
                if (body.getId() != null) {
                    bodyIdList.add(body.getId());
                }
            }
            if (!bodyIdList.isEmpty()) {
                if (docApiBodyService.exist(Wrappers.<DocApiBody>lambdaQuery()
                        .in(BaseEntity::getId, bodyIdList)
                        .ne(DocApiBody::getApiId, doc.getId()))) {
                    return ApiResult.error(1400, "请求响应内容与文档ID不匹配");
                }
            }
        }
        for (DocApiBody body : bodyList) {
            List<DocApiBodyExample> exampleList = body.getExampleList();
            if (CollectionUtils.isEmpty(exampleList)) {
                return ApiResult.invalidParameter("exampleList", "不能为空");
            }
            for (DocApiBodyExample example : exampleList) {
                if (StringUtils.isBlank(example.getExampleValue())) {
                    return ApiResult.invalidParameter("exampleValue", "不能为空");
                }
                if (example.getSourceType() == null) {
                    return ApiResult.invalidParameter("sourceType", "不能为空");
                }
                if (example.getValueFormat() == null) {
                    return ApiResult.invalidParameter("valueFormat", "不能为空");
                }
            }
        }
        return null;
    }

    /**
     * 保存文档时,校验文档中的响应参数信息
     *
     * @return 校验通过返回 null 否则返回 错误信息
     */
    private ApiResult<Object> checkResponseParameterList(DocApi doc) {
        List<DocApiParm> responseParameterList = doc.getRespParmList();
        if (CollectionUtils.isEmpty(responseParameterList)) {
            return null;
        }
        if (doc.getId() != null) {
            List<Long> responseParameterIdList = new ArrayList<>();
            for (DocApiParm responseParameter : responseParameterList) {
                if (responseParameter.getId() != null) {
                    responseParameterIdList.add(responseParameter.getId());
                }
            }
            if (!responseParameterIdList.isEmpty()) {
                if (docApiParmService.exist(Wrappers.<DocApiParm>lambdaQuery()
                        .in(BaseEntity::getId, responseParameterIdList)
                        .ne(DocApiParm::getApiId, doc.getId()))) {
                    return ApiResult.error(1300, "响应参数文档ID不匹配");
                }
                if (docApiParmService.exist(Wrappers.<DocApiParm>lambdaQuery()
                        .in(BaseEntity::getId, responseParameterIdList)
                        .ne(DocApiParm::getSourceType, HttpSourceType.RESPONSE))) {
                    return ApiResult.error(1301, "响应参数ID与数据源类型不匹配");
                }
            }
        }
        HashSet<String> responseParameterSet = new HashSet<>();
        for (DocApiParm responseParameter : responseParameterList) {
            if (StringUtils.isBlank(responseParameter.getParmName())) {
                return ApiResult.invalidParameter("parameterName", "不能为空");
            }
            if (StringUtils.isBlank(responseParameter.getParmTitle())) {
                return ApiResult.invalidParameter("parameterTitle", "不能为空");
            }
            if (responseParameter.getDataType() == null) {
                return ApiResult.invalidParameter("dataType", "不能为空");
            }
            responseParameterSet.add(responseParameter.getParmName());
        }
        if (responseParameterSet.size() != responseParameterList.size()) {
            return ApiResult.error(1305, "响应参数名不能重复");
        }
        return null;
    }

    /**
     * 保存文档时,校验文档中的请求参数信息
     *
     * @return 校验通过返回 null 否则返回 错误信息
     */
    private ApiResult<Object> checkRequestParameterList(DocApi doc) {
        List<DocApiParm> requestParameterList = doc.getReqParmList();
        if (CollectionUtils.isEmpty(requestParameterList)) {
            return null;
        }
        if (doc.getId() != null) {
            List<Long> requestParameterIdList = new ArrayList<>();
            for (DocApiParm requestParameter : requestParameterList) {
                if (requestParameter.getId() != null) {
                    requestParameterIdList.add(requestParameter.getId());
                }
            }
            if (!requestParameterIdList.isEmpty()) {
                if (docApiParmService.exist(Wrappers.<DocApiParm>lambdaQuery()
                        .in(BaseEntity::getId, requestParameterIdList)
                        .ne(DocApiParm::getApiId, doc.getId()))) {
                    return ApiResult.error(1200, "请求参数文档ID不匹配");
                }
                if (docApiParmService.exist(Wrappers.<DocApiParm>lambdaQuery()
                        .in(BaseEntity::getId, requestParameterIdList)
                        .ne(DocApiParm::getSourceType, HttpSourceType.REQUEST))) {
                    return ApiResult.error(1201, "请求参数ID与数据源类型不匹配");
                }
            }
        }
        HashSet<String> requestParameterSet = new HashSet<>();
        for (DocApiParm requestParameter : requestParameterList) {
            if (StringUtils.isBlank(requestParameter.getParmName())) {
                return ApiResult.invalidParameter("parameterName", "不能为空");
            }
            if (StringUtils.isBlank(requestParameter.getParmTitle())) {
                return ApiResult.invalidParameter("parameterTitle", "不能为空");
            }
            if (requestParameter.getDataType() == null) {
                return ApiResult.invalidParameter("dataType", "不能为空");
            }
            requestParameterSet.add(requestParameter.getParmName());
        }
        if (requestParameterSet.size() != requestParameterList.size()) {
            return ApiResult.error(1205, "请求参数名不能重复");
        }
        return null;
    }

    /**
     * 保存文档时 校验文档中的响应码列表
     *
     * @param doc 文档
     * @return 如果响应码校验失败返回错误信息, 否则返回 null
     */
    private ApiResult<Object> checkResponseCodeList(DocApi doc) {
        List<DocApiResponseCode> responseCodeList = doc.getRespCodeList();
        if (CollectionUtils.isEmpty(responseCodeList)) {
            return null;
        }
        if (doc.getId() != null) {
            List<Long> respCodeIdList = new ArrayList<>();
            for (DocApiResponseCode respCode : responseCodeList) {
                if (respCode.getId() != null) {
                    respCodeIdList.add(respCode.getId());
                }
            }
            if (!respCodeIdList.isEmpty()) {
                if (docApiResponseCodeService.exist(Wrappers.<DocApiResponseCode>lambdaQuery()
                        .in(BaseEntity::getId, respCodeIdList)
                        .ne(DocApiResponseCode::getApiId, doc.getId()))) {
                    return ApiResult.error(1100, "响应码ID与文档ID不匹配");
                }
            }
        }

        HashSet<Integer> respCodeSet = new HashSet<>();
        for (DocApiResponseCode item : responseCodeList) {
            if (StringUtils.isBlank(item.getCodeDescribe())) {
                return ApiResult.invalidParameter("codeDescribe", "不能为空");
            }
            if (item.getCode() == null) {
                return ApiResult.invalidParameter("code", "不能为空");
            }
            respCodeSet.add(item.getCode());
        }
        if (respCodeSet.size() != responseCodeList.size()) {
            return ApiResult.error(1103, "响应码不能重复");
        }
        return null;
    }

}

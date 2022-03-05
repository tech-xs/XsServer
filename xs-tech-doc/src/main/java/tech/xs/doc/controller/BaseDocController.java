package tech.xs.doc.controller;


import tech.xs.doc.service.DocApiBodyService;
import tech.xs.doc.service.DocApiParmService;
import tech.xs.doc.service.DocApiResponseCodeService;
import tech.xs.doc.service.DocApiService;
import tech.xs.system.service.SysApiService;

import javax.annotation.Resource;

/**
 * 文档Controller基类
 *
 * @author 沈家文
 * @date 2021/5/25 11:59
 */
public class BaseDocController {

    @Resource
    protected DocApiService docApiService;

    @Resource
    protected DocApiResponseCodeService docApiResponseCodeService;

    @Resource
    protected DocApiParmService docApiParmService;

    @Resource
    protected SysApiService sysUriResourceService;

    @Resource
    protected DocApiBodyService docApiBodyService;


}

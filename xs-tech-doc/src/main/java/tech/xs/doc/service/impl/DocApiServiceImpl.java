package tech.xs.doc.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import tech.xs.common.collections.CollectionUtils;
import tech.xs.common.lang.ArrayUtils;
import tech.xs.common.lang.StringUtils;
import tech.xs.doc.constant.ConstraintTypeConstant;
import tech.xs.doc.convert.ParameterTypeConvert;
import tech.xs.doc.dao.DocApiDao;
import tech.xs.doc.domain.entity.*;
import tech.xs.doc.enums.HttpSourceType;
import tech.xs.doc.enums.ParameterType;
import tech.xs.doc.service.DocApiService;
import tech.xs.framework.annotation.doc.*;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.enums.BooleanEnum;
import tech.xs.framework.enums.HttpMethodEnum;
import tech.xs.framework.exception.DocException;
import tech.xs.framework.util.SpringUtil;
import tech.xs.system.domain.entity.SysApi;
import tech.xs.system.domain.entity.SysApiGroup;
import tech.xs.system.service.SysApiGroupService;
import tech.xs.system.service.SysApiService;

import javax.annotation.Resource;
import javax.validation.constraints.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * 文档接口Service 实现类
 *
 * @author 沈家文
 * @date 2021/7/8 17:16
 */
@Service
@Slf4j
public class DocApiServiceImpl extends BaseDocServiceImpl<DocApiDao, DocApi> implements DocApiService {

    @Resource
    private WebApplicationContext applicationContext;
    @Resource
    private DocApiDao docApiDao;
    @Resource
    private SysApiService sysApiService;
    @Resource
    private SysApiGroupService sysApiGroupService;

    @Override
    public DocApi getDetailsByResourceId(Long resourceId) {
        DocApi docInterface = docApiDao.selectOne(Wrappers.<DocApi>lambdaQuery().eq(DocApi::getApiId, resourceId));
        if (docInterface == null) {
            return null;
        }
        docInterface.setApi(sysApiService.getById(resourceId));
        List<DocApiParm> requestParameterList = docApiParmService.list(Wrappers.<DocApiParm>lambdaQuery()
                .eq(DocApiParm::getApiId, docInterface.getId())
                .eq(DocApiParm::getSourceType, HttpSourceType.REQUEST)
                .orderByAsc(DocApiParm::getParmSort));
        docInterface.setReqParmList(requestParameterList);

        List<DocApiParm> responseParameterList = docApiParmService.list(Wrappers.<DocApiParm>lambdaQuery()
                .eq(DocApiParm::getApiId, docInterface.getId())
                .eq(DocApiParm::getSourceType, HttpSourceType.RESPONSE)
                .orderByAsc(DocApiParm::getParmSort));
        docInterface.setReqParmList(requestParameterList);
        docInterface.setRespParmList(responseParameterList);

        List<DocApiParm> parameterList = new ArrayList<>();
        parameterList.addAll(requestParameterList);
        parameterList.addAll(responseParameterList);

        if (CollectionUtils.isNotEmpty(parameterList)) {
            for (DocApiParm parameter : parameterList) {
                parameter.setExampleList(docApiParmExampleService.list(Wrappers.<DocApiParmExample>lambdaQuery()
                        .eq(DocApiParmExample::getParmId, parameter.getId())
                        .orderByAsc(DocApiParmExample::getSort)));
                parameter.setConstraintList(docApiParmConstraintService.list(Wrappers.<DocApiParmConstraint>lambdaQuery()
                        .eq(DocApiParmConstraint::getParmId, parameter.getId())
                        .orderByAsc(DocApiParmConstraint::getConstraintSort)));
                parameter.setConstList(docConstService.listDetails(docApiParmConstService.selectConstByParmId(parameter.getId())));
            }
        }
        List<DocApiBody> bodyList = docApiBodyService.list(Wrappers.<DocApiBody>lambdaQuery()
                .eq(DocApiBody::getApiId, docInterface.getId())
                .orderByAsc(DocApiBody::getSort));
        if (CollectionUtils.isNotEmpty(bodyList)) {
            for (DocApiBody body : bodyList) {
                body.setExampleList(docApiBodyExampleService.list(Wrappers.<DocApiBodyExample>lambdaQuery()
                        .eq(DocApiBodyExample::getBodyId, body.getId())
                        .orderByAsc(DocApiBodyExample::getSort)));
            }
        }
        docInterface.setBodyList(bodyList);

        List<DocApiResponseCode> responseCodeList = docApiResponseCodeService.list(Wrappers.<DocApiResponseCode>lambdaQuery()
                .eq(DocApiResponseCode::getApiId, docInterface.getId())
                .orderByAsc(DocApiResponseCode::getCode));
        docInterface.setRespCodeList(responseCodeList);

        return docInterface;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(DocApi doc) {
        if (doc.getId() == null) {
            docApiDao.insert(doc);
        } else {
            if (existById(doc.getId())) {
                docApiDao.updateById(doc);
            } else {
                docApiDao.insert(doc);
            }
        }
        deleteDetails(doc.getId());
        saveRequestParameterList(doc);

        saveResponseCodeList(doc);
        saveResponseParameterList(doc);
        saveBodyList(doc);
    }

    @Override
    public void deleteDetails(Long docId) {
        List<DocApiParm> parmList = docApiParmService.list(Wrappers.<DocApiParm>lambdaQuery().select(BaseEntity::getId).eq(DocApiParm::getApiId, docId));
        if (CollectionUtils.isNotEmpty(parmList)) {
            List<Long> parmIdList = new ArrayList<>();
            for (DocApiParm parameter : parmList) {
                parmIdList.add(parameter.getId());
            }
            docApiParmService.delete(Wrappers.<DocApiParm>lambdaQuery().eq(DocApiParm::getApiId, docId));
            docApiParmExampleService.delete(Wrappers.<DocApiParmExample>lambdaQuery().in(DocApiParmExample::getParmId, parmIdList));
            docApiParmConstraintService.delete(Wrappers.<DocApiParmConstraint>lambdaQuery().in(DocApiParmConstraint::getParmId, parmIdList));
            docApiParmConstService.delete(Wrappers.<DocApiParmConst>lambdaQuery().in(DocApiParmConst::getParmId, parmIdList));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generate() throws IOException, ClassNotFoundException, IllegalAccessException {
        generateApiGroup();
        generateApi();
        generateDoc();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generateDoc() throws IOException, ClassNotFoundException, IllegalAccessException {
        generateConstantDoc();
//        generateApiDoc();
    }

    private void generateApiGroup() throws IOException, ClassNotFoundException {
        log.info("开始构建Api组信息");
        List<SysApiGroup> currList = getCurrApiGroupList();
        if (CollectionUtils.isEmpty(currList)) {
            log.info("未查询到Api组件信息");
            return;
        }
        // 存在则更新,不存在则删除
        List<SysApiGroup> dbList = sysApiGroupService.list();
        List<SysApiGroup> updateList = new ArrayList<>();
        List<SysApiGroup> addList = new ArrayList<>();
        List<SysApiGroup> deleteList = new ArrayList<>();

        for (SysApiGroup currGroup : currList) {
            boolean isExist = false;
            for (SysApiGroup dbGroup : dbList) {
                if (currGroup.getClassName().equals(dbGroup.getClassName())) {
                    currGroup.setId(dbGroup.getId());
                    updateList.add(currGroup);
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                addList.add(currGroup);
            }
        }
        for (SysApiGroup dbGroup : dbList) {
            boolean isExist = false;
            for (SysApiGroup currGroup : currList) {
                if (currGroup.getClassName().equals(dbGroup.getClassName())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                deleteList.add(dbGroup);
            }
        }
        sysApiGroupService.updateById(updateList);
        sysApiGroupService.add(addList);
        sysApiGroupService.deleteById(deleteList);
        log.info("Api组信息构建结束");
    }

    private List<SysApiGroup> getCurrApiGroupList() throws IOException, ClassNotFoundException {
        List<SysApiGroup> groupList = new ArrayList<>();
        Set<Class<?>> classSet = new HashSet<>();
        List<Class<?>> controllerClassList = SpringUtil.getAnnotationClassList(Controller.class);
        if (CollectionUtils.isNotEmpty(controllerClassList)) {
            classSet.addAll(controllerClassList);
        }
        List<Class<?>> restControllerClassList = SpringUtil.getAnnotationClassList(RestController.class);
        if (CollectionUtils.isNotEmpty(restControllerClassList)) {
            classSet.addAll(restControllerClassList);
        }
        List<Class<?>> apiGroupClassList = SpringUtil.getAnnotationClassList(ApiGroup.class);
        if (CollectionUtils.isNotEmpty(apiGroupClassList)) {
            classSet.addAll(apiGroupClassList);
        }
        for (Class<?> classItem : classSet) {
            SysApiGroup group = new SysApiGroup();
            group.setName(classItem.getSimpleName());
            group.setClassName(classItem.getName());
            ApiGroup groupAnnotation = classItem.getAnnotation(ApiGroup.class);
            if (groupAnnotation != null) {
                if (StringUtils.isNotBlank(groupAnnotation.name())) {
                    group.setName(groupAnnotation.name());
                }
            }
            groupList.add(group);
        }
        return groupList;
    }

    private void generateConstantDoc() throws IOException, ClassNotFoundException, IllegalAccessException {
        docConstService.delete(Wrappers.lambdaQuery());
        docConstValueService.delete(Wrappers.lambdaQuery());
        docApiParmConstService.delete(Wrappers.lambdaQuery());
        List<Class<?>> docConstantClassList = SpringUtil.getAnnotationClassList(Quote.class);
        for (Class<?> docConstantClass : docConstantClassList) {
            Quote constantAnnotation = docConstantClass.getAnnotation(Quote.class);
            DocConst docConst = new DocConst();
            docConst.setTitle(constantAnnotation.title());
            docConst.setClassName(docConstantClass.getName());
            docConstService.add(docConst);
            Field[] fields = docConstantClass.getDeclaredFields();
            if (ArrayUtils.isEmpty(fields)) {
                continue;
            }
            for (Field field : fields) {
                QuoteValue valueAnnotation = field.getAnnotation(QuoteValue.class);
                if (valueAnnotation == null) {
                    continue;
                }
                DocConstValue docConstValue = new DocConstValue();
                docConstValue.setConstId(docConst.getId());
                docConstValue.setTitle(valueAnnotation.title());
                Object value = field.get(docConstantClass);
                if (value == null) {
                    continue;
                }
                docConstValue.setConstantValue(value.toString());
                docConstValueService.add(docConstValue);
            }
        }
    }

    private void generateApiDoc() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> meethodMap = mapping.getHandlerMethods();
        meethodMap.forEach((requestMapping, handlerMethod) -> {
            DocApi doc = getApiDoc(requestMapping, handlerMethod);
            if (doc != null) {
                log.info("构建Api文档:" + doc.getApi());
                save(doc);
            }
        });
    }

    private DocApi getApiDoc(RequestMappingInfo requestMapping, HandlerMethod handlerMethod) {
        String uri = getUri(requestMapping);
        if (StringUtils.isBlank(uri)) {
            return null;
        }
        HttpMethodEnum requestMethod = getMethod(requestMapping);
        if (requestMethod == null) {
            requestMethod = HttpMethodEnum.ALL;
        }

        Api docAnnotation = handlerMethod.getMethodAnnotation(Api.class);
        List<SysApi> resourceList = sysApiService.list(Wrappers.<SysApi>lambdaQuery()
                .eq(SysApi::getUri, uri)
                .eq(SysApi::getMethod, requestMethod));
        if (CollectionUtils.isEmpty(resourceList)) {
            return null;
        }
        SysApi resource = resourceList.get(0);
        String title = docAnnotation.name();
        if (StringUtils.isBlank(title)) {
            throw new DocException(uri, DocApi.class.getName() + " 注解[title]字段不能为空");
        }

        DocApi doc = new DocApi();
        doc.setApiId(resource.getId());
        doc.setApiDescribe(title);

        List<DocApi> docList = docApiDao.selectList(Wrappers.<DocApi>lambdaQuery().eq(DocApi::getApiId, resource.getId()));
        if (CollectionUtils.isNotEmpty(docList)) {
            doc.setId(docList.get(0).getId());
        }
        doc.setReqParmList(getRequestParameter(handlerMethod));
        return doc;
    }

    private List<DocApiParm> getRequestParameter(HandlerMethod handlerMethod) {
        MethodParameter[] parameters = handlerMethod.getMethodParameters();
        if (ArrayUtils.isEmpty(parameters)) {
            return null;
        }
        List<DocApiParm> interfaceParameterList = new ArrayList<>();
        for (MethodParameter methodParameter : parameters) {
            DocApiParm docParameter = new DocApiParm();
            docParameter.setSourceType(HttpSourceType.REQUEST);

            Param docParamAnnotation = methodParameter.getParameterAnnotation(Param.class);
            if (docParamAnnotation != null) {
                docParameter.setParmTitle(docParamAnnotation.title());
                String[] describe = docParamAnnotation.describe();
                if (describe.length > 0) {
                    StringBuilder builder = new StringBuilder();
                    for (String item : describe) {
                        builder.append(item);
                        builder.append("\n");
                    }
                    docParameter.setParmDescribe(builder.toString());
                }
                if (StringUtils.isNotBlank(docParamAnnotation.name())) {
                    docParameter.setParmName(docParamAnnotation.name());
                }
            }

            Parameter parameter = methodParameter.getParameter();
            if (StringUtils.isBlank(docParameter.getParmName())) {
                docParameter.setParmName(parameter.getName());
            }
            docParameter.setDataType(ParameterTypeConvert.getType(parameter.getType()));
            docParameter.setConstraintList(getParameterConstraint(docParameter, methodParameter));
            docParameter.setConstList(getConstList(methodParameter));
            docParameter.setExampleList(getApiParmExample(methodParameter));
            interfaceParameterList.add(docParameter);
        }
        return interfaceParameterList;
    }

    private List<DocApiParmExample> getApiParmExample(MethodParameter methodParameter) {
        List<DocApiParmExample> exampleList = new ArrayList<>();
        Param docParam = methodParameter.getParameterAnnotation(Param.class);
        if (docParam == null) {
            return exampleList;
        }
        ParamExample[] exampleArray = docParam.example();
        for (ParamExample paramExample : exampleArray) {
            DocApiParmExample example = new DocApiParmExample();
            example.setValueFormat(paramExample.format());
            String value = paramExample.value();
            if (StringUtils.isBlank(value)) {
                continue;
            }
            example.setExampleValue(value);
            exampleList.add(example);
        }
        return exampleList;
    }

    private List<DocConst> getConstList(MethodParameter methodParameter) {
        Param docParam = methodParameter.getParameterAnnotation(Param.class);
        if (docParam == null) {
            return null;
        }
        Class<?>[] citeArray = docParam.quote();
        if (ArrayUtils.isEmpty(citeArray)) {
            return null;
        }
        List<DocConst> docConstList = new ArrayList<>();
        for (Class<?> citeClass : citeArray) {
            List<DocConst> list = docConstService.list(Wrappers.<DocConst>lambdaQuery()
                    .eq(DocConst::getClassName, citeClass.getName()));
            if (CollectionUtils.isNotEmpty(list)) {
                docConstList.addAll(list);
            }
        }
        return docConstList;
    }

    private List<DocApiParmConstraint> getParameterConstraint(DocApiParm docParameter, MethodParameter methodParameter) {
        List<DocApiParmConstraint> constraintList = new ArrayList<>();
        Param docParamAnnotation = methodParameter.getParameterAnnotation(Param.class);
        if (docParamAnnotation != null) {
            String[] constraintStrList = docParamAnnotation.constraint();
            for (String item : constraintStrList) {
                DocApiParmConstraint constraint = new DocApiParmConstraint();
                constraint.setConstraintType(ConstraintTypeConstant.CUSTOM);
                constraint.setConstraintDescribe(item);
                constraintList.add(constraint);
            }
        }
        NotNull notNull = methodParameter.getParameterAnnotation(NotNull.class);
        if (notNull != null) {
            DocApiParmConstraint constraint = new DocApiParmConstraint();
            constraint.setConstraintType(ConstraintTypeConstant.NOT_NULL);
            constraint.setConstraintDescribe(ConstraintTypeConstant.NOT_NULL_MSG);
            constraintList.add(constraint);
        }
        Min min = methodParameter.getParameterAnnotation(Min.class);
        if (min != null) {
            DocApiParmConstraint constraint = new DocApiParmConstraint();
            constraint.setConstraintType(ConstraintTypeConstant.MIN);
            constraint.setConstraintValue(min.value());
            constraint.setConstraintDescribe(ConstraintTypeConstant.MIN_MSG + constraint.getConstraintValue());
            constraintList.add(constraint);
        }
        Max max = methodParameter.getParameterAnnotation(Max.class);
        if (max != null) {
            DocApiParmConstraint constraint = new DocApiParmConstraint();
            constraint.setConstraintType(ConstraintTypeConstant.MAX);
            constraint.setConstraintValue(max.value());
            constraint.setConstraintDescribe(ConstraintTypeConstant.MAX_MSG + constraint.getConstraintValue());
            constraintList.add(constraint);
        }
        Length length = methodParameter.getParameterAnnotation(Length.class);
        if (length != null) {
            if (length.min() != 0) {
                DocApiParmConstraint constraint = new DocApiParmConstraint();
                constraint.setConstraintType(ConstraintTypeConstant.LENGTH_MIN);
                constraint.setConstraintValue((long) length.min());
                constraint.setConstraintDescribe(ConstraintTypeConstant.LENGTH_MIN_MSG + constraint.getConstraintValue());
                if (docParameter.getDataType() == ParameterType.STRING) {
                    constraint.setConstraintDescribe(constraint.getConstraintDescribe() + "个字符");
                }
                constraintList.add(constraint);
            }
            if (length.max() != Integer.MAX_VALUE) {
                DocApiParmConstraint constraint = new DocApiParmConstraint();
                constraint.setConstraintType(ConstraintTypeConstant.LENGTH_MAX);
                constraint.setConstraintValue((long) length.max());
                constraint.setConstraintDescribe(ConstraintTypeConstant.LENGTH_MAX_MSG + constraint.getConstraintValue());
                if (docParameter.getDataType() == ParameterType.STRING) {
                    constraint.setConstraintDescribe(constraint.getConstraintDescribe() + "个字符");
                }
                constraintList.add(constraint);
            }
        }
        NotBlank notBlank = methodParameter.getParameterAnnotation(NotBlank.class);
        if (notBlank != null) {
            DocApiParmConstraint constraint = new DocApiParmConstraint();
            constraint.setConstraintType(ConstraintTypeConstant.NOT_BLANK);
            constraint.setConstraintDescribe(ConstraintTypeConstant.NOT_BLANK_MSG);
            constraintList.add(constraint);
        }
        NotEmpty notEmpty = methodParameter.getParameterAnnotation(NotEmpty.class);
        if (notEmpty != null) {
            DocApiParmConstraint constraint = new DocApiParmConstraint();
            constraint.setConstraintType(ConstraintTypeConstant.NOT_EMPTY);
            constraint.setConstraintDescribe(ConstraintTypeConstant.NOT_EMPTY_MSG);
            constraintList.add(constraint);
        }
        Size size = methodParameter.getParameterAnnotation(Size.class);
        if (size != null) {
            if (size.min() != 0) {
                DocApiParmConstraint constraint = new DocApiParmConstraint();
                constraint.setConstraintType(ConstraintTypeConstant.SIZE_MIN);
                constraint.setConstraintValue((long) size.min());
                constraint.setConstraintDescribe(ConstraintTypeConstant.SIZE_MIN_MSG + constraint.getConstraintValue());
                constraintList.add(constraint);
            }
            if (size.max() != Integer.MAX_VALUE) {
                DocApiParmConstraint constraint = new DocApiParmConstraint();
                constraint.setConstraintType(ConstraintTypeConstant.SIZE_MAX);
                constraint.setConstraintValue((long) size.max());
                constraint.setConstraintDescribe(ConstraintTypeConstant.SIZE_MAX_MSG + constraint.getConstraintValue());
                constraintList.add(constraint);
            }
        }
        return constraintList;
    }

    private void saveBodyList(DocApi doc) {
        List<DocApiBody> bodyList = doc.getBodyList();
        if (bodyList == null) {
            return;
        }
        if (bodyList.isEmpty()) {
            docApiBodyService.delete(Wrappers.<DocApiBody>lambdaQuery()
                    .eq(DocApiBody::getApiId, doc.getId()));
            return;
        }
        List<DocApiBody> addList = new ArrayList<>();
        List<DocApiBody> updateList = new ArrayList<>();
        List<Long> existIdList = new ArrayList<>();
        for (DocApiBody body : bodyList) {
            body.setApiId(doc.getId());
            body.setSort(bodyList.indexOf(body));
            if (body.getId() == null) {
                addList.add(body);
            } else {
                updateList.add(body);
                existIdList.add(body.getId());
            }
        }
        if (existIdList.isEmpty()) {
            docApiBodyService.delete(Wrappers.<DocApiBody>lambdaQuery()
                    .eq(DocApiBody::getApiId, doc.getId()));
        } else {
            docApiBodyService.delete(Wrappers.<DocApiBody>lambdaQuery()
                    .eq(DocApiBody::getApiId, doc.getId())
                    .notIn(BaseEntity::getId, existIdList));
        }
        if (!addList.isEmpty()) {
            docApiBodyService.add(addList);
        }
        if (!updateList.isEmpty()) {
            for (DocApiBody body : updateList) {
                docApiBodyService.updateById(body);
            }
        }
        for (DocApiBody body : bodyList) {
            saveDocInterfaceBodyExample(body);
        }
    }

    private void saveDocInterfaceBodyExample(DocApiBody body) {
        List<DocApiBodyExample> exampleList = body.getExampleList();
        if (CollectionUtils.isEmpty(exampleList)) {
            return;
        }
        List<DocApiBodyExample> addList = new ArrayList<>();
        List<DocApiBodyExample> updateList = new ArrayList<>();
        List<Long> existIdList = new ArrayList<>();
        for (DocApiBodyExample example : exampleList) {
            example.setBodyId(body.getId());
            example.setSort(exampleList.indexOf(example));
            if (example.getId() == null) {
                addList.add(example);
            } else {
                updateList.add(example);
                existIdList.add(example.getId());
            }
        }
        if (existIdList.isEmpty()) {
            docApiBodyExampleService.delete(Wrappers.<DocApiBodyExample>lambdaQuery()
                    .eq(DocApiBodyExample::getBodyId, body.getId()));
        } else {
            docApiBodyExampleService.delete(Wrappers.<DocApiBodyExample>lambdaQuery()
                    .eq(DocApiBodyExample::getBodyId, body.getId())
                    .notIn(BaseEntity::getId, existIdList));
        }
        if (!addList.isEmpty()) {
            docApiBodyExampleService.add(addList);
        }
        if (!updateList.isEmpty()) {
            for (DocApiBodyExample example : updateList) {
                docApiBodyExampleService.updateById(example);
            }
        }
    }

    private void saveResponseParameterList(DocApi doc) {
        List<DocApiParm> responseParameterList = doc.getRespParmList();
        if (responseParameterList == null) {
            return;
        }
        for (DocApiParm responseParameter : responseParameterList) {
            responseParameter.setApiId(doc.getId());
            responseParameter.setSourceType(HttpSourceType.RESPONSE);
            responseParameter.setParmSort(responseParameterList.indexOf(responseParameter));
        }
        docApiParmService.add(responseParameterList);
    }

    private void saveRequestParameterList(DocApi doc) {
        List<DocApiParm> reqParmList = doc.getReqParmList();
        if (CollectionUtils.isEmpty(reqParmList)) {
            return;
        }
        for (DocApiParm reqParm : reqParmList) {
            reqParm.setApiId(doc.getId());
            reqParm.setSourceType(HttpSourceType.REQUEST);
            reqParm.setParmSort(reqParmList.indexOf(reqParm));
        }
        docApiParmService.add(reqParmList);
        for (DocApiParm parm : reqParmList) {
            saveParmConstraintList(parm);
            saveParmConstList(parm);
            saveParmExample(parm);
        }
    }

    private void saveParmExample(DocApiParm parm) {
        List<DocApiParmExample> exampleList = parm.getExampleList();
        if (CollectionUtils.isEmpty(exampleList)) {
            return;
        }
        for (int i = 0; i < exampleList.size(); i++) {
            DocApiParmExample example = exampleList.get(i);
            example.setParmId(parm.getId());
            example.setSort(i * 10);
        }
        docApiParmExampleService.add(exampleList);
    }

    private void saveParmConstList(DocApiParm parm) {
        List<DocConst> constList = parm.getConstList();
        if (CollectionUtils.isEmpty(constList)) {
            return;
        }
        for (DocConst docConst : constList) {
            DocApiParmConst docApiParmConst = new DocApiParmConst();
            docApiParmConst.setParmId(parm.getId());
            docApiParmConst.setConstId(docConst.getId());
            docApiParmConstService.add(docApiParmConst);
        }
    }

    private void saveParmConstraintList(DocApiParm parm) {
        List<DocApiParmConstraint> constraintList = parm.getConstraintList();
        if (CollectionUtils.isEmpty(constraintList)) {
            return;
        }
        for (int i = 0; i < constraintList.size(); i++) {
            DocApiParmConstraint constraint = constraintList.get(i);
            constraint.setParmId(parm.getId());
            constraint.setConstraintSort(i * 10);
        }
        docApiParmConstraintService.add(constraintList);
    }

    private void saveResponseCodeList(DocApi doc) {
        List<DocApiResponseCode> responseCodeList = doc.getRespCodeList();
        if (responseCodeList == null) {
            return;
        }
        if (responseCodeList.isEmpty()) {
            docApiResponseCodeService.delete(Wrappers.<DocApiResponseCode>lambdaQuery()
                    .eq(DocApiResponseCode::getApiId, doc.getId()));
            return;
        }
        List<DocApiResponseCode> addList = new ArrayList<>();
        List<DocApiResponseCode> updateList = new ArrayList<>();
        List<Long> existIdList = new ArrayList<>();
        for (DocApiResponseCode item : responseCodeList) {
            item.setApiId(doc.getId());
            if (item.getId() == null) {
                addList.add(item);
            } else {
                updateList.add(item);
                existIdList.add(item.getId());
            }
        }
        if (!existIdList.isEmpty()) {
            docApiResponseCodeService.delete(Wrappers.<DocApiResponseCode>lambdaQuery()
                    .eq(DocApiResponseCode::getApiId, doc.getId())
                    .notIn(BaseEntity::getId, existIdList));
        } else {
            docApiResponseCodeService.delete(Wrappers.<DocApiResponseCode>lambdaQuery()
                    .eq(DocApiResponseCode::getApiId, doc.getId()));
        }
        if (!addList.isEmpty()) {
            docApiResponseCodeService.add(addList);
        }
        if (!updateList.isEmpty()) {
            for (DocApiResponseCode item : updateList) {
                docApiResponseCodeService.updateById(item);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generateApi() {
        log.info("开始扫描接口");
        List<SysApi> currList = getCurrDocApi();
        List<SysApi> dbList = sysApiService.list();
        // 存在则更新,没有在添加,db存在当前不存在则设置状态为不存在
        List<SysApi> addList = new ArrayList<>();
        List<SysApi> updateList = new ArrayList<>();
        List<SysApi> exsitList = new ArrayList<>();
        for (SysApi currApi : currList) {
            boolean exsit = false;
            for (SysApi dbApi : dbList) {
                if (currApi.equals(dbApi)) {
                    currApi.setId(dbApi.getId());
                    currApi.setExist(BooleanEnum.TRUE);
                    updateList.add(currApi);
                    exsit = true;
                    break;
                }
            }
            if (!exsit) {
                currApi.setExist(BooleanEnum.TRUE);
                addList.add(currApi);
            }
        }
        for (SysApi dbApi : dbList) {
            boolean exsit = false;
            for (SysApi currApi : currList) {
                if (dbApi.equals(currApi)) {
                    exsit = true;
                    break;
                }
            }
            if (!exsit) {
                dbApi.setExist(BooleanEnum.FALSE);
                exsitList.add(dbApi);
            }
        }
        if (!addList.isEmpty()) {
            sysApiService.add(addList);
        }
        if (!updateList.isEmpty()) {
            sysApiService.updateById(updateList);
        }
        if (!exsitList.isEmpty()) {
            sysApiService.updateById(exsitList);
        }
        log.info("接口扫描完毕");
    }

    @Override
    public List<SysApi> getCurrDocApi() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
        List<SysApi> apiList = new ArrayList<>();
        if (methodMap.isEmpty()) {
            return apiList;
        }

        methodMap.forEach((requestMapping, handlerMethod) -> {
            SysApi api = new SysApi();
            String uri = getUri(requestMapping);
            if (StringUtils.isBlank(uri)) {
                return;
            }
            api.setUri(uri);
            HttpMethodEnum requestMethod = getMethod(requestMapping);

            api.setMethod(HttpMethodEnum.ALL);
            if (requestMethod != null) {
                api.setMethod(requestMethod);
            }

            api.setName(handlerMethod.getMethod().getName());
            Api docAnnotation = handlerMethod.getMethodAnnotation(Api.class);
            if (docAnnotation != null) {
                String title = docAnnotation.name();
                if (StringUtils.isNotBlank(title)) {
                    api.setName(title);
                }
            }
            String beanName = handlerMethod.getBeanType().getName();
            List<SysApiGroup> groupList = sysApiGroupService.list(Wrappers.<SysApiGroup>lambdaQuery()
                    .eq(SysApiGroup::getClassName, beanName));
            if (CollectionUtils.isNotEmpty(groupList)) {
                api.setGroupId(groupList.get(0).getId());
            }
            apiList.add(api);
        });

        return apiList;
    }

    private HttpMethodEnum getMethod(RequestMappingInfo requestMapping) {
        RequestMethodsRequestCondition methodsCondition = requestMapping.getMethodsCondition();
        Set<RequestMethod> methodSet = methodsCondition.getMethods();
        if (CollectionUtils.isEmpty(methodSet)) {
            return null;
        }
        ArrayList<RequestMethod> methodList = new ArrayList<>(methodSet);
        RequestMethod method = methodList.get(0);
        return HttpMethodEnum.valueOf(method.name());
    }

    private String getUri(RequestMappingInfo requestMapping) {
        PatternsRequestCondition patternsCondition = requestMapping.getPatternsCondition();
        Set<String> patternSet = patternsCondition.getPatterns();
        if (CollectionUtils.isEmpty(patternSet)) {
            return null;
        }
        ArrayList<String> patternList = new ArrayList<>(patternSet);
        return patternList.get(0);
    }

}

package tech.xs.framework.util;


import tech.xs.framework.domain.model.ApiResult;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Set;

/**
 * 验证工具
 *
 * @author 沈家文
 * @date 2021-30-12 16:30
 */
public class ValidatorUtil {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验遇到第一个不合法的字段直接返回不合法字段，后续字段不再校验
     *
     * @param checkValue 校验值
     * @param <T>        校验类型
     * @return 返回结果集
     */
    public static <T> ApiResult validate(T checkValue) {
        Set<ConstraintViolation<T>> validateResultSet = validator.validate(checkValue);
        if (validateResultSet.isEmpty()) {
            return null;
        }
        for (ConstraintViolation<T> validateResult : validateResultSet) {
            String message = validateResult.getMessage();
            Path propertyPath = validateResult.getPropertyPath();
            return ApiResult.invalidParameter(propertyPath.toString(), message);
        }
        return null;
    }

    /**
     * 校验列表
     *
     * @param checkList 校验的列表
     * @param <T>       校验的泛型
     * @return 返回校验结果
     */
    public static <T> ApiResult validate(Collection<T> checkList) {
        if (checkList == null) {
            return null;
        }
        for (T checkValue : checkList) {
            ApiResult result = validate(checkValue);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

}

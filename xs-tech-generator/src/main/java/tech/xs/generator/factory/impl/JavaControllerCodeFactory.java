package tech.xs.generator.factory.impl;

import com.alibaba.fastjson.JSONArray;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import tech.xs.common.constant.FileConstant;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.constant.DefaultConstant;
import tech.xs.generator.converter.JavaTypeConverter;
import tech.xs.generator.domain.entity.GeneratorColumn;
import tech.xs.generator.domain.entity.GeneratorTable;
import tech.xs.generator.factory.CodeFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller 代码生成
 *
 * @author imsjw
 * Create Time: 2021/1/20
 */
@Component
public class JavaControllerCodeFactory extends AbstractCodeFactory implements CodeFactory {

    @Resource
    protected FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${generator.java-controller.out-path:}")
    private String outPath;

    @Value("${generator.java-controller.template-path:freemarker/generator/JavaController.ftl}")
    private String templatePath;

    @Value("${generator.template-encoding:UTF-8}")
    private String templateEncoding;

    @Value("${generator.java-controller.package:}")
    private String javaPackage;

    @Value("${generator.java-controller.extends-class-name:}")
    private String extendsClassName;

    @Value("${generator.author:author}")
    private String author;

    @Value("${generator.java-entity.package:}")
    private String javaEntityPackage;

    @Override
    public void generator(GeneratorTable table, List<GeneratorColumn> columns) {
        String fileName = StringUtils.underlineToCamel(table.getName(), true) + "Controller" + FileConstant.FILE_SUFFIX_JAVA;
        generator(table, columns, freeMarkerConfigurer, outPath, templatePath, templateEncoding, fileName);
    }

    @Override
    public Map<String, Object> buildModel(GeneratorTable table, List<GeneratorColumn> columns) {
        HashMap model = new HashMap<>(DefaultConstant.HASH_MAP_SIZE);
        model.put("package", javaPackage);
        String entityClassName = StringUtils.underlineToCamel(table.getName(), true);
        model.put("entityClassName", entityClassName);
        model.put("className", entityClassName + "Controller");
        model.put("extendsClassName", extendsClassName);
        String requestPath = "/" + table.getName().replaceAll("_", "/");
        model.put("requestPath", requestPath);
        if (StringUtils.isNotBlank(author)) {
            model.put("author", author);
        }
        String useEntityName = StringUtils.underlineToCamel(table.getName(), false);
        model.put("useEntityName", useEntityName);
        String useServiceName = useEntityName + "Service";
        model.put("useServiceName", useServiceName);
        model.put("javaEntityPackage", javaEntityPackage);

        String jsonString = JSONArray.toJSONString(columns);
        List<HashMap> generatorColumns = new ArrayList<>();
        List<HashMap> columnMaps = JSONArray.parseArray(jsonString, HashMap.class);
        for (HashMap map : columnMaps) {
            String javaType = JavaTypeConverter.converter((String) map.get("fieldType"));
            if (StringUtils.isNotBlank(javaType)) {
                map.put("javaType", javaType);
            }
            String columnName = (String) map.get("name");
            map.remove("name");
            if (StringUtils.equalsAny(columnName, "id", "update_time", "update_user", "create_time", "create_user")) {
                continue;
            }
            String fieldName = StringUtils.underlineToCamel(columnName, false);
            map.put("fieldName", fieldName);
            String getSetFieldName = StringUtils.underlineToCamel(columnName, true);
            map.put("getSetFieldName", getSetFieldName);
            generatorColumns.add(map);
        }
        model.put("columns", generatorColumns);
        return model;
    }

}

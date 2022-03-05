package tech.xs.generator.factory.impl;

import com.alibaba.fastjson.JSONArray;
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
 * 实体类生成工厂
 *
 * @author imsjw
 * Create Time: 2021/1/17
 */
@Component
public class JavaEntityCodeFactory extends AbstractCodeFactory implements CodeFactory {

    @Resource
    protected FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${generator.java-entity.template-path:freemarker/generator/JavaEntity.ftl}")
    private String templatePath;

    @Value("${generator.template-encoding:UTF-8}")
    private String templateEncoding;

    @Value("${generator.java-entity.package:}")
    private String javaPackage;

    @Value("${generator.java-entity.out-path:}")
    private String outPath;

    @Value("${generator.author:author}")
    private String author;

    @Value("${generator.java-entity.extends-class-name:}")
    private String extendsClassName;

    @Value("${generator.java-entity.extends-class-package:}")
    private String extendsClassPackage;

    @Override
    public void generator(GeneratorTable table, List<GeneratorColumn> columns) {
        String fileName = StringUtils.underlineToCamel(table.getName(), true) + FileConstant.FILE_SUFFIX_JAVA;
        generator(table, columns, freeMarkerConfigurer, outPath, templatePath, templateEncoding, fileName);
    }

    @Override
    protected Map<String, Object> buildModel(GeneratorTable table, List<GeneratorColumn> columnList) {
        Map<String, Object> model = new HashMap<>(DefaultConstant.HASH_MAP_SIZE);
        model.put("package", javaPackage);
        model.put("tableName", table.getName());
        if (StringUtils.isNotBlank(table.getRemark())) {
            model.put("tableDescribe", table.getRemark());
        }
        if (StringUtils.isNotBlank(author)) {
            model.put("author", author);
        }
        model.put("className", StringUtils.underlineToCamel(table.getName(), true));
        if (StringUtils.isNotBlank(extendsClassName)) {
            model.put("extendsClassName", extendsClassName);
        }
        if (StringUtils.isNotBlank(extendsClassPackage)) {
            model.put("extendsClassPackage", extendsClassPackage);
        }
        String jsonString = JSONArray.toJSONString(columnList);
        List<HashMap> generatorColumns = new ArrayList<>();
        List<HashMap> columnMaps = JSONArray.parseArray(jsonString, HashMap.class);
        for (HashMap map : columnMaps) {
            String javaType = JavaTypeConverter.converter((String) map.get("fieldType"));
            if (StringUtils.isNotBlank(javaType)) {
                map.put("javaType", javaType);
            }
            String columnName = (String) map.get("name");
            map.put("columnName", columnName);
            map.remove("name");
            if (StringUtils.equalsAny(columnName, "id", "update_time", "update_user", "create_time", "create_user")) {
                continue;
            }
            String fieldName = StringUtils.underlineToCamel(columnName, false);
            map.put("fieldName", fieldName);
            generatorColumns.add(map);
        }
        model.put("columns", generatorColumns);
        return model;
    }

}

package tech.xs.generator.factory.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import tech.xs.common.constant.FileConstant;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.constant.DefaultConstant;
import tech.xs.generator.domain.entity.GeneratorColumn;
import tech.xs.generator.domain.entity.GeneratorTable;
import tech.xs.generator.factory.CodeFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service生成工厂
 *
 * @author imsjw
 * Create Time: 2021/1/20
 */
@Component
public class JavaServiceCodeFactory extends AbstractCodeFactory implements CodeFactory {

    @Resource
    protected FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${generator.java-service.out-path:}")
    private String outPath;

    @Value("${generator.java-service.template-path:freemarker/generator/JavaService.ftl}")
    private String templatePath;

    @Value("${generator.template-encoding:UTF-8}")
    private String templateEncoding;

    @Value("${generator.java-service.package:}")
    private String javaPackage;

    @Value("${generator.java-entity.package:}")
    private String javaEntityPackage;

    @Value("${generator.java-service.extends-class-name:}")
    private String extendsClassName;

    @Value("${generator.author:author}")
    private String author;

    @Override
    public void generator(GeneratorTable table, List<GeneratorColumn> columns) {
        String fileName = StringUtils.underlineToCamel(table.getName(), true) + "Service" + FileConstant.FILE_SUFFIX_JAVA;
        generator(table, columns, freeMarkerConfigurer, outPath, templatePath, templateEncoding, fileName);
    }

    @Override
    protected Map<String, Object> buildModel(GeneratorTable table, List<GeneratorColumn> columns) {
        HashMap model = new HashMap<>(DefaultConstant.HASH_MAP_SIZE);
        model.put("package", javaPackage);
        model.put("package", javaPackage);
        String entityClassName = StringUtils.underlineToCamel(table.getName(), true);
        model.put("className", entityClassName + "Service");
        model.put("entityClassName", entityClassName);
        model.put("entityPackage", javaEntityPackage);
        model.put("extendsClassName", extendsClassName);
        if (StringUtils.isNotBlank(author)) {
            model.put("author", author);
        }
        return model;
    }

}

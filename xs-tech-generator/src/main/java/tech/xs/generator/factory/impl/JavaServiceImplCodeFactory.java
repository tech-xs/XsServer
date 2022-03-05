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
 * Service实现类代码生成工厂
 *
 * @author imsjw
 * Create Time: 2021/1/20
 */
@Component
public class JavaServiceImplCodeFactory extends AbstractCodeFactory implements CodeFactory {

    @Resource
    protected FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${generator.java-service-impl.out-path:}")
    private String outPath;

    @Value("${generator.java-service-impl.template-path:freemarker/generator/JavaServiceImpl.ftl}")
    private String templatePath;

    @Value("${generator.template-encoding:UTF-8}")
    private String templateEncoding;

    @Value("${generator.java-service-impl.package:}")
    private String javaPackage;

    @Value("${generator.java-service-impl.extends-class-name:}")
    private String extendsClassName;

    @Value("${generator.author:author}")
    private String author;

    @Value("${generator.java-entity.package:}")
    private String javaEntityPackage;

    @Value("${generator.java-dao.package:}")
    private String javaDaoPackage;

    @Value("${generator.java-service.package:}")
    private String javaServicePackage;

    @Override
    public void generator(GeneratorTable table, List<GeneratorColumn> columns) {
        String fileName = StringUtils.underlineToCamel(table.getName(), true) + "ServiceImpl" + FileConstant.FILE_SUFFIX_JAVA;
        generator(table, columns, freeMarkerConfigurer, outPath, templatePath, templateEncoding, fileName);
    }

    @Override
    protected Map<String, Object> buildModel(GeneratorTable table, List<GeneratorColumn> columns) {
        HashMap model = new HashMap<>(DefaultConstant.HASH_MAP_SIZE);
        model.put("package", javaPackage);
        String entityClassName = StringUtils.underlineToCamel(table.getName(), true);
        model.put("className", entityClassName + "ServiceImpl");
        model.put("entityClassName", entityClassName);
        String daoClassName = entityClassName + "Dao";
        model.put("daoClassName", daoClassName);
        model.put("extendsClassName", extendsClassName);
        String implementsInterfaceName = entityClassName + "Service";
        model.put("implementsInterfaceName", implementsInterfaceName);
        if (StringUtils.isNotBlank(author)) {
            model.put("author", author);
        }
        model.put("javaDaoPackage", javaDaoPackage);
        model.put("javaEntityPackage", javaEntityPackage);
        model.put("javaServicePackage", javaServicePackage);
        return model;
    }

}

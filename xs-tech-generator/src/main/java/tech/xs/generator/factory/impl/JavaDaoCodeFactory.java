package tech.xs.generator.factory.impl;

import org.apache.http.util.TextUtils;
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
 * Dao代码生成工厂
 *
 * @author imsjw
 * Create Time: 2021/1/20
 */
@Component
public class JavaDaoCodeFactory extends AbstractCodeFactory implements CodeFactory {

    @Resource
    protected FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${generator.java-dao.template-path:freemarker/generator/JavaDao.ftl}")
    private String templatePath;

    @Value("${generator.java-dao.out-path:}")
    private String outPath;

    @Value("${generator.template-encoding:UTF-8}")
    private String templateEncoding;

    @Value("${generator.java-dao.package:}")
    private String javaPackage;

    @Value("${generator.java-entity.package:}")
    private String javaEntityPackage;

    @Value("${generator.author:author}")
    private String author;

    @Override
    public void generator(GeneratorTable table, List<GeneratorColumn> columns) {
        String fileName = StringUtils.underlineToCamel(table.getName(), true) + "Dao" + FileConstant.FILE_SUFFIX_JAVA;
        generator(table, columns, freeMarkerConfigurer, outPath, templatePath, templateEncoding, fileName);
    }

    @Override
    protected Map<String, Object> buildModel(GeneratorTable table, List<GeneratorColumn> columns) {
        HashMap model = new HashMap<>(DefaultConstant.HASH_MAP_SIZE);
        model.put("package", javaPackage);
        String entityClassName = StringUtils.underlineToCamel(table.getName(), true);
        model.put("className", entityClassName + "Dao");
        model.put("entityClassName", entityClassName);
        model.put("entityPackage", javaEntityPackage);
        if (StringUtils.isNotBlank(author)) {
            model.put("author", author);
        }
        return model;
    }

}

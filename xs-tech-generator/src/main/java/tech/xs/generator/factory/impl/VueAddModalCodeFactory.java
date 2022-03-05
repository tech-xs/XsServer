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
 * 代码生成工厂
 * 用于生成 页面上的添加弹出框
 *
 * @author imsjw
 * Create Time: 2021/1/21
 */
@Component
public class VueAddModalCodeFactory extends AbstractCodeFactory implements CodeFactory {

    @Resource
    protected FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${generator.vue-add-modal.out-path:}")
    private String outPath;

    @Value("${generator.template-encoding:UTF-8}")
    private String templateEncoding;

    @Value("${generator.vue-add-modal.template-path:freemarker/generator/VueAddModal.ftl}")
    private String templatePath;

    @Override
    public void generator(GeneratorTable table, List<GeneratorColumn> columns) {
        String fileName = StringUtils.underlineToCamel(table.getName(), true) + "Service" + FileConstant.FILE_SUFFIX_JAVA;
        generator(table, columns, freeMarkerConfigurer, outPath, templatePath, templateEncoding, fileName);
    }

    @Override
    protected Map<String, Object> buildModel(GeneratorTable table, List<GeneratorColumn> columns) {
        HashMap model = new HashMap<>(DefaultConstant.HASH_MAP_SIZE);
        return model;
    }

}

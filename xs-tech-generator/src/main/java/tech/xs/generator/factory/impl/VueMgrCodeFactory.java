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
 * Vue管理页面生成工厂
 *
 * @author imsjw
 * Create Time: 2021/1/26
 */
@Component
public class VueMgrCodeFactory extends AbstractCodeFactory implements CodeFactory {

    @Resource
    protected FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${generator.template-encoding:UTF-8}")
    private String templateEncoding;

    @Value("${generator.vue-manage.out-path:}")
    private String outPath;

    @Value("${generator.vue-manage.template-path:freemarker/generator/VueMgr.ftl}")
    private String templatePath;

    @Override
    public void generator(GeneratorTable table, List<GeneratorColumn> columns) {
        String fileName = StringUtils.underlineToCamel(table.getName(), true) + "Mgr" + FileConstant.FILE_SUFFIX_VUE;
        generator(table, columns, freeMarkerConfigurer, outPath, templatePath, templateEncoding, fileName);
    }

    @Override
    protected Map<String, Object> buildModel(GeneratorTable table, List<GeneratorColumn> columns) {
        HashMap model = new HashMap<>(DefaultConstant.HASH_MAP_SIZE);
        String entityName = StringUtils.underlineToCamel(table.getName(), true);
        String addModalComponentName = "Add" + entityName;
        String componentDirName = StringUtils.underlineToCamel(table.getName(), false);
        model.put("addModalComponentName", addModalComponentName);
        model.put("componentDirName", componentDirName);
        String modifyModalComponentName = "Modify" + entityName;
        model.put("modifyModalComponentName", modifyModalComponentName);
        String componentName = entityName + "Mgr";
        model.put("componentName", componentName);
        return model;
    }

}

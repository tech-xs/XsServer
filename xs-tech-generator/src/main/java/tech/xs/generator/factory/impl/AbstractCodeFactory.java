package tech.xs.generator.factory.impl;

import cn.hutool.core.io.FileUtil;
import freemarker.template.Template;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import tech.xs.common.lang.StringUtils;
import tech.xs.generator.domain.entity.GeneratorColumn;
import tech.xs.generator.domain.entity.GeneratorTable;
import tech.xs.generator.exception.GeneratorException;
import tech.xs.generator.factory.CodeFactory;

import java.util.List;
import java.util.Map;

/**
 * 抽象代码生成工厂
 *
 * @author imsjw
 * Create Time: 2021/2/15
 */
public abstract class AbstractCodeFactory implements CodeFactory {

    protected void generator(GeneratorTable table, List<GeneratorColumn> columns, FreeMarkerConfigurer freeMarkerConfigurer, String outPath, String templatePath, String templateEncoding, String fileName) {
        try {
            if (StringUtils.isBlank(outPath)) {
                throw new GeneratorException("file out path is not null");
            }
            Map<String, Object> model = buildModel(table, columns);
            if (model == null) {
                return;
            }
            Template template = freeMarkerConfigurer.getConfiguration()
                    .getTemplate(templatePath, templateEncoding);
            template.setOutputEncoding(templateEncoding);
            String fileContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            FileUtil.writeString(fileContent, outPath + fileName, templateEncoding);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成代码时,构建模板引擎的model
     *
     * @param table
     * @param columns
     * @return
     */
    protected abstract Map<String, Object> buildModel(GeneratorTable table, List<GeneratorColumn> columns);

}

package tech.xs.generator.factory;

import tech.xs.generator.domain.entity.GeneratorColumn;
import tech.xs.generator.domain.entity.GeneratorTable;

import java.io.IOException;
import java.util.List;

/**
 * 代码生成工厂
 *
 * @author imsjw
 * Create Time: 2021/1/15
 */
public interface CodeFactory {


    /**
     * 根据表信息生成代码文件
     *
     * @param table
     * @param columns
     */
    void generator(GeneratorTable table, List<GeneratorColumn> columns);

}

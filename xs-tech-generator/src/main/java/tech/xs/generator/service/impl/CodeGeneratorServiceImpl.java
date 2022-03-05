package tech.xs.generator.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import tech.xs.generator.domain.entity.GeneratorColumn;
import tech.xs.generator.domain.entity.GeneratorTable;
import tech.xs.generator.factory.CodeFactory;
import tech.xs.generator.factory.impl.*;
import tech.xs.generator.service.CodeGeneratorService;
import tech.xs.generator.service.GeneratorColumnService;
import tech.xs.generator.service.GeneratorTableService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成Service实现类
 *
 * @author imsjw
 * Create Time: 2021/1/17
 */
@Service
public class CodeGeneratorServiceImpl implements CodeGeneratorService {

    @Resource
    private JavaEntityCodeFactory javaEntityCodeFactory;

    @Resource
    private JavaDaoCodeFactory javaDaoCodeFactory;

    @Resource
    private JavaServiceCodeFactory javaServiceCodeFactory;

    @Resource
    private JavaServiceImplCodeFactory javaServiceImplCodeFactory;

    @Resource
    private JavaControllerCodeFactory javaControllerCodeFactory;

    @Resource
    private VueServiceCodeFactory vueServiceCodeFactory;

    @Resource
    private VueAddModalCodeFactory vueAddModalCodeFactory;

    @Resource
    private VueMgrCodeFactory vueMgrCodeFactory;

    @Resource
    private GeneratorColumnService generatorColumnService;

    @Resource
    private GeneratorTableService generatorTableService;


    @Override
    public void generator() {
        List<GeneratorTable> tables = generatorTableService.list();
        List<CodeFactory> codeFactoryList = new ArrayList<>();
        codeFactoryList.add(javaEntityCodeFactory);
        codeFactoryList.add(javaDaoCodeFactory);
        codeFactoryList.add(javaServiceCodeFactory);
        codeFactoryList.add(javaServiceImplCodeFactory);
        codeFactoryList.add(javaControllerCodeFactory);
        codeFactoryList.add(vueServiceCodeFactory);
        codeFactoryList.add(vueAddModalCodeFactory);
        codeFactoryList.add(vueMgrCodeFactory);
        for (GeneratorTable table : tables) {
            List<GeneratorColumn> columns = generatorColumnService.list(Wrappers.<GeneratorColumn>lambdaQuery().eq(GeneratorColumn::getTableId, table.getId()));
            for (CodeFactory factory : codeFactoryList) {
                factory.generator(table, columns);
            }
        }
    }

}

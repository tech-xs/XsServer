package tech.xs.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.xs.generator.dao.GeneratorTableDao;
import tech.xs.generator.domain.entity.GeneratorTable;
import tech.xs.generator.service.GeneratorTableService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 代码生成模块 表Service实现类
 *
 * @author imsjw
 * Create Time: 2021/1/3
 */
@Slf4j
@Service
public class GeneratorTableServiceImpl extends BaseGeneratorServiceImpl<GeneratorTableDao, GeneratorTable> implements GeneratorTableService {

}

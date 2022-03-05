package tech.xs.generator;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.xs.generator.GeneratorApplication;
import tech.xs.generator.service.CodeGeneratorService;

import javax.annotation.Resource;


@Slf4j
@SpringBootTest(classes = GeneratorApplication.class)
@RunWith(SpringRunner.class)
public class Generator {

    @Resource
    private CodeGeneratorService codeGeneratorService;

    @Test
    public void generatorEntity() {
        codeGeneratorService.generator();
    }

}

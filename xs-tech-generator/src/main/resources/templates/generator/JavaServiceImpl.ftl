package ${package};

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ${javaDaoPackage}.${daoClassName};
import ${javaEntityPackage}.${entityClassName};
import ${javaServicePackage}.${implementsInterfaceName};

/**
<#if author??>
 * @author ${author}
</#if>
 * @date ${.now?string["yyyy/MM/dd HH:mm"]}
 */
@Slf4j
@Service
public class ${className} extends ${extendsClassName}<${daoClassName}, ${entityClassName}> implements ${implementsInterfaceName} {
}

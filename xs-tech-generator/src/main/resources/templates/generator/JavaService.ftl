package ${package};

import ${entityPackage}.${entityClassName};

/**
<#if author??>
 * @author ${author}
</#if>
 * @date ${.now?string["yyyy/MM/dd HH:mm"]}
 */
public interface ${className} extends ${extendsClassName}<${entityClassName}> {
}
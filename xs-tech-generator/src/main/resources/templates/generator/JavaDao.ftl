package ${package};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${entityPackage}.${entityClassName};

/**
<#if author??>
 * @author ${author}
</#if>
 * @date ${.now?string["yyyy/MM/dd HH:mm"]}
 */
public interface ${className} extends BaseMapper<${entityClassName}> {
}
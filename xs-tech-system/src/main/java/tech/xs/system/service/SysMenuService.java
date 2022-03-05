package tech.xs.system.service;

import tech.xs.system.constant.SysParamCheckConstant;
import tech.xs.system.domain.bo.menu.DeleteMenuBo;
import tech.xs.system.domain.bo.menu.ModifyMenuBo;
import tech.xs.system.domain.entity.SysMenu;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * 菜单Service
 *
 * @author 沈家文
 * @date 2020/10/22
 */
public interface SysMenuService extends BaseSysService<SysMenu> {

    /**
     * 查询树状菜单,包含禁用的菜单
     *
     * @return
     */
    List<SysMenu> tree();

    /**
     * 根据菜单Id列表构建菜单树
     *
     * @param menuIds
     * @return
     */
    List<SysMenu> tree(Collection<Long> menuIds);

    /**
     * 深度遍历获取菜单列表
     *
     * @param menuIds
     * @return
     */
    List<SysMenu> listDepthChild(Collection<Long> menuIds);

    List<SysMenu> listDepthChild(Long menuId);

    /**
     * 是否是子节点
     *
     * @param fatherId
     * @param childId
     * @return
     */
    boolean isChild(Long fatherId, Long childId);


    /**
     * 根据ID更新菜单,并更新对应的父ID
     * 如果父ID为空,则将数据库中的父菜单ID也设置为空
     *
     * @param bo
     * @author 沈家文
     * @date 2021/6/4 9:29
     */
    void updateById(ModifyMenuBo bo);

    void deleteDetails(DeleteMenuBo bo);
}

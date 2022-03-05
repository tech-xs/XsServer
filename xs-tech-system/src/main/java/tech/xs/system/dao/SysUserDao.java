package tech.xs.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tech.xs.system.domain.entity.SysUser;

/**
 * @author 沈家文
 * @date 2020/8/5
 */
public interface SysUserDao extends BaseMapper<SysUser> {

    /**
     * 更新用户密码
     *
     * @param userId   用户ID
     * @param password 用户密码
     */
    @Update("update sys_user set login_password = #{password} where id = #{userId}")
    void updateLoginPassword(@Param("userId") Long userId, @Param("password") String password);

    /**
     * 检查用户密码是否正确
     *
     * @param userId
     * @param password
     * @return
     */
    @Select("select count(0) from sys_user where id = #{userId} and login_password = #{password}")
    boolean checkLoginPassword(@Param("userId") Long userId, @Param("password") String password);


}

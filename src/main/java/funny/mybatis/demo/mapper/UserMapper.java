package funny.mybatis.demo.mapper;

import funny.mybatis.demo.model.SysRole;
import funny.mybatis.demo.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 16:56 2018/1/30
 */
public interface UserMapper {
    SysUser selectById(Long id);
    List<SysUser> selectAll();
    List<SysRole> selectRoleByUserId(Long userId);
    int insert(SysUser sysUser);
    int insertGetKey(SysUser sysUser);
    int insertSelectKey(SysUser sysUser);
    int updateById(SysUser sysUser);
    int deleteById(SysUser sysUser);

    List<SysRole> selectRolesByUserIdAndRoleEnable(@Param("userId") Long userId, @Param("enabled") Integer enabled);

    List<SysUser> selectByUser(SysUser sysUser);
    int updateByIdSelective(SysUser sysUser);
    int insert2(SysUser sysUser);
    SysUser selectByIdOrUserName(SysUser sysUser);

    List<SysUser> selectByIdList(@Param("idList") List<Long> idList);
    int insertList(List<SysUser> userList);
    int updateByMap(Map<String,Object> map);

    SysUser selectUserAndRoleById(Long id);
    SysUser selectUserAndRoleById2(Long id);
    SysUser selectUserAndRoleByIdSelect(Long id);
    List<SysUser> selectAllUserAndRoles();

    /**
     * 通过嵌套查询获取指定用户的信息以及用户的角色和权限信息
     * @param id
     * @return
     */
    SysUser selectAllUserAndRolesSelect(Long id);
    void selectUserById(SysUser user);
    List<SysUser> selectUserPage(Map<String,Object> params);
    void insertUserAndRoles(@Param("user")SysUser user,@Param("roleIds")String roleIds);
    int deleteUserById(Long id);
}

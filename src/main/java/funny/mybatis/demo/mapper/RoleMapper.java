package funny.mybatis.demo.mapper;

import funny.mybatis.demo.model.SysRole;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 16:56 2018/1/30
 */
@CacheNamespaceRef(RoleMapper.class)
public interface RoleMapper {
    @Select({"SELECT id,role_name AS roleName ,enabled,create_by AS createBy ," +
            "create_time AS createTime FROM sys_role where id =#{id}"})
    SysRole selectById(Long id);
    @Results(id = "roleResultMap",value = {
        @Result(property = "id" , column = "id" ,id = true),
        @Result(property = "roleName" , column = "role_name"),
        @Result(property = "enabled" , column = "enabled"),
        @Result(property = "createBy" , column = "create_by"),
        @Result(property = "createTime", column = "create_time")
    })
    @Select({"SELECT id,role_name,enabled,create_by ," +
            "create_time  FROM sys_role where id =#{id}"})
    SysRole selectById2(Long id);

    @ResultMap("roleResultMap")
    @Select("SELECT * FROM sys_role")
//    List<SysRole> selectAll();
    List<SysRole> selectAll(RowBounds rowBounds);
    @Insert({"INSERT INTO sys_role(id,role_name,enabled,create_by,create_time)" +
            "VALUES (#{id},#{roleName},#{enabled},#{createBy},#{createTime,jdbcType = TIMESTAMP})"
    })
    int insert(SysRole sysRole);
    //返回自增主键
    @Insert({"INSERT INTO sys_role(role_name,enabled,create_by,create_time)" +
            "VALUES (#{roleName},#{enabled},#{createBy},#{createTime,jdbcType = TIMESTAMP})"
    })
    @Options(useGeneratedKeys = true , keyProperty = "id")
    int insertGetKey(SysRole sysRole);
    //返回非自增主键
    @Insert({"INSERT INTO sys_role(role_name,enabled,create_by,create_time)" +
            "VALUES (#{roleName},#{enabled},#{createBy},#{createTime,jdbcType = TIMESTAMP})"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()",keyProperty = "id",resultType = Long.class,before = false)
    int insertSelectKey(SysRole sysRole);

    @Update({"UPDATE sys_role SET " +
            "role_name = #{roleName},enabled = #{enabled},create_by =#{createBy},create_time = #{createTime,jdbcType=TIMESTAMP}" +
            "WHERE id = #{id}"
    })
    int updateById(SysRole sysRole);
    @Delete("DELETE FROM sys_role WHERE id = #{id}")
    int deleteById(Long id);

    List<SysRole> selectAllRoleAndPrivileges();
    List<SysRole> selectRoleByUserIdChoose(Long userId);
}

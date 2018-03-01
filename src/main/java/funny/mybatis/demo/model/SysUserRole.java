package funny.mybatis.demo.model;

/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 16:50 2018/1/30
 */
public class SysUserRole {
    private Long userId;
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}

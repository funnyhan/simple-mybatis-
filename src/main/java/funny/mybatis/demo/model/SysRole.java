package funny.mybatis.demo.model;

import funny.mybatis.demo.type.Enabled;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 16:51 2018/1/30
 */
public class SysRole implements Serializable{
    private static final long serialVersionUID=6320941908222962112L;
    private Long id;
    private String roleName;
    //private Integer enabled;
    private Enabled enabled;
    private Long createBy;
    private Date createTime;

    List<SysPrivilege> privilegeList;

    public List<SysPrivilege> getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(List<SysPrivilege> privilegeList) {
        this.privilegeList = privilegeList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Enabled getEnabled() {
        return enabled;
    }

    public void setEnabled(Enabled enabled) {
        this.enabled = enabled;
    }

    /*   public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }*/

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

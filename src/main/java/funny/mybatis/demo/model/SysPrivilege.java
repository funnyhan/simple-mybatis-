package funny.mybatis.demo.model;

/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 16:52 2018/1/30
 */
public class SysPrivilege {
    private Long id;
    private String privilegeName;
    private String privilegeUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getPrivilegeUrl() {
        return privilegeUrl;
    }

    public void setPrivilegeUrl(String privilegeUrl) {
        this.privilegeUrl = privilegeUrl;
    }
}

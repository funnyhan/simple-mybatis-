package funny.mybatis.demo.Provider;

import org.apache.ibatis.jdbc.SQL;

/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 17:22 2018/1/31
 */
public class PrivilegeProvider {
    public String selectById(final Long id){
        /*return new SQL(){
            SELECT("id,privilege_name,privilege_url");
            FROM("sys_privilege");
            WHERE("id = #{id}");
        }.toString();*/
        return "SELECT id,privilege_name AS privilegeName,privilege_url AS privilegeUrl FROM sys_privilege WHERE id=#{id}";
    }
}

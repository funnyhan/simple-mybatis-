package funny.mybatis.demo.mapper;

import funny.mybatis.demo.Provider.PrivilegeProvider;
import funny.mybatis.demo.model.SysPrivilege;
import netscape.security.Privilege;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 16:56 2018/1/30
 */
public interface PrivilegeMapper {
    @SelectProvider(type = PrivilegeProvider.class,method = "selectById")
    SysPrivilege selectById(Long id);
}

package funny.mybatis.demo.mapper;

import funny.mybatis.demo.model.Country;
import funny.mybatis.demo.model.SysPrivilege;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 9:34 2018/1/31
 */
public class PrivilegeMapperTest extends BaseMapperTest{
    @Test
    public void testSelectById(){
        SqlSession sqlSession = getSqlSession();
        try{
            PrivilegeMapper privilegeMapper = sqlSession.getMapper(PrivilegeMapper.class);
            SysPrivilege privilege = privilegeMapper.selectById(1L);
            Assert.assertNotNull(privilege);
            Assert.assertEquals("用户管理",privilege.getPrivilegeName());
        }finally {
            sqlSession.close();
        }
    }
}

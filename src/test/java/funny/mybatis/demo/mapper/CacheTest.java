package funny.mybatis.demo.mapper;

import com.mysql.cj.core.log.NullLogger;
import funny.mybatis.demo.model.SysRole;
import funny.mybatis.demo.model.SysUser;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 17:54 2018/2/27
 */
public class CacheTest extends BaseMapperTest{
    @Test
    public void testL1Cache(){
        //获取sqlSession
        SqlSession sqlSession = getSqlSession();
        SysUser user1 = null;
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            user1 = userMapper.selectById(1L);
            user1.setUserName("NEW NAME");
            SysUser user2 = userMapper.selectById(1L);
            Assert.assertEquals("NEW NAME" ,user2.getUserName());
            Assert.assertEquals(user1,user2);
        }finally{
            sqlSession.close();
        }
        System.out.println("开启新的sqlSession");
        sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user2 = userMapper.selectById(1L);
            Assert.assertNotSame("NEW NAME",user2.getUserName());

            Assert.assertNotSame(user1,user2);
            SysUser user4 =new SysUser();
            user4.setId(2L);
            userMapper.deleteById(user4);
            SysUser user3 = userMapper.selectById(1L);
            Assert.assertNotSame(user2,user3);
        }finally{
            sqlSession.close();
        }
    }

    @Test
    public void testL2Cache(){
        //获取sqlSession
        SqlSession sqlSession = getSqlSession();
        SysRole role1 = null;
        try{
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //调用selectById 方法。查询id=1的用户
            role1 = roleMapper.selectById(1L);
            //对当前获取的对象重新赋值
            role1.setRoleName("NEW NAME");
            //再次查询获取id相同的用户
            SysRole role2 = roleMapper.selectById(1L);
            //虽然没有跟新数据库，但是这个用户和role1重新赋值的名字相同
            Assert.assertEquals("NEW NAME",role2.getRoleName());
            //无论如何，role2和role1完全就是同一个实例
            Assert.assertEquals(role1,role2);
        }finally{
            sqlSession.close();
        }
        System.out.println("开启新的sqlSession");
        sqlSession = getSqlSession();
        try{
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //调用selectById 方法。查询id=1的用户
            SysRole role2 = roleMapper.selectById(1L);
            //对当前获取的对象重新赋值
            Assert.assertEquals("NEW NAME",role2.getRoleName());
            //再次查询获取id相同的用户
            Assert.assertNotSame(role1,role2);
            //虽然没有跟新数据库，但是这个用户和role1重新赋值的名字相同
            SysRole role3 = roleMapper.selectById(1L);
            //无论如何，role2和role1完全就是同一个实例
            Assert.assertNotSame(role2,role3);
        }finally{
            sqlSession.close();
        }
    }
}

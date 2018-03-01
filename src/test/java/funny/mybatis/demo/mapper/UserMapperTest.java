package funny.mybatis.demo.mapper;

import com.mysql.cj.core.log.NullLogger;
import funny.mybatis.demo.Proxy.MyMapperProxy;
import funny.mybatis.demo.model.SysPrivilege;
import funny.mybatis.demo.model.SysRole;
import funny.mybatis.demo.model.SysUser;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import javax.management.relation.Role;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 11:15 2018/1/30
 */
public class UserMapperTest extends BaseMapperTest{


    @Test
    public void selectById(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectById(1L);
            Assert.assertNotNull(user);
            Assert.assertEquals("admin", user.getUserName());
        }finally {
            sqlSession.close();
        }

    }

    @Test
    public void selectAll(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> userList = userMapper.selectAll();
            Assert.assertNotNull(userList);
            Assert.assertTrue(userList.size()>0);
        }finally {
            sqlSession.close();
        }

    }

    @Test
    public void testInsert(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个user对象
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassword("123456");
            user.setUserEmail("test@mybatis.funny");
            user.setUserInfo("test info");
            //正常情况下应该读入一张图片存到byte数组中  注：叉
            user.setHeadImg(new byte[]{1,2,3});
            user.setCreateTime(new Date());
            //将新建的对象插入数据库中，特别注意这里的返回值result是执行的sql影响的行数
            int result = userMapper.insert(user);
            Assert.assertEquals(1,result);
            Assert.assertNull(user.getId());
        }finally {
            //为了不影响其他测试,这里选择回滚
            //由于默认的sqlSessionFactory.openSession 不是自动提交的
            //因此不手动执行commit也不会提交到数据库
            sqlSession.rollback();
            //不要忘记关闭sqlSession
            sqlSession.close();
        }
    }
    @Test
    public void testInsertGetKey(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个user对象
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassword("123456");
            user.setUserEmail("test@mybatis.funny");
            user.setUserInfo("test info");
            //正常情况下应该读入一张图片存到byte数组中  注：叉
            user.setHeadImg(new byte[]{1,2,3});
            user.setCreateTime(new Date());
            //将新建的对象插入数据库中，特别注意这里的返回值result是执行的sql影响的行数
            int result = userMapper.insertGetKey(user);
            Assert.assertEquals(1,result);
            Assert.assertNull(user.getId());
        }finally {
            //为了不影响其他测试,这里选择回滚
            //由于默认的sqlSessionFactory.openSession 不是自动提交的
            //因此不手动执行commit也不会提交到数据库
            sqlSession.rollback();
            //不要忘记关闭sqlSession
            sqlSession.close();
        }
    }
    @Test
    public void testInsertSelectKey(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个user对象
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassword("123456");
            user.setUserEmail("test@mybatis.funny");
            user.setUserInfo("test info");
            //正常情况下应该读入一张图片存到byte数组中  注：叉
            user.setHeadImg(new byte[]{1,2,3});
            user.setCreateTime(new Date());
            //将新建的对象插入数据库中，特别注意这里的返回值result是执行的sql影响的行数
            int result = userMapper.insertSelectKey(user);
            Assert.assertEquals(1,result);
            Assert.assertNull(user.getId());
        }finally {
            //为了不影响其他测试,这里选择回滚
            //由于默认的sqlSessionFactory.openSession 不是自动提交的
            //因此不手动执行commit也不会提交到数据库
            sqlSession.rollback();
            //不要忘记关闭sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateById(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个user对象
            SysUser user = userMapper.selectById(1L);
            Assert.assertEquals("admin",user.getUserName());

            user.setUserName("admin_test");
            user.setUserEmail("test@mybatis.funny");
            //将新建的对象插入数据库中，特别注意这里的返回值result是执行的sql影响的行数
            int result = userMapper.updateById(user);
            Assert.assertEquals(1,result);
            user = userMapper.selectById(1L);
            Assert.assertEquals("admin_test",user.getUserName());

        }finally {
            //为了不影响其他测试,这里选择回滚
            //由于默认的sqlSessionFactory.openSession 不是自动提交的
            //因此不手动执行commit也不会提交到数据库
            sqlSession.rollback();
            //不要忘记关闭sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void tesDeleteById(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个user对象
            SysUser user = userMapper.selectById(1L);
            Assert.assertNotNull(user);
            Assert.assertEquals(1,userMapper.deleteById(user));
            Assert.assertNull(userMapper.selectById(1L));


            SysUser user2 = userMapper.selectById(1001L);
            Assert.assertNotNull(user2);
            Assert.assertEquals(1,userMapper.deleteById(user2));
            Assert.assertNull(userMapper.selectById(1001L));
        }finally {
            //为了不影响其他测试,这里选择回滚
            //由于默认的sqlSessionFactory.openSession 不是自动提交的
            //因此不手动执行commit也不会提交到数据库
            sqlSession.rollback();
            //不要忘记关闭sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRolesByUserIsAndRoleEnabled(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个user对象
            SysUser user = userMapper.selectById(1L);
            List<SysRole> sysRoleList = userMapper.selectRolesByUserIdAndRoleEnable(1L,1);
            Assert.assertNotNull(sysRoleList);
            Assert.assertTrue(sysRoleList.size()>0);
        }finally {

            sqlSession.close();
        }
    }

    @Test
    public void testMyMapperProxy(){
        //获取SqlSession
        SqlSession sqlSession = getSqlSession();
        //获取UserMapper接口
        MyMapperProxy userMapperProxy = new MyMapperProxy(UserMapper.class,sqlSession);
        UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{UserMapper.class},
                userMapperProxy
        );
        //调用selectAll方法
        List<SysUser> user =userMapper.selectAll();
    }

    @Test
    public void testSelectByUser(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个user对象
            SysUser query = new SysUser();
            query.setUserName("admin");
            List<SysUser> userList =userMapper.selectByUser(query);
            Assert.assertTrue(userList.size()>0);
            query = new SysUser();
            //只查询用户邮箱时
            query.setUserEmail("test@mybatis.funny");
            userList =userMapper.selectByUser(query);
            Assert.assertTrue(userList.size()>0);
            //当同时查询用户名和邮箱时
            query = new SysUser();
            query.setUserName("admin");
            query.setUserEmail("test@mybatis.funny");
            userList =userMapper.selectByUser(query);
            Assert.assertTrue(userList.size()==0);

        }finally {

            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByIdSelective(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个user对象
            SysUser user = new SysUser();
            user.setId(1L);
            user.setUserEmail("test@mybatis.funny");
            //更新邮箱，特别注意，这里的返回值result执行的是SQL影响的行数
            int result = userMapper.updateByIdSelective(user);
            Assert.assertEquals(1,result);

            user = userMapper.selectById(1L);
            Assert.assertEquals("admin",user.getUserName());
            Assert.assertEquals("test@mybatis.funny",user.getUserEmail());


        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testInsert2Selective(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setUserName("test-selective");
            user.setUserPassword("123456");
            user.setUserInfo("test info");
            user.setCreateTime(new Date());
            userMapper.insert2(user);
            userMapper.selectById(user.getId());
//            Assert.assertEquals("test@mybatis.funny",user.getUserEmail());
        }finally {
//            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByIdOrUserName(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser query = new SysUser();
            query.setId(1L);
            query.setUserName("test-selective");
            SysUser user = userMapper.selectByIdOrUserName(query);
            Assert.assertNotNull(user);
            query.setId(null);
            user = userMapper.selectByIdOrUserName(query);
            Assert.assertNull(user);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByIdList(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser query = new SysUser();
            List<Long> ids =new ArrayList<>();
            ids.add(1L);
            ids.add(1001L);
            List<SysUser> userList = userMapper.selectByIdList(ids);
            Assert.assertNotNull(userList);

            Assert.assertTrue(userList.size()>0);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsertList(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> userList = new ArrayList<>();
            for (int i=0;i<2;i++){
                SysUser user = new SysUser();
                user.setUserName("test"+i);
                user.setUserPassword("123456");
                user.setUserEmail("test@mybatis.funny");
                userList.add(user);
            }

            int result = userMapper.insertList(userList);
            for (SysUser user :userList){
                System.out.println(user.getId());
            }
            Assert.assertEquals(2,result);
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByMap(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            Map<String,Object> map = new HashMap<>();
            map.put("id",1L);
            map.put("user_email","test@mybatis.funny");
            map.put("user_password","12345678");
            userMapper.updateByMap(map);
            SysUser user = userMapper.selectById(1L);
            Assert.assertEquals("test@mybatis.funny",user.getUserEmail());
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectUserAndRoleById(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //特别注意，在测试数据中 ，id =1L的用户有两个角色，不适合这个例子
            //这里使用只有一个角色的
            SysUser user = userMapper.selectUserAndRoleById(1001L);
            //user不为空
            Assert.assertNotNull(user);
            //role也不为空
            Assert.assertNotNull(user.getRole());
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectUserAndRoleById2(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //特别注意，在测试数据中 ，id =1L的用户有两个角色，不适合这个例子
            //这里使用只有一个角色的
            SysUser user = userMapper.selectUserAndRoleById2(1001L);
            //user不为空
            Assert.assertNotNull(user);
            //role也不为空
            Assert.assertNotNull(user.getRole());
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectUserAndRoleByIdSelect(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //特别注意，在测试数据中 ，id =1L的用户有两个角色，不适合这个例子
            //这里使用只有一个角色的
            SysUser user = userMapper.selectUserAndRoleByIdSelect(1001L);
            //user不为空
            System.out.println("调用user.equals(null)");
            user.equals(null);
            //role也不为空
            System.out.println("调用user.getRole()");
            Assert.assertNotNull(user.getRole());
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }
    @Test
    public void testSelectAllUserAndRoles(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //特别注意，在测试数据中 ，id =1L的用户有两个角色，不适合这个例子
            //这里使用只有一个角色的
            List<SysUser> userList = userMapper.selectAllUserAndRoles();
            System.out.println("用户数" + userList.size());
            for (SysUser user :userList){
                System.out.println("用户名:"+user.getUserName());
                for (SysRole role :user.getRoleList()){
                    System.out.println("角色名:"+role.getRoleName());
                    for (SysPrivilege privilege :role.getPrivilegeList()){
                        System.out.println("权限名:" +privilege.getPrivilegeName());
                    }
                }
            }
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAllUserAndRolesSelect(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //特别注意，在测试数据中 ，id =1L的用户有两个角色，不适合这个例子
            //这里使用只有一个角色的
            SysUser user = userMapper.selectAllUserAndRolesSelect(1L);
                System.out.println("用户名:"+user.getUserName());
                for (SysRole role :user.getRoleList()){
                    System.out.println("角色名:"+role.getRoleName());
                    for (SysPrivilege privilege :role.getPrivilegeList()){
                        System.out.println("权限名:" +privilege.getPrivilegeName());
                    }
                }

        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectUserById(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setId(1L);
            userMapper.selectUserById(user);
            Assert.assertNotNull(user.getUserName());
            System.out.println("用户名:"+user.getUserName());

        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectUserPage(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("userName","");
            params.put("offset",0);
            params.put("limit",10);
            List<SysUser> userList = userMapper.selectUserPage(params);
            Long total = (Long)params.get("total");
            System.out.println("总数："+total);
            for (SysUser user :userList){
                System.out.println("用户名："+ user.getUserName());
            }
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsertAndDelete(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassword("123456");
            user.setUserEmail("test@mybatis.funny");
            user.setUserInfo("test info");
            user.setHeadImg(new byte[]{1,2,3});
            //插入用户信息和角色关联信息
            userMapper.insertUserAndRoles(user,"1,2");
            Assert.assertNotNull(user.getId());
            Assert.assertNotNull(user.getCreateTime());
            //可以执行下面的commit后查看数据库中的数据
            //sqlSession.commit();
            //测试删除刚刚插入的数据
            userMapper.deleteUserById(user.getId());


        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testDirtyData(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleById(1001L);
            Assert.assertEquals("普通用户",user.getRole().getRoleName());
            System.out.println("角色名："+user.getRole().getRoleName());

        }finally {
            sqlSession.close();
        }

        System.out.println("开启新的sqlSession");
        sqlSession = getSqlSession();
        try{
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(1L);
            role.setRoleName("脏数据");
            roleMapper.updateById(role);
            sqlSession.commit();

        }finally {
            sqlSession.close();
        }
        System.out.println("开启新的sqlSession");
        sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysUser user = userMapper.selectUserAndRoleById(1001L);
            SysRole role = roleMapper.selectById(1L);
            Assert.assertEquals("普通用户",user.getRole().getRoleName());
            Assert.assertEquals("脏数据",role.getRoleName());
            System.out.println("角色名："+user.getRole().getRoleName());
            //还原数据
            role.setRoleName("普通用户");
            roleMapper.updateById(role);
            sqlSession.commit();

        }finally {
            sqlSession.close();
        }

    }
}


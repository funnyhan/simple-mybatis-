package funny.mybatis.demo.mapper;

import funny.mybatis.demo.model.SysPrivilege;
import funny.mybatis.demo.model.SysRole;
import funny.mybatis.demo.model.SysUser;
import funny.mybatis.demo.plugin.PageRowBounds;
import funny.mybatis.demo.type.Enabled;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 16:32 2018/1/31
 */
public class RoleMapperTest extends BaseMapperTest {
    @Test
    public void testSelectById(){
        SqlSession sqlSession= getSqlSession();
        try{
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(1L);
            Assert.assertNotNull(role);
            Assert.assertEquals("管理员",role.getRoleName());
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectById2(){
        SqlSession sqlSession= getSqlSession();
        try{
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById2(1L);
            Assert.assertNotNull(role);
            Assert.assertEquals("管理员",role.getRoleName());
        }finally {
            sqlSession.close();
        }
    }

/*    @Test
    public void testSelectAll(){
        SqlSession sqlSession= getSqlSession();
        try{
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> roleList = roleMapper.selectAll();
            Assert.assertNotNull(roleList);
            Assert.assertTrue(roleList.size()>0);
            Assert.assertNotNull(roleList.get(0).getRoleName());
        }finally {
            sqlSession.close();
        }
    }*/

    @Test
    public void testInsert(){
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //创建一个user对象
            SysRole role = new SysRole();
            role.setId(1002L);
            role.setRoleName("testRoleName");
            role.setCreateBy(1L);
//            role.setEnabled(1);
            role.setCreateTime(new Date());
            //将新建的对象插入数据库中，特别注意这里的返回值result是执行的sql影响的行数
            int result = roleMapper.insert(role);
            Assert.assertEquals(1,result);
            Assert.assertNull(role.getId());
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
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //创建一个user对象
            SysRole role = new SysRole();
            role.setId(1002L);
            role.setRoleName("testRoleName");
            role.setCreateBy(1L);
//            role.setEnabled(1);
            role.setCreateTime(new Date());
            //将新建的对象插入数据库中，特别注意这里的返回值result是执行的sql影响的行数
            int result = roleMapper.insertGetKey(role);
            Assert.assertEquals(1,result);
            Assert.assertNotNull(role.getId());
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
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //创建一个user对象
            SysRole role = new SysRole();
            role.setId(1002L);
            role.setRoleName("testRoleName");
            role.setCreateBy(1L);
//            role.setEnabled(1);
            role.setCreateTime(new Date());
            //将新建的对象插入数据库中，特别注意这里的返回值result是执行的sql影响的行数
            int result = roleMapper.insertSelectKey(role);
            Assert.assertEquals(1,result);
            Assert.assertNotNull(role.getId());
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
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //创建一个user对象
            SysRole role = roleMapper.selectById(1L);
            Assert.assertEquals("管理员",role.getRoleName());

            role.setRoleName("管理员Test");
            //将新建的对象插入数据库中，特别注意这里的返回值result是执行的sql影响的行数
            int result = roleMapper.updateById(role);
            Assert.assertEquals(1,result);
            role = roleMapper.selectById(1L);
            Assert.assertEquals("管理员Test",role.getRoleName());

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
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //创建一个user对象
            SysRole role = roleMapper.selectById(1L);
            Assert.assertNotNull(role);
            Assert.assertEquals(1,roleMapper.deleteById(1L));
            Assert.assertNull(roleMapper.selectById(1L));


            SysRole role2 = roleMapper.selectById(2L);
            Assert.assertNotNull(role2);
            Assert.assertEquals(1,roleMapper.deleteById(2L));
            Assert.assertNull(roleMapper.selectById(2L));
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
    public void testSelectAllRoleAndPrivileges(){
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> roleList = roleMapper.selectAllRoleAndPrivileges();
            System.out.println("角色数" + roleList.size());
                for (SysRole role :roleList){
                    System.out.println("角色名:"+role.getRoleName());
                    for (SysPrivilege privilege :role.getPrivilegeList()){
                        System.out.println("权限名:" +privilege.getPrivilegeName());
                    }
                }

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
    public void testSelectRoleByUserIdChoose(){
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //由于数据库数据enabled都是1,所有给其中一个角色的enabled赋值为0
            SysRole role = roleMapper.selectById(2L);
//            role.setEnabled(0);
            roleMapper.updateById(role);
            //获取用户1的角色
            List<SysRole> roleList = roleMapper.selectRoleByUserIdChoose(1L);
            for (SysRole r: roleList){
                System.out.println("角色名："+r.getRoleName());
                if(r.getId().equals(1L)){
                    //第一个角色存在权限信息
                    Assert.assertNotNull(r.getPrivilegeList());
                }else if(r.getId().equals(2L)){
                    //第二个角色的权限为NULL
                    Assert.assertNull(r.getPrivilegeList());
                    continue;
                }

                for (SysPrivilege privilege:r.getPrivilegeList()){
                    System.out.println("权限名："+privilege.getPrivilegeName());
                }
            }

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
    public void testUpdateById2(){
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //先查询出角色，然后修改角色的enabled值为disabled
            SysRole role = roleMapper.selectById(2L);
            Assert.assertEquals(Enabled.enabled,role.getEnabled());
            role.setEnabled(Enabled.disabled);
            roleMapper.updateById(role);

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
    public void testSelectAllByRowBounds(){
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //查询第一个，使用RowBounds类型是不会查询总数
            RowBounds rowBounds = new RowBounds(0,1);
            List<SysRole> list = roleMapper.selectAll(rowBounds);
            for (SysRole role : list){
                System.out.println("角色名:"+ role.getRoleName());
            }
            //使用PageRowBounds 时会查询总数
            PageRowBounds pageRowBounds = new PageRowBounds(0,1);
            list = roleMapper.selectAll(pageRowBounds);
            //获取总数
            System.out.println("查询总数："+pageRowBounds.getTotal());
            for (SysRole role : list){
                System.out.println("角色名:"+ role.getRoleName());
            }
            //再次查询获取第二个角色
            pageRowBounds = new PageRowBounds(1,1);
            list = roleMapper.selectAll(pageRowBounds);
            //获取总数
            System.out.println("查询总数："+pageRowBounds.getTotal());
            for (SysRole role : list){
                System.out.println("角色名:"+ role.getRoleName());
            }

        }finally {
            //为了不影响其他测试,这里选择回滚
            //由于默认的sqlSessionFactory.openSession 不是自动提交的
            //因此不手动执行commit也不会提交到数据库
            sqlSession.rollback();
            //不要忘记关闭sqlSession
            sqlSession.close();
        }
    }

}

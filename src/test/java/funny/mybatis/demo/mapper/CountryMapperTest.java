package funny.mybatis.demo.mapper;

import funny.mybatis.demo.model.Country;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 9:34 2018/1/31
 */
public class CountryMapperTest extends BaseMapperTest{


    @Test
    public void testSelectAll(){
        //使用时通过SqlSessionFactory工厂对象获取一个SqlSession
        SqlSession sqlSession = getSqlSession();
        //通过SqlSession的SelectList方法查找到CountryMapper.xml中的id="selectAll"的方法，执行SQL查询
        //MyBatis底层使用JDBC执行SQL,获得查询结果集ResultSet后,根据resultType对配置将结果映射为Country类型的集合，返回查询结果
        List<Country> countryList = sqlSession.selectList("funny.mybatis.demo.mapper.CountryMapper.selectAll");
        printCountryList(countryList);
    }

    private void printCountryList(List<Country> countryList){
        for(Country country:countryList){
            System.out.printf("%-4d%4s%4s\n",country.getId(),country.getCountryname(),country.getCountrycode());
        }
    }

    @Test
    public void testExample(){
       /* //获取sqlSession
        SqlSession sqlSession  = getSqlSession();
        //获取CountryMapper接口
        CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
        //创建Example对象
        CountryExample example = new CountryExample();
        //设置排序规则
        example.setOrderByClause("id desc,countryname asc");
        //设置是否distinct去重
        example.setDistinct(true);
        //创建条件
        CountryExample.Criteria criteria = example.createCriteria();
        //id >= 1
        criteria.andCou
        //id < 4
        //countrycode like '%U%'
        //最容易出错的地方，注意like必须自己写上通配符的位置
        //or的情况
        //countryname = 中国
        //执行查询*/
    }
}

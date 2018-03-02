package funny.mybatis.demo.mapper;

import funny.mybatis.demo.model.Country;
import funny.mybatis.demo.model.SysRole;
import funny.mybatis.demo.plugin.SimpleInterceptor;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.LoggingCache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.decorators.SerializedCache;
import org.apache.ibatis.cache.decorators.SynchronizedCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Assert;
import org.junit.Test;

import org.apache.ibatis.type.TypeHandlerRegistry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 18:03 2018/3/1
 */
public class SimpleTest  {
    @Test
    public void test() throws SQLException {
        //1.指定日志和创建配置对象
        //使用log4j记录日志
        LogFactory.useLog4JLogging();
        //创建配置对象
        final Configuration config = new Configuration();
        //配置Settings中的部分属性
        config.setCacheEnabled(true);
        config.setLazyLoadingEnabled(false);
        config.setAggressiveLazyLoading(true);

        //2.添加拦截器
        SimpleInterceptor interceptor1 = new SimpleInterceptor("拦截器1");
        SimpleInterceptor interceptor2 = new SimpleInterceptor("拦截器2");
        config.addInterceptor(interceptor1);
        config.addInterceptor(interceptor2);
        //3.创建数据源和JDBC事务
        UnpooledDataSource dataSource = new UnpooledDataSource();
        dataSource.setDriver("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        Transaction transaction = new JdbcTransaction(dataSource,null,false);
        //4.创建Executor
        /**
         * Executor是MyBatis底层执行数据库操作的直接对象，大多数MyBatis方便调用的方式都是对该对象的封装。
         * 通过Configuration的newExecutor方法创建的Executor会自动根据配置的拦截器对Executor进行多层代理。。
         * 通过这种代理机制使得Mybatis的扩展更方便，更强大。
         */
        final Executor executor = config.newExecutor(transaction);
        //5.创建SqlSource对象
        /**
         * 无论是通过xml方式还是注解方式配置SQL语句，在Mybatis中，SQL语句都会被封装成SqlSource对象。
         * XML中配置的静态SQL会对应生成StaticSqlSource，带有if标签或者${}用法的SQL会按动态SQL被处理为DynamicSqlSource。
         * 使用Provider类注解标记的方法会生成ProviderSqlSource。所有类型的SqlSource在最终执行前，都会被处理成StaticSqlSource
         */
        StaticSqlSource sqlSource = new StaticSqlSource(config,"SELECT * FROM country WHERE id = ?");
        //6.创建参数映射配置
        /**
         * 在第五部分的SQL中包含一个参数id，Mybatis文档中建议在XML中不去配置parameterMap属性，因为Mybatis会自动根据惨呼去判断和生成这个配置。
         * 在底层中，这个配置是必须存在的。
         */
        //????????
        TypeHandlerRegistry registry = new TypeHandlerRegistry();
        //由于上面的Sql有一个参数id，因此这里需要提供ParameterMapping(参数映射)
        List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
        //通过ParameterMapping.Builder创建ParameterMapping
        parameterMappings.add(new ParameterMapping.Builder(
                config,
                "id",
                registry.getTypeHandler(Long.class)
        ).build());
        ParameterMap.Builder paramBuilder = new ParameterMap.Builder(
                config,
                "defaultParameterMap",
                Country.class,
                parameterMappings
                );
        //7.创建结果映射
        /**
         * 这种配置方式和在XML中配置resultMap元素是相同的，经常使用resultType方式，在底层仍然是ResultMap对象，但是创建起来更容易。
         * ResultMap resultMap = new ResultMap.Builder(config,"defaultResultMap",Country.class,new ArrayList<ResultMapping>());
         * 上面的Builder中最后一个参数为空数组，Mybatis完全通过第三个参数类型来映射结果
         */
        @SuppressWarnings("serial")
        ResultMap resultMap = new ResultMap.Builder(
                config,
                "defaultResultMap",
                Country.class,
                new ArrayList<ResultMapping>(){{
                    add(new ResultMapping.Builder(config,"id","id",Long.class).build());
                    add(new ResultMapping.Builder(config,"countryname","countryname",String.class).build());
                    add(new ResultMapping.Builder(config,"countrycode","countrycode",String.class).build());

                }}
        ).build();
        //8.创建缓存对象
        /**
         * 这是Mybatis根据装饰模式创建的缓存对象，通过层层装饰是的简单的缓存拥有了可配置的复杂功能。
         * 各级装饰缓存的含义可以参考上述代码中对应的注释
         */
        final Cache countryCache = new SynchronizedCache(   //同步缓存
                new SerializedCache(                        //序列化缓存
                        new LoggingCache(                   //日志缓存
                                new LruCache(               //最少使用缓存
                                        new PerpetualCache("country_cache")))));    //持久缓存

        //9.创建MappedStatement对象
        /**
         * 上述第一、五、六、七、八五个部分已经准备好了一个SQL执行和映射的基本配置。
         * MappedStatement 就是对SQL更高层次的一个封装，这个对象包含了执行SQL所需的各种配置信息。
         */
        /**
         * MappedStatement.Builder 的第二个参数就是这个SQL语句的唯一id,
         * 在XML和接口模式下就是namespace和id的组合。
         */
        MappedStatement.Builder msBuilder = new MappedStatement.Builder(
                config,
                "funny.mybatis.demo.SimpleMapper.selectCountry",
                sqlSource,
                SqlCommandType.SELECT
        );
        msBuilder.parameterMap(paramBuilder.build());
        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        resultMaps.add(resultMap);
        //设置返回值resultMap
        msBuilder.resultMaps(resultMaps);
        //设置缓存
        msBuilder.cache(countryCache);
        //创建ms
        MappedStatement ms = msBuilder.build();


        List<Country> countries = executor.query(ms,1L, RowBounds.DEFAULT,Executor.NO_RESULT_HANDLER);
        for (Country country1:countries) {
            System.out.println(country1.getId() + "," + country1.getCountryname() + "," + country1.getCountrycode());
        }
        config.addMappedStatement(ms);
        SqlSession sqlSession = new DefaultSqlSession(config,executor,false);
        Country country = sqlSession.selectOne("selectCountry",2L);
        System.out.println(country.getId()+","+country.getCountryname()+","+country.getCountrycode());
        country = sqlSession.selectOne("funny.mybatis.demo.SimpleMapper.selectCountry",2L);
        System.out.println(country.getId()+","+country.getCountryname()+","+country.getCountrycode());

        MapperProxyFactory<SimpleMapper> mapperProxyFactory = new MapperProxyFactory<SimpleMapper>(SimpleMapper.class);
        //创建代理接口
        SimpleMapper simpleMapper = mapperProxyFactory.newInstance(sqlSession);
        //执行方法
        countries = simpleMapper.selectCountry(2L);
        for (Country country1:countries) {
            System.out.println(country1.getId() + "," + country1.getCountryname() + "," + country1.getCountrycode());
        }
    }
    /*@Test
    public void testMapper() {
        SqlSession sqlSession = getSqlSession();
        try {
            SimpleMapper simpleMapper = sqlSession.getMapper(SimpleMapper.class);
            Country country = simpleMapper.selectCountry(1L);
            Assert.assertNotNull(country);
            System.out.println(country.getId() + "," + country.getCountryname() + "," + country.getCountrycode());

        } finally {
            sqlSession.close();
        }
    }*/
}

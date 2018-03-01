package funny.mybatis.demo.plugin;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.generator.internal.db.DatabaseDialects;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Mybatis-通用分页拦截器
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 15:51 2018/2/28
 */

/**
 * 先判断当前的mybatis方法是否需要进行分页：
 *    如果不需要进行分页，就直接调用invocation.proceed()返回；
 *    如果需要进行分页，首先获取当前方法的BoundSql,这个对象包含了要执行的SQL和对应的参数。
 *        通过这个对象的SQL和参数生成一个count查询的BoundSql，由于这种情况下的MappedStatement对象中的resultMap或resultType
 *        类型为当前查询结果的类型，并不合适返回count查询值，因此通过newMappedStatement方法根据当前的MappedStatement生成一个返回值类型为Long的对象，
 *        然后通过Executor执行查询，得到了数据总数。得到数据总数后，根据dialect.afterCount判断是否继续进行分页查询，因为如果当前查询的结果为0，
 *        就不必继续进行分页查询了（为了节省时间），而是可以直接返回空值。
 *        如果需要进行分页，就使用dialect获取分页查询SQL，同count查询类似，得到分页数据的结果后，通过dialect对结果进行处理并返回。
 */

/**
 * 除了主要的逻辑部分之外，在setProperties中还要求必须设置dialect参数，该参数的值为Dialect实现类的全限定名称。
 * 这里进行反射实例化后，有调用了Dialect的setProperties，通过参数传递可以让Dialect实现更多可配置的功能。
 * 除了实例化dialect，这段代码还初始化了additionalParametersField,这是通过反射获取了BoundSql对象中的additionalParameters属性，
 * 在创建新的BoundSql对象中，通过这个属性反射获取了执行动态SQL时产生的动态参数
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Intercepts(
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class}
        )
)
public class PageInterceptor implements Interceptor {
    private static final List<ResultMapping> EMPTY_RESULTMAPPING = new ArrayList<ResultMapping>(0);
    private Dialect dialect;
    private Field additionalParametersField;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //获取拦截方法的参数
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameterObject = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        //调用方法判断是否需要进行分页，如果不需要，直接返回结果
        if(!dialect.skip(ms.getId(),parameterObject,rowBounds)){
            ResultHandler resultHandler = (ResultHandler) args[3];
            //当前的目标对象
            Executor executor = (Executor) invocation.getTarget();
            BoundSql boundSql = ms.getBoundSql(parameterObject);
            //反射获取动态参数
            Map<String,Object> additionalParameters = (Map<String, Object>) additionalParametersField.get(boundSql);
            //判断是否需要进行count查询
            if(dialect.beforeCount(ms.getId(),parameterObject,rowBounds)){
                //根据当前的ms创建一个返回值为Long类型的ms
                MappedStatement countMS = newMappedStatement(ms,Long.class);
                //创建count查询的缓存key
                CacheKey countKey = executor.createCacheKey(countMS,parameterObject,RowBounds.DEFAULT,boundSql);
                //调用方言获取count sql
                String countSql = dialect.getCountSql(boundSql,parameterObject,rowBounds,countKey);
                BoundSql countBoundSql = new BoundSql(ms.getConfiguration(),countSql,boundSql.getParameterMappings(),parameterObject);
                //当使用动态sql时，可能会产生临时的参数
                //这些参数需要手动设置到新的BoundSql中
                for(String key:additionalParameters.keySet()){
                    countBoundSql.setAdditionalParameter(key,additionalParameters.get(key));
                }
                //执行count查询
                Object countResultList = executor.query(countMS,parameterObject,RowBounds.DEFAULT,resultHandler,countKey,countBoundSql);
                Long count = (Long) ((List)countResultList).get(0);
                //处理查询总数
                dialect.afterCount(count,parameterObject,rowBounds);
                if(count == 0L){
                    //当查询总数为0时，直接返回空的结果
                    return dialect.afterPage(new ArrayList<>(),parameterObject,rowBounds);
                }
            }

            //判断是否需要进行分页查询
            if(dialect.beforePage(ms.getId(),parameterObject,rowBounds)){
                //生成分页的缓存key
                CacheKey pageKey = executor.createCacheKey(ms,parameterObject,rowBounds,boundSql);
                //调用方言获取分页sql
                String pageSql = dialect.getPageSql(boundSql,parameterObject,rowBounds,pageKey);;
                BoundSql pageBoundSql = new BoundSql(ms.getConfiguration(),pageSql,boundSql.getParameterMappings(),parameterObject);
                //设置动态参数
                for(String key : additionalParameters.keySet()){
                    pageBoundSql.setAdditionalParameter(key,additionalParameters.get(key));
                }
                //执行分页查询
                List resultList = executor.query(ms,parameterObject,RowBounds.DEFAULT,resultHandler,pageKey,pageBoundSql);
                return dialect.afterPage(resultList,parameterObject,rowBounds);
            }
        }
        //返回默认查询
        return invocation.proceed();
    }

    /**
     * 根据现有的ms创建一个新的返回值类型，使用新的返回值类型
     * @param ms
     * @param resultType
     * @return
     */
    public MappedStatement newMappedStatement(MappedStatement ms,Class<?> resultType){
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(),
                ms.getId()+"_Count",
                ms.getSqlSource(),
                ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties()!=null && ms.getKeyProperties().length !=0){
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()){
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(
                    keyProperties.length()-1,keyProperties.length()
            );
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        //count查询返回int
        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        ResultMap resultMap = new ResultMap.Builder(ms.getConfiguration(),ms.getId(),resultType,EMPTY_RESULTMAPPING).build();
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }
    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {
        String dialectClass = properties.getProperty("dialect");
        try {
            dialect = (Dialect) Class.forName(dialectClass).newInstance();
        } catch (Exception e) {
            throw  new RuntimeException("使用PageInterceptor分页插件时，必须设置dialect属性");
        }
        dialect.setProperties(properties);

        try {
            //反射获取BoundSql中的additionalParameters属性
            additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
            additionalParametersField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }
}

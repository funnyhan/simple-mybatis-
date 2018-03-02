package funny.mybatis.demo.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 18:00 2018/3/1
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Intercepts(
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class}
        )
)
public class SimpleInterceptor implements Interceptor {
    private String name;

    public SimpleInterceptor(String name){
        this.name = name;
    }
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("进入拦截器：" + name);
        Object result = invocation.proceed();
        System.out.println("跳出拦截器：" + name);
        return result;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}

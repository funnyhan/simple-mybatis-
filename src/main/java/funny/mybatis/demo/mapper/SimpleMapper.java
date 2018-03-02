package funny.mybatis.demo.mapper;

import funny.mybatis.demo.model.Country;
import org.apache.ibatis.annotations.Flush;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 9:40 2018/3/2
 */

@Resource()
public interface SimpleMapper {
    @Flush
    List<Country> selectCountry(Long id);
}

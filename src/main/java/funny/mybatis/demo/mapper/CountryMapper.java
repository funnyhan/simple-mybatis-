package funny.mybatis.demo.mapper;

import funny.mybatis.demo.model.Country;

import java.util.List;

/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 16:56 2018/1/30
 */
public interface CountryMapper {
    List<Country> selectAll();
}

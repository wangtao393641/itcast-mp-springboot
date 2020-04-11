package config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Bean;


@CacheConfig
@MapperScan("cn.itcast.mp.mapper")
public class MybatisPlusConfig {
    //配置分页插件的Bean
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}

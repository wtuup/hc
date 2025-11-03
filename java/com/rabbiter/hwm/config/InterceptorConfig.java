package com.rabbiter.hwm.config;

import com.rabbiter.hwm.config.interceptor.JwtInterceptor;
import com.rabbiter.hwm.utils.PathUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");

        String winPath = PathUtils.getClassLoadRootPath() + "/files/";
        //第一个方法设置访问路径前缀，第二个方法设置资源路径
        registry.addResourceHandler("/files/**").
                addResourceLocations("file:" + winPath);
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/register", "/**/export", "/**/import", "/file/**","/user/password","/user-file/**","/student-file/**")
                .excludePathPatterns("/files/**", "/swagger**/**", "/webjars/**", "/v3/**", "/doc.html");
        super.addInterceptors(registry);
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }


}

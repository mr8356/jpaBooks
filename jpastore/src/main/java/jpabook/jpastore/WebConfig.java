package jpabook.jpastore;

import java.util.List;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jpabook.jpastore.argumentresolver.LoginMemberArgumentResolver;
import jpabook.jpastore.filter.LogFilter;
import jpabook.jpastore.filter.LoginCheckFilter;
import jpabook.jpastore.interceptor.LogInterceptor;
import jpabook.jpastore.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    // TODO Auto-generated method stub
    resolvers.add(new LoginMemberArgumentResolver());
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LogInterceptor())
            .order(1)
            .addPathPatterns("/**")
            .excludePathPatterns("/css/**" , "/*.ico", "/error");

    // 추가된 코드. 인증 체크 인터셉터 추가
    registry.addInterceptor(new LoginCheckInterceptor())
            .order(2)
            .addPathPatterns("/**")
            .excludePathPatterns("/", "/members/add", "/login", "/logout", "/css/**", "/*.ico", "/test");
  }

  // @Bean
  public FilterRegistrationBean logFilter() {
    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();
    filterRegistrationBean.setFilter(new LogFilter()); // 여기서 만든 필터 클래스  등록
    filterRegistrationBean.setOrder(1);
    filterRegistrationBean.addUrlPatterns("/*");
    return filterRegistrationBean;
  }

  // @Bean
  public FilterRegistrationBean LoginCheckFilter() {
    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();
    filterRegistrationBean.setFilter(new LoginCheckFilter()); // 여기서 만든 필터 클래스  등록
    filterRegistrationBean.setOrder(2); // 1번인 로그필터 다음으로 수행.
    filterRegistrationBean.addUrlPatterns("/*");
    return filterRegistrationBean;
  }


}

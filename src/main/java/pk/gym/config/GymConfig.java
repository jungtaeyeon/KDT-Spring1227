package pk.gym.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class GymConfig implements WebMvcConfigurer{
  private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { 
    "classpath:/META-INF/resources/", 
    "classpath:/resources/", 
    "classpath:/static/", 
    "classpath:/public/" };
    
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

      InternalResourceViewResolver bean = new InternalResourceViewResolver();
      //bean.setViewClass(JstlView.class);
      bean.setPrefix("/WEB-INF/jsp/");
      bean.setSuffix(".jsp");
      registry.viewResolver(bean);
    }	 
    @Bean
    public MultipartResolver multipartResolver() {
      StandardServletMultipartResolver resolver =
          new StandardServletMultipartResolver();
      return resolver;
    }
}

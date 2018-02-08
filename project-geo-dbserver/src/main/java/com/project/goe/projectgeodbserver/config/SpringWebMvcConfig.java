package com.project.goe.projectgeodbserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.project.goe.projectgeodbserver.util.UserLoginSetting;

@Configuration
@CrossOrigin
public class SpringWebMvcConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private UserLoginSetting userLoginSetting;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		super.addResourceHandlers(registry);
	}

	@Bean
	public LoginFilterHandler loginFilterHandler() {
		return new LoginFilterHandler();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if(userLoginSetting.isUseInterceptor()) {
			registry.addInterceptor(loginFilterHandler()).addPathPatterns("/**").excludePathPatterns("/user/login");
		}
		
		super.addInterceptors(registry);
	}

}

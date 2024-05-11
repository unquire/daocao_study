package com.daocao.support.config.security;

import com.daocao.support.filter.JwtAuthticationFilter;
import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

/**
 * Author:啦啦啦
 * Package:com.daocao.support.config.security
 * Project:daocao_study
 * name:SecurityConfig
 * Date:2024/3/16 22:19
 */
@Configuration
@EnableWebSecurity //开启SpringSecurity的默认行为
public class SecurityConfig {
	// 引入UseDetailsService
	private final SysUserDetailsService sysUserDetailsService;
	private final JwtAuthticationFilter jwtAuthticationFilter;

	public SecurityConfig(SysUserDetailsService sysUserDetailsService, JwtAuthticationFilter jwtAuthticationFilter) {
		this.sysUserDetailsService = sysUserDetailsService;
		this.jwtAuthticationFilter = jwtAuthticationFilter;
	}

	/*
	* 配置过滤器链
	* */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		// 禁用csrf(Cross-site request forgery,跨站请求伪造)伪造请求,这句代码的意思是禁用了 Spring Security 中的 CSRF 保护机制。
		http.csrf(AbstractHttpConfigurer::disable);
		// 启用拦截策略,即任何用户可以登录到/auth/sys路由，但是对应其他的访问需要经过认证之后才能继续访问。
		http.authorizeHttpRequests(auth->auth.requestMatchers("/auth/sys").permitAll().anyRequest().authenticated());
		// 开启form认证
		// http.formLogin(Customizer.withDefaults());
		// 配置跨域，允许跨域
		http.cors(cors-> cors.configurationSource(configurationSource()));

		http.addFilterBefore(jwtAuthticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	// 创建AuthenticationManager
	@Bean
	public AuthenticationManager sysUserAuthenticationManager(){
		// 处理基于数据库的身份验证的提供者的信息
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(sysUserDetailsService);
		return new ProviderManager(provider);
	}
	/*
	* 配置密码编码器，对用户密码进行加密和验证
	* */
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	//配置跨域，允许跨域 配置CorsConfigurationSource
	public CorsConfigurationSource configurationSource(){
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		// 设置哪些方法可以跨域访问,参数Arrays.asList("GET","POST")
		corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
		// 是否允许发送身份凭证到服务器
		// corsConfiguration.setAllowCredentials(Collections.singletonList("*"));
		// 设置允许添加的请求标头，只允许 Authorization 标头进行跨域访问。
		corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
		// 设置允许的来源，如https://example.com
		corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
		// 创建这个CorsConfigurationSource对象
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**",corsConfiguration);
		return source;
	}
}

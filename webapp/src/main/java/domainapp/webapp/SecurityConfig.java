package domainapp.webapp;

import base.Services.UserService;

import graphql.introspection.GoodFaithIntrospection;
import graphql.introspection.Introspection;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(req -> req
                        /*.requestMatchers("/api/addProduct").authenticated()
                        .requestMatchers("/api/deleteProduct/**").authenticated()
                        .requestMatchers("/api/search").permitAll()
                        .requestMatchers("/api/searchByTermAndTags").permitAll()
                        .requestMatchers("/api/user/**").permitAll()
                        .requestMatchers("/api/good").permitAll()
                        .requestMatchers("/api/good/enrichWithAi").authenticated()
                        .requestMatchers("/api/getAllOwnedGoods").authenticated()
                        .requestMatchers("/api/checkLoginSession").authenticated()
                        .requestMatchers("/dashboard").hasAuthority("ADMIN")
                        .requestMatchers("/user/settings").authenticated()
                        .requestMatchers("/api/goods").permitAll()
                        .requestMatchers("/api/good/order").permitAll()*/
                        .requestMatchers("restful/**").permitAll()
                        .anyRequest().permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);
                /*.formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/api/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessUrl("/")
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(403);
                        })
                );
        http.addFilterAfter(((request, response, chain) -> {
            System.out.println("triggered");
            HttpServletRequest req=(HttpServletRequest) request;
            String uri=req.getRequestURI();
            chain.doFilter(request, response);
        }), UsernamePasswordAuthenticationFilter.class);*/
        http.headers(headers->headers.contentTypeOptions(HeadersConfigurer.ContentTypeOptionsConfig::disable));

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

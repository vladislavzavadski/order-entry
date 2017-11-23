package com.netcracker.orderentry.processor.config;

import com.netcracker.orderentry.processor.client.OfferClient;
import com.netcracker.orderentry.processor.client.OrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final OrderClient orderClient;
    private final OfferClient offerClient;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService, OrderClient orderClient, OfferClient offerClient) {
        this.userDetailsService = userDetailsService;
        this.orderClient = orderClient;
        this.offerClient = offerClient;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().anonymous().disable().formLogin().loginPage("/login").
                usernameParameter("username").passwordParameter("password").
                failureHandler(authenticationFailureHandler()).successHandler(authenticationSuccessHandler());
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return (httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return (httpServletRequest, httpServletResponse, authentication) -> {

            List<String> orderCookies = orderClient.authenticate(httpServletRequest.getParameter("username"),
                    httpServletRequest.getParameter("password"));

            httpServletRequest.getSession().setAttribute("inventoryCookies", orderCookies);

            List<String> offerCookies = offerClient.authenticate(httpServletRequest.getParameter("username"),
                    httpServletRequest.getParameter("password"));

            httpServletRequest.getSession().setAttribute("catalogCookies", offerCookies);
            httpServletResponse.sendError(HttpServletResponse.SC_OK);
        };
    }
}

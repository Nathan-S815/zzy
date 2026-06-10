package com.zzy.security;


import com.zzy.security.filter.AllowOriginFilter;
import com.zzy.security.filter.JwtAuthenticationFilter;
import com.zzy.security.filter.LoginFilter;
import com.zzy.security.filter.SsoLoginFilter;
import com.zzy.security.handler.CusLogoutSuccessHandler;
import com.zzy.security.handler.CustomAuthenticationEntryPoint;
import com.zzy.security.handler.LogOutHandlers;
import com.zzy.security.lib.dao.KaptchaInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Configuration
@EnableWebSecurity
@CrossOrigin(allowCredentials = "true")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    @Qualifier("customAuthenticationProvider")
    private AuthenticationProvider provider;


    @Autowired
    @Qualifier("accessDeniedHandlerCustom")
    private AccessDeniedHandler accessDeniedHandlerCustom;

    @Autowired
    private JwtAuthenticationFilter authenticationTokenFilter;

    @Autowired
    @Qualifier("succAuthHandler")
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    @Qualifier("failAuthHandler")
    private AuthenticationFailureHandler failHandler;

    @Autowired
    private KaptchaInfoMapper kaptchaInfoMapper;

//    @Autowired
//    private SsoLoginFilter ssoLoginFilter;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    @Autowired
    @Order(1)
    AllowOriginFilter allowOriginFilter;


    /**
     * 此处使用自定义的provider
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authen) throws Exception {
        authen.authenticationProvider(provider);
    }


    @Value("${security.test}")
    private Boolean isTest;


    @Override
    public void configure(WebSecurity web) throws Exception {
        String[] apiUrl = {"/**/getScenicSpot/**","/**/getScenicNews/**","/**/getWeather/**", "/**/getQualityRank/**","/**/getEntiretyAnalysis/**","/**/GetComplaintProposalInfoCount/**","/**/scenicInformation/**",
                "/**/getAllScenicPark/**","/**/getTouristFromByScenic/**","/**/getTouristFromByScenicNew/**", "/**/getTouristStayInProvince/**",
                "/**/getTouristSex/**","/**/getTouristSexNew/**","/**/getAgeDistribution/**","/**/getAgeDistributionNew/**","/**/getTravelLineRate/**", "/**/getHotelOccupancy/**",
                "/**/getForecastByWeek/**","/**/getHotelOccupancyByWeek/**","/**/getTotalNumber/**","/**/getTravelTotalReceptionNew/**",
                "/**/getTotalNumber/**","/**/getTravelTotalReception/**","/**/getTravelForecastWeek/**","/**/getNowWeekReport/**","/**/getTouristDistribution/**","/**/findCommentKeyWord/**",
                "/**/getTouristNumberContrast/**","/**/getCarPlaceInCount/**","/**/getNowCarCount/**","/**/getTouristStayOutProvince/**",
                "/**/getInCarCount/**", "/**/getOutCarCount/**","/**/listHotMapCount/**",
                "/**/getAllTouristNumber/**","/**/getConsumption/**","/**/tourist/**","/**/consumption/**",
                "/**/stay/**","/**/hotel/**","/**/travel/**","/**/evaluate/**","/**/parkSpace/**",
                "/**/tourist/**","/**/income/**",
                "/**/report/**","/**/event/**","/**/eventType/**","/**/v2/**","/**/depart/**","/**/listBaseNotice/**","/**/test/**"
        };
        web.ignoring().antMatchers("/assert/**","/css/**","/js/**","/**/scripts/**","/**/img/**");
        web.ignoring().antMatchers(apiUrl);
        web.ignoring().antMatchers("/**/vendor/**","/wsChat/**");
        web.ignoring().antMatchers("/images/**","/**/**.js","/**/**.css","/favicon.ico");
        web.ignoring().antMatchers("/mustache/**","/js/**/*.*");
        web.ignoring().antMatchers("/websocket/**","/**/doc.html");
        web.ignoring().antMatchers("**/html","/fonts/**", "**/favicon.ico", "/index.html","/**/*.js","/**/*.html");
        web.ignoring().antMatchers("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
        web.ignoring().antMatchers("/**/css/**", "/**/js/**","/**/img/**", "/webjars/**", "**/favicon.ico", "/index","/docs.html");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.headers().frameOptions().disable();
        httpSecurity
                .formLogin().loginProcessingUrl("/login").failureForwardUrl("/login?error").permitAll()
                .and().logout().addLogoutHandler(new LogOutHandlers()).logoutUrl("/logout").logoutSuccessHandler(new CusLogoutSuccessHandler())
                .permitAll()
                .and().authorizeRequests()
                .antMatchers("/auth/root/**").hasAnyAuthority("ROOT")
                .antMatchers("/auth/admin/**").hasAnyAuthority(new String[]{"ROLE_ADMIN","ROOT"})
                .and().authorizeRequests().antMatchers("/**/public/**","/index").permitAll()
                .antMatchers("/**/kaptcha/**","/excel/**","/**/downloadPdf/**","/**/reportExcel/**").permitAll();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).sessionFixation().migrateSession();
        // 禁用缓存
        httpSecurity.headers().cacheControl();
        httpSecurity
                .exceptionHandling().accessDeniedHandler(accessDeniedHandlerCustom).authenticationEntryPoint(customAuthenticationEntryPoint);
        httpSecurity.cors().and().csrf().disable();
        if(isTest){
            httpSecurity.authorizeRequests().anyRequest().permitAll();
        }else {
            httpSecurity.authorizeRequests().anyRequest().authenticated().accessDecisionManager(accessDecisionManager());
            httpSecurity.addFilterBefore(allowOriginFilter, CorsFilter.class);
            httpSecurity
                    .addFilterBefore(new LoginFilter(authenticationManager(),successHandler,failHandler).setKapMapper(kaptchaInfoMapper),UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        }
    }



    private AbstractAccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        decisionVoters.add(new AuthenticatedVoter());
        decisionVoters.add(webExpressionVoter);
        RoleVoter AuthVoter = new RoleVoter();
        AuthVoter.setRolePrefix("");//
        decisionVoters.add(AuthVoter);
        AbstractAccessDecisionManager accessDecisionManager = new AffirmativeBased(decisionVoters);
        return accessDecisionManager;
    }


}

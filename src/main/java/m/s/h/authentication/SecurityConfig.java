package m.s.h.authentication;

import m.s.h.authentication.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;import org.springframework.security.config.annotation.web.builders.WebSecurity;import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServlet;import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private JwtAuthenticationConfig config;
    private PasswordEncoder passwordEncoder;
    private UserDetailsServiceImpl userDetailsService;
    private MemberRepository memberRepository;

    @Autowired
    public SecurityConfig(JwtAuthenticationConfig config, PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService, MemberRepository memberRepository){
        this.config = config;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .cors().disable()
                .csrf().disable()
                .logout().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .exceptionHandling().authenticationEntryPoint(
                (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers(config.getUrl()).permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // **permit OPTIONS call to all**
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/auth/v1/join").permitAll()
                .antMatchers("/auth/v1/getUserIdDisplayName").permitAll()
                .anyRequest().authenticated();

        CsrfHeaderFilter csrfHeaderFilter = new CsrfHeaderFilter();
        httpSecurity.addFilterBefore(csrfHeaderFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAfter(new JwtUsernamePasswordAuthenticationFilter(config, authenticationManager(), this.memberRepository),
                UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring()
                .antMatchers("/swagger-ui.html")
                .antMatchers("/swagger-ui.html/**")
                .antMatchers("/v2/api-docs");

    }

}

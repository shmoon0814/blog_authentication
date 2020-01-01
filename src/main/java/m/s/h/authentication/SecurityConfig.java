package m.s.h.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;import org.springframework.security.config.annotation.web.builders.WebSecurity;import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;import org.springframework.security.crypto.factory.PasswordEncoderFactories;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;import javax.servlet.http.HttpServlet;import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    JwtAuthenticationConfig config;

    @Bean
    public JwtAuthenticationConfig jwtAuthenticationConfig(){
        return new JwtAuthenticationConfig();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser("admin").password(encoder.encode("admin")).roles("ADMIN", "USER").and()
                .withUser("shmoon").password(encoder.encode("shmoon")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
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
                .addFilterAfter(new JwtUsernamePasswordAuthenticationFilter(config, authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(config.getUrl()).permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring()
                .antMatchers("/swagger-ui.html")
                .antMatchers("/swagger-ui.html/**")
                .antMatchers("/v2/api-docs");

    }

}

package com.example.busreservation.security;
import com.example.busreservation.security.JwtAuthFilter;
import com.example.busreservation.services.CustomUserDetailsService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JWTEntryPoint jwtEntryPoint;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

   @Bean
   SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
       http.csrf(AbstractHttpConfigurer::disable).
               authorizeHttpRequests((authReq)->authReq
                       .requestMatchers(HttpMethod.GET)
                       .permitAll()
                       .requestMatchers("/api/auth/**")
                       .permitAll()
                       .requestMatchers(HttpMethod.POST,
                               "/api/bus/add",
                               "api/schedule/add",
                               "api/route/add")
                       .authenticated()
                       .requestMatchers(HttpMethod.POST,
                               "/api/reservation/add")
                       .permitAll()

                      )
                      .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint))
                      .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                      .httpBasic(Customizer.withDefaults());
       http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
       return http.build();
   }

   @Bean
   public PasswordEncoder passwordENcoder(){
       return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
      return  configuration.getAuthenticationManager();
   }

}

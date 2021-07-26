package com.dsi.banbeis.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@KeycloakConfiguration
public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    /**
     * Registers KeycloakAuthenticationProvider with spring security's authentication manager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authBuilder) {
        KeycloakAuthenticationProvider provider = keycloakAuthenticationProvider();

        // Setting this so the adapter automatically adds ROLE_ prefix to roles defined in Keycloak
        // Spring Security expects ROLE_ prefixes in all roles
        provider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());

        authBuilder.authenticationProvider(provider);
    }

    /**
     * This is provided by the Keycloak adapter library.
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public KeycloakClientRequestFactory keycloakClientRequestFactory;

    /**
     * Use Spring Boot native configuration for Keycloak.
     */
    @Bean
    public KeycloakConfigResolver configResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    /**
     * Provide a session authentication strategy bean which should be of type
     * RegisterSessionAuthenticationStrategy for public or confidential applications
     * and NullAuthenticatedSessionStrategy for bearer-only applications.
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public NullRequestCache nullRequestCache(){
        return new NullRequestCache();
    }

    /**
     * Global authorization configuration. Also, enables Spring Security CORS handling.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeRequests()
                .antMatchers("/sso/login*").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/authenticate/**").hasAnyRole("LIBRARIAN","ADMIN")
                .antMatchers("/login-check/**").hasAnyRole("LIBRARIAN","ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/books/**").authenticated()
                .antMatchers("/logout").authenticated()
                .anyRequest().denyAll()
                .and().requestCache().requestCache(nullRequestCache())

                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .formLogin()
                //.successHandler(new com.dsi.banbeis.config.RefererRedirectionAuthenticationSuccessHandler())
                //.defaultSuccessUrl("/authenticate",true)
                //.successHandler(new CustomAuthenticationSuccessHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");
        ;
        http.cors();
    }

    /**
     * Provide CORS configuration for Spring Security. Allowing all origins and methods for now.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.setAllowedMethods(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Helpful template to call upstream service providers passing the access token.
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public KeycloakRestTemplate keycloakRestTemplate() {
        return new KeycloakRestTemplate(keycloakClientRequestFactory);
    }

}

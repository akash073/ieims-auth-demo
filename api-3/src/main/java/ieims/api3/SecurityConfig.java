package ieims.api3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Autowired
    private OAuth2AuthorizedClientService clientService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .and()
                .cors()
                .disable()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()

                .authorizeRequests()
                .antMatchers("/oauth2/logout**").permitAll()
                .anyRequest().authenticated()

                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler(new SimpleUrlLogoutSuccessHandler() {

                    @Override
                    public void onLogoutSuccess(HttpServletRequest request,
                                                HttpServletResponse response, Authentication authentication)
                            throws IOException, ServletException {

                        String clientId = "api-3";
                        String clientSecret = "2652a05c-3b87-4a2e-9941-36428f3b2176";

                        OAuth2AuthenticationToken oauthToken =
                                (OAuth2AuthenticationToken) authentication;

                        OAuth2AuthorizedClient client =
                                clientService.loadAuthorizedClient(
                                        oauthToken.getAuthorizedClientRegistrationId(),
                                        oauthToken.getName());

                        String accessToken = client.getAccessToken().getTokenValue();
                        String refreshToken = client.getRefreshToken().getTokenValue();

                        //  OidcUser user = (OidcUser) authentication.getPrincipal();
                        String endSessionEndpoint =  "http://localhost:8000/auth/realms/development/protocol/openid-connect/logout";

                        UriComponentsBuilder builder = UriComponentsBuilder //
                                .fromUriString(endSessionEndpoint) //
                                .queryParam("client_id", clientId)

                                .queryParam("refresh_token",refreshToken)
                                .queryParam("client_secret", clientSecret)
                                ;

                        ResponseEntity<String> logoutResponse = getRestTemplate().getForEntity(builder.toUriString(), String.class);
                        if (logoutResponse.getStatusCode().is2xxSuccessful()) {
                            //log.info("Successfulley logged out in Keycloak");
                        } else {
                            //log.info("Could not propagate logout to Keycloak");
                        }

                        CookieUtils.deleteAllCookie(request,response);
                        //new SecurityContextLogoutHandler().logout(request, null, null);
                        super.onLogoutSuccess(request, response, authentication);
                    }
                })

        ;


      //  http.cors();
        // Add our custom Token based authentication filter
       // http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

   /* @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }*/

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }



}
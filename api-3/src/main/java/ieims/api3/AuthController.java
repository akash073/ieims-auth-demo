package ieims.api3;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AuthController {

    @Value("${successUrl}")
    private String targetUrl;

    @Autowired
    private OAuth2AuthorizedClientService clientService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/oauth2/authorize")
    public String  oauth2Authorize(HttpServletRequest request, HttpServletResponse response) {
        // CookieUtils.deleteAllCookie(request,response);
        return "redirect:" + targetUrl;
    }

    @GetMapping("/oauth2/logout")
    public @ResponseBody
    String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

       // CookieUtils.deleteAllCookie(request,response);
        onLogoutSuccess(request,response,authentication);
        return "ok";
    }


    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication)
    {

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
                .queryParam("client_id", refreshToken)

                .queryParam("refresh_token",refreshToken)
                .queryParam("client_secret", "2652a05c-3b87-4a2e-9941-36428f3b2176")
                ;

        ResponseEntity<String> logoutResponse = getRestTemplate().getForEntity(builder.toUriString(), String.class);
        if (logoutResponse.getStatusCode().is2xxSuccessful()) {
            //log.info("Successfulley logged out in Keycloak");
        } else {
            //log.info("Could not propagate logout to Keycloak");
        }

        //CookieUtils.deleteAllCookie(request,response);
        //new SecurityContextLogoutHandler().logout(request, null, null);
      //  super.onLogoutSuccess(request, response, authentication);
    }

    private RestTemplate getRestTemplate(){
        return restTemplate;
    }


}

package ieims.api3;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUrl;

    @GetMapping("/oauth2/authorize")
    public String  oauth2Authorize(HttpServletRequest request, HttpServletResponse response) {
        // CookieUtils.deleteAllCookie(request,response);
        return "redirect:" + targetUrl;
    }

    @GetMapping("/oauth2/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException {

        request.logout();
        String endSessionEndpoint =  issuerUrl + "/protocol/openid-connect/logout?redirect_uri="+targetUrl;
        CookieUtils.deleteAllCookie(request,response);
        return "redirect:" + endSessionEndpoint;
    }


}

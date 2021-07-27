package ieims.appmvc;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;

@Controller
public class AppController {

    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    @RequestMapping("/")
    public String index(Principal principal, HttpServletRequest request, Model model) {
        String username = (principal != null) ? principal.getName() : "n/a";
        KeycloakSecurityContext ksc =
                (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());

        String fullName = (ksc != null) ? ksc.getToken().getName() : "N/A";

        model.addAttribute("loggedIn", principal != null);
        model.addAttribute("username", username);
        model.addAttribute("fullName", fullName);

        return "index";
    }

    @RequestMapping("/test")
    public String test(Authentication authentication, Model model) {
        log.debug("authentication: {}", authentication);
        log.debug("authentication.details: {}", authentication.getDetails());

        String name = authentication.getName();
        String fullName = ((SimpleKeycloakAccount) authentication.getDetails())
                .getKeycloakSecurityContext().getToken().getName();

        model.addAttribute("name", name);
        model.addAttribute("fullName", fullName);

        return "test";
    }

    @PostMapping("/logout")
    public String logout(Authentication authentication, HttpServletRequest request) throws ServletException, UnsupportedEncodingException {
        log.info("Logout request by user: {}", authentication.getName());

        // ** NOTE **
        // Doing a `request.logout()` logs the user out, both in the current application and the SSO session. The
        // Keycloak adapter sends a back-channel POST to the Keycloak server to end the SSO session.
        //
        // But, it does not remove the Keycloak cookies in the browser. So front-end apps do not get informed about
        // the logout immediately.
        //
        //        request.logout();
        //        return "redirect:/";

        //
        // So instead, we are using the Keycloak logout URL. Redirecting the user to it removes the Keycloak
        // cookies, informs the Keycloak server to end the SSO session and then Keycloak uses the back-channel
        // logout URL `/k_logout` in this app to end the local session.
        //
        // The URL is hard-coded as it is only for demo purposes. The URL can be easily constructed from
        // reading the Spring Boot application properties.
        //

        return "redirect:http://localhost:8000/auth/realms/development/protocol/openid-connect/logout?redirect_uri="
                + URLEncoder.encode("http://localhost:5000/", "UTF-8");
    }

}

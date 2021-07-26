package ieims.api3;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    @Value("${successUrl}")
    private String targetUrl;

    @GetMapping("/oauth2/authorize")
    public String  oauth2Authorize(HttpServletRequest request, HttpServletResponse response) {
        // CookieUtils.deleteAllCookie(request,response);
        return "redirect:" + targetUrl;
    }

    @GetMapping("/oauth2/logout")
    public @ResponseBody
    String capitalize(@RequestParam String input,HttpServletRequest request, HttpServletResponse response) {

        CookieUtils.deleteAllCookie(request,response);

        return "ok";
    }

}

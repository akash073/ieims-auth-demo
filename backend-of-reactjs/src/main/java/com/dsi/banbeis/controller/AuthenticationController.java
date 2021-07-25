package com.dsi.banbeis.controller;

import com.dsi.banbeis.model.Book;
import com.dsi.banbeis.repository.BookRepository;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AuthenticationController {

    @Value("${react.root.url}")
    private String rootUrl;
    @Value("${react.accessDenied.url}")
    private String accessDeniedUrl;

    @Autowired
    private BookRepository bookRepository;


    @GetMapping(value = {"/authenticate"})
    public String authenticate(HttpServletRequest request) throws ServletException {

        KeycloakSecurityContext keycloakSecurityContext=
                (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());

        return "redirect:" + rootUrl;
    }
    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        request.getSession().invalidate();
        return "redirect:"+rootUrl;
    }

    @GetMapping(value = "/login-check", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String loginCheck(HttpServletRequest request) throws ServletException {
        return "ok";
    }

    @GetMapping(value = "/403")
    public String accessDenied(Model model) {
        return "redirect:" + accessDeniedUrl;
    }

    @GetMapping(value = "books/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Book> all() {
        return  bookRepository.readAll();
    }
}

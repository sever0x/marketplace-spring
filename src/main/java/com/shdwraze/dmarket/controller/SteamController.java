package com.shdwraze.dmarket.controller;

import com.shdwraze.dmarket.service.AccountService;
import com.shdwraze.dmarket.service.auth.SteamOpenID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@RestController
public class SteamController {
    private final String SITE_URL = "http://localhost:8080";
    @Autowired
    private AccountService accountService;
    @Autowired
    private SteamOpenID steamOpenID;

    @GetMapping("/link")
    public String loginAuth(HttpServletResponse response) throws IOException {
        response.sendRedirect(steamOpenID.login(getLinkToRedirect("/auth")));
        return null;
    }

    @GetMapping("/auth")
    public String authentication(HttpServletRequest request, HttpServletResponse response,
                                 Principal principal)
            throws IOException {
        String steamID = steamOpenID.verify(request.getRequestURL().toString(), request.getParameterMap());
        String link = getLinkToRedirect("/");

        if (principal != null) {
            accountService.linkToSteamAccount(steamID, principal.getName());
        }

        if (steamID == null) {
            response.sendRedirect(link);
        }

        request.getSession(true).setAttribute("steamid", steamID);
        response.sendRedirect(link);
        return "redirect:/";
    }

    private String getLinkToRedirect(String path) {
        return SITE_URL + path;
    }
}

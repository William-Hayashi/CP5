package br.com.fiap.roardemo.controller;

import br.com.fiap.roardemo.service.RoarService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoarController {

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal OAuth2User user) {
        model.addAttribute("roars", RoarService.getRoars());
        if (user != null) {
            model.addAttribute("userName", user.getAttribute("login")); // ou "name"
        }
        return "index";
    }

    @PostMapping("/roar")
    public String roar(@RequestParam("content") String content, @AuthenticationPrincipal OAuth2User user) {
        String username = (user != null) ? user.getAttribute("login") : "Anônimo";
        RoarService.addRoar(content, username);
        return "redirect:/";
    }


    @PostMapping("/like")
    public String like(@RequestParam("roarId") Long tweetId) {
        RoarService.likeRoar(tweetId);
        return "redirect:/";
    }

}

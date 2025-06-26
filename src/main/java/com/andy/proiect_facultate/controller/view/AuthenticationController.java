package com.andy.proiect_facultate.controller.view;

import com.andy.proiect_facultate.model.dto.request.RegisterRequest;
import com.andy.proiect_facultate.security.JwtUtil;
import com.andy.proiect_facultate.service.impl.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserServiceImpl userService;

    @GetMapping("/view/login")
    public String loginPage() {
        return "view/login";
    }

    @GetMapping("/view/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "view/register";
    }


    @PostMapping("/perform_login")
    public String performLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletResponse response,
            Model model) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            String token = jwtUtil.generateToken(authentication.getName(),
                    authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            return "redirect:/";

        } catch (AuthenticationException e) {
            model.addAttribute("error", "Email sau parolă incorecte");
            return "view/login";

        }
    }

    @PostMapping("/perform_register")
    public String performRegister(@ModelAttribute RegisterRequest registerRequest, Model model) {
        try {
            userService.registerUser(registerRequest);
            return "redirect:/view/login";

        } catch (Exception e) {
            model.addAttribute("error", "Înregistrare eșuată: " + e.getMessage());
            return "view/register";

        }
    }

    @GetMapping("/view/logout")
    public String logout(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        response.addCookie(jwtCookie);
        return "redirect:/view/logout";

    }

}

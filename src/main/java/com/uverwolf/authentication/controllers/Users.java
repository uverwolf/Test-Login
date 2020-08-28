package com.uverwolf.authentication.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uverwolf.authentication.models.User;
import com.uverwolf.authentication.services.UserService;

@Controller
public class Users {
	@Autowired
	UserService servicios;
	
	@GetMapping("/registration")
	public String formularioRegistro(@ModelAttribute("user")User user) {
		return "registrationPage.jsp";
	}
	@GetMapping("/login")
	public String login() {
		return "loginPage.jsp";
	}
	
	@PostMapping("/registration")
	public String registrarUsuario(@Valid @ModelAttribute("user")User user,BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			return"redirect:/registration";
		}else {
			servicios.registro(user);
			session.setAttribute("idUsuario",user.getId());
	
			return "redirect:/home";
		}
	}
	
	@PostMapping("/login")
	public String loginUser(@RequestParam("email")String email,@RequestParam("password")String password,Model modelo, HttpSession session) {
		if(servicios.autenticarUsuario(email, password)== true) {
			User usuario= servicios.buscarXEmail(email);
			session.setAttribute("idUsuario", usuario.getId());
			return "redirect:/home";
		}else {
			modelo.addAttribute("error", "Datos incorrectos");
			return "loginPage.jsp";
		}
	}
	
	@GetMapping("/home")
	public String home(HttpSession session, Model modelo) {
		Long userId= (Long) session.getAttribute("idUsuario");
		User u = servicios.buscarXId(userId);
		modelo.addAttribute("user",u);
		return "homePage.jsp";
	}
	@GetMapping("/logout")
	public String deslogear(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}

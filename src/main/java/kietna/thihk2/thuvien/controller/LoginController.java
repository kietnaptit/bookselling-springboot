package kietna.thihk2.thuvien.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import kietna.thihk2.thuvien.dao.UserDAO;
import kietna.thihk2.thuvien.model.User;

@Controller
public class LoginController {
	private UserDAO userDAO = new UserDAO();
	@GetMapping("/")
	public String homePage(Model model, HttpSession httpSession) {
		return "redirect:/login";
	}
	@GetMapping("/login")
	public String loginPage(Model model, HttpSession httpSession) throws IOException{
		User user = (User) httpSession.getAttribute("user");
		if(user != null && user.getLoggedIn()) {
			if(user.getRole().equals("admin")) {
				return "redirect:/admin";
			}else if(user.getRole().equals("user")) {
				return "redirect:/user";
			}
		}
		return "login";
	}
	@PostMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession httpSession) {
		User user = userDAO.checkLogin(username, password);
		if(user.getId()!= -1) {
			user.setLoggedIn(true);
			httpSession.setAttribute("user", user);
			if(user.getRole().equals("admin")) {
				return "redirect:/admin";
			}else if(user.getRole().equals("user")) {
				return "redirect:/user";
			}
		}else {
			return "errorlogin";
		}
		return "error";
	}
	@GetMapping("/register")
	public String registerPage(Model model) throws IOException{
		return "register";
	}
	@PostMapping("/register")
	public String register(@RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("email") String email,
			@RequestParam("name") String name, Model model) {
		boolean isexist = userDAO.checkExist(username);
		if(isexist) {	
			return "registererror";
		}else {
			User user = new User();
			user.setEmail(email);
			user.setName(name);
			user.setUsername(username);
			user.setPassword(password);
			boolean register = userDAO.registerUser(user);
			if(register) {
				return "registersuccess";
			}else {
				return "error";
			}
		}
	}
	@GetMapping("/logout")
	public String logout(Model model, HttpSession httpSession) {
		User user = (User)httpSession.getAttribute("user");
		if(user!=null) {
			user.setLoggedIn(false);
		}
		httpSession.invalidate();
		return "redirect:/login";
	}
	

}

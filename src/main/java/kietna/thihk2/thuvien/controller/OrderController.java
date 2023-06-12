package kietna.thihk2.thuvien.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import kietna.thihk2.thuvien.dao.OrderDAO;
import kietna.thihk2.thuvien.dao.UserDAO;
import kietna.thihk2.thuvien.model.Book;
import kietna.thihk2.thuvien.model.Order;
import kietna.thihk2.thuvien.model.User;

@Controller
public class OrderController {
	private OrderDAO orderDAO = new OrderDAO();
	@GetMapping("/orders")
	public String getOrders(Model model, HttpSession httpSession) {
		User user = (User)httpSession.getAttribute("user");
		if(user != null && user.getLoggedIn() && user.getRole().equals("user")) {
			List<Order> orders = orderDAO.getAllOrder(user);
			List<Order> cancelledOrders = orderDAO.getAllCanceledOrder(user);
			model.addAttribute("user", user);
			model.addAttribute("orders", orders);
			model.addAttribute("canceledorders", cancelledOrders);
			return "orders";
		}else {
			return "redirect:/login";
		}
	}
	@PostMapping("/order/new")
	public String newOrder(Model model, HttpSession httpSession
			,@RequestParam("id") String id
			,@RequestParam("title") String title
			,@RequestParam("quantity") String quantity) {
		User user = (User)httpSession.getAttribute("user");
		if(user != null && user.getLoggedIn() && user.getRole().equals("user")) {
			Order order = new Order();
			order.setBookid(Integer.valueOf(id));
			order.setBookname(title);
			order.setQuantity(Integer.valueOf(quantity));
			order.setUserid(user.getId());
			boolean success = orderDAO.createNewOrder(order);
			if(success) {
				return "redirect:/orders";
			}
		}
		return "error";
	}
	@PostMapping("/order/cancel")
	public String cancelOrder(Model model, HttpSession httpSession, @RequestParam("id") String id) {
		User user = (User)httpSession.getAttribute("user");
		if(user != null && user.getLoggedIn() && user.getRole().equals("user")) {
			boolean success = orderDAO.cancelAnOrder(Integer.valueOf(id));
			if(success) {
				return "redirect:/orders";
			}
		}
		return "error";
	}
}

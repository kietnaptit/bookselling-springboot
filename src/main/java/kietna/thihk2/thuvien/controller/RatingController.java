package kietna.thihk2.thuvien.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpSession;
import kietna.thihk2.thuvien.dao.RatingDAO;
import kietna.thihk2.thuvien.model.Rating;

@Controller
public class RatingController {
	private RatingDAO ratingDAO = new RatingDAO();
	@PostMapping("/rate")
	public RedirectView rating(Model model, HttpSession httpSession,
			@RequestParam("userid") String userid,
			@RequestParam("username") String username,
			@RequestParam("bookid") String bookid,
			@RequestParam("rating") String rating,
			@RequestParam("comment") String comment) {
		Rating rating2 = new Rating();
		rating2.setBookid(Integer.valueOf(bookid));
		rating2.setComment(comment);
		rating2.setRating(Integer.valueOf(rating));
		rating2.setUserid(Integer.valueOf(userid));
		rating2.setUsername(username);
		boolean success = false;
		int id = ratingDAO.getRatingIDOfAUserInABook(Integer.valueOf(bookid), Integer.valueOf(userid));
		if(id!=-1) {
			rating2.setId(id);
			success = ratingDAO.updateRating(rating2);
		}else {
			success = ratingDAO.newRating(rating2);
		}
		String redirectUrl = "/user/view/" + bookid;
		if(success) {
			return new RedirectView(redirectUrl);
		}
		return new RedirectView("/error");
	}
}

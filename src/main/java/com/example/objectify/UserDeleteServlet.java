package com.example.objectify;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;

@WebServlet(value = "/deleteUser")
public class UserDeleteServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String email = req.getParameter("email");

		if (email != null && !email.equals("")) {

			User user = ofy().load().type(User.class).filter("email", email).first().now();

			System.out.println("UserDetail-" + user.toString());

			ofy().transact(() -> {
				ofy().delete().keys(ofy().load().ancestor(Key.create(User.class, user.getId())).keys().list());

			});
		}

		resp.sendRedirect("/");
	}

}

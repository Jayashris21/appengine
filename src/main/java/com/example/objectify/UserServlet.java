package com.example.objectify;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

@WebServlet(value = "/user")
public class UserServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String age = request.getParameter("age");
		String mobile = request.getParameter("mobile");
		String addr1 = request.getParameter("address1");
		String addr2 = request.getParameter("address2");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zipcode = request.getParameter("zipcode");

		ofy().transact(() -> {
			
			Long uniqueID = UUID.randomUUID().getMostSignificantBits();
			
			User user = new User(name, email, mobile, Integer.parseInt(age));
			user.setId(uniqueID);
			
			Address address = new Address(addr1, addr2, city, state, zipcode);
			Ref<User> userId = Ref.create(user);
			address.setUser(userId);
			Key<Address> addressKey = ofy().save().entity(address).now();

			System.out.println("User:" + userId);

			user.address = Ref.create(addressKey);
			ofy().save().entity(user).now();

		});

		response.sendRedirect("/");

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String email = req.getParameter("email");

		if (email != null && !email.equals("")) {
			User user = ofy().load().type(User.class).filter("email", email).first().now();
			
			
			System.out.println("UserDetail-" + user.toString());
			ofy().transact(() -> {
				if (user.getId() != null) {

					Address usrAdd = user.address.get();
					System.out.println("Address-" + usrAdd.toString());

				}
			});

		}

		resp.sendRedirect("/");

	}

}

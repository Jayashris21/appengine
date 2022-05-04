package com.example.objectify;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.cmd.Query;

@WebServlet(value = "/compositeFilter")
public class CompositeQueryExample extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("Filtering users: age>23 and age<=27::");
		List<User> users = ofy().load().type(User.class).filter("age >", 23).filter("age <=", 27).list();

		for (User user : users) {
			System.out.println(user.toString());
		}

		System.out.println("\nFiltering Users belong to chennai and erode::");

		List<String> cities = new ArrayList<>(Arrays.asList("chennai", "erode"));

		Query<Address> addresses = ofy().load().type(Address.class).filter("city in", cities);

		for (Address addr : addresses) {

			User user = addr.getUser().get();
			System.out.println("UserDetails:" + user.toString());
			System.out.println(addr.toString() + "\n");

		}

		System.out.println("\nFiltering Users not in chennai::");

		Query<Address> addresses2 = ofy().load().type(Address.class).filter("city !=", "chennai");

		for (Address addr : addresses2) {

			User user = addr.getUser().get();
			System.out.println("UserDetails:" + user.toString());
			System.out.println(addr.toString() + "\n");

		}

		resp.sendRedirect("/");
	}

}

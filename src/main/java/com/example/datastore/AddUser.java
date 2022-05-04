package com.example.datastore;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;

import redis.clients.jedis.Jedis;

@WebServlet(value = "/datastore/addUser")
public class AddUser extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String age = request.getParameter("age");
		String addrLine1 = request.getParameter("address1");
		String addrLine2 = request.getParameter("address2");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zipcode = request.getParameter("zipcode");
		String mobile = request.getParameter("mobile");

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query q = new Query("User").setFilter(new FilterPredicate("email", FilterOperator.EQUAL, email));
		PreparedQuery pq = datastore.prepare(q);
		Entity result = pq.asSingleEntity();

		if (result != null && result.getKey() != null) {
			Entity user;
			Transaction txn1 = datastore.beginTransaction();

			try {
				user = datastore.get(result.getKey());
				user.setProperty("age", 24);
				
				
				Entity address = new Entity("Addres");
				datastore.put(txn1, user);
				txn1.commit();

			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}

		} else {

			Entity user = new Entity("User");

			user.setProperty("name", name);
			user.setProperty("email", email);
			user.setProperty("mobile", mobile);
			user.setProperty("age", age);

			datastore.put(user);

			Entity address = new Entity("Addres", user.getKey());

			address.setProperty("addressLine1", addrLine1);
			address.setProperty("addressLine2", addrLine2);
			address.setProperty("city", city);
			address.setProperty("state", state);
			address.setProperty("zipcode", zipcode);

			datastore.put(address);

			System.out.println("Adding user to the datastore::" + user + " " + address);

		}

		response.sendRedirect("/");

	}

}

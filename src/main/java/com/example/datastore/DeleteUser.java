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
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@WebServlet(value = "/datastore/deleteUser")
public class DeleteUser extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("Entering:");

		String email = request.getParameter("email");

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("User").setFilter(new FilterPredicate("email", FilterOperator.EQUAL, email));

		PreparedQuery pq = datastore.prepare(q);
		Entity result = pq.asSingleEntity();

		if (result != null && result.getKey() != null) {
			System.out.println("result:" + result.getKey());
			datastore.delete(result.getKey());

		} else {
			System.out.println("User Not Found");
		}
		
		response.sendRedirect("/");

	}

}

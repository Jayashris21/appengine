package com.example.datastore;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import redis.clients.jedis.Jedis;

@WebServlet(value = "/datastore/indexes")
public class IndexesExample extends HttpServlet {

	Jedis jedis = new Jedis("localhost");

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = jedis.lrange("ageRange1", 0, -1);
		jedis.expire("ageRange1", 100);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		if (!list.isEmpty()) {
			System.out.println("Redis Hit");
			for (int i = 0; i < list.size(); i++) {

				System.out.println("Getting From Redis " + list.get(i));
			}

		} else {
			System.out.println("Redis Miss");
			Filter ageFilter1 = new FilterPredicate("age", FilterOperator.GREATER_THAN, 23);
			Filter ageFilter2 = new FilterPredicate("age", FilterOperator.LESS_THAN, 27);

			Query q = new Query("User").setFilter(CompositeFilterOperator.and(ageFilter1, ageFilter2));

			List<Entity> r1 = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());

			for (Entity e : r1) {
				jedis.lpush("ageRange1", e.getProperty("name").toString());
			}

			List list1 = jedis.lrange("ageRange1", 0, -1);

			if (!list1.isEmpty()) {
				for (int i = 0; i < list1.size(); i++) {
					System.out.println("Getting from redis " + list1.get(i));
				}

			}

		}

		response.sendRedirect("/");
	}

}

package com.example.elasticsearch;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(value = "/eSearch")
public class ElasticSearchExample extends HttpServlet {

	public ElasticSearchExample() {

	}

	// To insert a new document into index
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String result;
		try {
			result = sendPOST("http://localhost:9200/order/_doc/4");
			System.out.println(result);
		} catch (ParseException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		
		boolean check = false;
		
		//Query Search - gets the document with 

		if (check) {
			try (CloseableHttpClient client = HttpClients.createDefault()) {

				String JSON_STRING = "{\n" + "  \"query\" : {\n" + " \"match\" : {\n"
						+ " \"user_name\" : \"jayashri\" \n" + "}\n" + "}\n" + "}";

				StringEntity requestEntity = new StringEntity(JSON_STRING, ContentType.APPLICATION_JSON);

				HttpPost request = new HttpPost("http://localhost:9200/order/_search");
				request.setEntity(requestEntity);

				CloseableHttpResponse response = client.execute(request);

				HttpEntity entity = response.getEntity();
				System.out.println(entity);
				String result;
				try {
					result = EntityUtils.toString(entity); // student = new
					System.out.println(result);
				} catch (ParseException e) {
					System.out.println(e);
				} catch (IOException e) {
					System.out.println(e);
				}

			}
		} else {
			
			//Id search
			try (CloseableHttpClient client = HttpClients.createDefault()) {

				HttpGet request = new HttpGet("http://localhost:9200/order/_doc/1/_source");

				Order response = client.execute(request,
						httpResponse -> mapper.readValue(httpResponse.getEntity().getContent(), Order.class));

				System.out.println(response.toString());
			}
		}

		resp.sendRedirect("/");
	}

	private static String sendPOST(String url) throws IOException, ParseException {

		String result = "";
		HttpPost post = new HttpPost(url);
		post.addHeader("content-type", "application/json");

		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"order_id\":\"4\",");
		json.append("\"orderitem_id\":\"1004\",");
		json.append("\"product\":\"Laptop Charger\",");
		json.append("\"category\":\"Electronic Devices\",");
		json.append("\"user_name\":\"Prabhu Raj\",");
		json.append("\"existing_user\":\"false\"");
		json.append("}");

		// send a JSON data
		post.setEntity(new StringEntity(json.toString()));

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			result = EntityUtils.toString(response.getEntity());
		}

		return result;
	}

}

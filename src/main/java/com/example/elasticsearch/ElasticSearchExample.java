package com.example.elasticsearch;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import com.example.objectify.User;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

@WebServlet(value="/eSearch")
public class ElasticSearchExample  extends HttpServlet{
	
	public ElasticSearchExample() {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		System.out.println("one");
		
		RestClient restClient = RestClient.builder(
		    new HttpHost("localhost", 9200)).build();
		
		//System.out.println("two");
		ElasticsearchTransport transport = new RestClientTransport(
		    restClient, new JacksonJsonpMapper());

		//System.out.println("three");
		ElasticsearchClient client = new ElasticsearchClient(transport);
		
		//User user = new User("Jayashri", "jay123@gmail.com", "9876543210", 25);
		
		/*Map<String, Object> dataMap = new HashMap<String, Object>();
		String id = UUID.randomUUID().toString();
	    dataMap.put("userId", id);
	    dataMap.put("name", "Jayashri");
	    
	    CreateIndexResponse createResponse = client.indices().create(
	    	    new CreateIndexRequest.Builder()
	    	        .index("my-index")
	    	        .
	    	        .build()
	    	);
		
		System.out.println("four");*/
		SearchResponse<User> search = client.search(s -> s
	            .index("userdetails")
	            .query(q -> q
	                .term(t -> t
	                    .field("name")
	                    .value(v -> v.stringValue("Ayala Barker"))
	                )),
	            User.class);
		
		for (Hit<User> hit: search.hits().hits()) {
            System.out.println(hit.source());
        }
		
	
	}
	

}

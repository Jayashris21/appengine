package com.test.pubsub;


import java.io.IOException;
import java.util.Base64;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.test.appengine.taskqueue.push.Worker;

@WebServlet(value = "/pubsub/push")
public class PubSubPush extends HttpServlet {
	
	private final Gson gson = new Gson();
	private final JsonParser jsonParser = new JsonParser();
	private static final Logger log = Logger.getLogger(Worker.class.getName());
		
	  @Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException, ServletException {
	    
	    String requestBody = req.getReader().lines().collect(Collectors.joining("\n"));
	    JsonElement jsonRoot = jsonParser.parse(requestBody);
	    String messageStr = jsonRoot.getAsJsonObject().get("message").toString();
	    Message message = gson.fromJson(messageStr, Message.class);
	    String decoded = decode(message.getData());
	    
	    boolean checkRetry = true;
	    if(checkRetry) {
	    	log.info(decoded+" , Hello from Pub Sub");
		    resp.setStatus(200);
	    } else {
	    	 resp.setStatus(503);
	    }
	    
	   
	  }

	 
	  private String decode(String data) {
	    return new String(Base64.getDecoder().decode(data));
	  }

	
	  
}
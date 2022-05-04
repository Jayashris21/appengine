package com.test.pubsub;

import java.io.IOException;
import java.util.logging.Logger;


import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;


import com.google.pubsub.v1.PubsubMessage;
import com.test.appengine.taskqueue.push.Worker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Publish", value="/publish")
public class PubSubPublish extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(Worker.class.getName());
	private Publisher publisher;

	public PubSubPublish() {}

	PubSubPublish(Publisher publisher) {
	    this.publisher = publisher;
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		Publisher publisher = this.publisher;
		
		try {
			String topicId = "testtopic";
			log.info("Publishing message - enter");
			
			if(publisher == null) {
				ProjectTopicName topicName = ProjectTopicName.newBuilder().setProject("fullhistoryinternsproject").setTopic(topicId).build();
				publisher = Publisher.newBuilder(topicName).build();
				
				
				final String payload = req.getParameter("payload");
				log.info("Payload:"+payload);
				PubsubMessage pubsubMessage =
				          PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8(payload)).build();
				
				publisher.publish(pubsubMessage);
				//resp.setStatus(200);
				//resp.sendRedirect("/");

				
			}
			log.info("Publishing message - exit ");
		} catch(Exception e) {
			log.info("Exception occured  "+e);
	    }
		
		
	}

}

package com.test.pubsub;

import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import com.test.appengine.taskqueue.push.Worker;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;



public class PubSubPull {
	
	
	private static final Logger log = Logger.getLogger(Worker.class.getName());

	public static void main(String... args) throws Exception {
	   
	    String projectId = "fullhistoryinternsproject";
	    String subscriptionId = "testsubscriptions";

	    subscribe(projectId, subscriptionId);
	  }

	private static void subscribe(String projectId, String subscriptionId) {
		
		ProjectSubscriptionName subscriptionName =
		        ProjectSubscriptionName.of(projectId, subscriptionId);
		
		
		
		MessageReceiver receiver =
		        (PubsubMessage message, AckReplyConsumer consumer) -> {
		          
		          log.info("Id: " + message.getMessageId());
		          log.info("Data: " + message.getData().toStringUtf8());
		         
		          message
		              .getAttributesMap()
		              .forEach((key, value) -> System.out.println(key + " = " + value));
		          consumer.ack();
		        };
		        
		        
		        Subscriber subscriber = null;
		        try {
		          subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
		          subscriber.startAsync().awaitRunning();
		          System.out.printf("Listening for messages on %s:\n", subscriptionName.toString());
		          
		          subscriber.awaitTerminated(30, TimeUnit.SECONDS);
		        } catch (TimeoutException timeoutException) {
		          
		          subscriber.stopAsync();
		        }      
		        
		        
	}


}

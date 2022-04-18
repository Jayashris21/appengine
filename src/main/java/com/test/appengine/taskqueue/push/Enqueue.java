package com.test.appengine.taskqueue.push;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
	    name = "TaskEnque",
	    description = "taskqueue: Enqueue a job with a key",
	    urlPatterns = "/enqueue"
	)
public class Enqueue extends HttpServlet {
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException {
		    String key = request.getParameter("key");
		 	//String key = "jay";
		    // Add the task to the default queue.
		    // [START addQueue]
		    Queue queue = QueueFactory.getQueue("my-push-queue");
		    queue.add(TaskOptions.Builder.withUrl("/worker").param("key", key));
		    // [END addQueue]

		    response.sendRedirect("/");
		  }

}

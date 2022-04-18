package com.test.cron;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.appengine.taskqueue.push.Worker;

@WebServlet(name = "cron", value = "/cron")
@SuppressWarnings("serial")
public class Cron extends HttpServlet {
	private static final Logger log = Logger.getLogger(Worker.class.getName());
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    PrintWriter out = resp.getWriter();
	    log.info("Hello from cron! Scheduled for every 1 min");
	  }
}

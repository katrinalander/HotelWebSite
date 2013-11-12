package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import core.Feedback;
import dbPackage.DBFeedback;


@WebServlet("/FeedbackServlet")
public class FeedbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("in Feedbackservlet");
		DBFeedback dbf = new DBFeedback();
		ArrayList <Feedback> feedback = null;
		try {
			feedback = dbf.getAllFeedback();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("can't take all feedbacks from db");
		}
		PrintWriter out = new PrintWriter(resp.getWriter());
		 Gson gson = new Gson();
		 String jsonStr = gson.toJson(feedback);
		 out.println(jsonStr);
	}

}

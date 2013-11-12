package servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;

import core.Feedback;
import core.Reservation;
import dbPackage.DBFeedback;

@WebServlet("/CreateFeedbackServlet")
public class CreateFeedbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(" in CreateFeedbackServlet");
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = req.getReader();
		String str;
		while ((str=reader.readLine())!=null){
			sb.append(str);
		}//while
		Feedback fb = new Gson().fromJson(sb.toString(), Feedback.class);
		DBFeedback dbf = new DBFeedback();
		System.out.println("feedback="+fb);
		try {
			dbf.insertFeedback(fb);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("I can't insert feedback in servlet");
		}
	}//service

}

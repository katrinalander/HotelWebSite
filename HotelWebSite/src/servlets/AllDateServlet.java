package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.google.gson.Gson;

import core.CalDate;
import core.User;
import dbPackage.DBCalendar;

@WebServlet("/AllDateServlet")
public class AllDateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("in alldateServlet");
		HttpSession session = req.getSession(true);
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = req.getReader();
		String str;
		while ((str=reader.readLine())!=null){
			sb.append(str);
		}
		String type = new Gson().fromJson(sb.toString(), String.class);
//		String type ="";
//		if(id.equals("1")){type="sngl";}
//		if(id.equals("2")){type="dbl";}
//		if(id.equals("3")){type="twin";}
//		if(id.equals("4")){type="trpl";}
//		if(id.equals("5")){type="king";}
//		if(id.equals("6")){type="hmn";}
		DBCalendar dbc = new DBCalendar();
		ArrayList <String> dates = null;
		try {
			dates = dbc.getAllDates(type);
		} catch (Exception e) {
			System.out.println("Problem with taking of all dates");
			e.printStackTrace();
		}
		PrintWriter out = new PrintWriter(resp.getWriter());
		 Gson gson = new Gson();
		 String jsonStr = gson.toJson(dates);
		 out.println(jsonStr);
	}

}

package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;

import core.User;
import dbPackage.DBUser;

@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("in UpdateUserServlet");
		boolean status = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = req.getReader();
		String str;
		while ((str=reader.readLine())!=null){
			sb.append(str);
		}
		User us = new Gson().fromJson(sb.toString(), User.class);
		DBUser dbu = new DBUser();
		try {
			dbu.updateUser(us);
			status=true;
		} catch (Exception e) {
			e.printStackTrace();System.out.println("can't update user data");
		}
		PrintWriter out = new PrintWriter(resp.getWriter());
		 Gson gson = new Gson();
		 String jsonStr = gson.toJson(status);
		 out.println(jsonStr);
	}

}

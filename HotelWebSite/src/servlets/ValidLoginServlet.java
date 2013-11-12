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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.mysql.jdbc.Connection;

import core.User;
import dbPackage.ConnectionDB;
import dbPackage.DBUser;



@WebServlet("/ValidLoginServlet")
public class ValidLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = req.getReader();
		String str;
		while ((str=reader.readLine())!=null){
			sb.append(str);
		}
		User us = new Gson().fromJson(sb.toString(), User.class);
		String email = us.getEmail();
		String password = us.getPassword();
		boolean status = false;
		try {
			DBUser user = new DBUser();
			status = user.validLogin(email, password);
		} catch (Exception e) {
			System.out.println("I can't check your login");
			e.printStackTrace();
		}
		PrintWriter out = resp.getWriter();
		out.print(status);
	}

}

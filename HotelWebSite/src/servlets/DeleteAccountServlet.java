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

import dbPackage.DBUser;

@WebServlet("/DeleteAccountServlet")
public class DeleteAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("in DeleteUserServlet");
		boolean status = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = req.getReader();
		String str="";
		while ((str=reader.readLine())!=null){
			sb.append(str);
		}
		String email = new Gson().fromJson(sb.toString(), String.class);
		DBUser dbu = new DBUser();
		try {
			dbu.deleteUser(email);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintWriter out = new PrintWriter(resp.getWriter());
		 Gson gson = new Gson();
		 String jsonStr = gson.toJson(status);
		 out.println(jsonStr);
	}

}

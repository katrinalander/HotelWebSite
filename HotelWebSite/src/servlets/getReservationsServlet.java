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

import com.google.gson.Gson;

import core.Reservation;
import dbPackage.DBReservations;


@WebServlet("/getReservationsServlet")
public class getReservationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = req.getReader();
		String str;
		while ((str=reader.readLine())!=null){
			sb.append(str);
		}
		String email = new Gson().fromJson(sb.toString(),String.class);
		DBReservations dbr = new DBReservations();
		ArrayList <Reservation> listReserv = null;
		try {
			listReserv = dbr.getAllReservations(email);
		} catch (Exception e) {
			e.printStackTrace();System.out.println("can't write ArrayList");
		}
		PrintWriter out = new PrintWriter(resp.getWriter());
		 Gson gson = new Gson();
		 String jsonStr = gson.toJson(listReserv);
		 out.println(jsonStr);
	}

}

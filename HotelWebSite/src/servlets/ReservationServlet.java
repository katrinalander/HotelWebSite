package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.google.gson.Gson;

import core.MailNotification;
import core.Reservation;
import core.User;
import dbPackage.DBCalendar;
import dbPackage.DBReservations;
import dbPackage.DBUser;


@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}


	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("In ReservationServlet");
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = req.getReader();
		String str;
		while ((str=reader.readLine())!=null){
			sb.append(str);
		}
		Reservation res = new Gson().fromJson(sb.toString(), Reservation.class);
		DBReservations dbr = new DBReservations();
		DBCalendar dbc = new DBCalendar();
		try {
			dbc.insertDate(res);
			dbr.insertReservations(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

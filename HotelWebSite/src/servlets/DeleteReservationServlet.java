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

import core.Reservation;
import dbPackage.DBCalendar;
import dbPackage.DBReservations;


@WebServlet("/DeleteReservationServlet")
public class DeleteReservationServlet extends HttpServlet {
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
		Reservation res = new Gson().fromJson(sb.toString(), Reservation.class);
		int id = res.getReservId();
		DBReservations dbr = new DBReservations();
		Reservation reserv = null;
		try {
			reserv = dbr.getReservation(id);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("can't take reservation by Id");
		}
		DBCalendar dbc = new DBCalendar();
		try {
			dbc.deleteReservation(reserv.getReservId());
			dbr.deleteReservation(reserv.getReservId());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("can't delete this reservations");
		}
	}//service

}

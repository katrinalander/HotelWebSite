package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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
import core.Room;
import core.Search;
import dbPackage.DBCalendar;
import dbPackage.DBRoom;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("in SearchServlet");
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = req.getReader();
		String str;
		while ((str=reader.readLine())!=null){
			sb.append(str);// search parameter like: {"checkIn":"2013-11-02T23:35:21.036Z","checkOut":"2013-11-02T23:35:21.036Z","sngl":4,"dbl":"1","trpl":"0"}
		}
		Search search = new Gson().fromJson(sb.toString(), Search.class);
		String checkIn = search.getCheckIn();
		String checkOut = search.getCheckOut();
		int sngl = search.getSngl();
		int dbl = search.getDbl();
		int trpl = search.getTrpl();
		DBCalendar dbc = new DBCalendar();
		ArrayList <String> dates=null;
		try {
			dates = (ArrayList<String>) dbc.daysBetweenDates(checkIn, checkOut);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ArrayList <Room> rooms = new ArrayList<Room>();
		Room r = new Room();
		DBRoom dbr = new DBRoom();
		boolean status = true;
		CalDate cd = null;
		for(String date:dates){
			cd = new CalDate(date,sngl,dbl,trpl);
			if(!dbc.checkRooms(cd)){
				status=false;
			}//if
		}//for
		System.out.println("staus available room="+status);
		if(status){
			if(cd.getSngl()!=0){
				try {
					r = dbr.getRoom(1);
					r.setNum(cd.getSngl());
					rooms.add(r);
				} catch (Exception e) {
					e.printStackTrace();System.out.println("error in getting room");
				}
			}//sngl
				if(cd.getDbl()!=0){
					try {
						r = dbr.getRoom(2);
						r.setNum(cd.getDbl());
						rooms.add(r);
					} catch (Exception e) {
						e.printStackTrace();System.out.println("error in getting room");
					}
				}//dbl
				if(cd.getTrpl()!=0){
					System.out.println("in 3 if");
					try {
						r = dbr.getRoom(4);
						r.setNum(cd.getTrpl());
						rooms.add(r);
					} catch (Exception e) {
						e.printStackTrace();System.out.println("error in getting room");
					}
				}//trpl
		}//if status
		PrintWriter out = new PrintWriter(resp.getWriter());
		 Gson gson = new Gson();
		 String jsonStr = gson.toJson(rooms);
		 out.println(jsonStr);
	}

}

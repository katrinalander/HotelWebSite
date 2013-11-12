package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;

import core.Room;
import dbPackage.DBRoom;


@WebServlet("/AllRoomServlet")
public class AllRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DBRoom dbr = new DBRoom();
		ArrayList <Room> rooms = null;
		try {
			rooms = dbr.getAllRooms();
		} catch (Exception e) {
			System.out.println("Problem with taking of all rooms");
			e.printStackTrace();
		}
		PrintWriter out = new PrintWriter(resp.getWriter());
		 Gson gson = new Gson();
		 String jsonStr = gson.toJson(rooms);
		 out.println(jsonStr);
	}//service

}

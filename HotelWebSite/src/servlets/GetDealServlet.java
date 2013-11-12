package servlets;

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

import core.Room;
import core.RoomsDeal;
import core.User;
import dbPackage.DBUser;

@WebServlet("/GetDealServlet")
public class GetDealServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RoomsDeal rd = new RoomsDeal();
		ArrayList<Room> dealrooms = null;
		try {
			dealrooms = rd.getRoomsDeal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Rooms deal will be: "+dealrooms);
		
		PrintWriter out = new PrintWriter(resp.getWriter());
		Gson gson = new Gson();
		String jsonStr = gson.toJson(dealrooms);
		out.println(jsonStr);
	}

}

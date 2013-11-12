package dbPackage;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;

import core.Room;

public class DBRoom {
	public Room getRoom(int room_id) throws Exception{
		Connection con = ConnectionDB.getInstance().getConnection();
		PreparedStatement pstm = con.prepareStatement("select * from rooms where room_id="+room_id);
		ResultSet rs = (ResultSet) pstm.executeQuery();
		Room r = new Room();
		while(rs.next() ){
		r.setId(rs.getInt("room_id"));
		if(rs.getString("type").equals("sngl")){
			r.setType("Single room");}
		if(rs.getString("type").equals("dbl")){
			r.setType("Double room");}
		if(rs.getString("type").equals("twin")){
			r.setType("Twin room");}
		if(rs.getString("type").equals("trpl")){
			r.setType("Triple room");}
		if(rs.getString("type").equals("king")){
			r.setType("King room");}
		if(rs.getString("type").equals("hmn")){
			r.setType("Honeymoon");}
		r.setPrice_regular(rs.getDouble("price_regular"));
		r.setPrice_deal(rs.getDouble("price_deal"));
		r.setArea(rs.getDouble("area"));
		r.setImageUrl(rs.getString("imageUrl"));
		r.setAmount(rs.getInt("amount"));
		r.setDescription("");
		}//while
		return r;
	}
	
	public ArrayList<Room> getAllRooms() throws Exception{
		Connection con = ConnectionDB.getInstance().getConnection();
		PreparedStatement pstm = con.prepareStatement("select * from rooms");
		ResultSet rs = (ResultSet) pstm.executeQuery();
		ArrayList<Room> rooms = new ArrayList<Room>();
		Room room = null;
		while(rs.next()){
			room = new Room();
			room.setId(rs.getInt("room_id"));
			room.setType2(rs.getString("type"));
			if(rs.getString("type").equals("sngl")){
				room.setType("Single room");
				room.setDescription("For those who came to us alone and wants to comfort and privacy of - " +
						"our single room with a comfortable twin bed.");}
			if(rs.getString("type").equals("dbl")){
				room.setType("Double room");
				room.setDescription("Lovely room with a splendid view from the window and a comfortable queen bed," +
						" would be the perfect place for couples.");}
			if(rs.getString("type").equals("twin")){ 
				room.setType("Twin room");
				room.setDescription("Spacious comfortable room with two twin beds will be great for two friends. " +
						"If desired, the beds can be pushed close by.");}
			if(rs.getString("type").equals("trpl")){
				room.setType("Triple room");
				room.setDescription("Triple room with 3 twin beds that can be moved at will close by, " +
						"it will great for families  with children.");}
			if(rs.getString("type").equals("king")){
				room.setType("King apartment");
				room.setDescription("Royal room is spacious, luxurious, furnished in the style of Rococo. You will feel themselves VIPs.");}
			if(rs.getString("type").equals("hmn")){
				room.setType("Honeymoon");
				room.setDescription("The most romantic room with a beautiful view of the sunset. The best place for honeymooners.");}
			room.setArea         (rs.getDouble("area"));
			room.setPrice_regular(rs.getDouble("price_regular"));
			room.setPrice_deal   (rs.getDouble("price_deal"));
			room.setImageUrl     (rs.getString("imageUrl"));
			room.setAmount       (rs.getInt("amount"));
			rooms.add(room);
		}
		return rooms;
	}
}

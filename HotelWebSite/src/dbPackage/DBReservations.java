package dbPackage;

import java.sql.Date;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import core.MailNotification;
import core.Reservation;

public class DBReservations {
	public void insertReservations (Reservation reser) throws Exception{
		Connection con=ConnectionDB.getInstance().getConnection();
		PreparedStatement pstm=(PreparedStatement)con.prepareStatement
				("insert into reservations(email,check_in,check_out,sngl,dbl,twin,trpl,king,hmn,price) " +
						"values(?,?,?,?,?,?,?,?,?,?)");
		pstm.setString   (1, reser.getEmail());
		pstm.setString(2, reser.getCheckIn());
		pstm.setString(3, reser.getCheckOut());
		pstm.setInt(4, reser.getSngl());
		pstm.setInt(5, reser.getDbl());
		pstm.setInt(6, reser.getTwin());
		pstm.setInt(7, reser.getTrpl());
		pstm.setInt(8, reser.getKing());
		pstm.setInt(9, reser.getHmn());
		pstm.setDouble(10, reser.getPrice());
	    pstm.execute();
	  //-------------------Building message email---------------------------------------------
	  	String type ="";
	    if(reser.getSngl()!=0){type=type+"Single room ";}
	    if(reser.getDbl()!=0){type=type+"Double room ";}
	    if(reser.getTwin()!=0){type=type+"Twin room ";}
	    if(reser.getTrpl()!=0){type=type+"Triple room ";}
	    if(reser.getKing()!=0){type=type+"King apartment ";}
	    if(reser.getHmn()!=0){type=type+"Honeymoon ";}
	    PreparedStatement pstm2=(PreparedStatement)con.prepareStatement("SELECT * FROM reservations WHERE reserv_id=(SELECT MAX(reserv_id) FROM reservations)");
	    	String email = reser.getEmail();
	  		String checkIn = reser.getCheckIn();
	  		String checkOut = reser.getCheckOut();
	  		ResultSet rs = (ResultSet) pstm2.executeQuery();
	  		int resId = 0;
	  		while(rs.next()){
	  		resId = rs.getInt("reserv_id");}
	  		double price = reser.getPrice();
	  		String subject = "Reservation in hotel Paradise";
	  		String text = "<img src=\"cid:image\">"+
	  				"<h2>This message was generated automatically for "+email+", from <a href=\"http://localhost:8080/HotelWebSite/\" style=\"color:#ff9911\">Paradise.hotel</a></h2>" +
	  				"after Your successed reservation on site Paradise hotel.</h2>" +
	  				"<p>Number of your reservation: <b>"+resId+"</b></p>" +
	  				"<p>Room type is: <b>"+type+"</b></p>" +
	  				"<p>Check in date: <b>"+checkIn+"</b></p>" +
	  				"<p>Check out date: <b>"+checkOut+"</b></p>" +
	  				"<p>Price: <b>$"+price+"</b></p>" +
	  				"<h2>Please print this email and show to the receptionist for quicker registration.</h2>";
	  		MailNotification mn = new MailNotification(email,subject,text);
	  		mn.run();
	}//insert reservation
	
	public void deleteReservation(int resrvID) throws Exception{
		Connection con=ConnectionDB.getInstance().getConnection();
		Reservation res = getReservation(resrvID);
		PreparedStatement pstm=(PreparedStatement) con.prepareStatement("delete from reservations where reserv_id = " + resrvID);
		pstm.execute();
//-------------------Building message email---------------------------------------------
		int id = res.getReservId();
		String email = res.getEmail();
		String checkIn = res.getCheckIn();
		String checkOut = res.getCheckOut();
		String type ="";
	    if(res.getSngl()!=0){type=type+"Single room ";}
	    if(res.getDbl()!=0){type=type+"Double room ";}
	    if(res.getTwin()!=0){type=type+"Twin room ";}
	    if(res.getTrpl()!=0){type=type+"Triple room ";}
	    if(res.getKing()!=0){type=type+"King apartment ";}
	    if(res.getHmn()!=0){type=type+"Honeymoon ";}
	    String subject = "Cancelation of reservation in hotel Paradise";
  		String text = "<img src=\"cid:image\">"+
  				"<h2>This message was generated automatically for "+email+", from <a href=\"http://localhost:8080/HotelWebSite/\" style=\"color:#ff9911\">Paradise.hotel</a></h2>" +
  				"after Your cancelation of reservation on site Paradise hotel.</h2>" +
  				"<p>Number of your reservation: <b>"+id+"</b></p>" +
  				"<p>Room type is: <b>"+type+"</b></p>" +
  				"<p>Check in date: <b>"+checkIn+"</b></p>" +
  				"<p>Check out date: <b>"+checkOut+"</b></p>" +
  				"<h2>We hope that you will continue to use the services of our hotel.</h2>";
  		MailNotification mn = new MailNotification(email,subject,text);
  		mn.run();
	}//delete reservation
	
	public ArrayList<Reservation> getAllReservations(String email) throws Exception{
		Connection con = ConnectionDB.getInstance().getConnection();
		PreparedStatement pstm = (PreparedStatement) con.prepareStatement("select * from reservations where email='"+email+"'");
		ResultSet rs = (ResultSet) pstm.executeQuery();
		ArrayList<Reservation> reserv = new ArrayList<Reservation>();
		Reservation res = null;
		while(rs.next()){
			res = new Reservation();
			res.setReservId(rs.getInt("reserv_id"));
			res.setEmail(rs.getString("email"));
			res.setCheckIn(rs.getString("check_in"));
			res.setCheckOut(rs.getString("check_out"));
			String ty ="";
			res.setSngl(rs.getInt("sngl")); if(rs.getInt("sngl")!=0){ty=ty+"Single room ";}
			res.setDbl(rs.getInt("dbl"));   if(rs.getInt("dbl")!=0){ty=ty+"Double room";}
			res.setTwin(rs.getInt("twin")); if(rs.getInt("twin")!=0){ty=ty+"Twin room";}
			res.setTrpl(rs.getInt("trpl")); if(rs.getInt("trpl")!=0){ty=ty+"Triple room";}
			res.setKing(rs.getInt("king")); if(rs.getInt("king")!=0){ty=ty+"King apartment";}
			res.setHmn(rs.getInt("hmn"));   if(rs.getInt("hmn")!=0){ty=ty+"Honeymoon";}
			res.setType(ty);
			res.setPrice(rs.getDouble("price"));
			reserv.add(res);
		}
		System.out.println("reserv: "+reserv);
		return reserv;
	}//getAllReservations
	
	public Reservation getReservation(int id) throws Exception{
		Connection con = ConnectionDB.getInstance().getConnection();
		PreparedStatement pstm = (PreparedStatement) con.prepareStatement("select * from reservations where reserv_id="+id);
		ResultSet rs = (ResultSet) pstm.executeQuery();
		Reservation res = new Reservation();
		while(rs.next() ){
			res.setReservId(rs.getInt("reserv_id"));
			res.setEmail(rs.getString("email"));
			res.setCheckIn(rs.getString("check_in"));
			res.setCheckOut(rs.getString("check_out"));
			String ty ="";
			res.setSngl(rs.getInt("sngl")); if(rs.getInt("sngl")!=0){ty=ty+"Single room ";}
			res.setDbl(rs.getInt("dbl"));   if(rs.getInt("dbl")!=0){ty=ty+"Double room";}
			res.setTwin(rs.getInt("twin")); if(rs.getInt("twin")!=0){ty=ty+"Twin room";}
			res.setTrpl(rs.getInt("trpl")); if(rs.getInt("trpl")!=0){ty=ty+"Triple room";}
			res.setKing(rs.getInt("king")); if(rs.getInt("king")!=0){ty=ty+"King apartment";}
			res.setHmn(rs.getInt("hmn"));   if(rs.getInt("hmn")!=0){ty=ty+"Honeymoon";}
			res.setType(ty);
			res.setPrice(rs.getDouble("price"));
		}
		System.out.println("res: "+res);
		return res;
	}//getAllReservations
	
}

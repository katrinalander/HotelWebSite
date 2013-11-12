package dbPackage;

import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

import core.Feedback;
import core.MailInformation;

public class DBFeedback {

	public void insertFeedback(Feedback fb) throws Exception{
		System.out.println("fb="+fb);
		Connection con=ConnectionDB.getInstance().getConnection();
		PreparedStatement pstm=(PreparedStatement)con.prepareStatement("insert into feedback(name, raiting, message, email, date) values(?,?,?,?,?)");
		pstm.setString(1, fb.getName());
		pstm.setInt(2, fb.getRaiting());
		pstm.setString(3, fb.getMessage());
		pstm.setString(4, fb.getEmail());
		pstm.setString(5, fb.getDate());
	    pstm.execute();
//-------------Email Info---------------------------
	    String subject = "You have a new comment in feedback page";
	    String text = "<img src=\"cid:image\">"+
	    		"<h3>On site Paradise hotel client <ins>"+fb.getName()+"</ins> leave new feedback.</h3>" +
	    		"<p>He mark your service with rating: <b>" + fb.getRaiting()+ "</b></p>"+
	    		"<p>Comment: "+fb.getMessage()+" </p>" +
	    		"<p>Client e-mail: <b>"+fb.getEmail()+"</b></p>";
	    MailInformation mi = new MailInformation(subject, text);
	    mi.run();
	}//insertfeedback
	
	public void deleteFeedback(int id) throws Exception{
		Connection con = ConnectionDB.getInstance().getConnection();
		PreparedStatement pstm = (PreparedStatement) con.prepareStatement("delete from feedback where id="+id);
		pstm.execute();
	}//deleteFeedback
	
	public ArrayList <Feedback> getAllFeedback() throws Exception{
		Connection con = ConnectionDB.getInstance().getConnection();
		PreparedStatement pstm = (PreparedStatement) con.prepareStatement("select * from feedback");
		ResultSet rs = (ResultSet) pstm.executeQuery();
		ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
		Feedback fb = null;
		while(rs.next()){
			fb = new Feedback();
			fb.setId(rs.getInt("id"));
			fb.setName(rs.getString("name"));
			fb.setRaiting(rs.getInt("raiting"));
			fb.setMessage(rs.getString("message"));
			fb.setEmail(rs.getString("email"));
			fb.setDate(rs.getString("Date"));
//			fb.setStatus(false);
			feedbacks.add(fb);
		}
		return feedbacks;
	}//getAllFeedback
}

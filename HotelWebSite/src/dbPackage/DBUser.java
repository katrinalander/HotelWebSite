package dbPackage;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import core.MailNotification;
import core.User;

public class DBUser {
	public boolean validLogin(String email, String password) throws Exception{
		boolean status = false;
		Connection con = ConnectionDB.getInstance().getConnection();
		Statement stm = (Statement)con.createStatement();
		User user = getUser(email);
		if(email.equals(user.getEmail()) && password.equals(user.getPassword())){
			System.out.println("You login in");
			status = true;
		}
//		else if(user.getPassword().equals(password)){
//			System.out.println("You login in");
//			status = true;
//		}
		else{
			System.out.println("You put wrong password");
		}
		return status;
	}
	
	public void insertUser (User user) throws Exception{
		Connection con=ConnectionDB.getInstance().getConnection();
		PreparedStatement pstm=(PreparedStatement)con.prepareStatement("insert into users(user_name, email, address, phone, password) values(?,?,?,?,?)");
		pstm.setString(1, user.getUser_name());
		pstm.setString(2, user.getEmail());
		pstm.setString(3, user.getAddress());
		pstm.setString(4, user.getPhone());
		pstm.setString(5, user.getPassword());
	    pstm.execute();
//-------------------Building message email---------------------------------------------
	    String email = user.getEmail();
	    String password = user.getPassword();
	    String subject = "Create an account on Paradise hotel web site";
  		String text = "<img src=\"cid:image\">"+
  				"<h3>This message was generated automatically for "+email+", from <a href=\"http://localhost:8080/HotelWebSite/\" style=\"color:#ff9911\">Paradise.hotel</a></h3>" +
  				"<h3>Your created account with success on site Paradise hotel. For login to your account please use next:</h3>" +
  				"<p>E-mail: <b>"+email+"</b></p>" +
  				"<p>Password: <b>"+password+"</b></p>" +
  				"<h3>We hope that you will like to use the services of our hotel.</h3>";
  		MailNotification mn = new MailNotification(email,subject,text);
  		mn.run();
	}//insert user
	
	public String getUserName (String email) throws Exception{
		String name =null;
		System.out.println("email="+email);
		Connection con = ConnectionDB.getInstance().getConnection();
		Statement stm = (Statement) con.createStatement();
		ResultSet rs = (ResultSet)stm.executeQuery("select * from users where email = '"+email+"'");
		while(rs.next()){
			name = rs.getString("user_name");
		}//while
		System.out.println("name from dbuser="+name);
		return name;
	}
	
	public User getUser(String email){
		User user = new User();
		try {
			Connection con=ConnectionDB.getInstance().getConnection();
			Statement stm=(Statement) con.createStatement();
			ResultSet rs = (ResultSet) stm.executeQuery("select * from users where email = '"+ email+"'");
			while(rs.next() ){
			user.setAddress    (rs.getString("address"));
			user.setUser_name  (rs.getString("user_name"));
			user.setEmail      (rs.getString("email"));
			user.setPassword   (rs.getString("password"));
			user.setPhone      (rs.getString("phone"));
			}//while
		}	
		 catch (Exception e) {
			e.printStackTrace();
		}//catch
		finally{
			return user;
		}//finally
	}//metod getUser by email
	
	public void deleteUser(String email) throws Exception{
		Connection con=ConnectionDB.getInstance().getConnection();
		PreparedStatement pstm=(PreparedStatement) con.prepareStatement("delete from users where email = '" + email +"'");
		pstm.execute();
//----------------Creating message-----------------------------------
		String subject ="Your account was delete from Paradise hotel web site";
		String text = "<img src=\"cid:image\">"+
				"<h5>You delete Your account from <a href=\"http://localhost:8080/HotelWebSite/\" style=\"color:#ff9911\">Paradise hotel web site</a>. Now You can't use our online serveces.</h5>" +
				"<h5>We apologize if our service was not so good for You.</h5>";
		MailNotification mn = new MailNotification(email,subject,text);
  		mn.run();
	}//delete user
	
	public void updateUser(User user)throws Exception{
		Connection con=ConnectionDB.getInstance().getConnection();
		PreparedStatement pstm=(PreparedStatement) con.prepareStatement("update users set user_name=?,email=?,address=?,phone=?,password=? " +
				"where email='"+user.getEmail()+"'");
		pstm.setString(1, user.getUser_name());
		pstm.setString(2, user.getEmail());
		pstm.setString(3, user.getAddress());
		pstm.setString(4, user.getPhone());
		pstm.setString(5, user.getPassword());
	    pstm.execute();
	}//update user
}

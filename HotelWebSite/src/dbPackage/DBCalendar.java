package dbPackage;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import core.CalDate;
import core.Reservation;
import core.Room;


public class DBCalendar {
	
	public static List<String> daysBetweenDates(String str_date, String end_date) throws ParseException
	{
		List<String> dates = new ArrayList<String>();

		DateFormat formatter ; 

		formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date  startDate = (Date)formatter.parse(str_date); 
		Date  endDate = (Date)formatter.parse(end_date);
		long interval = 24*1000 * 60 * 60; // 1 hour in millis
		long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
		long curTime = startDate.getTime();
		while (curTime <= endTime) {
		    dates.add(formatter.format(new Date(curTime)));
		    curTime += interval;
		}
		return dates;
	}
	
	public void deleteReservation(int resId) throws Exception{
		Connection con=ConnectionDB.getInstance().getConnection();
		String resType = null;
		String resType1 = null;
		String resType2 = null;
		DBReservations dbr = new DBReservations();
		Reservation res = dbr.getReservation(resId);
		String type = res.getType();
		String checkIn = res.getCheckIn();
		String checkOut = res.getCheckOut();
		if(type.equals("Single room ")){resType="sngl";}
		if(type.equals("Double room")){resType="dbl";}
		if(type.equals("Twin room")){resType="twin";}
		if(type.equals("Triple room")){resType="trpl";}
		if(type.equals("King apartment")){resType="king";}
		if(type.equals("Honeymoon")){resType="hmn";}
		List<String>dates = daysBetweenDates(checkIn, checkOut);
		if(resType!=null){
		for(int j=0;j<dates.size();j++){
			CalDate cal = getDate(dates.get(j));
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement("update calendar set "+resType+"=? " +
					"where cal_date='"+dates.get(j)+"'");
			if(resType.equals("sngl")){pstm.setInt(1, (cal.getSngl()+res.getSngl()));}
			if(resType.equals("dbl")){pstm.setInt(1, (cal.getDbl()+res.getDbl()));}
			if(resType.equals("twin")){pstm.setInt(1, (cal.getTwin()+res.getTwin()));}
			if(resType.equals("trpl")){pstm.setInt(1, (cal.getTrpl()+res.getTrpl()));}
			if(resType.equals("king")){pstm.setInt(1, (cal.getKing()+res.getKing()));}
			if(resType.equals("hmn")){pstm.setInt(1, (cal.getHmn()+res.getHmn()));}
			pstm.execute();
		}//for j
		}//if
		else{
			if(type.equals("Single room Double room")){resType1="sngl"; resType2="dbl";}
			if(type.equals("Double room Triple room")){resType1="dbl"; resType2="trpl";}
			if(type.equals("Single room Triple room")){resType1="sngl"; resType2="trpl";}
			for(int i=0;i<dates.size();i++){
				CalDate cal = getDate(dates.get(i));
				PreparedStatement pstm = (PreparedStatement) con.prepareStatement("update calendar set "+resType1+"=?, " +resType2+"=? "+
						"where cal_date='"+dates.get(i)+"'");
				if(resType1.equals("sngl")&& resType2.equals("dbl")){
					pstm.setInt(1, (cal.getSngl()+res.getSngl()));
					pstm.setInt(2, (cal.getDbl()+res.getDbl()));}
				if(resType1.equals("dbl")&& resType2.equals("trpl")){
					pstm.setInt(1, (cal.getDbl()+res.getDbl()));
					pstm.setInt(2, (cal.getTrpl()+res.getTrpl()));}
				if(resType1.equals("sngl")&& resType2.equals("trpl")){
					pstm.setInt(1, (cal.getSngl()+res.getSngl()));
					pstm.setInt(2, (cal.getTrpl()+res.getTrpl()));}
				pstm.execute();
			}//for i
		}//else
	}
	
	public void insertDate (Reservation res) throws Exception{
		Connection con=ConnectionDB.getInstance().getConnection();
//---------------------Days between 2 date----------------------------
		String checkIn = res.getCheckIn();
		String checkOut = res.getCheckOut();
		List<String> dates = daysBetweenDates(checkIn, checkOut);
		
//--------------------------------------------------------------------		
		
		DBRoom dbr = new DBRoom();
		CalDate cal = new CalDate(res.getCheckIn(),res.getSngl(),res.getDbl(),res.getTwin(),res.getTrpl(),res.getKing(),res.getHmn());
		for(int j=0;j<dates.size();j++){
//----------------Check if this date is in table calendar: no - create, yes - update-------------------------------------
			if(checkDate(dates.get(j))){
				PreparedStatement pstm2=(PreparedStatement)con.prepareStatement("update calendar set sngl=?,dbl=?,twin=?,trpl=?,king=?,hmn=? " +
						"where cal_date='"+dates.get(j)+"'");
				CalDate dat = getDate(dates.get(j));
				pstm2.setInt(1, (dat.getSngl()-cal.getSngl()));
				pstm2.setInt(2, (dat.getDbl()-cal.getDbl()));
				pstm2.setInt(3, (dat.getTwin()-cal.getTwin()));
				pstm2.setInt(4, (dat.getTrpl()-cal.getTrpl()));
				pstm2.setInt(5, (dat.getKing()-cal.getKing()));
				pstm2.setInt(6, (dat.getHmn()-cal.getHmn()));
				pstm2.execute();
				}
			else {
//----------------------------if no - insert------------------------------------------
				PreparedStatement pstm=(PreparedStatement)con.prepareStatement
						("insert into calendar(cal_date,sngl,dbl,twin,trpl,king,hmn) " +
								"values(?,?,?,?,?,?,?)");
				ArrayList<Room> allRoom = dbr.getAllRooms();
				pstm.setString(1, dates.get(j));
				for(int i=0; i<allRoom.size();i++){
					Room r = allRoom.get(i);
					if(r.getType().equals("Single room")){
						pstm.setInt(2, (r.getAmount() - cal.getSngl()));
						}
					if(r.getType().equals("Double room")){
						pstm.setInt(3, (r.getAmount() - cal.getDbl()));
						}
					if(r.getType().equals("Twin room")){
						pstm.setInt(4, (r.getAmount() - cal.getTwin()));
						}
					if(r.getType().equals("Triple room")){
						pstm.setInt(5, (r.getAmount() - cal.getTrpl()));
						}
					if(r.getType().equals("King apartment")){
						pstm.setInt(6, (r.getAmount() - cal.getKing()));
						}
					if(r.getType().equals("Honeymoon")){
						pstm.setInt(7, (r.getAmount() - cal.getHmn()));
						}
				}//while i
			    pstm.execute();
			}//else
		}//while j
		
	}//insert date in calendar
	
	public CalDate getDate(String cal_date){
		CalDate date = new CalDate();
		try {
			Connection con=ConnectionDB.getInstance().getConnection();
			Statement stm=(Statement) con.createStatement();
			ResultSet rs = (ResultSet) stm.executeQuery("select * from calendar where cal_date = '"+ cal_date+"'");
			while(rs.next() ){
			date.setCal_date (rs.getString("cal_date"));
			date.setSngl     (rs.getInt("sngl"));
			date.setDbl      (rs.getInt("dbl"));
			date.setTwin     (rs.getInt("twin"));
			date.setTrpl     (rs.getInt("trpl"));
			date.setKing     (rs.getInt("king"));
			date.setHmn      (rs.getInt("hmn"));
			}//while
		}	
		 catch (Exception e) {
			e.printStackTrace();
		}//catch
		finally{
			return date;
		}//finally
	}//method getDate
	
	public boolean checkDate(String cal_date){
		boolean status = false;
		try {
			Connection con=ConnectionDB.getInstance().getConnection();
			Statement stm=(Statement) con.createStatement();
			ResultSet rs = (ResultSet) stm.executeQuery("select * from calendar where cal_date = '"+ cal_date+"'");
			if(rs.first()){
				status=true;
			}
		}	
		 catch (Exception e) {
			e.printStackTrace();
		}//catch
		finally{
			return status;
		}//finally
	}//method getDate
	
	public ArrayList<String> getAllDates(String type){
		String date ="";
		ArrayList<String> alldates = new ArrayList<String>();
		try {
			Connection con=ConnectionDB.getInstance().getConnection();
			Statement stm=(Statement) con.createStatement();
			ResultSet rs = (ResultSet) stm.executeQuery("select * from calendar where "+type+"=0");
			while(rs.next()){
				date=rs.getString("cal_date");
				alldates.add(date);
			}//while
		}	
		 catch (Exception e) {
			e.printStackTrace();
		}//catch
		finally{
			return alldates;
		}//finally
	}//method getAllDates for unavailable dates
	
	public boolean checkRooms(CalDate date){
		boolean status = true;
		String d = date.getCal_date();
		CalDate date2 = new CalDate();
		if(checkDate(d)){
			date2 = getDate(d);
			if((date2.getSngl()-date.getSngl())<0 || (date2.getDbl()-date.getDbl())<0 || (date2.getTrpl()-date.getTrpl())<0){
				status=false;
			}
		}
		else if(date2.getSngl()<date.getSngl() || date2.getDbl()<date.getDbl() || date2.getTrpl()<date.getTrpl()){
			status = false;
		}
		return status;
	}//methods checkRooms
}

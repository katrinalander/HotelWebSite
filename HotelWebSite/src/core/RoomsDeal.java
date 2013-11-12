package core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dbPackage.DBCalendar;
import dbPackage.DBRoom;

public class RoomsDeal {
	
	public ArrayList<Room> getRoomsDeal() throws Exception{
		Date now = new Date();
		Calendar cal = Calendar.getInstance();  
		cal.setTime(now);  
		cal.add(Calendar.DAY_OF_YEAR, 1); // <--  
		Date d1 = cal.getTime();
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_YEAR, 2);
		Date d2 = cal.getTime();
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_YEAR, 3);
		Date d3 = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dt1 = sdf.format(d1);
		String dt2 = sdf.format(d2);
		String dt3 = sdf.format(d3);
		System.out.println("next 3 days will be: "+dt1+", "+dt2+", "+dt3);
//------------------------------------------------------------------------
		DBCalendar dbc = new DBCalendar();
		CalDate cal1 = dbc.getDate(dt1);
		CalDate cal2 = dbc.getDate(dt2);
		CalDate cal3 = dbc.getDate(dt3);
		int indexSngl=0;
		int indexDbl=0;
		int indexTwin=0;
		int indexTrpl=0;
		if((cal1.getSngl()>5)&&(cal2.getSngl()>5)&&(cal3.getSngl()>5)){
			if(cal1.getSngl()>cal2.getSngl()){indexSngl=cal1.getSngl();}
			else if(cal3.getSngl()>cal1.getSngl()){indexSngl=cal3.getSngl();}
			else{indexSngl=cal2.getSngl();}
		}//if sngl
		if((cal1.getDbl()>10)&&(cal2.getDbl()>10)&&(cal3.getDbl()>10)){
			if(cal1.getDbl()>cal2.getDbl()){indexDbl=cal1.getDbl();}
			else if(cal3.getDbl()>cal1.getDbl()){indexDbl=cal3.getDbl();}
			else{indexDbl=cal2.getDbl();}
		}//if Dbl
		if((cal1.getTwin()>10)&&(cal2.getTwin()>10)&&(cal3.getTwin()>10)){
			if(cal1.getTwin()>cal2.getTwin()){indexTwin=cal1.getTwin();}
			else if(cal3.getTwin()>cal1.getTwin()){indexTwin=cal3.getTwin();}
			else{indexTwin=cal2.getTwin();}
		}//if Twin
		if((cal1.getTrpl()>5)&&(cal2.getTrpl()>5)&&(cal3.getTrpl()>5)){
			if(cal1.getTrpl()>cal2.getTrpl()){indexTwin=cal1.getTrpl();}
			else if(cal3.getTrpl()>cal1.getTrpl()){indexTrpl=cal3.getTrpl();}
			else{indexTrpl=cal2.getTrpl();}
		}//if Trpl
		DBRoom dbr = new DBRoom();
		ArrayList <Room> rooms = dbr.getAllRooms();
		ArrayList <Room> roomsdeal = new ArrayList<Room>();
		for (int i=0; i<4;i++){
			Room r = rooms.get(i);
			Room room = new Room();
			room.setId(r.getId());
			room.setType(r.getType());
			room.setPrice_regular(r.getPrice_regular());
			room.setPrice_deal(r.getPrice_deal());
			room.setDescription(r.getDescription());
			room.setImageUrl(r.getImageUrl());
			if(r.getType2().equals("sngl")){
				room.setAmount(indexSngl);
			}
			if(r.getType2().equals("dbl")){
				room.setAmount(indexDbl);
			}
			if(r.getType2().equals("twin")){
				room.setAmount(indexTwin);
			}
			if(r.getType2().equals("trpl")){
				room.setAmount(indexTrpl);
			}
			if(room.getAmount()!=0){
			roomsdeal.add(room);}
		}//for
		return roomsdeal;
	}
}

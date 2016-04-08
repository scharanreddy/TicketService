package org.sricharan.ticketService.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.sricharan.ticketService.dao.TicketDao;
import org.sricharan.ticketService.dto.SeatHold;

public class TicketService 
{
	
	public static int numSeatsAvailable(int venueLevel) throws SQLException
	{
		int seats;
		seats = TicketDao.getNoOfSeats(venueLevel);
		return seats;
	}
	
	public static List<SeatHold> findAndHoldSeats(int numSeats,int minlevel,int maxLevel,String custEmail) throws SQLException
	{
	    ArrayList<SeatHold> list = new ArrayList<SeatHold>();
	    ArrayList<SeatHold> returnList = new ArrayList<SeatHold>();
		list = (ArrayList<SeatHold>) TicketDao.getSeats(minlevel, maxLevel);
		int count=0;
		if(list.size()>numSeats)
		{
			for(int i=0;i<numSeats;i++)
			{
				Random ran = new Random();
				int seatHoldID = ran.nextInt(100)+1;
				returnList.add(list.get(i));
				TicketDao.holdSeatUpdate(list.get(i).getID(), seatHoldID);
			}
		}
		else if(list.size()<numSeats)
		{
			for(SeatHold sh:list)
			{
				Random ran = new Random();
				int seatHoldID = ran.nextInt(100)+1;
				returnList.add(list.get(count));
				TicketDao.holdSeatUpdate(sh.getID(), seatHoldID);
				count++;
			}
		}
		
		return returnList;
		
	}
	
	public static String reserveSeats(int seatHoldId,String custEmail) throws SQLException
	{
		Random ran = new Random();
		int rand = ran.nextInt(100)+1;
		TicketDao.reserveSeats(seatHoldId,custEmail);
		return String.valueOf(rand);
		
	}

}
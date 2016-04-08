package org.sricharan.ticketService.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.LocalDateTime;
import org.sricharan.ticketService.dao.TicketDao;
import org.sricharan.ticketService.dto.SeatHold;
import org.sricharan.ticketService.service.TicketService;

public class TicketDataService {
	
	 private static int HOLD_TIMEOUT=10;
     public static void processReservations() throws SQLException, InterruptedException
	{
	  String choice1,choice2,choice3,choice4,choice5,choice6,email;
	 
	  ArrayList<SeatHold> list = new ArrayList<SeatHold>();
	  TicketDao.setUp();
	  
	  System.out.println("Welcome to Ticket Reservation System");
	  System.out.println("Enter 1 to find the number of Seats Available for a given level");
	  System.out.println("Enter 2 to find and Reserve Seats");
	  Scanner scan = new Scanner(System.in);
	  System.out.println("Enter Your Choice");
	  choice1=scan.nextLine();
	  if(choice1.equalsIgnoreCase("1"))
	  {
		  System.out.println("Enter the Level number 1 for Orchestra,2 for Main, 3 for Balcony 1 and 4 for Balcony 2");
		  choice2=scan.nextLine();
		  System.out.println("The total number of available seats neither held nor reserved is "+TicketService.numSeatsAvailable(Integer.parseInt(choice2)));
	  }
	  if(choice1.equalsIgnoreCase("2"))
	  { 
		  System.out.println("Enter the minimum Level for seats number 1 for Orchestra,2 for Main, 3 for Balcony 1 and 4 for Balcony 2");
		  choice3=scan.nextLine();
		  System.out.println("Enter the maximum Level for seats number 1 for Orchestra,2 for Main, 3 for Balcony 1 and 4 for Balcony 2");
		  choice4=scan.nextLine();
		  System.out.println("Enter the Number of Seats Needed");
		  choice5=scan.nextLine();
		  System.out.println("Enter Customer Email");
		  email=scan.nextLine();
		  list=(ArrayList<SeatHold>) TicketService.findAndHoldSeats(Integer.parseInt(choice5), Integer.parseInt(choice3), Integer.parseInt(choice4), email);
		  System.out.println("Do you want to reserve the seats please enter Y to reserve and N to Drop the hold");
		  
		  choice6=scan.nextLine();
		  if(choice6.equalsIgnoreCase("Y"))
		  {
			  for(SeatHold sh:list)
			  {
				  String token=TicketService.reserveSeats(sh.getSeatHoldID(), email);
				  System.out.println("The reservation token for seat "+sh.getID()+" is "+token);
			  }
		  }
		  else
		  {
			  Thread.sleep(HOLD_TIMEOUT*1000);// hold expiry in 10 s
			  for(SeatHold sh:list)
			  {
				  TicketDao.releaseSeats(sh.getID());
			  }
		  }
	  }
      
		
	}
	
	

	public static void main(String args[])
	{
		try {
			processReservations();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

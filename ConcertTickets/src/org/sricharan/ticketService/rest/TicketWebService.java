package org.sricharan.ticketService.rest;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.sricharan.ticketService.Data.TicketDataService;
import org.sricharan.ticketService.dao.TicketDao;

@Path("/admin")

//TODO:Extend the REST API
public class TicketWebService {
	
	
	
//    @Path("{c}")
//	@GET
//	@Produces("MediaType.PLAIN_TEXT")
//	public int  seatsAvailable(@PathParam("c") int c) {
//	 
//    	int seatsAvailable = 0;
//		try {
//			seatsAvailable = TicketDao.getNoOfSeats(c);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	return seatsAvailable;
//    	
//    }
    
    
//    @Path("{c}")
//   	@GET
//   	@Produces("MediaType.")
//   	public SeatHold  findAndHoldSeats(@PathParam("c") Double c) {
//   	 
//       	int seatsAvailable = TicketDataService.getAvailableSeats(c);
//       	return seatsAvailable;
//       	
//       }
    
    

}

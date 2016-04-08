package org.sricharan.ticketService.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sricharan.ticketService.dto.SeatHold;

public class TicketDao {
	
	
	private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";
    
    
    /**
     * Setup to Create the table for the BASIC Concert Application
     * @throws SQLException
     */
  
    
     public static void setUp() throws SQLException
    {
    	  Connection connection = getDBConnection();
          PreparedStatement createPreparedStatement = null;
    	  String CreateQuery = "CREATE TABLE EVENT(ID int PRIMARY KEY, levelid int,levelname varchar(255),price number(9,2) ,rowID int,seatID int,seatHoldID int,customerEmail varchar(255))";
    	  
          try 
          {
        	  connection.setAutoCommit(false);
        	  createPreparedStatement = connection.prepareStatement(CreateQuery);
              createPreparedStatement.executeUpdate();
              SeatHold sh= new SeatHold(1, 1, "Orchestra", 100.00, 1, 1, 0, "");
              SeatHold sh1= new SeatHold(2, 2, "Main", 75.00, 2, 2, 0, "");
              SeatHold sh2= new SeatHold(3, 3, "Balcony 1", 50.00, 3, 3, 0, "");
              SeatHold sh3= new SeatHold(4, 4, "Balcony 2", 40.00, 4, 4, 0, "");
              insertWithPreparedStatement(sh);
              insertWithPreparedStatement(sh1);
              insertWithPreparedStatement(sh2);
              insertWithPreparedStatement(sh3);
              connection.commit();
              createPreparedStatement.close();
          }catch (SQLException e) {
              System.out.println("Exception Message " + e.getLocalizedMessage());
          } catch (Exception e) {
              e.printStackTrace();
          } finally {
              
          }
    }
    
     /**
      * Method to check if table exists in the H2 in memory DB
      * @return true or false
      * @throws SQLException
      */
    public static boolean checkIfTableExists() throws SQLException
    {
    	 Connection connection = getDBConnection();
    	 PreparedStatement selectPreparedStatement = null;
    	 String SelectQuery = "select * from EVENT";
    	 selectPreparedStatement = connection.prepareStatement(SelectQuery);
         ResultSet rs = selectPreparedStatement.executeQuery();
         selectPreparedStatement.close();
         if(rs!=null)
         {
        	
        	 return true;
         }
         else
         {
        	  
              return false;
         }
    }
    
    /**
     * Method to Insert the seat Hold object into DB
     * @param  SeatHold object
     * @throws SQLException
     */

     //TODO : Add ORM support ex. Hibernate or Eclipse Link
     public static void insertWithPreparedStatement(SeatHold seat) throws SQLException {
        Connection connection = getDBConnection();
    
        PreparedStatement insertPreparedStatement = null;
        //PreparedStatement selectPreparedStatement = null;

        String InsertQuery = "INSERT INTO EVENT" + " (ID,levelid, levelname,price,rowID,seatID,seatHoldID,customerEmail) values " + "(?,?,?,?,?,?,?,?)";
        

        try {
            

            insertPreparedStatement = connection.prepareStatement(InsertQuery);
            insertPreparedStatement.setInt(1, seat.getID());
            insertPreparedStatement.setInt(2, seat.getLevelID());
            insertPreparedStatement.setString(3,seat.getLevelName());
            insertPreparedStatement.setDouble(4,seat.getPrice());
            insertPreparedStatement.setInt(5,seat.getRowID());
            insertPreparedStatement.setInt(6,seat.getSeatID());
            insertPreparedStatement.setInt(7,seat.getSeatHoldID());
            insertPreparedStatement.setString(8, seat.getCustomerEmail());
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();

            //selectPreparedStatement = connection.prepareStatement(SelectQuery);
            //ResultSet rs =  insertPreparedStatement.executeQuery();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           
        }
    }
    
    public static List<SeatHold> getSeats(int minlevel,int maxLevel) throws SQLException
    {
    	Connection        connection = getDBConnection();
    	ArrayList<SeatHold> list = new ArrayList<SeatHold>();
    	String   getQuery = "select * from EVENT where levelid>=? and levelid<=? and seatHoldID=?";
    	PreparedStatement getPreparedStatement = connection.prepareStatement(getQuery);
    	getPreparedStatement.setInt(1, minlevel);
    	getPreparedStatement.setInt(2, maxLevel);
    	getPreparedStatement.setInt(3, 0);
    	ResultSet rs = getPreparedStatement.executeQuery();
    	while(rs.next())
    	{
    		SeatHold sh=new SeatHold(rs.getInt("ID"), rs.getInt("levelid"), rs.getString("levelname"), rs.getDouble("price"),rs.getInt("rowID"), rs.getInt("seatID"), rs.getInt("seatHoldID"), rs.getString("customerEmail"));
    		list.add(sh);
    	}
    	getPreparedStatement.close();
    	
		return list;
    	
    }
    
    public static List<SeatHold> getHeldSeats() throws SQLException
    {
    	Connection        connection = getDBConnection();
    	ArrayList<SeatHold> list = new ArrayList<SeatHold>();
    	String   getQuery = "select * from EVENT where seatHoldID<>0";
    	PreparedStatement getPreparedStatement = connection.prepareStatement(getQuery);
    	ResultSet rs = getPreparedStatement.executeQuery();
    	while(rs.next())
    	{
    		SeatHold sh=new SeatHold(rs.getInt("ID"), rs.getInt("levelid"), rs.getString("levelname"), rs.getDouble("price"),rs.getInt("rowID"), rs.getInt("seatID"), rs.getInt("seatHoldID"), rs.getString("customerEmail"));
    		list.add(sh);
    	}
    	getPreparedStatement.close();
    	
		return list;
    	
    }
    
    public static void releaseSeats(int ID) throws SQLException
    {
    	Connection        connection = getDBConnection();
    	String   getQuery = "UPDATE EVENT SET seatHoldID=? WHERE ID=?";
    	PreparedStatement getPreparedStatement = connection.prepareStatement(getQuery);
    	getPreparedStatement.setInt(1, 0);
    	getPreparedStatement.setInt(2, ID);
    	int res=getPreparedStatement.executeUpdate();
    	getPreparedStatement.close();
    
    }
    
    public static void reserveSeats(int seatHoldID,String custEmail) throws SQLException
    {
    	Connection        connection = getDBConnection();
    	String   getQuery = "UPDATE EVENT SET customerEmail=? WHERE seatHoldID=?";
    	PreparedStatement getPreparedStatement = connection.prepareStatement(getQuery);
    	getPreparedStatement.setString(1, custEmail);;
    	getPreparedStatement.setInt(2,seatHoldID);
    	int res=getPreparedStatement.executeUpdate();
    	getPreparedStatement.close();
    	
    }
    
    public static int getNoOfSeats(int levelid) throws SQLException
    {
    	 Connection        connection = getDBConnection();
    	 String            CountQuery = "select count(ID) as count from EVENT where seatHoldID=? and levelid=?";
    	 PreparedStatement selectPreparedStatement = connection.prepareStatement(CountQuery);
    	 selectPreparedStatement.setInt(1,0);
    	 selectPreparedStatement.setInt(2,levelid);
         ResultSet rs = selectPreparedStatement.executeQuery();
         int count=0;
         if(rs.next())
         {
          count = rs.getInt("count");
         }
         return count;
    	
    }
    
    public static void holdSeatUpdate(int ID,int seatHoldID) throws SQLException
    {
    	Connection        connection = getDBConnection();
   	    String            holdQuery = "UPDATE EVENT SET seatHoldID=? WHERE ID=?";
   	    PreparedStatement holdPreparedStatement = connection.prepareStatement(holdQuery);
   	    holdPreparedStatement.setInt(1,seatHoldID);
   	    holdPreparedStatement.setInt(2, ID);
        boolean rs = holdPreparedStatement.execute();
       
    }

    
    
    

    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
}



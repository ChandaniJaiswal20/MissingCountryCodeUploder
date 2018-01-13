package com.wipo.countryCode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;


public class DBdataProcessor {
	
	UtilityConfiguration  uc	=	null;
	
	
	private String databaseHost	= null; 
	private String databasePort	=	null;
	private String databaseTool	=	null;
	private String databaseName	=	null;
	private	String userName		=	null;
	private	String password		=	 null;
	private	Connection connection	=	null;
	
	public static Logger logger	=	Logger.getLogger(DBdataProcessor.class);
	
	
	
	
	public DBdataProcessor(String databaseHost, String databasePort,
			String databaseTool, String databaseName,String userName,String password ){
		
		
		 this.databaseHost	= databaseHost; 
		 this.databasePort	=	databasePort;
		 this.databaseTool	=	databaseTool;
		 this.databaseName	=	databaseName;
		 this.userName		=	userName;
		 this.password		=	 password;
		 
			init();
		
	}
	
	public void init(){
		
		if(connection==null)
			configureConnection();
		
	}
	
	public Connection getConnection(){
		
		return connection;
	}
	
	
	public void closeConnection(){
		
		 if(connection!=null){
		 
		 try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
		
	}
	
	
	
	public  void 	configureConnection() {
		// TODO Auto-generated method stub
		//Connection conn = null;
		if( databaseTool.equalsIgnoreCase("oracle") ){
			
			logger.info("Database Type is Oracle....");
			
		//("jdbc:oracle:thin:@" + databaseHost + ":" + databasePort + ":"+databaseName,userName,password  );
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection	=	DriverManager.getConnection("jdbc:oracle:thin:@" + databaseHost + ":" + databasePort + ":"+databaseName,userName,password  );
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			
			logger.error("Error Occured while loading OracleDriver class ", e);
			//e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Error Occured while obtaining connection ", e);
		//	e.printStackTrace();
		}
		}
		else if(databaseTool.equalsIgnoreCase("sql")){
			
			logger.info("Database Type is MSSQL....");
			
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				
				logger.info("Driver class loaded successfully....");
				
				connection	=	DriverManager.getConnection( "jdbc:sqlserver://"+databaseHost+ ":" + databasePort +";" +
			    		  "databaseName="+databaseName+";"+"user="+userName+";"+"password="+password);
				logger.info("Driver class loaded successfully....");
			} catch (ClassNotFoundException e) {
				logger.error("Error Occured while loading SQLDriver class ", e);
			//	e.printStackTrace();
			} catch (SQLException e) {
				logger.error("Error Occured While obtaining  Connection: ", e);
			//	e.printStackTrace();
			}
			
			
		}
		
		
	
		
		
	}
	
	
	
	

	

		public List<String> readCountryCodeFromDB(){
			
			ResultSet rs	= null;
			Statement ps	= null;
			List<String>	listOfCountryCode	=	new ArrayList<String>();
			
			
			
			String query	=	"select COUNTRY_CODE from CF_GEO_COUNTRY";
			
		
	
		 try {
			 init();
			ps	=	connection.createStatement();
			

			rs	=	 ps.executeQuery(query);
			
			while(rs.next()){
				listOfCountryCode.add(rs.getString("COUNTRY_CODE"));
				
				}
		//	logger.debug("Country code obtained from database are::"+listOfCountryCode);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Error Occured while Reading COUNTRY_CODE from CF_GEO_COUNTRY table. ", e);
			e.printStackTrace();
		}
		 
			finally{
				 if (rs != null){
					 
					 try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
				 
				 
				 if(ps!=null){
				 
					 try {
						ps.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
				 
				 
		
		}
		 
	
		 
	
			
			 
		return listOfCountryCode;
		 
	
			
		}
		
		
	public void insertCountryCodeIntoDB(Map<String,ArrayList<String>> map_of_missing_CountryCode){
			
			PreparedStatement ps	= null;
	 try {
			 init();
			 
			 connection.setAutoCommit(false);
			 
			Iterator<Entry<String, ArrayList<String>>> iteEntry	=  map_of_missing_CountryCode.entrySet().iterator();
			
			 String query=	"Insert into CF_GEO_COUNTRY (ROW_VERSION,COUNTRY_CODE,COUNTRY_NAME,NATIONALITY,IND_WIPO_STATISTICS,XML_DESIGNER)"+""
						+ " values (1,?,?,?,?,'<PARAMS></PARAMS>')";	
			 
			 ps		=	 connection.prepareStatement(query);
			 	while(iteEntry.hasNext()){
			 		
					 	Map.Entry<String,ArrayList<String>> enties	=	(Map.Entry<String,ArrayList<String>>) iteEntry.next();
					 	
					 	
					 			String countryCode	=	 enties.getKey();
					 			ArrayList<String> alist	=	  enties.getValue();
					 			String countryName	=	alist.get(0);
					 			String nationality	=	countryName;
//column "CF_GEO_COUNTRY"."COUNTRY_NAME" size is 60 and "CF_GEO_COUNTRY"."NATIONALITY" size is 30 SO TRUNCATING DATA WHOSE VALUE IS LARGER THAN THIS
					 			if(countryName.length()>30){
					 				logger.debug("=================================================");
					 				logger.debug("Nationality whose length  greater than 30 ::"+nationality);
					 					nationality	=	nationality.substring(0, 30);
					 			
					 				logger.debug("Nationality after trucating beyond 30  ::"+nationality);
					 				
					 			
					 				
						 				if(countryName.length()>60){
						 					logger.debug("===========================================");
						 				logger.debug("countryName whose length  greater than 60 ::"+countryName);
						 				countryName	=	countryName.substring(0, 60);
						 			
						 				logger.debug("countryName after trucating beyond 60 ::"+countryName);
						 				
						 				logger.debug("===========================================");
						 				}
					 			}
					 			
					 		
					 		
						ps.setString(1, countryCode);
						ps.setString(2, countryName);
						ps.setString(3, nationality);
						ps.setString(4, alist.get(1));
										
						ps.executeUpdate();
					 
			 			 		
			 	}
			 	connection.commit();	 	
			 		
			logger.info("Missing Country Code inserted into CF_GEO_COUNTRY table  successfully.....!!!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Error Occured while inserting COUNTRY_CODE into CF_GEO_COUNTRY table. ", e);
			e.printStackTrace();
			
			try {
				
				logger.info("Going to rollback transaction....!!");
				connection.rollback();
				logger.info("Transaction rollback successfully...!!");
				
			} catch (SQLException e1) {
				
				logger.error("Error Occured while rollbacking Transaction ", e);
				e1.printStackTrace();
			}
		}
		 
			finally{
				
				 
				 if(ps!=null){
				 
					 try {
						ps.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
				 
				 
		
		}
		 
	
			
	}
		
		
		

}

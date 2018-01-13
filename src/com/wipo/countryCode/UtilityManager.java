package com.wipo.countryCode;


import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class UtilityManager {

	
	 static{
	        
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
	        System.setProperty("current.date.time", dateFormat.format(new Date()));
	    }
	

	 private static UtilityManager uniqueInstance =	null;
	
	
	  private UtilityConfiguration configuration 	=	null;
	
	  private 	final	DBdataProcessor	dataProcessor	; 
	 
	  private	Map<String,String>  isoCountryCodeMap	=	 new 	HashMap<String, String>();
	  private	Map<String,String>  wipoST3CodeMap	=	 new 	HashMap<String, String>();
	  private	Map<String,String>  wipoISOAlpha2CodeMap	=	 new 	HashMap<String, String>();
	  private	Map<String,ArrayList<String>>  map_of_missing_CountryCode	=	 new 	HashMap<String, ArrayList<String>>();
	  private	List <String> list_of_missing_CountryCode=	new ArrayList<String>();
	  private	List <String> list_of_CountryCode_from_DB=	new ArrayList<String>();
	
	  private 	static final	String  LOG4JCONFIGFILE	= "log4j.properties";
	  private	static Connection conn	=null;
	  private 	static Logger logger	=	Logger.getLogger(UtilityManager.class);
	  

	private UtilityManager()
	{

		PropertyConfigurator.configure(LOG4JCONFIGFILE);
		logger.info("Initializing Utility...........................................!!!!!");
		logger.debug("################################################");
		logger.debug("=================================================");
		
		logger.debug("LOG4JCONFIGFILE:::"+LOG4JCONFIGFILE);
		configuration 	=	new UtilityConfiguration();	//aggregation (has a)
		configuration.loadConfiguration();
		configuration.checkValuesOfConfigFile();
	//	xsdparser = new xsdparser(configuration. +":"+configuration.iiopPort);
		logger.debug("Obtaining Database Connection....");
		dataProcessor	= new DBdataProcessor(configuration.databaseHost,configuration.databasePort,
					  configuration.databaseTool,configuration.databaseName,configuration.userName,configuration.password);
		
		logger.info("Initializing Completed...............................................!!!!!!");		
		logger.debug("=================================================");
		logger.debug("################################################");
	}

		public static  UtilityManager getInstance(){
			
					if(uniqueInstance==null)
					{	
						uniqueInstance	=	 new UtilityManager();
					}
					
					
			return uniqueInstance;
					
		}
	
		public UtilityConfiguration getConfiguration(){
			return configuration;
						
		}
	


	
	public void configureLogger(){
	//	log4jConfigFile	=	configuration.patentAnnuityUtilityFolder;
	//	log4jConfigFile	="log4j.properties";
	//	log4jConfigFile	=	System.getProperty("user.dir")+File.separator+"log4j.properties";
		
		PropertyConfigurator.configure(LOG4JCONFIGFILE);
			
	}
		
	
	public void parseWIPOST3Code(){
		logger.debug("======================================================");
		XSDParser xsdparser		=	new XSDParser(configuration.schemaFileNameForWIPOST3Code, configuration.simpleTypeNameForWIPOST3Code);
		
		wipoST3CodeMap	=	xsdparser.getCountryCodeMap();
		logger.debug("Data obtained after parsing ::"+ configuration.schemaFileNameForWIPOST3Code+" are::"+wipoST3CodeMap);
		logger.debug("#########################################################");
		
	}
	
	
	public void parseISOAlpha2(){
		
		logger.debug("======================================================");
		
		XLSXParser xslsparser		=	new XLSXParser(configuration.fileNameForISOAlpha2Code);
		
		wipoISOAlpha2CodeMap	=	xslsparser.getCountryCodeMap();
		
		logger.debug("Data obtained after parsing ::"+ configuration.fileNameForISOAlpha2Code+" are::"+wipoISOAlpha2CodeMap);
		logger.debug("#########################################################");
		
	}
	
	
	public void parseISOCountryCode(){
		logger.debug("======================================================");
		XSDParser xsdparser			=	new XSDParser(configuration.schemaFileNameForISOCountryCode, configuration.simpleTypeNameForISOCountryCode);
		isoCountryCodeMap	=	xsdparser.getCountryCodeMap();
		
		logger.debug("Data obtained after parsing::"+ configuration.schemaFileNameForWIPOST3Code+" are::"+isoCountryCodeMap);
		
		logger.debug("#########################################################");
	}



	public	void  getCountryCodeFromDatabase() {
	
			 
		
		list_of_CountryCode_from_DB	=	dataProcessor.readCountryCodeFromDB();
		
		Collections.sort(list_of_CountryCode_from_DB);
		logger.debug("=================================================");
		
		logger.debug("List of CountryCode present in database::"+list_of_CountryCode_from_DB);
		
		logger.debug("################################################");

	}

				public void processMissingCountryCode(){
					
					compareList(wipoST3CodeMap,"S");
					
					logger.info("Missing countryCode from wipoST3CodeType.xsd not present in  Databse are::"+list_of_missing_CountryCode);
					
					logger.info("Number of Missing countryCode from wipoST3CodeType.xsd  ::"+list_of_missing_CountryCode.size());
					logger.info("=================================================");	
					mergeMissingCountryCode();
					
					compareList(isoCountryCodeMap,"N");
					
					
					
					logger.info("Missing countryCode from  isoCountryCodeType.xsd  not present in  Databse are::"+list_of_missing_CountryCode);
					
					logger.info("Number of Missing CountryCode from isoCountryCodeType.xsd   ::"+list_of_missing_CountryCode.size());
					
					logger.info("=================================================");
					mergeMissingCountryCode();
					
					
					compareList(wipoISOAlpha2CodeMap,"S");
					
					
					logger.info("Missing countryCode from ISO-Alpha2-All.xlsx not present in  Databse are::"+list_of_missing_CountryCode);
					
					logger.info("Number of Missing countryCode from ISO-Alpha2-All.xlsx  ::"+list_of_missing_CountryCode.size());
					logger.info("=================================================");	
					
					
					logger.info("map_of_missing_CountryCode  from both xsd and xlsx which is not present in database ::"+map_of_missing_CountryCode);
					
					logger.info("Number of CountryCode from both xsd which is not present in database ::"+map_of_missing_CountryCode.size());
					
					logger.info("=================================================");
				}
				
				
				public void mergeMissingCountryCode(){
					
					list_of_CountryCode_from_DB.addAll(list_of_missing_CountryCode);
					list_of_missing_CountryCode.clear();
				}
	
           public void   compareList(Map<String,String> firstmap,String ind_wipo_statistics){
        	   Iterator entries = firstmap.entrySet().iterator();
        	 
        	   while(entries.hasNext()){
        		   
        		   Map.Entry entry = (Map.Entry) entries.next();
        		   
        		   String countryCode=(String)entry.getKey();
        		   
        		   String countryName	=(String)	entry.getValue();
        		   
        		
        		   
        		   if(!list_of_CountryCode_from_DB.contains(countryCode)){
        			   ArrayList<String> lista	=	new ArrayList<String>();
            		   
            		   lista.add(countryName);
            		   lista.add(ind_wipo_statistics);
            		   
        			   list_of_missing_CountryCode.add(countryCode);
        			   map_of_missing_CountryCode.put(countryCode,lista);
        		   }
        		   
        	   }
        	   
                	 
                	 
                 }                                                                                                                                 


	
	public void stopUtility(){
		if(dataProcessor.getConnection()!=null){
			logger.debug("Closing database connection..........!!");
			dataProcessor.closeConnection();
			logger.debug("Database connection closed..........!!");
			
		}
	
		logger.info("Processing Completed.......................!!!");
		logger.info("=================================================");
		logger.info("Please check processed details in log file generated in log folder");
		logger.info("********************************************************");
	}

	public void insertMissingCountryCodeInDB() {
		
		dataProcessor.insertCountryCodeIntoDB(map_of_missing_CountryCode);
	
	}
	
}

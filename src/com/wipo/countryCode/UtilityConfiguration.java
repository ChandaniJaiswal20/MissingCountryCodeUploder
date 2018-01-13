package com.wipo.countryCode;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;




public class UtilityConfiguration {

	ConfigParam	config	=	null;
	
	public String databaseHost;
	public String databasePort;
	public String databaseTool;
	public String databaseName;
	public String userName;
	public String password;
	
	public String 	schemaFileNameForISOCountryCode=null;
	public String	schemaFileNameForWIPOST3Code=null;
	public String	simpleTypeNameForISOCountryCode=null;
	public String	simpleTypeNameForWIPOST3Code=null;
	public String	fileNameForISOAlpha2Code=null;
	
	//fileNameForISOAlpha2Code
	public static Logger logger	= Logger.getLogger(UtilityConfiguration.class);

	public UtilityConfiguration loadConfiguration() {
		logger.debug("loading configuration.....");
			config	= new ConfigParam("countryCodeUtility.properties");
		//	patentAnnuityUtilityFolder	=	config.getProperty("patentAnnuityUtilityFolder");
		
			databaseHost	= config.getProperty("databaseHost");
			databasePort	= config.getProperty("databasePort");
			databaseTool	= config.getProperty("databaseTool");
			databaseName	=	config.getProperty("databaseName");
			userName		=	config.getProperty("userName");
			password		=	config.getProperty("password");
			schemaFileNameForISOCountryCode		=	config.getProperty("schemaFileNameForISOCountryCode");
			schemaFileNameForWIPOST3Code		=	config.getProperty("schemaFileNameForWIPOST3Code");
			simpleTypeNameForISOCountryCode		=	config.getProperty("simpleTypeNameForISOCountryCode");
			simpleTypeNameForWIPOST3Code		=	config.getProperty("simpleTypeNameForWIPOST3Code");
			fileNameForISOAlpha2Code		=	config.getProperty("fileNameForISOAlpha2Code");
	
			logger.debug("databaseHost:::"+databaseHost);
			logger.debug("databasePort:::"+databasePort);
			logger.debug("databaseTool:::"+databaseTool);
			logger.debug("databaseName:::"+databaseName);
			logger.debug("userName:::"+userName);
			logger.debug("schemaFileNameForISOCountryCode:::"+schemaFileNameForISOCountryCode);
			logger.debug("schemaFileNameForWIPOST3Code:::"+schemaFileNameForWIPOST3Code);
			logger.debug("simpleTypeNameForISOCountryCode:::"+simpleTypeNameForISOCountryCode);
			logger.debug("simpleTypeNameForWIPOST3Code:::"+simpleTypeNameForWIPOST3Code);
			logger.debug("fileNameForISOAlpha2Code:::"+fileNameForISOAlpha2Code);
		
			
			
			return this;
				
	}
	
	
	public void checkValuesOfConfigFile(){
		
	
		if(databaseHost==null||databaseHost.isEmpty()){
			
			
			logger.error("Please specify value of  databaseHost in countryCodeUtility.properties");
			throw new MissingConfigurationException("Please specify value of  databaseHost in countryCodeUtility.properties");
		}
		
		if(databasePort==null||databasePort.isEmpty()){
			
			
			logger.error("Please specify value of  databasePort in countryCodeUtility.properties");
			throw new MissingConfigurationException("Please specify value of  databasePort in countryCodeUtility.properties");
		}
		
		if(databaseTool==null||databaseTool.isEmpty()){
			
			logger.error("Please specify value of  databaseTool in countryCodeUtility.properties");
			throw new MissingConfigurationException("Please specify value of  databaseTool in countryCodeUtility.properties");
			
		}
		else if(!(databaseTool.equalsIgnoreCase("sql")||databaseTool.equalsIgnoreCase("oracle"))){
			
			logger.error("Please specify correct database tool in countryCodeUtility.properties ie. for oracle database::<oracle> and for sql database::<sql>");
			throw new MissingConfigurationException("Please specify correct database tool in countryCodeUtility.properties ie. for oracle database::<oracle> and for sql database::<sql>");
		}
		
		if(databaseName==null||databaseName.isEmpty()){
			
			
			logger.error("Please specify value of  databaseName in countryCodeUtility.properties");
			throw new MissingConfigurationException("Please specify value of  databaseName in countryCodeUtility.properties");
		}
		
		if(userName==null||userName.isEmpty()){
			
			logger.error("Please specify value of  userName in countryCodeUtility.properties");
			throw new MissingConfigurationException("Please specify value of  userName in countryCodeUtility.properties");
		}
		
		if(password==null||password.isEmpty()){
			
			
			logger.error("Please specify value of  password in countryCodeUtility.properties");
			throw new MissingConfigurationException("Please specify value of  password in countryCodeUtility.properties");
		}
		
		if(schemaFileNameForISOCountryCode==null||schemaFileNameForISOCountryCode.isEmpty()){
			
			
			logger.error("Please specify value of  schemaFileNameForISOCountryCode in countryCodeUtility.properties");
			throw new MissingConfigurationException("Please specify value of  schemaFileNameForISOCountryCode in countryCodeUtility.properties");
		}
		
		if(schemaFileNameForWIPOST3Code==null||schemaFileNameForWIPOST3Code.isEmpty()){
			
			logger.error("Please specify value of  schemaFileNameForWIPOST3Code in countryCodeUtility.properties");
			throw new MissingConfigurationException("Please specify value of  schemaFileNameForWIPOST3Code in countryCodeUtility.properties");
		}
		
		if(simpleTypeNameForISOCountryCode==null||simpleTypeNameForISOCountryCode.isEmpty()){
			
			
			logger.error("Please specify value of  simpleTypeNameForISOCountryCode in countryCodeUtility.properties");
			throw new MissingConfigurationException("Please specify value of  simpleTypeNameForISOCountryCode in countryCodeUtility.properties");
		}
		
		if(simpleTypeNameForWIPOST3Code==null||simpleTypeNameForWIPOST3Code.isEmpty()){
			
			
			logger.error("Please specify value of  simpleTypeNameForWIPOST3Code in countryCodeUtility.properties");
			throw new MissingConfigurationException("Please specify value of  simpleTypeNameForWIPOST3Code in countryCodeUtility.properties");
		}
		
		
	if(fileNameForISOAlpha2Code==null||fileNameForISOAlpha2Code.isEmpty()){
			
			
			logger.error("Please specify value of  fileNameForISOAlpha2Code in countryCodeUtility.properties");
			throw new MissingConfigurationException("Please specify value of  fileNameForISOAlpha2Code in countryCodeUtility.properties");
		}
		
		
	}

	
	public void checkDateFormat(){
		
		
		
	}
	
	public static void main(String[] args) {
		
		UtilityConfiguration uc = new UtilityConfiguration();
	
			
	//String		log4jConfigFile	=	System.getProperty("user.dir")+File.separator+"log4j.properties";
		
		String		log4jConfigFile	=	"D://PatentAnnuityUtility//log4j.properties";
			
			PropertyConfigurator.configure(log4jConfigFile);
			logger.info("log4jConfigFile:::"+log4jConfigFile);	
	
		uc.loadConfiguration();
		uc.checkValuesOfConfigFile();
		
		
		
	}
	
}

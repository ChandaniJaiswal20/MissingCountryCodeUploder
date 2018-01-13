package com.wipo.countryCode;

/**
 * This class is used to instantiate utility that is used to uploads County codes in IPAS Database that are incomplete and 
 * sometime even inaccurate.  Loading of full ST.3 data from WIPO (web site) and 
 * International Standardization Organization (ISO) will help to organize country data in IPAS environment and
 *  reduce errors of processing/loading Madrid transactions due to missing country codes. 
 * 
 * Issue: IPAS-enabled offices frequently face problems in loading “Birth” Madrid transactions 
 * due to missing/invalid Vienna codes and/or country codes.  County codes used to be 
 * maintained in IPAS in ad-hoc manner and largely dependent on past configurations, 
 * IPAS project managers/regional experts and offices require knowledge to alter/modify the country 
 * list when problems arise.  The countries lists are generally found to be incomplete and 
 * sometime even inaccurate.  Loading of full ST.3 data from WIPO (web site) and 
 * International Standardization Organization (ISO) will help to organize country data in IPAS environment and
 *  reduce errors of processing/loading Madrid transactions due to missing country codes. 
 * 
 *  @author Chandani Jaiswal
 * 
 */

public class UtilityController {
	
	
	
	public static void main(String[] args) {
		
				
				
				UtilityManager	um =	UtilityManager.getInstance();
				
				um.getCountryCodeFromDatabase();
				um.parseWIPOST3Code();
				um.parseISOCountryCode();
				um.parseISOAlpha2();
				
				um.processMissingCountryCode();
				
				um.insertMissingCountryCodeInDB();
				
				um.stopUtility();
		

	
			}
		
	}
	



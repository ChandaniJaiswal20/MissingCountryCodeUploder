package com.wipo.countryCode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sun.xml.xsom.XSFacet;
import com.sun.xml.xsom.XSRestrictionSimpleType;
import com.sun.xml.xsom.XSSchema;
import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.XSSimpleType;
import com.sun.xml.xsom.parser.AnnotationContext;
import com.sun.xml.xsom.parser.AnnotationParser;
import com.sun.xml.xsom.parser.XSOMParser;
import com.sun.xml.xsom.util.DomAnnotationParserFactory;

public class XSDParser {
	public static Logger logger	=	Logger.getLogger(DBdataProcessor.class);
	private  XSSchema xsSchema;
	private  XSSchemaSet schemaSet = null;
	private XSOMParser parser = new XSOMParser();

	private static final String TAG_NAME	=	"documentation";
	private Map<String,String> countryCodeMap	=	new HashMap<String,String>();
	private String schemaFileName	=	null;
	private String simpleTypeName	=	null;
	
	public XSDParser(String schemaFileName,String simpleTypeName) { 
		this.schemaFileName	=	schemaFileName;
		this.simpleTypeName	=	simpleTypeName;
		  this.initXSSchema(); 
		 } 

	private void initXSSchema() { 
		  try { 
		   this.parser.setAnnotationParser(new DomAnnotationParserFactory(){ 
		    public AnnotationParser create() { 
		     return new AnnotationParser() { 
		      final StringBuffer content = new StringBuffer(); 
		      private boolean parsingDocumentation = false;
		      public ContentHandler getContentHandler(AnnotationContext context, 
		        String parentElementName, ErrorHandler errorHandler, 
		        EntityResolver entityResolver) { 
		       return new ContentHandler() { 
		    	   
		    	   
		            public void characters(char[] ch, int start, int length)
		            throws SAXException {
		               if(parsingDocumentation){
		            	   content.append(ch,start,length);
		                }
		            }
		            
		            public void endElement(String uri, String localName, String name)
		            throws SAXException {
		                if(localName.equals(TAG_NAME)){
		                    parsingDocumentation = false;
		                }
		            }
		    	   
		   
		 
		        public void endDocument() throws SAXException {} 
		      
		        public void endPrefixMapping(String prefix) throws SAXException {} 
		        public void ignorableWhitespace(char[] ch, int start, int length) 
		          throws SAXException {} 
		        public void processingInstruction(String target, String data) 
		          throws SAXException {} 
		        public void setDocumentLocator(Locator locator) {} 
		        public void skippedEntity(String name) throws SAXException {} 
		        public void startDocument() throws SAXException {} 
		        public void startElement(String uri, String localName,
						String qName, Attributes atts) throws SAXException {
					   if(localName.equals(TAG_NAME)){
		                    parsingDocumentation = true;
		                }
					
				}
		        public void startPrefixMapping(String prefix, String uri) 
		          throws SAXException {} 
		       }; 
		      } 
		 
		      @Override 
		      public Object getResult(Object existing) { 
		       return content.toString(); 
		      } 
		     }; 
		    } 
		   }); 
		   
		   if(this.parser == null) { 
			 //   log.error("schema parser is null!"); 
			   } else { 
			    ErrorHandler errorHandler = new ErrorHandler() {
					
					@Override
					public void warning(SAXParseException exception) throws SAXException {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void fatalError(SAXParseException exception) throws SAXException {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void error(SAXParseException exception) throws SAXException {
						// TODO Auto-generated method stub
						
					}
				};
			    
				 this.parser.setErrorHandler(errorHandler); 
				  this.parser.parse(new File(schemaFileName)); 
				  this.schemaSet = this.parser.getResult();
			    this.processSchema();
			   }
		  }  catch (Exception e) { 
			   e.printStackTrace(); 
			   
			   logger.error("Error occurred while processing schema file::"+schemaFileName);
			  } 
			 } 
		  
		// parse xsd
			private  void processSchema(){
				 logger.debug("Going to process Schema file ::"+schemaFileName+".......");
					 XSSimpleType  simpleType						=	null;
					 XSRestrictionSimpleType restrictionSimpleType 	=   null;
					 List<XSFacet> listOfenumerationFacet	= new ArrayList<XSFacet>() ;
					
					 this.xsSchema = this.schemaSet.getSchema(1);
		       
			       simpleType				=     xsSchema.getSimpleType(simpleTypeName);
			       restrictionSimpleType 	=    simpleType.asRestriction();
			       listOfenumerationFacet	=   restrictionSimpleType.getDeclaredFacets(XSFacet.FACET_ENUMERATION);
		 
				  for(XSFacet individualFacet:listOfenumerationFacet){
					  
					  String countryCode	=	individualFacet.getValue().toString();
					  String 	countryName	=	individualFacet.getAnnotation().getAnnotation().toString();
					 
					
	  				// System.out.println(countryName);
					  countryCodeMap.put(countryCode, countryName);
				  }
				  
				  
				  logger.debug("Schema file"+schemaFileName+" processed sucessfully.......");  
				
			}
			
			public Map<String,String> getCountryCodeMap(){
				
				  return countryCodeMap;
			}
			
	
	public static void main(String[] args) {
		
		XSDParser xsdparser	=	 new XSDParser("WIPOST3CodeType.xsd","WIPOST3CodeType");
		
		xsdparser.processSchema();
		
		
	}
	

	
	


	
	

}

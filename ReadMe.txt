			++ READ ME ++
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Prerequisites:

Jdk(Java version 1.6.*) should be installed in the running system.

The following things needs to be configured in the run.bat files

Set JAVA_HOME in environment variables to point to the jdk directory.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
MissingCountryCodeUploader folder should contains following folders/files:

1)countryCodeUtility.properties		(file)
2)lib      			(folder)
3)MissingCountryCodeUploaderUtility.jar	(file)
4)ReadMe.txt			(file)
5)run.bat			(file)
6)WIPOST3CodeType.xsd	(file)
7)ISOCountryCodeType.xsd	(file)
8)log4j.properties 		(file)



'countryCodeUtility.properties' files contains properties of database and XSD file details which needs to be modified accordingly before running this program.
The following things needs to be configured in the countryCodeUtility.properties file

Set databaseHost with the IP Address of the Database of IPAS.
Set databasePort with the port of the Database of IPAS.
Set the Database connection details against databaseName, userName and password.


'lib' folder contains all jar files that is required for this program.

'MissingCountryCodeUploaderUtility.jar' is main executable jar to upload drawings.

'ReadMe' file contains the instructions to run this program.

'run.bat' file contains script to execute this program.


++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
HOW TO RUN PROGRAM?
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

1) Open 'countryCodeUtility.properties' file and change properties of all attributes and save it.
2) Double click on 'run.bat' to start program.














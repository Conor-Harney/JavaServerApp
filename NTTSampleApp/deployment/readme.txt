This folder is used to build, test and run the application. 

Prerequisite: 
***
	To run this application, a database with the following specs will need to be set up: 
	<host>localhost</host>
	<port>3306</port>
	<database>Mysql</database>
	<username>root</username>
	<password>NTT_pass01</password>

	Alternately, you will need to change the 
		NTT_DB_Credentials.xml
	file in the 
		JavaServerApp\NTTSampleApp\src\main\resources\xml

	after the databses is created, the sql:
		JavaServerApp\NTTSampleApp\src\main\resources\sql\serverTableSchema.sql
	will need to be run on the database

	The app will then need to be installed and run.
***

There are 4 ways to run this app:
***
	1 – Using the bat files contained
	The contents are as follows:
		install.bat – Clean compile the app to a target directory in the parent folder. 
		test.bat – Run unit tests on the compiled target folder.
		run.bat – Start the app from the target folder in the parent directory.
		installTestRun.bat – all of the above

	2 – Using the manual commands in the parent directory:
	The commands to run a manual install, test and run the app from the parent directory are are as follows:
		mvn clean compile assembly:single
		mvn test
		cd target/
		java -jar sampleapp-1.0.0-jar-with-dependencies.jar

	3 – Running run.bat in the standalone folder

	4 – Running the jar manually in the standalone folder
		java -jar sampleapp-1.0.0-jar-with-dependencies.jar
***

package com.openproject.ivcargo.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionAndCredentials {

	
	final static String AttachmentPath="PATH";
	final static String OpenProjectOrganzationURL="URL ASSIGNED TO ORGNIZATION";
	final static String OpenProjectUserName="USERNAME";
	final static String OpenProjectPassword="PASSWORD";
	final static String OpenProjectOrganizationBasicOth="BASIC OTH PASSWORD";
	private final static String fromEmail="FROM EMAIL";
	private final static String emailPassword="PASSWORD OF FROM EMAIL";
	private final static String toCCEmail="TO CC_EMAIL";
	
	
	
	static Connection addConncetionToDatabase() throws ClassNotFoundException, SQLException
	{
		Connection c;
		Class.forName("org.postgresql.Driver");
		c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/support","postgres", "purav");
		c.setAutoCommit(false);
		return c;
	}
//Add SMTP credentials
	static Properties addSMTPPropertiies()
	{
		Properties props;
		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		return props;
	}
	
//Add Email Credentials
	static void addCredentialsOfMail()
	{
		//All the values or entities needed for mailing are put in a HashMap named "map_obj"
		IndexController.map_obj.put("fromEmail",fromEmail);
		IndexController.map_obj.put("password",emailPassword);
		IndexController.map_obj.put("toCCEmail",toCCEmail);

	}

//Add Attachment Path
	
	


}

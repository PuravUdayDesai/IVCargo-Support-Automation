package com.openproject.ivcargo.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/*
 * This is a single thread class(anonymous class)
 * used to mail to the respective clients.
 */
public class MailGenetator{

	Thread t;
	Authenticator auth;
	HashMap<String,String> map_objs;
	MimeMessage message ;
	Properties props;
	MailGenetator()
	{
		props=ConnectionAndCredentials.addSMTPPropertiies();
		map_objs=IndexController.map_obj;
		auth= new Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(map_objs.get("fromEmail"), map_objs.get("password"));
			}
		};
		Session session = Session.getInstance(props, auth);
		message= new MimeMessage(session);  
	}
	public void createMail(final String id, final boolean value)
	{

		t=new Thread() {
			public void run()
			{
				try
				{
					//adds recipients and CC members
					message.setFrom(new InternetAddress(map_objs.get("fromEmail")));  
					message.addRecipient(Message.RecipientType.TO,new InternetAddress(map_objs.get("email")));
					if((map_objs.get("toCCEmail")!=null))
						message.addRecipient(Message.RecipientType.CC, new InternetAddress(map_objs.get("toCCEmail")));  
					message.setSubject("IVCargo Support: "+map_objs.get("subject"));  

					Multipart multipart = new MimeMultipart();

					String inPriority="burlywood";
					if(map_objs.get("priority").equals("Low")){inPriority="rgb(60, 216, 13)";}
					else if(map_objs.get("priority").equals("Normal")){inPriority="rgb(247, 223, 9)";}
					else if(map_objs.get("priority").equals("High")){inPriority="rgb(209, 43, 43)";}
					else if(map_objs.get("priority").equals("Immediate")){inPriority="rgb(126, 4, 4)";}  

					BodyPart messageBodyPartDesc = new MimeBodyPart();
					messageBodyPartDesc.setDescription("Respected "+map_objs.get("name")+"\n We IVCargo Support Team has received your email and will revert you back as soon as possible. By the time be in touch with IVCargo.");
					BodyPart messageBodyPart1 = new MimeBodyPart();  
					//Styles the Mail using HTML and CSS 
					messageBodyPart1.setContent("<table style=\"border-collapse: collapse;   border-spacing: 15px;\" cellpadding=10px><tr><td style=\"padding: 10px; text-align: left;border-bottom: 1px solid rgb(32, 30, 30) ;border-right: 1px solid rgb(32, 30, 30) ; background-color:burlywood \">Customer Name:</td><td style=\"padding: 10px; text-align: left;border-bottom: 1px solid rgb(32, 30, 30) ;border-right: 1px solid rgb(32, 30, 30) ; background-color:burlywood\">"+map_objs.get("name")+"</td> </tr><tr><td style=\"padding: 10px; text-align: left;border-bottom: 1px solid rgb(32, 30, 30)  ;border-right: 1px solid rgb(32, 30, 30) ; background-color:cornsilk\">Support ID:</td><td style=\"padding: 10px; text-align: left;border-bottom: 1px solid rgb(32, 30, 30)  ;border-right: 1px solid rgb(32, 30, 30) ; background-color:cornsilk\">"+IndexController.allMaps.get("id")+"</td> </tr><tr><td style=\"padding: 10px; text-align: left;border-bottom: 1px solid rgb(32, 30, 30)  ;border-right: 1px solid rgb(32, 30, 30) ; background-color:burlywood\">Subject:</td><td style=\"padding: 10px; text-align: left;border-bottom: 1px solid rgb(32, 30, 30)  ;border-right: 1px solid rgb(32, 30, 30) ;background-color:burlywood \">"+map_objs.get("subject")+"</td></tr><tr><td style=\"padding: 10px; text-align: left;border-bottom: 1px solid rgb(32, 30, 30)  ;border-right: 1px solid rgb(32, 30, 30) ; background-color:cornsilk\">Description:</td><td style=\"padding: 10px; text-align: left;border-bottom: 1px solid rgb(32, 30, 30)  ;border-right: 1px solid rgb(32, 30, 30) ; background-color:cornsilk\">"+map_objs.get("description")+"</td></tr><tr><td style=\"padding: 10px; text-align: left;border-bottom: 1px solid rgb(32, 30, 30) ;border-right: 1px solid rgb(32, 30, 30) ; background-color:burlywood\">Priority:</td><td style=\"padding: 10px; text-align: left;border-bottom: 1px solid rgb(32, 30, 30) ;  background-color:"+inPriority+"; border-right: 1px solid rgb(32, 30, 30) ; \">"+map_objs.get("priority")+"</td></tr><tr><td style=\"padding: 10px; text-align: left;border-bottom: 1px solid rgb(32, 30, 30)  ;border-right: 1px solid rgb(32, 30, 30) ; background-color:cornsilk\">Branch:</td><td style=\"padding: 10px; text-align: left;border-bottom: 1px solid rgb(32, 30, 30) ;border-right: 1px solid rgb(32, 30, 30) ; background-color:cornsilk\">"+map_objs.get("branch")+"</td></tr> <tr><td style=\"padding: 10px; text-align: left;border-bottom: 1px solid rgb(32, 30, 30) ;border-right: 1px solid rgb(32, 30, 30) ; background-color:burlywood\">Contact Info:</td><td><table><tr><td style=\"background-color:burlywood ;\">Contact Number:</td><td style=\"padding: 10px; text-align: left;border-right: 1px solid rgb(32, 30, 30) ;background-color:burlywood \">"+map_objs.get("contact")+"</td></tr><tr><td style=\"padding: 10px; text-align: left;border-bottom: 1px solid rgb(32, 30, 30) ; background-color:burlywood\">Email Address:</td><td style=\"padding: 10px; text-align: left;border-bottom: 1px solid rgb(32, 30, 30) ;border-right: 1px solid rgb(32, 30, 30) ; background-color:burlywood\">"+map_objs.get("email")+"</td></tr></table></td></tr></table>","text/html");  
					multipart.addBodyPart(messageBodyPart1);

					//mail with attachment
					if(value)
					{
						Statement stmt = null;
						HashMap<String,String> mailPaths=new HashMap<>();

						stmt = ConnectionAndCredentials.addConncetionToDatabase().createStatement();
						ResultSet rs = stmt.executeQuery( "SELECT * FROM supportattachment WHERE id='"+id+"';" );
						int iter=0;
						while(rs.next())
						{
							/*
							 * Iteratively puts the attachment paths
							 * from supportattachment table to HashMap
							 */
							mailPaths.put(""+iter, rs.getString("attachment"));
							iter++;
						}
						MimeBodyPart messageBodyPart[]=new MimeBodyPart[iter];

						for(int i=0;i<iter;i++)
						{
							messageBodyPart[i]=new MimeBodyPart();
							//adds Data Of File
							DataSource source = new FileDataSource(mailPaths.get(""+i));  
							messageBodyPart[i].setDataHandler(new DataHandler(source));  
							//adds File Name
							messageBodyPart[i].setFileName(AttachmentGenerator.getFileName(mailPaths.get(""+i)));  
							//adds to message body(multipart)
							multipart.addBodyPart(messageBodyPart[i]);
						}
					}
					//sending mail
					message.setContent(multipart);
					Transport.send(message);
					//mail successfully sent
				}
				catch(MessagingException | ClassNotFoundException | SQLException ex)
				{
					//Mail Failed
				}
			}
		};
		t.start();
	}
}

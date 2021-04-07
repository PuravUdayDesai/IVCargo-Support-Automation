package com.openproject.ivcargo.view;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/*
 * This is a single thread class(anonymous class)
 * used to create attachments in ticket 
 */
public class AttachmentGenerator
{
	Statement stmt;
	HashMap<String,String> attachmentPaths=new HashMap<>();
	Thread t;
	/*
	 * This function is used to get the 
	 * original file name by filtering the
	 * whole path.
	 * 
	 * This function is also used in MailGenetator class too
	 */
	static String getFileName(String fn)
	{
		return fn.replace(ConnectionAndCredentials.AttachmentPath+"/"+BodyReturner.AccountGroupId+"/", "");
	}

	public void attachmentGenerator(final String id)
	{
		t=new Thread()
		{
			public void run()
			{
				
					try {
						stmt =ConnectionAndCredentials.addConncetionToDatabase().createStatement();
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
					ResultSet rs = null;
					try {
						rs = stmt.executeQuery( "SELECT * FROM supportattachment WHERE id= '"+id+"';" );
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					int iter=0;
					//gets all the values in supportattachment table and puts into HashMap
					try {
						while(rs.next())
						{
							try {
								attachmentPaths.put(""+iter, rs.getString("attachment"));
							} catch (SQLException e) {
								
								e.printStackTrace();
							}
							iter++;
						}
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					for(int i=0;i<iter;i++)
					{
						//Iteratively creates attachment in Ticket
						try {
							@SuppressWarnings("unused")
							HttpResponse<String> jsonResponse = Unirest.post(ConnectionAndCredentials.OpenProjectOrganzationURL+"/api/v3/work_packages/"+IndexController.allMaps.get("id")+"/attachments")
							.header("Authorization", ConnectionAndCredentials.OpenProjectOrganizationBasicOth)
							.header("accept", "application/json")
							.field("metadata", "{\"fileName\":\""+getFileName(attachmentPaths.get(""+i))+"\"}")
							.field("file",new File(attachmentPaths.get(""+i)) )
							.asString();
						} catch (UnirestException e) {
							
							e.printStackTrace();
						}			
					}
			

			}
		};t.start();
	}
}


package com.openproject.ivcargo.view;

import java.sql.Statement;
import java.util.HashMap;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;


/*
 * It is a single thread class(anonymous class)
 * which is used to delete the created attachments
 * from the folder(UserAttachments) at Server disk
 */

public class DeleteAttachment 
{
	HashMap<String,String> attachmentPaths=new HashMap<>();
	Statement stmt;
	Thread t;
	String idToSearch;
	public void del(String id) throws Exception
	{		
		idToSearch=id;
		t=new Thread() 
		{
			@SuppressWarnings("null")
			public void run()
			{
				
					try {
						stmt = ConnectionAndCredentials.addConncetionToDatabase().createStatement();
					} catch (ClassNotFoundException | SQLException e) {
						
						e.printStackTrace();
					}
					ResultSet rs = null;
					try {
						rs = stmt.executeQuery( "SELECT * FROM supportattachment WHERE id= '"+idToSearch+"';" );
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					int iter=0;
					try {
						while(rs.next())
						{
							attachmentPaths.put(""+iter, rs.getString("attachment"));
							iter++;
						}
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					File f[]=new File[iter];
					for(int i=0;i<iter;i++)
					{
						f[i]=new File(attachmentPaths.get(""+i));
						f[i].delete();
					}
				
			}
		};t.start();
	}
}

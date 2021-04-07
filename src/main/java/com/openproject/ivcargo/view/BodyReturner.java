package com.openproject.ivcargo.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/*
 * This is a single thread class(anonymous class)
 * used to generate a string body appropriate to
 * the request achieved.
 */
public class BodyReturner
{
	String desc,subject,priority;
	String body;
	Thread t;
	String openProjectId,openProjectName,accountGroupId;
	public static String AccountGroupId;
	void getBody(HashMap<String,String> bodyMap)throws Exception
	{
		Statement stmt = ConnectionAndCredentials.addConncetionToDatabase().createStatement();
		String query="SELECT * FROM public.\"clientAccId\" WHERE id='"+IndexController.idOfAcc+"';";
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next())
		{
			accountGroupId=rs.getString("AccountGroupId");
			AccountGroupId=accountGroupId;
			break;
		}


		t=new Thread()
		{
			public void run()
			{
					Statement stmt2 = null;
					try {
						stmt2 = ConnectionAndCredentials.addConncetionToDatabase().createStatement();
					} catch (ClassNotFoundException | SQLException e) {
						
						e.printStackTrace();
					}
					String query="SELECT * FROM customerinfo WHERE \"AccountGroupId\"="+accountGroupId+";";
					ResultSet rs = null;
					try {
						rs = stmt2.executeQuery(query);
					} catch (SQLException e) {
						
						e.printStackTrace();
					}

					try {
						while(rs.next())
						{
							openProjectId=rs.getString("OpenProjectId");
							openProjectName=rs.getString("OpenProjectName");
							break;
						}
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					//assigns default values if Correct record is not found
					if(openProjectId==null||openProjectName==null)
					{
						openProjectId="1048";
						openProjectName="TEST: TEST GROUP FOR AUTOMATION";
					}
					desc=bodyMap.get("description");
					subject=bodyMap.get("subject");
					priority=bodyMap.get("priority");

				//assigns the priority value respectively
				String priorityValue="7";
				if(priority.equals("Normal")) {priorityValue="8";}
				else if(priority.equals("High")) {priorityValue="9";}
				else if(priority.equals("Immediate")) {priorityValue="10";}
				//A string in the JSON format to create Ticket is being created
				body= "{\r\n" +
						"    \"_type\": \"WorkPackage\",\r\n" +
						"    \"_links\": {\r\n" +
						"        \"priority\": {\r\n" +
						"            \"href\": \"/api/v3/priorities/"+priorityValue+"\",\r\n" +
						"            \"title\": \""+ priorityValue+"\"\r\n" +
						"        },\r\n" +
						"        \"project\": {\r\n" +
						"            \"href\": \"/api/v3/projects/2\",\r\n" +
						"            \"title\": \"Demo\"\r\n" +
						"        }\r\n" +
						"    },\r\n" +
						"    \"customField11\": {\r\n" +
						"        \"format\": \"markdown\",\r\n" +
						"        \"raw\": \"NA\",\r\n" +
						"        \"html\": \"<b>NA</b>\"\r\n" +
						"    },\r\n" +
						"    \"customField1\": {\r\n" +
						"        \"title\": \""+openProjectName+"\",\r\n" +
						"        \"href\": \"/api/v3/custom_options/"+openProjectId+"\"\r\n" +
						"    },\r\n" +
						"    \"type\": {\r\n" +
						"        \"_type\": \"Type\",\r\n" +
						"        \"id\": 1,\r\n" +
						"        \"name\": \"New\",\r\n" +
						"        \"_links\": {\r\n" +
						"            \"self\": {\r\n" +
						"                \"href\": \"/api/v3/types/1\",\r\n" +
						"                \"title\": \"New\"\r\n" +
						"            }\r\n" +
						"        }\r\n" +
						"    },\r\n" +
						"    \"subject\": \""+subject+"\",\r\n" +
						"    \"description\": {\r\n" +
						"        \"format\": \"markdown\",\r\n" +
						"        \"raw\": \""+desc.replace("\"","\\\"")+"\",\r\n" +
						"        \"html\": \""+desc.replace("\"","\\\"")+"\"\r\n" +
						"    }\r\n" +
						"}";
				//puts in the HashMap of IndexController class for its reference and further working
				IndexController.allMaps.put("body", body);
			}
		};t.start();
	}
}

package com.openproject.ivcargo.view;

import java.util.*;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

/*
 * After MainController, IndexController can be
 * viewed as the heart and brain of this project
 * all the main functions are performed in this 
 * class only.
 */
@Component
public class IndexController
{
	static HashMap<String, String> allMaps=new HashMap<>(); // values from getbody() method(BodyReturner Class)
	static boolean result;//It is used in ServerValidation(server validation)
	static boolean processComplete;
	static HashMap<String,String> map_obj =new HashMap<>();
	static String idOfAcc;
	static int attNumber;

	@Scheduled(fixedDelay =15*60*1000)//Scheduler runs after every 15 minutes and executes all pending request
	public static void createAttachment() throws Exception
	{  
		//Disables Cookie warning
		HttpClient httpClient = HttpClients.custom()
				.disableCookieManagement()
				.build();
		/*
		 *Get Values from database only first
		 */

		String id="",name="",contact="",sub="",priority="",emailId="",description="",branch="";
		Connection c=ConnectionAndCredentials.addConncetionToDatabase();
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM public.supportclient;");
		int iterToCount=0;
		while ( rs.next() )
		{
			MainController.incrementId();
			//Maximum 20 requests are only allowed at each schedule
			if(iterToCount==20)
			{
				break;
			}
			//all the entities are assigned their respective values
			id=rs.getString("id");//id
			idOfAcc=id;
			name=rs.getString("name");//name
			contact=rs.getString("contact");//contact
			sub=rs.getString("subject");//subject
			priority=rs.getString("priority");//priority
			emailId=rs.getString("email");//email
			description=rs.getString("description");//description
			branch=rs.getString("branch");//branch

			MailGenetator mailGen = new MailGenetator();//Creates an object of MailGenerator to generate mails

			ServerValidation sv = new ServerValidation();//Initialization of server validation
			sv.serverValid(name,contact,emailId,sub,priority);//validating fields at server side for Attenuation(Hacking) prevention
			sv.t.join();//using join() to control the flow of control of thread serverValid()
			if(result)
			{


				Unirest.setHttpClient(httpClient);

				HashMap<String,String> bodyMap=new HashMap<>();//adding HashMap
				bodyMap.put("description", description);
				bodyMap.put("subject", sub);
				bodyMap.put("priority", priority);


				BodyReturner br=new BodyReturner();//Returns JSON Body as per request(query)
				br.getBody(bodyMap);
				br.t.join();
				JSONObject json=new JSONObject(allMaps.get("body"));

				//Creates Ticket using Unirest(Lightweight HTTP Request Client Libraries) HTTP POST request
				//Internet must be connected else this request will not execute
				HttpResponse<JsonNode> response = Unirest.post(ConnectionAndCredentials.OpenProjectOrganzationURL+"/api/v3/work_packages?notify=false")
						.basicAuth(ConnectionAndCredentials.OpenProjectUserName, ConnectionAndCredentials.OpenProjectPassword)
						.header("Content-Type","application/json")
						.body(json)
						.asJson();

				/*
				 * A Thread which returns the ID of the Ticket request
				 * The whole response body is passed to it and it
				 * Filters id from the whole JSON response body
				 */
				IdReturner idr=new IdReturner();
				idr.getId(response.getBody().toString());
				idr.t.join();

				//All the values or entities needed for mailing are put in a HashMap named "map_obj"
				ConnectionAndCredentials.addCredentialsOfMail();
				map_obj.put("email",emailId);
				map_obj.put("subject",sub);
				map_obj.put("description",description);
				map_obj.put("name", name);
				map_obj.put("contact",contact);
				map_obj.put("branch", branch);
				map_obj.put("priority", priority);

				if(response.getStatus()==201)//if the Ticket creation is successful then it checks for attachments
				{
					/*
					 * Check whether is there any entry of the id in attachment
					 */
					stmt = c.createStatement();
					ResultSet rsAtt = stmt.executeQuery( "SELECT \"id\",\"attachment\" FROM \"public\".\"supportattachment\" WHERE id= '"+id+"';" );
					while(rsAtt.next())
					{
						attNumber++;
					}

					if(attNumber>=1)
					{

						AttachmentGenerator attachGen=new AttachmentGenerator();
						attachGen.attachmentGenerator(id);
						attachGen.t.join();

						mailGen.createMail(id,true);// Mail Tread
						mailGen.t.join();
					}
					else
					{
						//sends mail without attachment
						mailGen.createMail(null,false);
						mailGen.t.join();
					}

					//delete row
					DeleteAttachment delAttachment=new DeleteAttachment();
					delAttachment.del(id);
					stmt = c.createStatement();
					String sqlDel="DELETE FROM public.\"clientAccId\" WHERE ID = '"+id+"';";
					String sql = "DELETE FROM public.supportclient WHERE  ID = '"+id+"';";

					stmt.executeUpdate(sql);
					c.commit();
					stmt.executeUpdate(sqlDel);
					c.commit();
					/*
					 * Query for getting names of the attachment
					 * Of the respective id and deleting the same
					 */
					ResultSet rsDelAtt = stmt.executeQuery( "SELECT * FROM public.supportattachment WHERE id= '"+id+"';" );
					int iterForDel=0;
					HashMap<String,String> mapToDel=new HashMap<>();
					while(rsDelAtt.next())
					{
						mapToDel.put(""+iterForDel,rsDelAtt.getString("attachment"));
						iterForDel++;
					}
					File[] f=new File[iterForDel];
					for(int i=0;i<iterForDel;i++)
					{
						f[i]=new File(mapToDel.get(""+i));
						f[i].delete();
					}
					String sqlAtt = "DELETE from public.supportattachment where ID = '"+id+"';";
					stmt.executeUpdate(sqlAtt);
					c.commit();
					MainController.decrementId();

					System.out.println("Success");
				}
				else
				{
					System.out.println("Error");

				}
			}
			else
			{
				System.out.println("Server Error");
			}
			iterToCount++;
		}
	}
}

package com.openproject.ivcargo.view;

import java.io.File;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/*
URL TO BE USED:
http://localhost:8081/?executiveId=16472&executiveName=IV%20Admin&emailAddr=purav7838@gmail.com&accountGroupId=270&accountGroupName=Suraj%20Carrying%20Corporation&branchId=19168&branchName=MUMBAI
*/
/*
 * It is the main controller class
 * which is redirected when "SUBMIT"
 * button is clicked.
 */
@Controller
public class MainController 
{
	static private int id;//id is used to assign a unique id to each request
	Connection c = null;

	Statement stmt = null;
	Statement stmtAtt = null;
	static boolean result=false;
	static void incrementId()//It increments id by 1 when a new request is created
	{
		id++;
	}
	static void decrementId()//It decrements id by 1 when a request is completed successfully
	{
		--id;
	}

	@RequestMapping(value="/")
	public String home()//It maps to index.jsp a IP_ADDRESS:PORT/
	{
		return "index";
	}

	@RequestMapping(value = "/createTicket", method = RequestMethod.POST)
	@ResponseBody
	public void mainControl(HttpServletResponse respo,@RequestParam("accountGroupId") String accGroupId,@RequestParam(value="subject") String sub,@RequestParam(value="content") String description,@RequestParam(value="priority") String priority, @RequestParam(value="uploadingFiles") MultipartFile[] file,@RequestParam(value="p_name") String name,@RequestParam(value="email") String emailId,@RequestParam(value="con_no") String contact,@RequestParam(value="branch") String branch) throws Exception
	{
		
		incrementId();
		ServerValidation sv = new ServerValidation();//Initialization of server validation
		sv.serverValid(name,contact,emailId,sub,priority);//validating fields at server side for Attenuation(Hacking) prevention
		sv.t.join();//using join() to control the flow of control of thread serverValid()
		if(result)
		{
			c=ConnectionAndCredentials.addConncetionToDatabase();
			stmt = c.createStatement();
			//Inserts a Request in table supportclient table
			String sql = "INSERT INTO supportclient VALUES ('"+id+"','"+sub+"','"+description+"','"+priority+"','"+name+"','"+emailId+"','"+branch+"','"+contact+"');";
			//check if there is any AccountGroupId existing or not
			Statement stmt2 = null;
			stmt2 = ConnectionAndCredentials.addConncetionToDatabase().createStatement();
			String query="SELECT * FROM customerinfo WHERE \"AccountGroupId\"="+accGroupId+";";
			ResultSet rs = stmt2.executeQuery(query);

			while(rs.next())
			{
				accGroupId=rs.getString("AccountGroupId");
				break;
			}
			if(accGroupId==null)
			{
				//System.out.println("AccountGroupId Values Are NULL");
				accGroupId="229";
			}
			//Inserts AccountGroupId given into clientAccId table with request id
			String sqlIns="INSERT INTO public.\"clientAccId\" VALUES('"+id+"',"+accGroupId+");";
			stmt.executeUpdate(sql);//executes supportclient table request
			stmt.executeUpdate(sqlIns);//executes clientAccId table request
			c.commit();


			String[] path=new String[file.length];
			File[] f=new File[file.length];

			/*
			 * path[] contains full path of the file which will we created
			 * f[] is used to create file
			 * f[[] has path[i] as its parameter
			 */
			if(!file[0].isEmpty())//checks if does the request contains attachments or not
			{        
				stmtAtt = c.createStatement();
				for(int i=0;i<file.length;i++)
				{
					path[i]=new String();

					if(!file[i].isEmpty())
					{
						String filename=file[i].getOriginalFilename();
						String fname=filename;
						path[i]=ConnectionAndCredentials.AttachmentPath+"/"+accGroupId+"/"+fname;
						/*
						 * Add path[i] to database
						 */
						//Inserts attachment paths in supportattachment table
						String sqlAtt = "INSERT INTO supportattachment VALUES ('"+id+"','"+path[i]+"');";
						stmtAtt.executeUpdate(sqlAtt);
						f[i]=new File(path[i]);
						f[i].createNewFile();//creates a file in the folder Specified Folder/File name in ConnectionAndCredentials Class
						OutputStream fout=new FileOutputStream(f[i]);
						byte[] bytes = file[i].getBytes();
						fout.write(bytes);
						fout.close();
					}
				}
			}
			c.commit();
			respo.sendRedirect("/success");//redirects to success.jsp
		}
		else
		{
			//System.out.println("Server Error");
			respo.sendRedirect("/error");//redirects to error.jsp
		}
	}
	@RequestMapping(value="success", method = RequestMethod.GET)
	public String success()
	{
		return "success";
	}
	@RequestMapping(value="error", method = RequestMethod.GET)
	public String err()
	{
		return "error";
	}

}

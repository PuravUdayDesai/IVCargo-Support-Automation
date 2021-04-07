package com.openproject.ivcargo.view;
import java.sql.*;
import java.io.*;
class CreateAccountGroupIdFolders
{
public static void main(String[] args)throws Exception
{
Statement stmt2 = null;

stmt2 = ConnectionAndCredentials.addConncetionToDatabase().createStatement();
String query="SELECT * FROM customerinfo ;";
ResultSet rs = stmt2.executeQuery(query);

while(rs.next())
{
File theDir = new File(ConnectionAndCredentials.AttachmentPath+rs.getString("AccountGroupId"));

// if the directory does not exist, create it
if (!theDir.exists()) {
    try{
        theDir.mkdir();
    } 
    catch(SecurityException se){

    }        

}
}

}
}

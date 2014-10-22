package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Connector {
	public Connection initConnection(){
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/OFS","root","pass");
			
		}
		catch(ClassNotFoundException e ){
			e.printStackTrace();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return con;
	}
	
	public void closeConnection(Connection con){
		try{
			if(con!=null){
				con.close();
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
}

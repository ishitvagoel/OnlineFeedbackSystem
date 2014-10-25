package com.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.db.*;
/**
 * Servlet implementation class Verifier
 */
@WebServlet("/Verifier")
public class Verifier extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Verifier() {
        super();
        // TODO Auto-generated constructor stub
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("user");
		String password = request.getParameter("password");
		PrintWriter out=response.getWriter();
		Connector connection1 = new Connector();
		Connection con = connection1.initConnection();
		PreparedStatement query = null;
		ResultSet result = null;
		try{
			query = con.prepareStatement("select username,password, name, uid, isAdmin from users where username = ? and password = ?");
			query.setString(1, username);
			query.setString(2, password);
			result = query.executeQuery();
			
			if(!result.next()){	
				
				out.print("Invalid Login");
			}else{
				
				ServletContext context = request.getSession().getServletContext();
				context.setAttribute("name", result.getString("name"));
				context.setAttribute("uid", result.getInt("uid") );
				
				if(result.getInt("isAdmin") == 0){
					//out.write("Test Message");
					response.sendRedirect("UserHome");
				}
				else{
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("adminHome.jsp");
					dispatcher.forward(request, response);
				}
					
			}
			//Connection closed
			connection1.closeConnection(con);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	 }

}

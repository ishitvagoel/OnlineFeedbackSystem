package com.home;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.coyote.Request;

import com.db.Connector;

/**
 * Servlet implementation class UserHome
 */
@WebServlet("/UserHome")
public class UserHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserHome() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext context = request.getSession().getServletContext();
		PrintWriter writer = response.getWriter();
		writer.write("<h3> Welcome " + (String)context.getAttribute("name") +" to the Online Feedback System</h3><br/>");
		Connector connector = new Connector();
		Connection connection = connector.initConnection();
		String sql = "select c.coursename , ct.courseid from courses c, coursetaken ct where ct.uid = ? and ct.courseid = c.courseid";
		PreparedStatement statement = null;
		ResultSet courseIds = null;
		try{
			statement = connection.prepareStatement(sql);
			statement.setInt(1, (Integer)context.getAttribute("uid"));
			courseIds = statement.executeQuery();
			
			while(courseIds.next()){
				writer.write("<br/>");
				//****************To verify the following
				writer.write("<a href = \"QuestionDisplay?courseid=" + courseIds.getInt("ct.courseid") + "\">" + courseIds.getString("c.coursename") + "</a><br/>");
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

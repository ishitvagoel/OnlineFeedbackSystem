package com.feedback;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.Connector;

/**
 * Servlet implementation class Calculator
 */
@WebServlet("/Calculator")
public class Calculator extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Calculator() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext context = request.getSession().getServletContext();
		Connector connector = new Connector();
		Connection connection = connector.initConnection();
		PrintWriter writer = response.getWriter();
		
		//writer.write("Hello");
		//***************** To Correct . context attribute is not being made available. Null Pointer Excpetion is occuring.
		//int lob_tot = (Integer)context.getAttribute("lob_tot");
		//int fac_tot = (Integer)context.getAttribute("fac_tot");
		//writer.write(lob_tot);
		
		int lob_avg, fac_avg, lob_sum = 0 , fac_sum = 0 ;
		for(int i = 1 ; i <= 3 ; i++){ // Upper limit is hardcoded just for the time being. It must be the value of total questions received through the context.
			 lob_sum += Integer.parseInt(request.getParameter("lob_q"+i));
			 fac_sum += Integer.parseInt(request.getParameter("fac_q"+i));
		}
		lob_avg = lob_sum/3 ;
		fac_avg = fac_sum/3 ;
		try {
			Statement statement = connection.createStatement();
			
			statement.executeUpdate("insert into averaging (courseid, facilitator,lob_avg, fac_avg) values ("+request.getParameter("courseid") + "," + request.getParameter("fuid") + "," + lob_avg + "," + fac_avg + ")");
			writer.write("Thank you for the feedback<br>Learning Objectives Average :" + lob_avg +"<br>Facilitator Average :"+ fac_avg);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

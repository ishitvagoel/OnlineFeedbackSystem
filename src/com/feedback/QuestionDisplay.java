package com.feedback;

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

import com.db.Connector;

/**
 * Servlet implementation class QuestionDisplay
 */
@WebServlet("/QuestionDisplay")
public class QuestionDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionDisplay() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext context = getServletContext();
		PrintWriter writer = response.getWriter();
		
		//writer.write(request.getParameter("courseid"));
		Connector connector = new Connector();
		Connection connection = connector.initConnection();
		String sql1 = "select question from questions where courseid = " + request.getParameter("courseid") + " and type = 1";
		String sql2 = "select question from questions where courseid = " + request.getParameter("courseid") + " and type = 2";
		String sql3 = "select rate, textval from scales where scale = " + request.getParameter("scale") + " order by rate asc";
		ResultSet questions = null;
		ResultSet rates = null;
		Statement statementQues = null ;
		Statement statementRates = null ;
		int lob_qno = 1;
		int fac_qno = 1;
		try {
		    statementQues = connection.createStatement();
		    statementRates = connection.createStatement();
		    questions = statementQues.executeQuery(sql1);
			rates = statementRates.executeQuery(sql3);
			writer.write("<html><head></head><body><form action = \"Calculator?fuid=" + request.getParameter("fuid") + "&courseid=" + request.getParameter("courseid") + "\" method = \"post\"><table>" +
						"<th>" +
						"<td>Question</td><td>Ratings</td>"+
						"</th>");
						
			while(questions.next()){
				writer.write("<tr><td>" + questions.getString("question") +"</td>");
				while(rates.next()){
					writer.write("<td>"+rates.getString("textval")+ " <input type = \"radio\" name=\"lob_q"+ lob_qno +"\" value = " + rates.getInt("rate") + " ></td>");
				}
				rates.beforeFirst();
				writer.write("</tr>");
				lob_qno++;
				
			}
			questions = statementQues.executeQuery(sql2);
			rates.beforeFirst();
			writer.write("<br><br>Questions about the facilitator");
			while(questions.next()){
				writer.write("<tr><td>" + questions.getString("question") +"</td>");
				while(rates.next()){
					writer.write("<td>"+rates.getString("textval")+ " <input type = \"radio\" name=\"fac_q"+ fac_qno +"\" value = " + rates.getInt("rate") + " ></td>");
				}
				rates.beforeFirst();
				writer.write("</tr>");
				fac_qno++;
				
			}
			writer.write("</table><input type = \"submit\"></form></body></html>");
			
			//writer.write(lob_qno);
			
			connector.closeConnection(connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.setAttribute("lob_tot", lob_qno);
		context.setAttribute("fac_tot", fac_qno);
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

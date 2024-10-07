package com.sai.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sai.jdbc.Connector;

public class Login_Servlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		Connector con = new Connector();
		Connection c = con.connect();
		String query = "select * from student where email=? and password=?";
		try {
			PreparedStatement pstmt = c.prepareStatement(query);
			pstmt.setString(1,email);
			pstmt.setString(2,password);
			ResultSet r = pstmt.executeQuery();
			
			if(r.next()){ 
			if (r.getString("email").equals(email)) 
			{
				if (r.getString("password").equals(password)) 
					
				{
					
					HttpSession session = req.getSession();
					session.setAttribute("name", r.getString("name"));
					session.setAttribute("email",r.getString("email"));
	               session.setAttribute("branch", r.getString("branch"));
					session.setAttribute("rollno", r.getString("rollno"));
					
					res.sendRedirect("Home_Servlet");
					

				} else {
					
					//inavlid password
					res.sendRedirect("login.html");
				}
			}
			else 
			{
				//invalid email
				res.sendRedirect("login.html");
			}
			}
			else 
			{
				//inavlid user
				res.sendRedirect("login.html");
			}
		}
		 catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	
	}
}
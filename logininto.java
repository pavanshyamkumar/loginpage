package com.register;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/logininto")
public class logininto extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public logininto() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
try {
			
			String username=request.getParameter("user");
			String pass=request.getParameter("pass");
			String dbname=null;
			String dbpass=null;
			
			String sql="select * from member where uname=? and password=?";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb","root","Shyam1999@");
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,username);
			ps.setString(2,pass);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				dbname=rs.getString(1);
				dbpass=rs.getString("password");
			}
			if(username.equals(dbname) && pass.equals(dbpass))
			{
				HttpSession session=request.getSession();
				session.setAttribute("username",username);
				response.sendRedirect("welcome.jsp");
				
			}
			else
			{
				request.setAttribute("error", "The Login Ceridinels is Incorrect");
				RequestDispatcher rd=request.getRequestDispatcher("registration.jsp");
				rd.include(request, response);
				PrintWriter out=response.getWriter();
				out.println("<script>document.getElementById('error').innerHTML='Sorry UserName or Password'; </script>");
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

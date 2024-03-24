package signup.Myservlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import com.mysql.cj.xdevapi.Statement;

@WebServlet("/register")
public class Registration extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		String password=request.getParameter("pass");
		String mobile=request.getParameter("contact");
		
		PrintWriter out=response.getWriter();
		
		Connection con=null;
		try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/login","root","kunu");
			
			PreparedStatement pstmt=con.prepareStatement("insert into userdata values(?,?,?,?)");
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			pstmt.setString(3, password);
			pstmt.setString(4, mobile);
			
			int count=pstmt.executeUpdate();
			
			if(count>0) {
				response.setContentType("text/html");
				out.print("<h3 style='color:green'>Sign Up Success...</h3>");
				RequestDispatcher rd=request.getRequestDispatcher("registration.jsp");
				rd.include(request, response);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

}

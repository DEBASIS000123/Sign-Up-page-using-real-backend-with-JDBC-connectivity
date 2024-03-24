package signup.Myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class login extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uname=req.getParameter("username");
		String pass=req.getParameter("password");
		Connection con=null;
		PrintWriter out=resp.getWriter();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/login","root","kunu");
			PreparedStatement pstmt=con.prepareStatement("select * from userdata where name=? and password=? ");
			pstmt.setString(1, uname);
			pstmt.setString(2, pass);
			ResultSet rs=pstmt.executeQuery();
			HttpSession session=req.getSession();
			if(rs.next()) {
				session.setAttribute("name", uname);
				RequestDispatcher rd=req.getRequestDispatcher("index.jsp");
				rd.forward(req,resp);
			}
			else {
				resp.setContentType("text/html");
				out.print("<h3 style='color:red'>Password Not Found..</h3>");
				RequestDispatcher rd=req.getRequestDispatcher("login.jsp");
				rd.include(req,resp);
			}
		}
		catch(Exception e){
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

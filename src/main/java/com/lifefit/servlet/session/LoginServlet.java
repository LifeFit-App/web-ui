package com.lifefit.servlet.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lifefit.model.Person;
import com.lifefit.rest.pcs.client.LifeFitClient;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public LoginServlet() {
        super();        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		LifeFitClient client = new LifeFitClient();
		String email = request.getParameter("email");
		String pass = request.getParameter("password");
		
		//Authenticate user
		Person user = client.authenticateUser(email, pass);
		
		if(user != null){
			HttpSession session = request.getSession();
			//Create user session
			session.setAttribute("idPerson", user.getIdPerson());	
			//forward to movie list page
			getServletContext().getRequestDispatcher("/Home").forward(request, response); 				
		}
		else{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
			PrintWriter out = response.getWriter();
			//Send error messages
			out.println("<font color=red>Neither email or password is wrong.</font>");
			rd.include(request, response);
		}
	}

}

package com.lifefit.servlet.session;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lifefit.model.Goal;
import com.lifefit.model.LifeStatus;
import com.lifefit.model.Person;
import com.lifefit.rest.pcs.client.LifeFitClient;

@WebServlet("/Home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		LifeFitClient client = new LifeFitClient();		
		HttpSession session = request.getSession();
		
		//Get stored idPerson from session
		int idPerson = (Integer)session.getAttribute("idPerson");
		
		//Get person with idPerson by invoking process centric services
		Person person = client.readPerson(idPerson);
		List<LifeStatus> personLifeStatus = person.getLifeStatus();
		request.setAttribute("person", person);
		request.setAttribute("personLifeStatus", personLifeStatus);
		
		//get person's goal
		Goal goal = client.getGoal(idPerson);
		request.setAttribute("goal", goal);
		
		String dailyGoalStatus = client.checkDailyGoalStatus(idPerson);
		request.setAttribute("goalStatus", dailyGoalStatus);
				
		getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
	}

}

package com.lifefit.servlet.session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lifefit.model.Goal;
import com.lifefit.model.Measure;
import com.lifefit.rest.pcs.client.LifeFitClient;

@WebServlet("/Goal")
public class GoalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public GoalServlet() {
        super();     
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			HttpSession session = request.getSession();
			LifeFitClient client = new LifeFitClient();
			
			String action = (request.getParameter("action") != null ? request.getParameter("action") : "");
			String measureType = (request.getParameter("measureName") != null ? request.getParameter("measureName") : "");
			String goalTarget = (request.getParameter("goalTarget") != null ? request.getParameter("goalTarget") : "0");
			
			//get stored idPerson from session
			int idPerson = (Integer)session.getAttribute("idPerson");
			//get person's goal
			Goal personGoal = client.getGoal(idPerson);
			
			if(!measureType.equalsIgnoreCase("") && !goalTarget.equalsIgnoreCase("0")){
				if(action.equalsIgnoreCase("saveGoal")){	
					if(personGoal == null)
						personGoal = new Goal();
					
					personGoal.setGoalTarget(Double.parseDouble(goalTarget));
				
					if(client.savePersonGoal(personGoal, idPerson, measureType))
					{
						//forward to home page			
						getServletContext().getRequestDispatcher("/Home").forward(request, response);
						return;
					}
				}
			}			
			
			request.setAttribute("goal", personGoal);
			
			List<Measure> measureTypes = new ArrayList<Measure>();
			measureTypes = client.getMeasureTypesGoal();
			
			request.setAttribute("measureTypes", measureTypes);
			getServletContext().getRequestDispatcher("/goal.jsp").forward(request, response);								
		}
		catch(Exception e){e.printStackTrace();}			
	}

}

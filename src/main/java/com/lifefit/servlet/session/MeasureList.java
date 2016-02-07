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

import com.lifefit.model.LifeStatus;
import com.lifefit.model.Measure;
import com.lifefit.rest.pcs.client.LifeFitClient;

@WebServlet("/Measure")
public class MeasureList extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public MeasureList() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			HttpSession session = request.getSession();
			LifeFitClient client = new LifeFitClient();
			
			String action = (request.getParameter("action") != null ? request.getParameter("action"): "");			
			String measureType = (request.getParameter("measureName") != null ? request.getParameter("measureName") : "");
			String measureValue = (request.getParameter("measureValue") != null ? request.getParameter("measureValue") : "0");	
			
			if(!measureType.equalsIgnoreCase("") && !measureType.equalsIgnoreCase("0")){
				if(action.equalsIgnoreCase("saveHP")){															
					//get stored idPerson from session
					int idPerson = (Integer)session.getAttribute("idPerson");
					
					LifeStatus lifeStatus = new LifeStatus();
					lifeStatus.setValue(Double.parseDouble(measureValue));
					
					//save new person health measure
					lifeStatus = client.savePersonHealthMeasure(lifeStatus, idPerson, measureType);				
					if(lifeStatus != null){
						//forward to home page			
						getServletContext().getRequestDispatcher("/Home").forward(request, response);
						return;
					}					 
				}
			}			
									
			List<Measure> measureTypes = new ArrayList<Measure>();
			measureTypes = client.getMeasureTypes();
			
			request.setAttribute("measureTypes", measureTypes);
			getServletContext().getRequestDispatcher("/measure.jsp").forward(request, response);										
		}
		catch(Exception e){e.printStackTrace();}	
	}

}

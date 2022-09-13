package com.gosaas.item;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.gosaas.constants.ProjectConstants;
import com.gosaas.database.Connect;
import com.gosaas.utils.Logger;

/**
 * Servlet implementation class LoadItem
 */
@WebServlet("/LoadItem")
public class LoadItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadItem() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    final String SELECT_ITEMS_SQL = "select * from items";
	    
	    JSONObject responseObject = new JSONObject();
	    JSONArray array = new JSONArray();
	    
		Logger logger = null;
	    
	    Connect conn = new Connect();
	    Connection db = conn.getConnection();
	    PreparedStatement preparedStatement;
	    
		try {
			logger = new Logger("Assignment", ProjectConstants.LOGS_PATH);
			preparedStatement = db.prepareStatement(SELECT_ITEMS_SQL);
			
			System.out.println(preparedStatement);
	        ResultSet rs = preparedStatement.executeQuery();
	         
	        while (rs.next()) {
	        	JSONObject record = new JSONObject();
	        	
	        	record.put("itemId", rs.getLong("itemId"));
        		record.put("itemName", rs.getString("itemName"));
        		record.put("itemClass", rs.getString("itemClass"));
        		record.put("itemDescription", rs.getString("itemDescription"));
		  		
		  		array.add(record);
	        }
	        
	        responseObject.put("Items_data", array);
	        responseObject.putIfAbsent("status", "success");
	        logger.info(SELECT_ITEMS_SQL);
	        	
		} catch (SQLException | NullPointerException e) {
			logger.error(e.getLocalizedMessage());
			System.out.println(e);
			responseObject.put("status", "error");
    		responseObject.put("message",
					e.getLocalizedMessage() == null ? e
							: e.getLocalizedMessage().contains("code 401:")
									? "Your session has been expired. Please launch Application from Oracle Cloud again"
									: e.getLocalizedMessage());
		}

		String jsonResponse = new Gson().toJson(responseObject);
		response.setContentType("application/json");
		response.getWriter().println(jsonResponse);
	}

}

package com.gosaas.cloud;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.gosaas.database.Connect;
import com.gosaas.utils.Logger;
import com.gosaas.constants.ProjectConstants;

/**
 * Servlet implementation class LoadData
 */
@WebServlet("/LoadData")
public class LoadData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadData() {
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
		
		JSONObject responseObject = new JSONObject();

		Logger logger = null;

		try {
			logger = new Logger("Assignment", ProjectConstants.LOGS_PATH);
			String userCredentials = ProjectConstants.CLOUD_USERNAME+":"+ProjectConstants.CLOUD_PASSWORD;
			String basicAuth = new String(Base64.getEncoder().encode(userCredentials.getBytes()));
			   
			URL obj = new URL(ProjectConstants.CLOUD_URL + "?" + ProjectConstants.CLOUD_QUERY);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("GET");
			   
			con.setRequestProperty ("Authorization", "Basic " + basicAuth);
			con.setRequestProperty("Content-Type", "application/json");
			   
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + ProjectConstants.CLOUD_URL);
			System.out.println("Response Code : " + responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer cloud_response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
				cloud_response.append(inputLine);
			}
			
			in.close();
			   
			   
			JSONParser parse = new JSONParser(); 
			JSONObject jsonobject = (JSONObject)parse.parse(cloud_response.toString());
			   
			JSONArray jsonarray = (JSONArray) jsonobject.get("items");
			
			final String INSERT_ITEMS_SQL = "INSERT INTO items" + "  (itemId, itemName, itemClass, itemDescription) VALUES " + " (?, ?, ?, ?);";
		    final String DELETE_ITEMS_SQL = "DELETE FROM items;";
		    
		    Connect conn = new Connect();
		    Connection db = conn.getConnection();
		    PreparedStatement preparedStatement = db.prepareStatement(INSERT_ITEMS_SQL);
		    PreparedStatement preparedStatementDefault = db.prepareStatement(DELETE_ITEMS_SQL);
		    
		    preparedStatementDefault.executeUpdate();
			   
			for(int i=0;i<jsonarray.size();i++) {
			    JSONObject jsonobject_item = (JSONObject)jsonarray.get(i);
			    
			    try {
			    	preparedStatement.setLong(1, (Long) jsonobject_item.get("ItemId"));
			    	preparedStatement.setString(2, (String) jsonobject_item.get("ItemNumber"));
			    	preparedStatement.setString(3, (String) jsonobject_item.get("ItemClass"));
			        preparedStatement.setString(4, (String) jsonobject_item.get("ItemDescription"));
	                System.out.println(preparedStatement);
			       
			        preparedStatement.executeUpdate();
			        
			        responseObject.putIfAbsent("status", "success");
			                
		    	} catch (SQLException e1) {
		    		logger.error(e1.getLocalizedMessage());
			    	System.out.println(e1);
			   		responseObject.put("status", "error");
			   		responseObject.put("message",
							e1.getLocalizedMessage() == null ? e1
									: e1.getLocalizedMessage().contains("code 401:")
											? "Your session has been expired. Please launch Application from Oracle Cloud again"
											: e1.getLocalizedMessage());
		    	}
		    }
			   
		} catch (Exception e) {
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

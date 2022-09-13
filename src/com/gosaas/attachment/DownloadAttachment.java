package com.gosaas.attachment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gosaas.constants.ProjectConstants;
import com.gosaas.database.Connect;
import com.gosaas.utils.Logger;

/**
 * Servlet implementation class DownloadAttachment
 */
@WebServlet("/DownloadAttachment")
public class DownloadAttachment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadAttachment() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String SELECT_ATTACHMENTS_SQL = "select * from attachment where attachmentId = ?";
		
		Logger logger = null;
	
	    Connect conn = new Connect();
	    Connection db = conn.getConnection();
	    PreparedStatement preparedStatement;
	    
		try {
			logger = new Logger("Assignment", ProjectConstants.LOGS_PATH);
			
			preparedStatement = db.prepareStatement(SELECT_ATTACHMENTS_SQL);
	        preparedStatement.setString(1, request.getParameter("attachmentId"));
			System.out.println(preparedStatement);
			
	        ResultSet rs = preparedStatement.executeQuery();
	        
		    while (rs.next()) {
		       	Blob attachmentFile = rs.getBlob("attachmentFile");
		       	
		       	InputStream is = attachmentFile.getBinaryStream();
		        byte[] bytes = new byte[1024];
		        int bytesRead;
		            
		        OutputStream os = response.getOutputStream();
		        while((bytesRead=is.read(bytes))!=-1){
		           	os.write(bytes, 0, bytesRead);
		        }
		        is.close();
		    }
		        
		} catch (SQLException e) {
			logger.error(e.getLocalizedMessage());
			System.out.println(e);
		}
	} 
			
}


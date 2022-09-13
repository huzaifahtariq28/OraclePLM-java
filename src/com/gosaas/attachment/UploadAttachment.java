package com.gosaas.attachment;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.gosaas.constants.ProjectConstants;
import com.gosaas.database.Connect;
import com.gosaas.utils.Logger;

/**
 * Servlet implementation class UploadAttachment
 */
@WebServlet("/UploadAttachment")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
public class UploadAttachment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadAttachment() {
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
		final String INSERT_ATTACHMENTS_SQL = "INSERT INTO attachment (attachmentName, itemId, attachmentFile, attachmentDescription) VALUES (?, ?, ?, ?);";
		
		Logger logger = null;
		
		String itemId = request.getParameter("itemId");
		String attachmentName = request.getParameter("attachmentName");
		String attachmentDescription = request.getParameter("attachmentDescription");
		Part part = request.getPart("attachmentFile");
		
		if (part!=null) {
			Connect conn = new Connect();
		    Connection db = conn.getConnection();
		    PreparedStatement preparedStatement;
		    try {
				logger = new Logger("Assignment", ProjectConstants.LOGS_PATH);
				
				InputStream is = part.getInputStream();
				
				preparedStatement = db.prepareStatement(INSERT_ATTACHMENTS_SQL);
				preparedStatement.setString(1, attachmentName);
	            preparedStatement.setString(2, itemId);
	            preparedStatement.setBlob(3, is);
	            preparedStatement.setString(4, attachmentDescription);
	            System.out.println(preparedStatement);
	            preparedStatement.executeUpdate();
	            response.sendRedirect("http://localhost:8080/TemplateProject/#attachments?itemId="+itemId);
	            
			} catch (SQLException e) {
				logger.error(e.getLocalizedMessage());
				System.out.println(e);
			}
		}
		
	}

}

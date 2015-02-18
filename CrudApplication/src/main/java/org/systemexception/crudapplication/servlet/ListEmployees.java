package org.systemexception.crudapplication.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.systemexception.crudapplication.pojo.Employees;

/**
 *
 * @author leo
 */
public class ListEmployees extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Employees employees = new Employees();
		try {
			out.println("<link href=\"resources/css/bootstrap.min.css\" rel=\"stylesheet\">");
			out.println("<h1>Employee List</h1>");
			// Start printing table
			out.println("<table class=\"table table-hover\">");
			out.println("<tr><th>Employee ID</th><th>Name</th><th>Last Name</th></tr>");
			for (int i = 0; i < employees.countEmployees(); i++) {
				String empID = employees.getEmpList().get(i).getEmpIdString();
				String empName = employees.getEmpList().get(i).getEmpName();
				String empLastName = employees.getEmpList().get(i).getEmpSurname();
				out.println("<tr><td>" + empID + "</td><td>" + empName + "</td><td>" + empLastName + "</td></tr>");
			}
			out.println("</table>");
			out.println("<hr>");
		} finally {
			out.close();
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}

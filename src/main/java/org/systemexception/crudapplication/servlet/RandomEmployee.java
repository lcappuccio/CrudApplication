package org.systemexception.crudapplication.servlet;

import org.systemexception.crudapplication.api.EmployeeDao;
import org.systemexception.crudapplication.impl.EmployeeDaoImpl;
import org.systemexception.crudapplication.pojo.Employee;
import org.systemexception.crudapplication.pojo.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leo
 */
public class RandomEmployee extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(RandomEmployee.class.getCanonicalName());
	private final EmployeeDao empDao = new EmployeeDaoImpl();

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
		LOG.log(Level.INFO, "request from {0}", request.getRemoteAddr());
		try {
			out.println(Util.PAGE_HEADER);
			out.println("<div class=\"container\">");
			out.println("<h2>Random Employee</h2><hr>");
			// Fetch random Employee details
			Random random = new Random();
			int randomId = random.nextInt(empDao.countEmployees());
			Employee employee = empDao.findById(randomId);
			out.println("Employee: " + employee.getEmpName() + " " + employee.getEmpSurname());
			out.println("</div>");
			out.println(Util.PAGE_END);
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

package org.systemexception.crudapplication.servlet;

import org.systemexception.crudapplication.exception.PojoMapperException;
import org.systemexception.crudapplication.model.Employees;
import org.systemexception.crudapplication.pojo.PojoMapper;
import org.systemexception.logger.impl.LoggerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author leo
 * @date 19/02/2015 23:04
 */
public class EmployeesJson extends HttpServlet {

	private static final org.systemexception.logger.api.Logger LOG = LoggerImpl.getFor(EmployeesJson.class);
	private final PojoMapper pojoMapper = new PojoMapper();

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 * @throws org.systemexception.crudapplication.exception.PojoMapperException
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, PojoMapperException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Employees employees = new Employees();
		String employeesJson = pojoMapper.employeesToJson(employees);
		LOG.info("request from " + request.getRemoteAddr());
		LOG.info("retrieved " + employees.countEmployees()+ " employees");
		try {
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet EmployeesJson</title>");
			out.println("</head>");
			out.println("<body>");
			out.println(employeesJson);
			out.println("</body>");
			out.println("</html>");
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
		try {
			processRequest(request, response);
		} catch (PojoMapperException ex) {
			LOG.error("Error in servlet " + this.getServletInfo(), ex);
		}
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
		try {
			processRequest(request, response);
		} catch (PojoMapperException ex) {
			LOG.error("Error in servlet " + this.getServletInfo(), ex);
		}
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

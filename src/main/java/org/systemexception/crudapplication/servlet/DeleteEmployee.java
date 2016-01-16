/**
 * @author leo
 * @date 07/03/2015 20:26
 */
/*
 * Copyright (C) 2015 leo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.systemexception.crudapplication.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.systemexception.crudapplication.api.EmployeeDao;
import org.systemexception.crudapplication.impl.EmployeeDaoImpl;
import org.systemexception.crudapplication.model.Employee;
import org.systemexception.crudapplication.model.Employees;
import org.systemexception.crudapplication.pojo.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteEmployee extends HttpServlet {

	private static final Logger LOG = LogManager.getLogger(DeleteEmployee.class);
	private final EmployeeDao empDao = new EmployeeDaoImpl();

	/**
	 * Returns a delete button with values to post
	 *
	 * @param empID
	 * @return html string with an ugly empID value
	 */
	private String getDeleteEmployeeButton(String empID) {
		return "<button type=\"submit\" class=\"btn btn-default\" "
				+ "name=\"empID\" value=\"" + empID + "\"" + ">Delete</button>";
	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	public void processRequest(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Employees employees = new Employees();
		LOG.info("request from " + request.getRemoteAddr());
		LOG.info("retrieved " + employees.countEmployees() + " employees");
		try {
			out.println(Constants.PAGE_HEADER);
			out.println("<div class=\"container\">");
			out.println("<h2>List Employees</h2><hr>");
			// Start printing table
			out.println("<form class=\"form-delete\" action=\"DeleteEmployee\" method=\"POST\">");
			out.println("<table class=\"table table-hover\">");
			out.println("<tr><th>Employee ID</th><th>Name</th><th>Last Name</th><th></th></tr>");
			for (int i = 0; i < employees.countEmployees(); i++) {
				String empID = String.valueOf(employees.getEmpList().get(i).getEmpId());
				String empName = employees.getEmpList().get(i).getEmpName();
				String empLastName = employees.getEmpList().get(i).getEmpSurname();
				out.println("<tr><td>" + empID + "</td><td>" + empName + "</td><td>" + empLastName + "</td>"
						+ "<td>" + getDeleteEmployeeButton(empID) + "</td></tr>");
			}
			out.println("</table>");
			out.println("</form>");
			out.println("</div>");
			out.println(Constants.PAGE_END);
		} finally {
			out.close();
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the
	// code.">

	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	public void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		String empID = request.getParameter("empID");
		Employee employee = new Employee((Integer.valueOf(empID)), null, null);
		empDao.deleteEmployee(employee);
		processRequest(request, response);
		LOG.info("Request delete for empID: " + empID);

	}

}

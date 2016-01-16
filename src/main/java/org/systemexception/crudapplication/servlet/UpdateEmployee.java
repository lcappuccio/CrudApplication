/**
 * @author leo
 * @date 08/03/2015 21:09
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

public class UpdateEmployee extends HttpServlet {

	private static final Logger LOG = LogManager.getLogger(UpdateEmployee.class);
	private final EmployeeDao empDao = new EmployeeDaoImpl();

	/**
	 * Returns a delete button with values to post
	 *
	 * @param empID
	 * @return
	 */
	private String getUpdateEmployeeButton(String empID) {
		return "<button type=\"submit\" class=\"btn btn-default\" "
				+ "name=\"empID\" value=\"" + empID + "\"" + ">Update</button>";
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
			out.println(Constants.PAGE_HEADER_UPDATE_EMPLOYEE);
			out.println("<div class=\"container\">");
			out.println("<h2>Update Employees</h2><hr>");
			// Start printing table
			out.println("<form class=\"form-update\">");
			out.println("<table class=\"table table-hover\" id=\"updateEmployeeTable\">");
			out.println("<tr><th>Employee ID</th><th>Name</th><th>Last Name</th><th></th></tr>");
			for (int i = 0; i < employees.countEmployees(); i++) {
				String empID = String.valueOf(employees.getEmpList().get(i).getEmpId());
				String empName = employees.getEmpList().get(i).getEmpName();
				String empLastName = employees.getEmpList().get(i).getEmpSurname();
				out.println("<tr><td><div class=\"empID\">" + empID + "</div></td>"
						+ "<td contenteditable='true'>" + empName + "</td>"
						+ "<td contenteditable='true'>" + empLastName + "</td>"
						+ "<td>" + getUpdateEmployeeButton(empID) + "</td></tr>");
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
		int empID = Integer.parseInt(request.getParameter("empID"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		Employee employee = new Employee(empID, firstName, lastName);
		empDao.updateEmployee(employee);
		LOG.info("Request update for empID: " + empID + ": " + firstName + "," + lastName);
		processRequest(request, response);
	}

}

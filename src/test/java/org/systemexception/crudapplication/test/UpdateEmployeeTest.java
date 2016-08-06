package org.systemexception.crudapplication.test;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.systemexception.crudapplication.dao.EmployeeDaoImpl;
import org.systemexception.crudapplication.model.Employee;
import org.systemexception.crudapplication.servlet.ServletConstants;
import org.systemexception.crudapplication.servlet.UpdateEmployee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author leo
 * @date 27/12/15 02:56
 */
public class UpdateEmployeeTest {

	private final EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
	private final Employee employee = new Employee(999, "Test", "Test");

	@Before
	public void setUp() {
		employeeDao.insertEmployeeWithId(employee);
	}

	@After
	public void tearDown() {
		employeeDao.deleteEmployee(employee);
	}

	@Test
	public void testForm() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		when(response.getWriter()).thenReturn(new PrintWriter(BadWorldTest.FILE_NAME));

		PrintWriter writer = new PrintWriter(BadWorldTest.FILE_NAME);

		new UpdateEmployee().doGet(request, response);

		writer.flush();
		assertTrue(FileUtils.readFileToString(new File(BadWorldTest.FILE_NAME), "UTF-8").contains("Update Employees"));
	}

	@Test
	public void testDelete() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		when(request.getParameter(ServletConstants.PARAMETER_EMP_ID.toString()))
				.thenReturn(String.valueOf(employee.getEmpId()));
		when(request.getParameter(ServletConstants.PARAMETER_EMP_FIRST_NAME.toString()))
				.thenReturn(String.valueOf("TestUpdatedName"));
		when(request.getParameter(ServletConstants.PARAMETER_EMP_LAST_NAME.toString()))
				.thenReturn(String.valueOf("TestUpdatedSurname"));
		when(response.getWriter()).thenReturn(new PrintWriter(BadWorldTest.FILE_NAME));

		PrintWriter writer = new PrintWriter(BadWorldTest.FILE_NAME);

		new UpdateEmployee().doPost(request, response);

		verify(request, atLeast(1)).getParameter(ServletConstants.PARAMETER_EMP_ID.toString());
		verify(request, atLeast(1)).getParameter(ServletConstants.PARAMETER_EMP_FIRST_NAME.toString());
		verify(request, atLeast(1)).getParameter(ServletConstants.PARAMETER_EMP_LAST_NAME.toString());
		writer.flush();
		assertTrue("TestUpdatedName".equals(employeeDao.findById(999).getEmpName()));
		assertTrue("TestUpdatedSurname".equals(employeeDao.findById(999).getEmpSurname()));
	}

}
/**
 * @author leo
 * @date 24/02/2015 22:59
 */
package org.systemexception.crudapplication.impl;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.systemexception.crudapplication.api.EmployeeDao;
import org.systemexception.crudapplication.model.Employee;
import org.systemexception.crudapplication.pojo.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

	private static final Logger LOG = LogManager.getLogger(EmployeeDaoImpl.class);
	private final HikariDataSource dataSource = new HikariDataSource();
	private Connection conn;

	/**
	 * Returns a connection type
	 *
	 * @return
	 * @throws SQLException
	 */
	private Connection getConnection() throws SQLException {
		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		/*
		 * TODO IP address, schema, user and password should be in external properties
		 * for testing use ("jdbc:mysql://192.168.1.3:3306/test");
		 * for testing also ("jdbc:mariadb://192.168.1.3:3306/test");
		 * for production use ("jdbc:mysql://localhost:3306/test");
		 * for production use ("jdbc:mysql://localhost:3306/test");
		 * dataSource.setUsername("test");
		 * dataSource.setPassword("test");
		 * for travis use:
		 * dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/myapp_test");
		 * dataSource.setUsername("travis");
		 * dataSource.setPassword(null);
		 */
		/*
		TEST
		 */
		dataSource.setJdbcUrl("jdbc:mysql://192.168.1.3:3306/test");
		dataSource.setUsername("test");
		dataSource.setPassword("test");
		/*
		TRAVIS
		 */
//		dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/myapp_test");
//		dataSource.setUsername("travis");
//		dataSource.setPassword(null);
		dataSource.setConnectionTimeout(5000);
		dataSource.setIdleTimeout(10000);
		dataSource.setMaximumPoolSize(4);
		dataSource.setAutoCommit(false);
		conn = dataSource.getConnection();
		return (conn);
	}

	@Override
	public Employee findById(int empId) {
		PreparedStatement pss = null;
		ResultSet rs = null;
		Employee emp = new Employee();
		try {
			conn = getConnection();
			pss = conn.prepareStatement(
					"SELECT EMPLOYEE_ID, EMPLOYEE_NAME, EMPLOYEE_SURNAME FROM EMPLOYEES WHERE EMPLOYEE_ID = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pss.setInt(1, empId);
			rs = pss.executeQuery();
			while (rs.next()) {
				emp.setEmpId(rs.getInt("EMPLOYEE_ID"));
				emp.setEmpName(rs.getString("EMPLOYEE_NAME"));
				emp.setEmpSurname(rs.getString("EMPLOYEE_SURNAME"));
			}
		} catch (SQLException e) {
			exceptionHandler(e);
		} finally {
			closeAll(conn, pss, rs);
		}
		return emp;
	}

	@Override
	public List<Employee> getAllEmployees() {
		PreparedStatement pss = null;
		ResultSet rs = null;
		List<Employee> empList = new ArrayList<Employee>();
		try {
			conn = getConnection();
			pss = conn.prepareStatement("SELECT EMPLOYEE_ID, EMPLOYEE_NAME, EMPLOYEE_SURNAME FROM EMPLOYEES",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pss.executeQuery();
			empList = resultSetToEmployeeList(rs);
		} catch (SQLException e) {
			exceptionHandler(e);
		} finally {
			closeAll(conn, pss, rs);
		}
		return empList;
	}

	@Override
	public List<Employee> findByName(String empName) {
		PreparedStatement pss = null;
		ResultSet rs = null;
		List<Employee> empList = new ArrayList<>();
		try {
			conn = getConnection();
			pss = conn
					.prepareStatement(
							"SELECT EMPLOYEE_ID, EMPLOYEE_NAME, EMPLOYEE_SURNAME FROM EMPLOYEES WHERE lower" +
									"(EMPLOYEE_NAME) LIKE ?",
							ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pss.setString(1, "%" + empName.toLowerCase(Constants.LOCALE) + "%");
			rs = pss.executeQuery();
			empList = resultSetToEmployeeList(rs);
		} catch (SQLException e) {
			exceptionHandler(e);
		} finally {
			closeAll(conn, pss, rs);
		}
		return empList;
	}

	@Override
	public int countEmployees() {
		PreparedStatement pss = null;
		ResultSet rs = null;
		int empCount = 0;
		try {
			conn = getConnection();
			pss = conn.prepareStatement("SELECT count(*) FROM EMPLOYEES", ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = pss.executeQuery();
			while (rs.next()) {
				empCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			exceptionHandler(e);
		} finally {
			closeAll(conn, pss, rs);
		}
		return empCount;
	}

	@Override
	public boolean insertEmployee(Employee emp) {
		boolean operationResult = false;
		PreparedStatement pss = null;
		ResultSet rs = null;
		int maxEmpId = 0;
		try {
			conn = getConnection();
			pss = conn.prepareStatement("SELECT max(EMPLOYEE_ID) AS MAXEMPID FROM EMPLOYEES",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pss.executeQuery();
			while (rs.next()) {
				maxEmpId = rs.getInt(1);
			}
			rs.close();
			pss.close();
			pss = conn.prepareStatement(
					"INSERT INTO EMPLOYEES (EMPLOYEE_ID,EMPLOYEE_NAME, EMPLOYEE_SURNAME) VALUES (?,?,?)",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pss.setInt(1, maxEmpId + 1);
			pss.setString(2, emp.getEmpName());
			pss.setString(3, emp.getEmpSurname());
			int countRows = pss.executeUpdate();
			if (countRows > 0) {
				operationResult = true;
				conn.commit();
			}
		} catch (SQLException e) {
			exceptionHandler(e);
		} finally {
			closeAll(conn, pss, rs);
		}
		return operationResult;
	}

	/**
	 * Insert employee with a given ID, used in test
	 *
	 * @param emp
	 * @return
	 */
	public boolean insertEmployeeWithId(Employee emp) {
		boolean operationResult = false;
		PreparedStatement pss = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pss = conn.prepareStatement(
					"insert into EMPLOYEES (EMPLOYEE_ID, EMPLOYEE_NAME, EMPLOYEE_SURNAME) values (?,?,?)",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pss.setInt(1, emp.getEmpId());
			pss.setString(2, emp.getEmpName());
			pss.setString(3, emp.getEmpSurname());
			int countRows = pss.executeUpdate();
			if (countRows > 0) {
				operationResult = true;
				conn.commit();
			}
		} catch (SQLException e) {
			exceptionHandler(e);
		} finally {
			closeAll(conn, pss, rs);
		}
		return operationResult;
	}

	@Override
	public boolean deleteEmployee(Employee emp) {
		boolean operationResult = false;
		PreparedStatement pss = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pss = conn.prepareStatement("DELETE FROM EMPLOYEES WHERE EMPLOYEE_ID = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pss.setInt(1, emp.getEmpId());
			int countRows = pss.executeUpdate();
			if (countRows > 0) {
				operationResult = true;
				conn.commit();
			}
		} catch (SQLException e) {
			exceptionHandler(e);
		} finally {
			closeAll(conn, pss, rs);
		}
		return operationResult;
	}

	@Override
	public boolean updateEmployee(Employee emp) {
		boolean operationResult = false;
		PreparedStatement pss = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pss = conn.prepareStatement("UPDATE EMPLOYEES SET EMPLOYEE_NAME = ?, EMPLOYEE_SURNAME = ? WHERE " +
							"EMPLOYEE_ID = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pss.setString(1, emp.getEmpName());
			pss.setString(2, emp.getEmpSurname());
			pss.setInt(3, emp.getEmpId());
			int countRows = pss.executeUpdate();
			if (countRows > 0) {
				operationResult = true;
				conn.commit();
			}
		} catch (SQLException e) {
			exceptionHandler(e);
		} finally {
			closeAll(conn, pss, rs);
		}
		return operationResult;
	}

	/**
	 * Quietly close all database resources
	 *
	 * @param conn
	 * @param pss
	 * @param rs
	 */
	private void closeAll(Connection conn, PreparedStatement pss, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pss != null) {
				pss.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			exceptionHandler(e);
		}
	}

	public boolean cleanTests() {
		boolean operationResult = false;
		PreparedStatement pss = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pss = conn.prepareStatement("DELETE FROM EMPLOYEES WHERE EMPLOYEE_NAME = 'TEST'",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pss.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			exceptionHandler(e);
		} finally {
			closeAll(conn, pss, rs);
		}
		return operationResult;
	}

	private List<Employee> resultSetToEmployeeList(ResultSet rs) throws SQLException {
		List<Employee> empList = new ArrayList<>();
		while (rs.next()) {
			Employee emp = new Employee();
			emp.setEmpId(rs.getInt("EMPLOYEE_ID"));
			emp.setEmpName(rs.getString("EMPLOYEE_NAME"));
			emp.setEmpSurname(rs.getString("EMPLOYEE_SURNAME"));
			empList.add(emp);
		}
		return empList;
	}

	/**
	 * Exception handler to log file
	 *
	 * @param exception
	 */
	private void exceptionHandler(SQLException exception) {
		LOG.error("Error in DAO\n{0}", exception);
	}
}

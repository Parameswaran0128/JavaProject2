package com.payrollSystem.java;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class getFunctions {
	protected static List<String> getEmployeeName(Connection connection, String employeeID) {
	    List<String> names = new ArrayList<>();
	    String query = "SELECT EmployeeName FROM Employee WHERE EmpID = ?";
	    
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, employeeID);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        
	        while (resultSet.next()) {
	            String name = resultSet.getString("EmployeeName");
	            names.add(name);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return names;
	}

		
		protected static List<String> getDept(Connection connection,String EmpID) {
			String Dept = null;
			String query = "SELECT Department FROM Employee WHERE EmpID = ?";
			List<String> DeptList = new ArrayList<>();
			try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setString(1, EmpID);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Dept = resultSet.getString("Department");
					DeptList.add(Dept);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
//			System.out.println(name);
			return DeptList;
		}
		
		protected static List<String> getDesignation(Connection connection,String EmpID) {
			String Design = null;
			String query = "SELECT Designation FROM Employee WHERE EmpID = ?";
			List<String> designList = new ArrayList<>();
			try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setString(1, EmpID);
				
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					Design = resultSet.getString("Designation");
					designList.add(Design);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
//			System.out.println(name);
			return designList;
		}
		
		protected static List<Map<String, Object>> getEmployeeLeaveDetails(Connection connection, String employeeID, LocalDate fromDate, LocalDate toDate) {
		    List<Map<String, Object>> leaveDetails = new ArrayList<>();
		    String query = "SELECT * FROM LeaveDetail WHERE EmpID = ? AND FromDate >= ? AND ToDate <= ?";
		    
		    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		        preparedStatement.setString(1, employeeID);
		        preparedStatement.setDate(2, Date.valueOf(fromDate));
		        preparedStatement.setDate(3, Date.valueOf(toDate));
		        
		        ResultSet resultSet = preparedStatement.executeQuery();
		        
		        while (resultSet.next()) {
		            Map<String, Object> leaveDetail = new HashMap<>();
		            leaveDetail.put("LeaveID", resultSet.getString("LeaveID"));
		            leaveDetail.put("TypeOfLeave", resultSet.getString("TypeOfLeave"));
		            leaveDetail.put("FromDate", resultSet.getDate("FromDate").toLocalDate());
		            leaveDetail.put("ToDate", resultSet.getDate("ToDate").toLocalDate());
		            leaveDetails.add(leaveDetail);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    
		    return leaveDetails;
		}
		
		protected static double getBasicSalary(Connection conn, String empId) {
		    double basicSalary = 0.0;
		    String query = "SELECT sd.BasicPay " +
	                   "FROM Employee e " +
	                   "JOIN SalaryDetails sd ON e.Designation = sd.Designation AND e.Department = sd.Department " +
	                   "WHERE e.EmployeeID = ?";
		    try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
		        preparedStatement.setString(1, empId);
		        ResultSet resultSet = preparedStatement.executeQuery();
		        if (resultSet.next()) {
		            basicSalary = resultSet.getDouble("BasicPay");                  
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
//		    System.out.println(basicSalary);
		    return basicSalary;
		}

		protected static List<Map<String, Object>> getLeaveSalaryDetails(Connection connection,String Empid) {
			List<Map<String, Object>> leaveSalaryList = new ArrayList<>();
			String query =  "SELECT * FROM LeaveSalary ls " + "JOIN LeaveDetail ld ON ls.LeaveID = ld.LeaveID " + "WHERE ld.EmpID = ?";
			Map<String, Object> leaveSalaryMap = new HashMap<>();
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setString(1, Empid);
				ResultSet resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()) {
					leaveSalaryMap.put("LeaveSalaryid", resultSet.getString("LeaveSalaryid"));
	                leaveSalaryMap.put("LeaveID", resultSet.getString("LeaveID"));
	                leaveSalaryMap.put("noofDays", resultSet.getInt("noofDays"));
	                leaveSalaryMap.put("LeaveSalary", resultSet.getDouble("LeaveSalary"));
	                
	                leaveSalaryList.add(leaveSalaryMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return leaveSalaryList;
		}
		
		protected static double NytShiftSalary(Connection connection,LocalDate fromDate,LocalDate toDate,String empID) {
			
			double nytShiftSalary = 0.0;
			

	        String query = "SELECT COUNT(*) AS NytShiftCount, SUM(NytShiftAmt) AS TotalNytShiftAmount FROM NytShiftDetail WHERE EmpID = ? AND NytShiftDate BETWEEN ? AND ?";
;
	        
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        	preparedStatement.setString(1, empID);
	            preparedStatement.setDate(2, Date.valueOf(fromDate));
	            preparedStatement.setDate(3, Date.valueOf(toDate));
	            
	            
	            try(ResultSet empResultSet = preparedStatement.executeQuery()) {
					empResultSet.next();
					int NytShiftCount = empResultSet.getInt("NytShiftCount");
					nytShiftSalary = empResultSet.getDouble("TotalNytShiftAmount");
					
					if (NytShiftCount == 0) {
						return nytShiftSalary;
					}
					else {
						return nytShiftSalary;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return nytShiftSalary;
				}
	   
	        	} catch (SQLException e1) {
	        			// TODO Auto-generated catch block
	        			e1.printStackTrace();
	        	}
			return nytShiftSalary;
		}


			
}

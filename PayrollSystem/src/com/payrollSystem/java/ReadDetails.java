package com.payrollSystem.java;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.sql.ResultSetMetaData;

public class ReadDetails {
	protected static List<Map<String, Object>> DbToHashMap(Connection connection,String tableName){
    	List<Map<String, Object>> tableDataList = new ArrayList<>();
    	String sqlQuery = "SELECT * FROM " + tableName;
		try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
				ResultSet resultSet = preparedStatement.executeQuery()) {
				ResultSetMetaData metaData  = resultSet.getMetaData();
				int columnCount = metaData.getColumnCount();
			while(resultSet.next()) {
				Map<String, Object> dataMap = new HashMap<>();
				for(int Column =1;Column <= columnCount;Column++) {
					String columnName = metaData.getColumnName(Column);
					Object columnValue = resultSet.getObject(Column);
					dataMap.put(columnName, columnValue);
				}
				
				tableDataList.add(dataMap);
			}
//			System.out.println(tableDataList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return tableDataList;
	}
	
	protected static List<Map<String, Object>> ReadSpecificEmpDetails(Connection connection, String tableName, String empId) {
	    List<Map<String, Object>> tableDataList = new ArrayList<>();
	    String sqlQuery = "SELECT * FROM " + tableName + " WHERE EmpID = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
	        preparedStatement.setString(1, empId);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            ResultSetMetaData metaData = resultSet.getMetaData();
	            int columnCount = metaData.getColumnCount();
	            while (resultSet.next()) {
	                Map<String, Object> dataMap = new HashMap<>();
	                for (int column = 1; column <= columnCount; column++) {
	                    String columnName = metaData.getColumnName(column);
	                    Object columnValue = resultSet.getObject(column);
	                    dataMap.put(columnName, columnValue);
	                }
	                tableDataList.add(dataMap);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
//	    System.out.println(tableDataList);
	    return tableDataList;
	}
	
	protected static List<Map<String, Object>> ReadSpecificEmployeeDetails(Connection connection, String empId) {
	    List<Map<String, Object>> tableDataList = new ArrayList<>();
	    String sqlQuery = "SELECT * FROM  Employee WHERE EmployeeID = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
	        preparedStatement.setString(1, empId);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            ResultSetMetaData metaData = resultSet.getMetaData();
	            int columnCount = metaData.getColumnCount();
	            while (resultSet.next()) {
	                Map<String, Object> dataMap = new HashMap<>();
	                for (int column = 1; column <= columnCount; column++) {
	                    String columnName = metaData.getColumnName(column);
	                    Object columnValue = resultSet.getObject(column);
	                    dataMap.put(columnName, columnValue);
	                }
	                tableDataList.add(dataMap);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
//	    System.out.println(tableDataList);
	    return tableDataList;
	}
	
	protected static List<Map<String, Object>> ReadSpecificMonthSalaryDetails(Connection connection, String tableName, int Month, int Year) {
	    List<Map<String, Object>> tableDataList = new ArrayList<>();
	    String sqlQuery = "SELECT * FROM " + tableName + " WHERE Month = ? AND Year = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
	        preparedStatement.setInt(1, Month);
	        preparedStatement.setInt(2,Year);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            ResultSetMetaData metaData = resultSet.getMetaData();
	            int columnCount = metaData.getColumnCount();
	            while (resultSet.next()) {
	                Map<String, Object> dataMap = new HashMap<>();
	                for (int column = 1; column <= columnCount; column++) {
	                    String columnName = metaData.getColumnName(column);
	                    Object columnValue = resultSet.getObject(column);
	                    dataMap.put(columnName, columnValue);
	                }
	                tableDataList.add(dataMap);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
//	    System.out.println(tableDataList);
	    return tableDataList;
	}
	
	protected static List<Map<String, Object>> ReadSpecificPeriodDetails(Connection connection, String tableName, LocalDate fromDate,LocalDate toDate) {
	    List<Map<String, Object>> tableDataList = new ArrayList<>();
	    String sqlQuery = "SELECT * FROM " + tableName + " WHERE NytShiftDate BETWEEN ? AND ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
	        preparedStatement.setDate(1, Date.valueOf(fromDate));
	        preparedStatement.setDate(2,Date.valueOf(toDate));
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            ResultSetMetaData metaData = resultSet.getMetaData();
	            int columnCount = metaData.getColumnCount();
	            while (resultSet.next()) {
	                Map<String, Object> dataMap = new HashMap<>();
	                for (int column = 1; column <= columnCount; column++) {
	                    String columnName = metaData.getColumnName(column);
	                    Object columnValue = resultSet.getObject(column);
	                    dataMap.put(columnName, columnValue);
	                }
	                tableDataList.add(dataMap);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
//	    System.out.println(tableDataList);
	    return tableDataList;
	}
	protected static List<Map<String, Object>> ReadSpecificLeavePeriodDetails(Connection connection, String tableName, LocalDate fromDate,LocalDate toDate) {
	    List<Map<String, Object>> tableDataList = new ArrayList<>();
	    String sqlQuery = "SELECT * FROM " + tableName + " WHERE FromDate >= ? AND ToDate <= ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
	        preparedStatement.setDate(1, Date.valueOf(fromDate));
	        preparedStatement.setDate(2,Date.valueOf(toDate));
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            ResultSetMetaData metaData = resultSet.getMetaData();
	            int columnCount = metaData.getColumnCount();
	            while (resultSet.next()) {
	                Map<String, Object> dataMap = new HashMap<>();
	                for (int column = 1; column <= columnCount; column++) {
	                    String columnName = metaData.getColumnName(column);
	                    Object columnValue = resultSet.getObject(column);
	                    dataMap.put(columnName, columnValue);
	                }
	                tableDataList.add(dataMap);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
//	    System.out.println(tableDataList);
	    return tableDataList;
	}
	
	protected static List<Map<String, Object>> ReadCompleteSalaryDetails(Connection connection,String EmpId){
    	List<Map<String, Object>> tableDataList = new ArrayList<>();
    	 String sqlQuery = "SELECT ns.NetSalaryID, ns.TotalEarning, ns.TotalDeduction, ns.netSalary, es.SalaryID, es.EmpId, es.BPAndDA, es.HRA, es.Conveyance, es.ProvidentFund, es.ESI, es.Loan, es.ProfessionTax, es.TDSOrIT, es.Month, es.Year,es.leaveSalary,es.nytShiftSalary " +
    			 			"FROM netSalaryDetails ns " +
                 "JOIN EmpSalary es ON ns.salaryID = es.SalaryID AND es.EmpID = ? ";
    	 try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
 	        preparedStatement.setString(1, EmpId);
 	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
 	            ResultSetMetaData metaData = resultSet.getMetaData();
 	            int columnCount = metaData.getColumnCount();
 	            while (resultSet.next()) {
 	                Map<String, Object> dataMap = new HashMap<>();
 	                for (int column = 1; column <= columnCount; column++) {
 	                    String columnName = metaData.getColumnName(column);
 	                    Object columnValue = resultSet.getObject(column);
 	                    dataMap.put(columnName, columnValue);
 	                }
 	                tableDataList.add(dataMap);
 	            }
 	        }
 	       
 	    } catch (SQLException e) {
 	        e.printStackTrace();
 	    }
    	 System.out.println(tableDataList);
    	return tableDataList;
	}
	
	protected static List<Map<String, Object>> ReadMonthSalaryDetails(Connection connection,String EmpId,int month,int year){
    	List<Map<String, Object>> tableDataList = new ArrayList<>();
    	 String sqlQuery = "SELECT ns.NetSalaryID, ns.TotalEarning, ns.TotalDeduction, ns.netSalary, es.SalaryID, es.EmpId, es.BPAndDA, es.HRA, es.Conveyance, es.ProvidentFund, es.ESI, es.Loan, es.ProfessionTax, es.TDSOrIT, es.Month, es.Year ,es.leaveSalary,es.nytShiftSalary " +
    			 			"FROM netSalaryDetails ns " +
                 "JOIN EmpSalary es ON ns.salaryID = es.SalaryID AND es.EmpID = ? AND es.Month = ? AND es.Year = ?";
    	 try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
 	        preparedStatement.setString(1, EmpId);
 	        preparedStatement.setInt(2, month);
 	        preparedStatement.setInt(3, year);
 	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
 	            ResultSetMetaData metaData = resultSet.getMetaData();
 	            int columnCount = metaData.getColumnCount();
 	            while (resultSet.next()) {
 	                Map<String, Object> dataMap = new HashMap<>();
 	                for (int column = 1; column <= columnCount; column++) {
 	                    String columnName = metaData.getColumnName(column);
 	                    Object columnValue = resultSet.getObject(column);
 	                    dataMap.put(columnName, columnValue);
 	                }
 	                tableDataList.add(dataMap);
 	            }
 	        }
 	       
 	    } catch (SQLException e) {
 	        e.printStackTrace();
 	    }
//    	 System.out.println(tableDataList);
    	return tableDataList;
	}
	
	protected static List<Map<String, Object>> ReadLeaveDetails(Connection connection,String empId) {
		List<Map<String, Object>> tableDataList = new ArrayList<>();
		String sqlQuery = "SELECT l.LeaveID, l.EmpID, l.TypeOfLeave, l.FromDate, l.ToDate, ls.LeaveSalaryid, ls.noofDays, ls.LeaveSalary " +
                "FROM LeaveDetail l " +
                "JOIN LeaveSalary ls ON l.LeaveID = ls.LeaveID WHERE l.EmpID = ?";


   	 try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
	        preparedStatement.setString(1, empId);
	      
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            ResultSetMetaData metaData = resultSet.getMetaData();
	            int columnCount = metaData.getColumnCount();
	            while (resultSet.next()) {
	                Map<String, Object> dataMap = new HashMap<>();
	                for (int column = 1; column <= columnCount; column++) {
	                    String columnName = metaData.getColumnName(column);
	                    Object columnValue = resultSet.getObject(column);
	                    dataMap.put(columnName, columnValue);
	                }
	                tableDataList.add(dataMap);
	            }
	        }
	       
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
//   	 System.out.println(tableDataList);
   	 return tableDataList;
	}
	
	
	

}


package com.payrollSystem.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class WriteDetails {
	public static void insertData(Connection connection,String tableName,List<Map<String, Object>> dataList) {
		String sqlQuery = GenerateInsertQuery(tableName, dataList.get(0)) ;
		try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
//			System.out.println("Hello1");
//			int j = 0;
			for(Map<String, Object> map : dataList) {
				setParameter(preparedStatement, map);
				preparedStatement.addBatch();

			}
			int[] result = preparedStatement.executeBatch();
			System.out.println("Insert Successful");
		}
		catch (SQLException e) {
			System.err.println(e);
		}
	}
	private static String GenerateInsertQuery(String tableName,Map<String,Object> MapData) {
		StringBuilder insertQueryBuilder = new StringBuilder("INSERT INTO " + tableName + "(");
		for(String columnName : MapData.keySet()) {
			insertQueryBuilder.append(columnName).append(",");
		}
		insertQueryBuilder.deleteCharAt(insertQueryBuilder.length()-1);
		insertQueryBuilder.append(") VALUES (");
		for(int i=0;i<MapData.size();i++) {
			insertQueryBuilder.append("?,");
		}
		insertQueryBuilder.deleteCharAt(insertQueryBuilder.length()-1);
		insertQueryBuilder.append(")");
//		System.out.println(insertQueryBuilder.toString());
		return insertQueryBuilder.toString();
	}
//	private static 	void setParameter(PreparedStatement preparedStatement,Map<String, Object> MapData)throws SQLException{
//		int index = 1;
////		for(Object val:MapData.values()) {
////			
//////			if(val instanceof Boolean) {
//////				preparedStatement.setBoolean(index, (Boolean) val);
//////			}else {				
//////			}
//////			preparedStatement.setObject(index, (val instanceof Boolean) ? ((Boolean) val ? 1 : 0) : val);
////			preparedStatement.setInt(index, (Boolean) val ? 1 : 0);
////			index++;
////		}
//		for (Object val : MapData.values()) {
//	        preparedStatement.setObject(index, val);
//	        index++;
//	    }
//		
//	}
	
	private static void setParameter(PreparedStatement preparedStatement, Map<String, Object> MapData) throws SQLException {
	    int index = 1;
	    for (Object val : MapData.values()) {
	        if (val instanceof Boolean) {
	            preparedStatement.setBoolean(index, (Boolean) val);
	        } else {
	            preparedStatement.setObject(index, val);
	        }
	        index++;
	    }
	}





}

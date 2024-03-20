package com.payrollSystem.java;

import java.io.FileWriter;
import java.util.Map;

import com.google.gson.Gson;

public class jsonFileCreation {
		
	protected static void createJsonFile(String filePath,Map<String, Object> Mapdata) {
		Gson gson = new Gson();
		String json = gson.toJson(Mapdata);
		try (FileWriter writer = new FileWriter(filePath)) {
			writer.write(json);
			System.out.println("File Creation Successful");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

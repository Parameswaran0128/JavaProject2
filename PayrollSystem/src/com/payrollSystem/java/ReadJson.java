package com.payrollSystem.java;

import java.io.FileReader;
import java.io.Reader;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class ReadJson {
    protected static List<Map<String, Object>> JsonFileReader(String filepath,String TableName){
        List<Map<String, Object>> MapList = new ArrayList<>();
        // String filePath = "C:\\Paraman\\Study\\JavaProject\\Payroll\\src\\JSON\\User.json";
        try(Reader reader = new FileReader(filepath)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray UserArrayData = jsonObject.getAsJsonArray(TableName);
            MapList =  JsonArrayToMapList(UserArrayData);
             System.out.println(MapList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MapList;
    }
    private static Map<String,Object> josnToMap(JsonObject jsonObject){
        Map<String,Object> map = new HashMap<>();
        Set<Map.Entry<String,JsonElement>> entrySet = jsonObject.entrySet();

        for (Map.Entry<String,JsonElement> entry : entrySet){
            String key = entry.getKey();
            JsonElement value = entry.getValue();
            if(value.isJsonObject()){
                map.put(key, josnToMap(value.getAsJsonObject()));
            }
            else if(value.isJsonArray()){
                map.put(key, jsonArraytoList(value.getAsJsonArray()));
            }  
            else{
                map.put(key, value.isJsonNull() ? null : value.getAsString());
            }
        }
        return map;
    }

    private static List<Map<String,Object>> JsonArrayToMapList(JsonArray jsonArray){

        List<Map<String,Object>>  ListMap = new ArrayList<>();

        for(JsonElement jsonElement : jsonArray){
            if (jsonElement.isJsonObject()) {
                Map<String,Object> map = josnToMap(jsonElement.getAsJsonObject());
                ListMap.add(map);
            }
        }

        return ListMap ;
    }

    private static List<Object> jsonArraytoList(JsonArray jsonArray){
        List<Object> Jsonlist = new ArrayList<>();

        for(JsonElement ListinJson : jsonArray){
            if(ListinJson.isJsonObject()){
                Jsonlist.add(josnToMap(ListinJson.getAsJsonObject()));
            }else if(ListinJson.isJsonArray()){
                Jsonlist.add(jsonArraytoList(ListinJson.getAsJsonArray()));
            }
            else{
                Jsonlist.add(ListinJson.isJsonNull() ? null : ListinJson.getAsString());
            }
        }

        return Jsonlist;
    } 
    
    
}


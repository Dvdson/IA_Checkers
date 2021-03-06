package main;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonChecker {
	
	public static boolean isOK(JsonObject W){
		if(W == null) return false;
		if(W.has("memory")){
			if(W.get("memory").isJsonArray()){
				for (int i = 0; i < W.get("memory").getAsJsonArray().size(); i++) {
					if((W.get("memory").getAsJsonArray().get(i).getAsJsonObject().has("input") &&
						W.get("memory").getAsJsonArray().get(i).getAsJsonObject().has("output"))){
						if(W.get("memory").getAsJsonArray().get(i).getAsJsonObject().get("input").isJsonArray()){
							if(W.get("memory").getAsJsonArray().get(i).getAsJsonObject().get("input").getAsJsonArray().size() == 32){
								for (int j = 0; j < 32; j++) {
									if(W.get("memory").getAsJsonArray().get(i).getAsJsonObject().get("input").getAsJsonArray().get(j).isJsonPrimitive()){
										if(W.get("memory").getAsJsonArray().get(i).getAsJsonObject().get("input").getAsJsonArray().get(j).getAsJsonPrimitive().isNumber()){}
										else return false;// if input[j] attribute is not a Number
									}else return false;// if input[j] attribute is not a primitive
								}
							}else return false;// if input attribute do not has size of 32
						}else return false;// if input attribute is not a JsonArray 
						if(W.get("memory").getAsJsonArray().get(i).getAsJsonObject().get("output").isJsonPrimitive()){
							if(W.get("memory").getAsJsonArray().get(i).getAsJsonObject().get("output").getAsJsonPrimitive().isNumber()){}
							else return false;// if output attribute is not a Number
						}else return false;// if output attribute is not a primitive
					}else return false;// if do not have "input" and "output"  attribute
				}
			}else return false;// if memory attribute do not is a JsonArray
		}else return false;// if do not have "memory"  attribute
		
		if(W.has("pesos")){
			if(W.get("pesos").isJsonArray()){
				if(W.get("pesos").getAsJsonArray().size() == 33){
					
				}else return false;// if do not have "memory"  attribute
			}else return false;// if do not have "memory"  attribute
		}else return false;// if do not have "memory"  attribute
		
		return true;
	}

	public static Integer hasInput(JsonArray memory,JsonObject input){
		ArrayList<Integer> aux = new Gson().fromJson(input.get("input").getAsJsonArray(), ArrayList.class);
		for (int i = 0; i < memory.size(); i++) {			
			ArrayList<Integer> input_memo = new Gson().fromJson(memory.get(i).getAsJsonObject().get("input").getAsJsonArray(), ArrayList.class);
			
			if(input_memo.equals(aux)) return i;
		}
		return memory.size();
		
	}

}

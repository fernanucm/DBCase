package Presentacion.Theme;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Theme {

	private static ArrayList<String> themes;
	private static String current;
	private static HashMap<String,Color> colors;
	
	public Theme(){}
	
	public static void loadThemes(){
		colors = new HashMap<String,Color>();
		themes = new ArrayList<String>();
		listFilesForFolder(new File("./themes/"));
	}
	
	private static void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles())
	    	if (fileEntry.isDirectory()) listFilesForFolder(fileEntry);
	        else  themes.add(fileEntry.getName().split(".json")[0]); 
	}

	
	
	@SuppressWarnings("unchecked")
	private static void loadTheme() {
		JSONParser parser = new JSONParser();
        try {
        	JSONObject obj = (JSONObject) parser.parse(new FileReader("./themes/"+current+".json"));
        	JSONObject jsoncolors = (JSONObject) obj.get("colors");
			Set<String> key = jsoncolors.keySet();
        	for(String s : key) {
        		JSONArray color = (JSONArray) jsoncolors.get(s);
        		colors.put(s, new Color(Integer.parseInt(color.get(0).toString()), Integer.parseInt(color.get(1).toString()), Integer.parseInt(color.get(2).toString())));
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void changeTheme(String name) {
		if(themes.contains(name)) {
			current = name;
			loadTheme();
		}
	}
	
	public String getThemeName(){
		return current;
	}
	
	public ArrayList<String> getAvaiableThemes(){
		return themes;
	}
	/*
	 * Getters de los colores
	 * */
	public Color main() {
		return colors.get("main");
	}
	public Color background() {
		return colors.get("background");
	}
	public Color entity() {
		return colors.get("entity");
	}
	public Color atribute() {
		return colors.get("atribute");
	}
	public Color relation() {
		return colors.get("relation");
	}
	public Color fontColor() {
		return colors.get("fontColor");
	}
	public Color lines() {
		return colors.get("lines");
	}
	public Color control() {
		return colors.get("control");
	}
	public Color codeBackground() {
		return colors.get("codeBackground");
	}
	public Color SelectionBackground() {
		return colors.get("SelectionBackground");
	}
	
}

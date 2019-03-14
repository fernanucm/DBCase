package modelo.tools;

public class HTMLUtils {
	
	public static String toItalic(String s){
		String aux = "<i><font face=\"Sans Serif\" size=\"3\">"+s+"</font></i>";
		return aux;
	}
	
	public static String toBold(String s){
		String aux = "<b><font face=\"Sans Serif\" size=\"3\">"+s+"</font></b>";
		return aux;	
	}
	
	public static String toPlain(String s){
		String aux = "<font face=\"Sans Serif\" size=\"3\">"+s+"</font>";
		return aux;	
	}
	
	public static String toRedColor(String s){
		String aux = "<font color=\"#FF0000\"><font face=\"Sans Serif\" size=\"3\">"+s+"</font></font>";
		return aux;	
	}
	
	public static String toGreenColor(String s){
		String aux = "<font color=\"#008000\"><font face=\"Sans Serif\" size=\"3\">"+s+"</font></font>";
		return aux;	
	}
	
	public static String toBlueColor(String s){
		String aux = "<font color=\"#0000FF\"><font face=\"Sans Serif\" size=\"3\">"+s+"</font></font>";
		return aux;	
	}
	
	public static String toYellowColor(String s){
		String aux = "<font color=\"#FFCC00\"><font face=\"Sans Serif\" size=\"3\">"+s+"</font></font>";
		return aux;	
	}
	
	
	public static String newLine(){
		return "<br />";	
	}
	
	
	
	

	
}

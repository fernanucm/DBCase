package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Prueba {

	public static void main (String[] args) {
		System.out.println();
		System.out.println("     CONECTOR ORACLE");
		System.out.println("     ---------------");
		System.out.println();

//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//			return;
//		}
	    
        try {
        	System.out.println("Declarando conector...");
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("Conector declarado");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
	    DriverManager.setLoginTimeout(10);
	    
	    try {
	    	System.out.println("Abriendo la conexión...");
	    	Connection connTANIA;
			connTANIA = DriverManager.getConnection(
					"jdbc:oracle:thin:ges00/ges00@tania.fdi.ucm.es:1521:db003");
		    connTANIA.setAutoCommit(false);
		    System.out.println("Conexión abierta");
		    
		    Statement stmtTANIA = connTANIA.createStatement();

		    String query = "SELECT table_name FROM user_tables";
		    System.out.println("Realizando consulta: " + query);
		    ResultSet rs = stmtTANIA.executeQuery(query);
		    System.out.println("Resultados obtenidos:");
		    while (rs.next()){
		    	System.out.println("    " + rs.getString("table_name"));
		    	System.out.println("Eliminando tabla...");
		    	try {
		    		Statement stBorrar = connTANIA.createStatement();
		    		stBorrar.executeUpdate("DROP TABLE " + rs.getString("table_name") +
		    				" CASCADE CONSTRAINTS");
		    	}catch (SQLException e) {
		    		System.out.println("Error en el borrado:");
		    		e.printStackTrace();
		    	}
		    }
		    System.out.println();
		    
		    System.out.println("Cerrando conexión...");   
		    stmtTANIA.close();
		    connTANIA.close();
		    System.out.println("Conexión cerrada");
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println();
		System.out.println("Hasta pronto");
	}
	
}

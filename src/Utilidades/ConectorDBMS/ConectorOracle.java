package Utilidades.ConectorDBMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import LogicaNegocio.Servicios.Enumerado;
import LogicaNegocio.Servicios.Tabla;
import Presentacion.Lenguajes.Lenguaje;
import Utilidades.HTMLUtils;

/**
 * Conecta la aplicación con un gestor de bases de datos Oracle
 * 
 * @author Denis Cepeda
 */
public class ConectorOracle extends ConectorDBMS {

	private Connection _conexion;
	@Override
	public void abrirConexion(String ruta, String usuario, String password)
			throws SQLException {
		String driver = "oracle.jdbc.OracleDriver";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.err.println(Lenguaje.getMensaje(Lenguaje.NO_CONECTOR));
			e.printStackTrace();
			
			return;
		}
		
		int rompe = ruta.lastIndexOf("#");
		String database = "";
		if (rompe > 0){
			database = ":" + ruta.substring(rompe+1);
			ruta = ruta.substring(0, rompe);
		}
		
		//_conexion = DriverManager.getConnection(ruta, usuario, password);
		_conexion = DriverManager.getConnection("jdbc:oracle:thin:" + usuario + 
					"/" + password +  "@" + ruta + database); 
		
		_conexion.setAutoCommit(false);
		
		if(!_conexion.isClosed())
			System.out.println("Conectado correctamente a '" + ruta + "' usando TCP/IP...");
	}

	@Override
	public void cerrarConexion() throws SQLException {
		if(_conexion != null)
			_conexion.close();
	}

	@Override
	public void ejecutarOrden(String orden) throws SQLException {
		// Si acaba en ;, quitarlo
		int fin = orden.indexOf(";");
		if (fin >= 0){
			orden = orden.substring(0,fin);
		}
		
		Statement st = _conexion.createStatement();
		st.executeUpdate(orden);
		st.close();
	}

	@Override
	public String obtenerCodigoCreacionTabla(Tabla t) {
		// Crear la tabla
		String codigo = "CREATE TABLE "+t.getNombreTabla()+"(";
		
		// Para cada atributo...
		Vector<String[]> atributos = t.getAtributos();
		for (int i=0;i<atributos.size();i++){
			if (i>0) codigo+=", ";
			//metemos el atributo
			codigo += atributos.elementAt(i)[0];
			// metemos el dominio
			codigo+=" "+equivalenciaTipoOracle(atributos.elementAt(i)[1]);
			
			// not null
			if (atributos.elementAt(i)[4].equalsIgnoreCase("1"))
				codigo+= " NOT NULL";
		}
		//cerramos la creacion de la tabla
		codigo+=");\n";
		return codigo;
	}

	@Override
	public String obtenerCodigoCreacionTablaHTML(Tabla t) {
		// Crear la tabla
		String codigo=HTMLUtils.toRedColor("CREATE TABLE ")+t.getNombreTabla()+"(";
		
		// Para cada atributo...
		Vector<String[]> atributos = t.getAtributos();
		for (int i=0;i<atributos.size();i++){
			if (i>0) codigo+=", ";
			//metemos el atributo
			codigo+=atributos.elementAt(i)[0];
			//metemos el dominio
			String dominio = equivalenciaTipoOracle(atributos.elementAt(i)[1]);
			codigo+=" "+HTMLUtils.toRedColor(dominio);
			
			// Not null
			if (atributos.elementAt(i)[4].equalsIgnoreCase("1"))
				codigo+= HTMLUtils.toRedColor(" NOT NULL");
		}
		//cerramos la creacion de la tabla
		codigo+=")" + ";"+HTMLUtils.newLine()+HTMLUtils.newLine();
		return codigo;
	}

	@Override
	public String obtenerCodigoClavesTabla(Tabla t) {
		String codigo="";
		
		//si tiene claves primarias, las añadimos.
		Vector<String[]> primaries = t.getPrimaries();
		if (!primaries.isEmpty()){
			codigo+="ALTER TABLE " + t.getNombreTabla() + 
					" ADD CONSTRAINT " + t.getNombreTabla() + "_pk" + 
					" PRIMARY KEY (";
			for (int i=0;i<primaries.size();i++){
				if (i>0)codigo+=", ";
				codigo+=primaries.elementAt(i)[0];
			}
			codigo+=");\n";
		}
		
		//si tiene claves foraneas:
		Vector<String[]> foreigns = t.getForeigns();
		if(!foreigns.isEmpty()){
			for (int j=0;j<foreigns.size();j++){
				String atributo = foreigns.elementAt(j)[0];
				if (atributo.indexOf("(") >= 0){
					atributo = atributo.substring(0, atributo.indexOf("("));
				}
				codigo+="ALTER TABLE " + t.getNombreTabla() +
						" ADD CONSTRAINT " + t.getNombreTabla() + "_" + atributo +
						" FOREIGN KEY ("+
						foreigns.elementAt(j)[0]+") REFERENCES "+
						foreigns.elementAt(j)[2]+";\n";
			}
				
		}
		
		// Si tiene uniques, se ponen
		Vector<String> uniques = t.getUniques();
		if(!uniques.isEmpty()){
			for (int j=0;j<uniques.size();j++){
				codigo+="ALTER TABLE "+t.getNombreTabla()+
				" ADD CONSTRAINT "+t.getNombreTabla() + "_unique_" + j + 
				" UNIQUE" + 
				"("+uniques.elementAt(j)+");\n";
			}	
		}
		
		return codigo;
	}

	@Override
	public String obtenerCodigoClavesTablaHTML(Tabla t) {
		String codigo="";
		
		//si tiene claves primarias, las añadimos
		Vector<String[]> primaries = t.getPrimaries();
		if (!primaries.isEmpty()){
			codigo+= HTMLUtils.toRedColor("ALTER TABLE ") + t.getNombreTabla() +
			HTMLUtils.toBlueColor(" ADD CONSTRAINT ") + t.getNombreTabla() + "_pk" +
			HTMLUtils.toBlueColor(" PRIMARY KEY ")+"(";
			for (int i=0;i<primaries.size();i++){
				if (i>0)codigo+=", ";
				codigo+=primaries.elementAt(i)[0];
			}
			codigo+=");"+HTMLUtils.newLine();
		}
		//si tiene claves foraneas:
		Vector<String[]> foreigns = t.getForeigns();
		if(!foreigns.isEmpty()){
			for (int j=0;j<foreigns.size();j++){
				String atributo = foreigns.elementAt(j)[0];
				if (atributo.indexOf("(") >= 0){
					atributo = atributo.substring(0, atributo.indexOf("("));
				}
				codigo+= HTMLUtils.toRedColor("ALTER TABLE ") + t.getNombreTabla() +
						HTMLUtils.toBlueColor(" ADD CONSTRAINT ") + 
										t.getNombreTabla() + "_" +atributo +
						HTMLUtils.toBlueColor(" FOREIGN KEY ")+
				"("+foreigns.elementAt(j)[0]+") " + HTMLUtils.toBlueColor(" REFERENCES ")+foreigns.elementAt(j)[2]+";"+
				HTMLUtils.newLine();
			}
				
		}
		
		// Si tiene uniques, se ponen
		Vector<String> uniques = t.getUniques();
		if(!uniques.isEmpty()){
			for (int j=0;j<uniques.size();j++){
				codigo+=HTMLUtils.toRedColor("ALTER TABLE ")+t.getNombreTabla()+
				HTMLUtils.toBlueColor(" ADD CONSTRAINT ")+t.getNombreTabla() + "_unique_" + j + 
				HTMLUtils.toBlueColor(" UNIQUE") + 
				"("+uniques.elementAt(j)+");" + HTMLUtils.newLine();
			}	
		}
		
		return codigo;
	}

	@Override
	public String obtenerCodigoEnumerado(Enumerado e) {
		// Crear la tabla
		String codigo ="CREATE TABLE "+e.getNombre()+"(";
		codigo += "value_list VARCHAR2(" + e.getLongitud() + ")";
		codigo+=");\n";
		
		// Establecer la clave primaria
		codigo+="ALTER TABLE "+e.getNombre()+" ADD CONSTRAINT " + e.getNombre() + "_pk" + 
				" PRIMARY KEY (value_list);\n";
		
		// Insertar los valores
		for (int i=0; i<e.getNumeroValores(); i++){
			String valor = e.getValor(i);
			if (valor.startsWith("'")) {
				valor = valor.substring(1, valor.length() - 1);
			}
			
			codigo += "INSERT INTO " + e.getNombre() + " values ('" + valor + "');\n";
		}
		
		codigo += "\n";
		return codigo;
	}

	@Override
	public String obtenerCodigoEnumeradoHTML(Enumerado e) {
		// Crear la tabla
		String codigo =HTMLUtils.toRedColor("CREATE TABLE ")+e.getNombre()+"(";
		codigo += "value_list " + HTMLUtils.toRedColor("VARCHAR2(" + e.getLongitud() + ")");
		codigo+=")" + ";"+HTMLUtils.newLine();

		// Establecer la clave primaria
		codigo+=HTMLUtils.toRedColor("ALTER TABLE ")+e.getNombre()+
				HTMLUtils.toRedColor(" ADD CONSTRAINT ") + e.getNombre() + "_pk" +
				HTMLUtils.toRedColor(" PRIMARY KEY ")+"(value_list);"+HTMLUtils.newLine();
		
		// Insertar los valores
		for (int i=0; i<e.getNumeroValores(); i++){
			String valor = e.getValor(i);
			if (valor.startsWith("'")) {
				valor = valor.substring(1, valor.length() - 1);
			}
			
			codigo += HTMLUtils.toRedColor("INSERT INTO ") + e.getNombre() + 
						HTMLUtils.toRedColor(" VALUES ") + "(" + 
						HTMLUtils.toGreenColor("'" + valor + "'") + ");" + 
						HTMLUtils.newLine();
		}
		
		codigo += HTMLUtils.newLine();
		return codigo;
	}
	
	// METODOS AUXILIARES
	private String equivalenciaTipoOracle(String tipo) {
		// Tipos simples que no hay que modificar
		if (tipo.equalsIgnoreCase("INTEGER") ||
			tipo.equalsIgnoreCase("DATE") ||
			tipo.equalsIgnoreCase("BLOB")){
				return tipo;
		}
		
		// Tipos simples a modificar
		if (tipo.equalsIgnoreCase("FLOAT")){
			return "REAL";
		}
		if (tipo.equalsIgnoreCase("BIT")){
			return "CHAR(1)";
		}
		if (tipo.equalsIgnoreCase("DATETIME")){
			return "TIMESTAMP";
		}
		
		// Tipos compuestos que no hay que modificar
		if (tipo.indexOf("(") > 0){
			String tipoSinParam = tipo.substring(0, tipo.indexOf("("));
			if (tipoSinParam.equalsIgnoreCase("CHAR") ||
				tipoSinParam.equalsIgnoreCase("INTEGER")){
				return tipo;
			}

			// Tipos compuestos que sí hay que modificar
			String param = tipo.substring(tipo.indexOf("("));
			if (tipoSinParam.equalsIgnoreCase("VARCHAR")){
				return "VARCHAR2" + param;
			}
			if (tipoSinParam.equalsIgnoreCase("TEXT")){
				return "CLOB" + param;
			}
			if (tipoSinParam.equalsIgnoreCase("DECIMAL")){
				return "NUMBER" + param;
			}
		}
		
		// Tipos pertenecientes a los dominios creados
		return tipo + "_sinAnalizar";
	}

	@Override
	public void usarDatabase(String nombre) throws SQLException {
		Statement stBuscar = _conexion.createStatement();
		String consulta = "SELECT table_name FROM user_tables";
		ResultSet rs = stBuscar.executeQuery(consulta);
		
		while (rs.next()){
	    	System.out.println("    " + rs.getString("table_name"));
	    	System.out.println("Eliminando tabla...");
	    	try {
	    		Statement stBorrar = _conexion.createStatement();
	    		stBorrar.executeUpdate("DROP TABLE " + rs.getString("table_name") +
	    				" CASCADE CONSTRAINTS");
	    	}catch (SQLException e) {
	    		// No pasa nada. Esto es que ya se ha borrado
	    		// con el cascade constraints, o que es una vista
	    	}
	    }
	}
}
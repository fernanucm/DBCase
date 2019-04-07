package modelo.conectorDBMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import modelo.servicios.Enumerado;
import modelo.servicios.Tabla;
import modelo.tools.HTMLUtils;
import vista.lenguaje.Lenguaje;

/**
 * Conecta la aplicación a una base de datos de Microsoft Access
 * 
 * @author Denis Cepeda
 */
public class ConectorAccessOdbc extends ConectorDBMS {

	protected Connection _conexion;
	
	@Override
	public void abrirConexion(String ruta, String usuario, String password)
			throws SQLException {
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.err.println(Lenguaje.text(Lenguaje.NO_CONECTOR));
			throw new SQLException(Lenguaje.text(Lenguaje.NO_CONECTOR));
		}
		
		_conexion = DriverManager.getConnection(ruta, usuario, password);
		
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
			codigo+=" "+equivalenciaTipoAccess(atributos.elementAt(i)[1]);
			
			// Indicamos si not null
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
		String codigo="<p><strong>CREATE TABLE </strong>"+t.getNombreTabla()+"(";
		
		// Para cada atributo...
		Vector<String[]> atributos = t.getAtributos();
		for (int i=0;i<atributos.size();i++){
			if (i>0) codigo+=", ";
			//metemos el atributo
			codigo+=atributos.elementAt(i)[0];
			//metemos el dominio
			String dominio = equivalenciaTipoAccess(atributos.elementAt(i)[1]);
			codigo+=" <strong>"+dominio+"</strong>";
			
			// Indicamos si not null
			if (atributos.elementAt(i)[4].equalsIgnoreCase("1"))
				codigo+= "<strong> NOT NULL</strong>";
		}
		//cerramos la creacion de la tabla
		codigo+=");</p>";
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
			codigo+= "<p><strong>ALTER TABLE </strong>" + t.getNombreTabla() +
			"<strong> ADD CONSTRAINT </strong>" + t.getNombreTabla() + "_pk" +
			"<strong> PRIMARY KEY </strong>"+"(";
			for (int i=0;i<primaries.size();i++){
				if (i>0)codigo+=", ";
				codigo+=primaries.elementAt(i)[0];
			}
			codigo+=");</p>";
		}
		
		//si tiene claves foraneas:
		Vector<String[]> foreigns = t.getForeigns();
		if(!foreigns.isEmpty()){
			for (int j=0;j<foreigns.size();j++){
				String atributo = foreigns.elementAt(j)[0];
				if (atributo.indexOf("(") >= 0){
					atributo = atributo.substring(0, atributo.indexOf("("));
				}
				codigo+= "<p><strong>ALTER TABLE </strong>" + t.getNombreTabla() +
						"<strong> ADD CONSTRAINT </strong>" + 
										t.getNombreTabla() + "_" +atributo +
						"<strong> FOREIGN KEY </strong>"+
				"("+foreigns.elementAt(j)[0]+") " + "<strong> REFERENCES </strong>"+foreigns.elementAt(j)[2]+";"+
				"</p>";
			}		
		}
		
		// Si tiene uniques, se ponen
		Vector<String> uniques = t.getUniques();
		if(!uniques.isEmpty()){
			for (int j=0;j<uniques.size();j++){
				codigo+="<p><strong>ALTER TABLE </strong>"+t.getNombreTabla()+
				"<strong> ADD CONSTRAINT </strong>"+t.getNombreTabla() + "_unique_" + j + 
				"<strong> UNIQUE</strong>" + 
				"("+uniques.elementAt(j)+");" + "</p>";
			}	
		}
		
		return codigo;
	}
	
	@Override
	public String obtenerCodigoEnumerado(Enumerado e) {
		// Crear la tabla
		String codigo ="CREATE TABLE "+e.getNombre()+"(";
		codigo += "value_list VARCHAR(" + e.getLongitud() + ")";
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
			
			codigo += "INSERT INTO " + e.getNombre() + " (value_list) values ('" + valor + "');\n";
		}
		
		codigo += "\n";
		return codigo;
	}

	@Override
	public String obtenerCodigoEnumeradoHTML(Enumerado e) {
		// Crear la tabla
		String codigo ="<p><strong>CREATE TABLE </strong>"+e.getNombre()+"(";
		codigo += "value_list " + "<strong>VARCHAR(" + e.getLongitud() + ")</strong>";
		codigo+=")" + ";</p>";

		// Establecer la clave primaria
		codigo+="<p><strong>ALTER TABLE </strong>"+e.getNombre()+
				"<strong> ADD CONSTRAINT </strong>" + e.getNombre() + "_pk" +
				"<strong> PRIMARY KEY </strong>"+"(value_list);</p>";
		
		// Insertar los valores
		for (int i=0; i<e.getNumeroValores(); i++){
			String valor = e.getValor(i);
			if (valor.startsWith("'")) {
				valor = valor.substring(1, valor.length() - 1);
			}
			
			codigo += "<p><strong>INSERT INTO </strong>" + e.getNombre() + " (value_list) " +
						"<strong> VALUES </strong>" + "(" + 
						HTMLUtils.toGreenColor("'" + valor + "'") + ");" + 
						"</p>";
		}
		
		return codigo;
	}
	
	// METODOS AUXILIARES
	private String equivalenciaTipoAccess(String tipo) {
		// Tipos simples que no hay que modificar
		if (tipo.equalsIgnoreCase("INTEGER") ||
			tipo.equalsIgnoreCase("DATETIME")){
				return tipo;
		}
		
		// Tipos simples a modificar
		if (tipo.equalsIgnoreCase("FLOAT")){
			return "DOUBLE";
		}
		if (tipo.equalsIgnoreCase("BIT")){
			return "YESNO";
		}
		if (tipo.equalsIgnoreCase("DATE")){
			return "DATETIME";
		}
		if (tipo.equalsIgnoreCase("BLOB")){
			return "IMAGE";
		}
		
		// Tipos compuestos que no hay que modificar
		if (tipo.indexOf("(") > 0){
			String tipoSinParam = tipo.substring(0, tipo.indexOf("("));
			if (tipoSinParam.equalsIgnoreCase("CHAR") ||
				tipoSinParam.equalsIgnoreCase("VARCHAR")){
				return tipo;
			}

			// Tipos compuestos que sí hay que modificar
			String param = tipo.substring(tipo.indexOf("("));
			if (tipoSinParam.equalsIgnoreCase("TEXT")){
				return "MEMO" + param;
			}
			if (tipoSinParam.equalsIgnoreCase("DECIMAL")){
				return "CURRENCY";
			}
			if (tipoSinParam.equalsIgnoreCase("INTEGER")){
				return "INTEGER";
			}
		}
		
		// Tipos pertenecientes a los dominios creados
		return tipo + "_sinAnalizar";
	}

	@Override
	public void usarDatabase(String nombre) throws SQLException {
		return;
	}
}

package Utilidades.ConectorDBMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import LogicaNegocio.Servicios.Enumerado;
import LogicaNegocio.Servicios.Tabla;
import Presentacion.Lenguajes.Lenguaje;
import Utilidades.HTMLUtils;

/**
 * Conecta la aplicación con un gestor de bases de datos MySQL
 * 
 * @author Denis Cepeda
 */
public class ConectorMySQL extends ConectorDBMS {

	private Connection _conexion;
	
	@Override
	public void abrirConexion(String ruta, String usuario, String password) throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.err.println(Lenguaje.getMensaje(Lenguaje.NO_CONECTOR));
			e.printStackTrace();
			
			return;
		} 
		
		_conexion = DriverManager.getConnection(ruta, usuario, password);
		
		if(!_conexion.isClosed())
			System.out.println("Conectado correctamente a '" + ruta + "' usando TCP/IP...");
	}

	@Override
	public void cerrarConexion() throws SQLException{
		if(_conexion != null)
			_conexion.close();
	}

	@Override
	public void ejecutarOrden(String orden) throws SQLException{
		Statement st = _conexion.createStatement();
		st.executeUpdate(orden);
		st.close();
	}
	
	@Override
	public String obtenerCodigoCreacionTabla(Tabla t) {
		// Eliminar la tabla (si existía)
		String codigo="DROP TABLE IF EXISTS "+t.getNombreTabla()+";\n";
		
		// Crear la tabla
		codigo+="CREATE TABLE "+t.getNombreTabla()+"(";
		
		// Para cada atributo...
		Vector<String[]> atributos = t.getAtributos();
		for (int i=0;i<atributos.size();i++){
			if (i>0) codigo+=", ";
			//metemos el atributo
			codigo += atributos.elementAt(i)[0];
			
			// metemos el dominio
			codigo+=" "+equivalenciaTipoMySQL(atributos.elementAt(i)[1]);
			
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
		// Eliminar la tabla (si existía)
		String codigo=HTMLUtils.toRedColor("DROP TABLE IF EXISTS ")+t.getNombreTabla()+";"+HTMLUtils.newLine();
		
		// Crear la tabla
		codigo+=HTMLUtils.toRedColor("CREATE TABLE ")+t.getNombreTabla()+"(";
		
		// Para cada atributo...
		Vector<String[]> atributos = t.getAtributos();
		for (int i=0;i<atributos.size();i++){
			if (i>0) codigo+=", ";
			//metemos el atributo
			codigo+=atributos.elementAt(i)[0];
			
			//metemos el dominio
			String dominio = equivalenciaTipoMySQL(atributos.elementAt(i)[1]);
			codigo+=" "+HTMLUtils.toRedColor(dominio);
			
			// Indicamos si es NOT NULL
			if (atributos.elementAt(i)[4].equalsIgnoreCase("1"))
				codigo+= HTMLUtils.toRedColor(" NOT NULL");
			
		}
		//cerramos la creacion de la tabla
		//" + HTMLUtils.toBlueColor(" ENGINE = InnoDB") + "
		codigo+=");"+HTMLUtils.newLine()+HTMLUtils.newLine();
		return codigo;
	}

	@Override
	public String obtenerCodigoClavesTabla(Tabla t) {
		String codigo="";
	
		//si tiene claves primarias, las añadimos.
		Vector<String[]> primaries = t.getPrimaries();
		if (!primaries.isEmpty()){
			codigo+="ALTER TABLE "+t.getNombreTabla()+" ADD PRIMARY KEY (";
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
				codigo+="ALTER TABLE "+t.getNombreTabla()+" ADD FOREIGN KEY ("+
						foreigns.elementAt(j)[0]+") REFERENCES "+
						foreigns.elementAt(j)[2]+";\n";
			}	
		}
		
		// Si tiene uniques, se ponen
		Vector<String> uniques = t.getUniques();
		if(!uniques.isEmpty()){
			for (int j=0;j<uniques.size();j++){
				codigo+="ALTER TABLE "+t.getNombreTabla()+" ADD UNIQUE KEY ("+
				uniques.elementAt(j)+");\n";
			}
				
		}
		
		return codigo;
	}

	@Override
	public String obtenerCodigoClavesTablaHTML(Tabla t) {
		String codigo="";
		
		//si tiene claves primarias, las añadimos.

		Vector<String[]> primaries = t.getPrimaries();
		if (!primaries.isEmpty()){
			codigo+=HTMLUtils.toRedColor("ALTER TABLE ")+t.getNombreTabla()+HTMLUtils.toBlueColor(" ADD PRIMARY KEY ")+"(";
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
				codigo+=HTMLUtils.toRedColor("ALTER TABLE ")+t.getNombreTabla()+HTMLUtils.toBlueColor(" ADD FOREIGN KEY ")+
				"("+foreigns.elementAt(j)[0]+") " + HTMLUtils.toBlueColor(" REFERENCES ")+foreigns.elementAt(j)[2]+";"+
				HTMLUtils.newLine();
			}
				
		}
		
		// Si tiene uniques, se ponen
		Vector<String> uniques = t.getUniques();
		if(!uniques.isEmpty()){
			for (int j=0;j<uniques.size();j++){
				codigo+=HTMLUtils.toRedColor("ALTER TABLE ")+t.getNombreTabla()+HTMLUtils.toBlueColor(" ADD UNIQUE KEY ")+
				"("+uniques.elementAt(j)+");" + HTMLUtils.newLine();
			}
				
		}
		
		return codigo;
	}
	
	@Override
	public String obtenerCodigoEnumerado(Enumerado e) {
		// Eliminar la tabla (si existía)
		String codigo="DROP TABLE IF EXISTS "+e.getNombre()+";\n";
		
		// Crear la tabla
		codigo+="CREATE TABLE "+e.getNombre()+"(";
		codigo += "value_list VARCHAR(" + e.getLongitud() + ")";
		codigo+=") ENGINE=InnoDB;\n";
		
		// Establecer la clave primaria
		codigo+="ALTER TABLE "+e.getNombre()+" ADD PRIMARY KEY (value_list);\n";
		
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
		// Eliminar la tabla (si existía)
		String codigo=HTMLUtils.toRedColor("DROP TABLE IF EXISTS ")+e.getNombre()+";"+HTMLUtils.newLine();
		
		// Crear la tabla
		codigo+=HTMLUtils.toRedColor("CREATE TABLE ")+e.getNombre()+"(";
		codigo += "value_list " + HTMLUtils.toRedColor("VARCHAR(" + e.getLongitud() + ")");
		codigo+=")" + HTMLUtils.toBlueColor(" ENGINE = InnoDB") + ";"+HTMLUtils.newLine();

		// Establecer la clave primaria
		codigo+=HTMLUtils.toRedColor("ALTER TABLE ")+e.getNombre()+
				HTMLUtils.toRedColor(" ADD PRIMARY KEY ")+"(value_list);"+HTMLUtils.newLine();
		
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
	
	// --- --- --- MÉTODOS AUXILIARES --- --- ---
	private String equivalenciaTipoMySQL(String tipo) {
		// Tipos simples que no hay que modificar
		if (tipo.equalsIgnoreCase("INTEGER") ||
			tipo.equalsIgnoreCase("BIT") ||
			tipo.equalsIgnoreCase("DATE") ||
			tipo.equalsIgnoreCase("DATETIME") ||
			tipo.equalsIgnoreCase("BLOB")){
				return tipo;
		}
		
		// Tipos simples a modificar
		if (tipo.equalsIgnoreCase("FLOAT")){
			return "REAL";
		}
		
		// Tipos compuestos que no hay que modificar
		if (tipo.indexOf("(") > 0){
			String tipoSinParam = tipo.substring(0, tipo.indexOf("("));
			if (tipoSinParam.equalsIgnoreCase("CHAR") ||
				tipoSinParam.equalsIgnoreCase("VARCHAR") ||
				tipoSinParam.equalsIgnoreCase("TEXT") ||
				tipoSinParam.equalsIgnoreCase("DECIMAL") ||
				tipoSinParam.equalsIgnoreCase("INTEGER")){
				return tipo;
			}
		}
		
		// Tipos pertenecientes a los dominios creados
		return tipo + "_sinAnalizar";
	}

	@Override
	public void usarDatabase(String nombre) throws SQLException {
		ejecutarOrden ("DROP DATABASE IF EXISTS " + nombre + ";");
		ejecutarOrden ("CREATE DATABASE " + nombre +  ";");
		ejecutarOrden ("USE " + nombre + ";");
	}
}
package LogicaNegocio.Servicios;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import LogicaNegocio.Transfers.TransferConexion;
import Utilidades.ConectorDBMS.ConectorDBMS;
import Utilidades.ConectorDBMS.FactoriaConectores;
//la clase tabla, almacenara las tablas a traducir del disenio al script.
public class Tabla {

	/* Se almacena cada atributo y su dominio en una pareja String[].
	 * en el caso de las claves foraneas, se almacena tambien la tabla de referencia y el atributo 
	 * al que referencian.
	 */
	private String nombreTabla;
	
	/*
	 * atributos[i][0] = nombre
	 * atributos[i][1] = dominio
	 * atributos[i][2] = tabla de referencia
	 * atributos[i][3] = unique (y/n)
	 * atributos[i][4] = not null (y/n)
	 */
	private Vector<String[]> atributos;
	
	/*
	 * primaries[i][0] = nombre
	 * primaries[i][1] = dominio
	 * primaries[i][2] = tabla de referencia (atributo al que referencia)
	 */
	private Vector<String[]> primaries;
	
	/*
	 * foreigns[i][0] = nombre
	 * foreigns[i][1] = dominio
	 * foreigns[i][2] = tabla de referencia
	 */
	private Vector<String[]> foreigns;
	
	private Vector<String> uniques;
	
	public Tabla(String nombre){
		nombreTabla=nombre;
		atributos=new Vector<String[]>();
		primaries=new Vector<String[]>();
		foreigns=new Vector<String[]>();
		uniques = new Vector<String>();
	}
	
	
	
	public Vector<String[]> getAtributos() {
		return atributos;
	}
	public void setAtributos(Vector<String[]> atributos) {
		this.atributos = atributos;
	}
	public Vector<String[]> getForeigns() {
		return foreigns;
	}
	public void setForeigns(Vector<String[]> foreigns) {
		this.foreigns = foreigns;
	}
	public Vector<String[]> getPrimaries() {
		return primaries;
	}
	public void setPrimaries(Vector<String[]> primaries) {
		this.primaries = primaries;
	}
	public Vector<String> getUniques() {
		return uniques;
	}
	public void setUniques(Vector<String> uniques) {
		this.uniques = uniques;
	}
	
	public void aniadeAtributo(String nombre, String dominio,String tablaReferencia,
														Hashtable<Integer,Enumerado> dominios,
														boolean unique, boolean notNull){
		String [] trio=new String[5];
		trio[0]=nombre;
		trio[1]=dominio;
		trio[2]=tablaReferencia;
		
		if (unique) trio[3] = "1";
		else trio[3] = "0";
		
		if (notNull) trio[4] = "1";
		else trio[4] = "0";
		
		// Comprobar si el dominio pertenece a dominios, y si es así, aniadir la referencia
		boolean encontrado = false;
		Iterator<Enumerado> doms = dominios.values().iterator();
		while (!encontrado && doms.hasNext()){
			Enumerado e = doms.next();
			if (e.getNombre().equalsIgnoreCase(dominio)){
				encontrado = true;
				trio[1] = "VARCHAR(" + e.getLongitud() + ")";
				trio[2] = e.getNombre() + "(value_list)";
			}
		}
		
		atributos.add(trio);
		if (encontrado) 
			aniadeClaveForanea(trio[0], trio[1], trio[2]);
	}
	
	public void aniadeListaAtributos(Vector<String[]> listado, Hashtable<Integer,Enumerado> dominios){
		for (int i=0;i<listado.size();i++){
			String[] trio = listado.elementAt(i);
			if (trio.length < 4){
				aniadeAtributo(trio[0], trio[1], trio[2], dominios,
						false,
						false);
			}else{
				aniadeAtributo(trio[0], trio[1], trio[2], dominios,
								trio[3].equalsIgnoreCase("1"),
								trio[4].equalsIgnoreCase("1"));
			}
		}
	}
	
	public void aniadeListaClavesPrimarias(Vector<String[]> listado){
		for (int i=0;i<listado.size();i++){
			primaries.add(listado.elementAt(i));
		}
	}
	
	public void aniadeListaClavesForaneas(Vector<String[]> listado,String nombreEntidad, String[] atributosReferenciados){
		for (int i=0;i<listado.size();i++){
			String []trio=new String[3];
			String []par=listado.elementAt(i);
			trio[0]=par[0];
			trio[1]=par[1];
			trio[2]=nombreEntidad + "(" + atributosReferenciados[i] + ")";
			foreigns.add(trio);
		}
	}

	public void aniadeClavePrimaria(String nombre,String dominio,String tablaReferencia){
		String [] trio=new String[3];
		trio[0]=nombre;
		trio[1]=dominio;
		trio[2]= tablaReferencia;
		primaries.add(trio);
	}
	
	private void aniadeClaveForanea(String nombre, String dominio,String tablaDeReferencia){
		String [] trio=new String[3];
		trio[0]=nombre;
		trio[1]=dominio;
		trio[2]=tablaDeReferencia;
		foreigns.add(trio);
	}
	
	private void aniadeAtributo(String nombre, String dominio,String tablaReferencia, 
														boolean unique, boolean notNull){
		String [] trio=new String[5];
		trio[0]=nombre;
		trio[1]=dominio;
		trio[2]=tablaReferencia;
		
		if (unique) trio[3] = "1";
		else trio[3] = "0";
		
		if (notNull) trio[4] = "1";
		else trio[4] = "0";
		
		atributos.add(trio);
	}
	
	public Tabla creaClonSinAmbiguedadNiEspacios(){
		// Crea la tabla con el mismo nombre
		Tabla t = new Tabla(ponGuionesBajos(this.nombreTabla));
		
		// Aniade todos los atributos
		for (int i=0;i<atributos.size();i++){
			String repe="";
			if (this.estaRepe(atributos.elementAt(i)[0], atributos)) 
				repe +="_"+atributos.elementAt(i)[2];
			t.aniadeAtributo(ponGuionesBajos(atributos.elementAt(i)[0]+repe), 
							atributos.elementAt(i)[1], 
							ponGuionesBajos(atributos.elementAt(i)[2]),
							atributos.elementAt(i)[3].equalsIgnoreCase("1"),
							atributos.elementAt(i)[4].equalsIgnoreCase("1"));
		}
		
		// Aniade las claves primarias
		if (!primaries.isEmpty()){
			for (int i=0;i<primaries.size();i++){
				String repe ="";
				if (this.estaRepe(primaries.elementAt(i)[0], atributos)) {
					String ref = primaries.elementAt(i)[2];
					if (ref.indexOf("(") >= 0){
						repe +="_"+ref.substring(0, ref.indexOf("("));
					}else{
						repe +="_"+ref;
					}
				}
				
				t.aniadeClavePrimaria(ponGuionesBajos(primaries.elementAt(i)[0]+repe), 
									primaries.elementAt(i)[1], 
									ponGuionesBajos(primaries.elementAt(i)[2]));
			}
		}
		
		// Aniade las claves foráneas
		if (!foreigns.isEmpty()){
			for (int i=0;i<foreigns.size();i++){
				String repe="";
				if (this.estaRepe(foreigns.elementAt(i)[0], atributos)) {
					String ref = foreigns.elementAt(i)[2];
					if (ref.indexOf("(") >= 0){
						repe +="_"+ref.substring(0, ref.indexOf("("));
					}else{
						repe +="_"+ref;
					}
				}
				
				t.aniadeClaveForanea(ponGuionesBajos(foreigns.elementAt(i)[0]+repe), 
									foreigns.elementAt(i)[1], 
									ponGuionesBajos(foreigns.elementAt(i)[2]));
			}
		}
		
		// Aniade las unique
		if (!uniques.isEmpty()) {
			for (int i = 0; i < uniques.size(); i++) {
				// Extraer tabla a la que referencian
				String datosUnique = uniques.get(i);
				String tabla;
				String[] unis;
				if (datosUnique.indexOf("#") < 0){
					unis = datosUnique.split(",+");
					tabla = "";
				}else{
					String[] uniquesTabla = datosUnique.split("#");
					unis = uniquesTabla[0].split(",+");
					tabla = uniquesTabla[1];
				}
				
				// Extraer uniques
				String resul = "";
				for (int m = 0; m<unis.length; m++){
					// Comprobar si se ha renombrado
					if (this.estaRepe(unis[m], atributos)) {
						unis[m] += "_" + tabla;
					}
					
					// Aniadir a la lista de uniques
					if (m == 0) {
						resul += ponGuionesBajos(unis[m].trim());
					}else{
						resul += ", " + ponGuionesBajos(unis[m].trim());
					}
				}
				
				t.getUniques().add(resul);
			}
		}
		
		return t;
	}
	
	public String codigoEstandarCreacionDeTabla(TransferConexion conexion){
		Tabla t = creaClonSinAmbiguedadNiEspacios();
		ConectorDBMS traductor = FactoriaConectores.obtenerConector(conexion.getTipoConexion());
		return traductor.obtenerCodigoCreacionTabla(t);
	}
	
	public String codigoHTMLCreacionDeTabla(TransferConexion conexion){
		Tabla t = creaClonSinAmbiguedadNiEspacios();
		ConectorDBMS traductor = FactoriaConectores.obtenerConector(conexion.getTipoConexion());
		return traductor.obtenerCodigoCreacionTablaHTML(t);
	}
	
	public String codigoEstandarClavesDeTabla(TransferConexion conexion){
		Tabla t = creaClonSinAmbiguedadNiEspacios();
		ConectorDBMS traductor = FactoriaConectores.obtenerConector(conexion.getTipoConexion());
		return traductor.obtenerCodigoClavesTabla(t);
	}
	
	public String codigoHTMLClavesDeTabla(TransferConexion conexion){
		Tabla t = creaClonSinAmbiguedadNiEspacios();
		ConectorDBMS traductor = FactoriaConectores.obtenerConector(conexion.getTipoConexion());
		return traductor.obtenerCodigoClavesTablaHTML(t);
	}

	public String modeloRelacionalDeTabla(){
		String mr="<p><font size=\"4\">"+this.ponGuionesBajos(nombreTabla)+" (";
		Vector<String[]>definitivo= new Vector<String[]>();
		//dejamos los elementos en las 3 listas sin duplicados.
		definitivo=this.filtra(atributos, primaries);
		int i=0,k=0;
		
		if(!primaries.isEmpty())
			for (i=0;i<primaries.size();i++){
				String repe="";
				if (this.estaRepe(primaries.elementAt(i)[0], atributos)) repe +="_"+primaries.elementAt(i)[2];
				if (i>0) mr+=", ";
				mr+="<u>"+this.ponGuionesBajos(primaries.elementAt(i)[0]+repe)+"</u>";
			}

		for (int j=0;j<definitivo.size();j++){
			if (i>0||k>0) mr+=", ";
			String repe="";
			if (this.estaRepe(definitivo.elementAt(j)[0], atributos)) repe +="_"+definitivo.elementAt(j)[2];
			mr+=this.ponGuionesBajos(definitivo.elementAt(j)[0]+repe);
		}	
		mr+=")</font></p>";
		return mr;
	}
	
	
	public String getNombreTabla() {
		return nombreTabla;
	}
	
	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}
	
	
	
	
	//metodos auxiliares:
	private String ponGuionesBajos(String cadena){
		//sustituye todos los blancos y guiones medios, por guiones bajos.
		cadena= cadena.replaceAll(" ", "_");
		cadena= cadena.replaceAll("-", "_");
		return cadena;
	}
	
	public void aniadeListaAtributosComoSlave(Vector<String[]> listado){
		for (int i=0;i<listado.size();i++){
			String[]par=new String[2];
			String[]aux=listado.elementAt(i);
			par[0]=aux[0]+"_slave";
			par[1]=aux[1];
			atributos.add(par);
		}
	}
	
	private boolean estaRepe(String elem,Vector<String[]> vector){
		int cont =0;
		int i=0;
		
		while (i<vector.size()&& cont<2){
			String [] trio = vector.elementAt(i);
			if (trio[0].equalsIgnoreCase(elem)){
				cont++;
			}
			i++;
		}
		
		return (cont>1);
	}
	
	/**
	 * Filtra el contenido de un vector con el otro. 
	 * @param v1 
	 * @param v2
	 * @return Devuelve el vector v1 sin los valores de v2
	 */
	private Vector<String[]> filtra(Vector<String[]> v1,Vector<String[]> v2 ){
		Vector<String[]> aux= new Vector<String[]>();
		
		
		for (int i =0;i<v1.size();i++){
			String[] par1 = v1.elementAt(i);
			int j=0;
			boolean esta=false;
			while(j<v2.size()&&!esta){
				String[] par2=v2.elementAt(j);
				if (par1[0].equals(par2[0]) && par1[1].equals(par2[1]) && par1[2].equals(par2[2])){
					esta=true;
				}
				j++;
			}
			if (!esta)aux.add(par1);
		}
		
		return aux;
	}
	
}

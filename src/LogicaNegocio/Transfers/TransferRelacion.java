package LogicaNegocio.Transfers;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.Vector;

import Persistencia.EntidadYAridad;
@SuppressWarnings("rawtypes")
public class TransferRelacion extends Transfer {

	private int idRelacion;
	private String nombre;
	private String tipo;
	private String rol;
	private Vector listaEntidadesYAridades;
	private Vector listaAtributos;
	private Vector listaRestricciones;
	private Vector listaUniques;
	private Point2D posicion;
	private int volumen;
	private int frecuencia;

	public TransferRelacion() {

	}

	public TransferRelacion clonar() {
		TransferRelacion clon_tr = new TransferRelacion();
		clon_tr.setIdRelacion(this.getIdRelacion());
		clon_tr.setNombre(this.getNombre());
		clon_tr.setTipo(this.getTipo());
		clon_tr.setRol(this.getRol());
		clon_tr.setListaEntidadesYAridades((Vector) this
				.getListaEntidadesYAridades().clone());
		clon_tr.setListaAtributos((Vector) this.getListaAtributos().clone());
		if (!clon_tr.getTipo().equals("IsA")){
			clon_tr.setListaRestricciones((Vector) this.getListaRestricciones().clone());
			clon_tr.setListaUniques((Vector) this.getListaUniques().clone());
		}
		clon_tr.setPosicion((Point2D) this.getPosicion().clone());
		clon_tr.setVolumen(this.getVolumen());
		clon_tr.setFrecuencia(this.getFrecuencia());
		return clon_tr;
	}
	//Antes sólo tenía un parámetro, el primero; arg0
	@SuppressWarnings("unchecked")
	public void CopiarRelacion(TransferRelacion arg0,int idBuscado,Boolean repetido) {
		this.idRelacion = arg0.getIdRelacion();
		this.nombre = arg0.getNombre();
		this.tipo = arg0.getTipo();
		this.listaEntidadesYAridades = arg0.getListaEntidadesYAridades();
		this.listaAtributos = arg0.getListaAtributos();
		this.listaRestricciones = arg0.getListaRestricciones();
		this.volumen = arg0.getVolumen();
		this.frecuencia = arg0.getFrecuencia();
		this.listaUniques = arg0.getListaUniques();
		this.rol=arg0.getRol();
		//Si entidad ya está asociada con dicha relación, la línea que las unirá deberá ser diferente a la existente
		 //Filtramos la lista de entidades quitando las entidades que ya estan
			//Puesto que se van a permitir hacer relaciones circulares no filtramos la lista de entidades
			//Vector<EntidadYAridad> vectorTupla = this.getRelacion().getListaEntidadesYAridades();
			Vector<EntidadYAridad> vectorTupla = this.listaEntidadesYAridades;
			Vector vectorIdsEntidades = new Vector();
			int cont = 0;
			while(cont<vectorTupla.size()){
				vectorIdsEntidades.add((vectorTupla.get(cont)).getEntidad());
				cont++;
			}
			cont = 0;
			//Vector<TransferEntidad> listaEntidadesFiltrada = new Vector<TransferEntidad>();
			int limite=this.getListaEntidadesYAridades().size();
			while((cont<limite)&& (repetido==false)){
				//TransferEntidad te = (TransferEntidad)this.listaEntidadesYAridades.get(cont);
				//if(!vectorIdsEntidades.contains(te.getIdEntidad()))
				if(vectorIdsEntidades.contains(idBuscado))
					repetido=true;
				//	listaEntidadesFiltrada.add(te);
				cont++;
			}
		//this.setListaEntidades(listaEntidadesFiltrada);
		if (repetido) this.posicion = new Point2D.Double(arg0.getPosicion().getX(), arg0.getPosicion().getY());
		else this.posicion = new Point2D.Double(arg0.getPosicion().getX(), arg0.getPosicion().getY());
			
	}
	
	@SuppressWarnings("unchecked")
	public void CopiarRelacionUnoUno(Vector v) {
		this.idRelacion = (Integer)v.get(0);
		this.nombre = (String)v.get(4);
		this.tipo = (String)v.get(7);
		this.listaEntidadesYAridades =(Vector) v.get(5);
		this.listaAtributos = (Vector)v.get(6);
		this.rol = (String)v.get(8);
		//Si entidad ya está asociada con dicha relación, la línea que las unirá deberá ser diferente a la existente
			Vector<EntidadYAridad> vectorTupla = this.listaEntidadesYAridades;
			Vector vectorIdsEntidades = new Vector();
			int cont = 0;
			while(cont<vectorTupla.size()){
				vectorIdsEntidades.add((vectorTupla.get(cont)).getEntidad());
				cont++;
			}			
	}

	@Override
	public String toString() {
		return this.nombre;
	}

	@Override
	public Shape toShape() {
		// Si es IsA retorna el triángulo
		if (getTipo().equals(new String("IsA"))) 
			return this.toShapeIsA();
		
		
		// Si es grande ajusta el tamaño al nombre
		int anchura = Math.max(this.nombre.length() * 11 / 2,50);
		int altura = Math.max(this.nombre.length() * 5 / 2,30);
		Polygon p = new Polygon();
		p.addPoint(-anchura, 0);
		p.addPoint(0, -altura);
		p.addPoint(anchura, 0);
		p.addPoint(0, altura);
		return p;
	}

	private Shape toShapeIsA() {
		Polygon p = new Polygon();		
		p.addPoint(-35, -23);
		p.addPoint(35, -23);
		p.addPoint(0, 35);
		return p;
	}

	// Dibuja el segundo rombo externo
	public Shape outerShape() {
		Polygon figura;
		// Si el tamaño del nombre es pequeño dibuja rombo standard
		if (this.nombre.length() < 8) {
			figura = new Polygon();
			figura.addPoint(-57, 0);
			figura.addPoint(0, -35);
			figura.addPoint(57, 0);
			figura.addPoint(0, 35);
			return figura;
		}
		// Si es grande ajusta el tamaño al nombre
		int anchura = (this.nombre.length() * 11 / 2) + 7;
		int altura = (this.nombre.length() * 5 / 2) + 5;
		figura = new Polygon();
		figura.addPoint(-anchura, 0);
		figura.addPoint(0, -altura);
		figura.addPoint(anchura, 0);
		figura.addPoint(0, altura);
		return figura;
	}

	public int getIdRelacion() {
		return idRelacion;
	}

	public void setIdRelacion(int idRelacion) {
		this.idRelacion = idRelacion;
	}

	public Vector getListaAtributos() {
		return listaAtributos;
	}

	public void setListaAtributos(Vector listaAtributos) {
		this.listaAtributos = listaAtributos;
	}

	public Vector getListaEntidadesYAridades() {
		return listaEntidadesYAridades;
	}

	public void setListaEntidadesYAridades(Vector listaEntidadesYAridades) {
		this.listaEntidadesYAridades = listaEntidadesYAridades;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRol() {
		return rol;
	}
	
	public void setRol(String nuevoRol) {
		this.rol = nuevoRol;
	}	
	@Override
	public Point2D getPosicion() {
		return posicion;
	}

	public void setPosicion(Point2D posicion) {
		this.posicion = posicion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Vector getListaRestricciones() {
		return listaRestricciones;
	}
	public void setListaRestricciones(Vector listaRestricciones) {
		this.listaRestricciones = listaRestricciones;
	}
	public Vector getListaUniques() {
		return listaUniques;
	}
	public void setListaUniques(Vector listaUniques) {
		this.listaUniques = listaUniques;
	}

	public int getVolumen() {
		return volumen;
	}

	public void setVolumen(int volumen) {
		this.volumen = volumen;
	}

	public int getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(int frecuencia) {
		this.frecuencia = frecuencia;
	}
		
}

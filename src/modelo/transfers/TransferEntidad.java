package modelo.transfers;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Vector;
@SuppressWarnings("rawtypes")
public class TransferEntidad extends Transfer{
	private int idEntidad;
	private String nombre;
	private boolean debil;
	private Vector listaAtributos;
	private Vector listaClavesPrimarias;
	private Vector listaRestricciones;
	private Vector listaUniques;
	private Point2D posicion;
	private int volumen;
	private int frecuencia;
	
	public TransferEntidad clonar(){
		TransferEntidad clon_te = new TransferEntidad();
		clon_te.setIdEntidad(this.getIdEntidad());
		clon_te.setNombre(this.getNombre());
		clon_te.setDebil(this.isDebil());
		clon_te.setListaAtributos((Vector) this.getListaAtributos().clone());
		clon_te.setListaClavesPrimarias((Vector) this.getListaClavesPrimarias().clone());
		clon_te.setListaRestricciones((Vector) this.getListaRestricciones().clone());
		clon_te.setListaUniques((Vector) this.getListaUniques().clone());
		clon_te.setPosicion((Point2D) this.getPosicion().clone());	
		clon_te.setVolumen(this.getVolumen());
		clon_te.setFrecuencia(this.getFrecuencia());
		return clon_te;
	}
	
	public void CopiarEntidad(TransferEntidad arg0){
		this.idEntidad = arg0.getIdEntidad();
		this.nombre = arg0.getNombre();
		this.debil = arg0.isDebil();
		this.listaAtributos = arg0.getListaAtributos();
		this.listaClavesPrimarias = arg0.getListaClavesPrimarias();
		this.listaRestricciones = arg0.getListaRestricciones();
		this.listaUniques = arg0.getListaUniques();
		this.volumen = arg0.getVolumen();
		this.frecuencia = arg0.getFrecuencia();
		this.posicion = new Point2D.Double(arg0.getPosicion().getX(),
										   arg0.getPosicion().getY());
	}
	
	@Override
	public Point2D getPosicion() {
		return posicion;
	}
	public void setPosicion(Point2D posicion) {
		this.posicion = posicion;
	}
	public boolean isDebil() {
		return debil;
	}
	public void setDebil(boolean debil) {
		this.debil = debil;
	}
	public int getIdEntidad() {
		return idEntidad;
	}
	public void setIdEntidad(int idEntidad) {
		this.idEntidad = idEntidad;
	}
	public Vector getListaAtributos() {
		return listaAtributos;
	}
	public void setListaAtributos(Vector listaAtributos) {
		this.listaAtributos = listaAtributos;
	}
	public Vector getListaClavesPrimarias() {
		return listaClavesPrimarias;
	}
	public void setListaClavesPrimarias(Vector listaClavesPrimarias) {
		this.listaClavesPrimarias = listaClavesPrimarias;
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	@Override
	public String toString() {
		return this.nombre;
	}

	@Override
	public Shape toShape() {
		// 	Si el tamaño del nombre es pequeño dibuja rectángulo standard
		if (this.nombre.length() < 8) return new RoundRectangle2D.Double(-50,-20,100,40,8,8);
		
		// Si es grande ajusta el tamaño al nombre
		int anchura = this.nombre.length() * 11 / 2;
		return new RoundRectangle2D.Double(-anchura,-20,anchura * 2,40,8,8);
	}
	
	//	 Dibuja el segundo rectángulo externo (para entidades débiles)
	public Shape outerShape(){
		RoundRectangle2D figura;
		// Si el tamaño del nombre es pequeño dibuja rectángulo standard
		if (this.nombre.length() < 8){
			figura = new RoundRectangle2D.Double(-55,-25,110,50,8,8);
			return figura;
		}
		// Si es grande ajusta el tamaño al nombre
		int anchura = (this.nombre.length() * 11 / 2) + 5;
		figura = new RoundRectangle2D.Double(-anchura,-25,anchura * 2,50,8,8);
		return figura;
	}
}

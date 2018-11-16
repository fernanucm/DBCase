package LogicaNegocio.Transfers;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

public class TransferEntidad extends Transfer{
	private int idEntidad;
	private String nombre;
	private boolean debil;
	@SuppressWarnings("unchecked")
	private Vector listaAtributos;
	@SuppressWarnings("unchecked")
	private Vector listaClavesPrimarias;
	@SuppressWarnings("unchecked")
	private Vector listaRestricciones;
	@SuppressWarnings("unchecked")
	private Vector listaUniques;
	private Point2D posicion;
	
	public TransferEntidad(){
		
	};
	
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
	public Vector getListaAtributos() {
		return listaAtributos;
	}
	@SuppressWarnings("unchecked")
	public void setListaAtributos(Vector listaAtributos) {
		this.listaAtributos = listaAtributos;
	}
	@SuppressWarnings("unchecked")
	public Vector getListaClavesPrimarias() {
		return listaClavesPrimarias;
	}
	@SuppressWarnings("unchecked")
	public void setListaClavesPrimarias(Vector listaClavesPrimarias) {
		this.listaClavesPrimarias = listaClavesPrimarias;
	}
	@SuppressWarnings("unchecked")
	public Vector getListaRestricciones() {
		return listaRestricciones;
	}
	@SuppressWarnings("unchecked")
	public void setListaRestricciones(Vector listaRestricciones) {
		this.listaRestricciones = listaRestricciones;
	}
	@SuppressWarnings("unchecked")
	public Vector getListaUniques() {
		return listaUniques;
	}
	@SuppressWarnings("unchecked")
	public void setListaUniques(Vector listaUniques) {
		this.listaUniques = listaUniques;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return this.nombre;
	}

	@Override
	public Shape toShape() {
		// 	Si el tamaño del nombre es pequeño dibuja rectángulo standard
		if (this.nombre.length() < 8){
			return new Rectangle(-40,-15,80,30);
		}
		// Si es grande ajusta el tamaño al nombre
		int anchura = this.nombre.length() * 10 / 2;
		return new Rectangle(-anchura,-15,anchura * 2,30);
	}
	
	//	 Dibuja el segundo rectángulo externo
	public Shape outerShape(){
		Rectangle2D figura;
		// Si el tamaño del nombre es pequeño dibuja rectángulo standard
		if (this.nombre.length() < 8){
			figura = new Rectangle2D.Double(-45,-20,90,40);
			return figura;
		}
		// Si es grande ajusta el tamaño al nombre
		int anchura = (this.nombre.length() * 10 / 2) + 5;
		figura = new Rectangle2D.Double(-anchura,-20,anchura * 2,40);
		return figura;
	}
		

}

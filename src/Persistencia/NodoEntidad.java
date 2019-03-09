package Persistencia;

public class NodoEntidad {
	private String nombre;
	private EntidadYAridad entidadYAridad;
	private tipoDeEntidad tipo;
	
	public NodoEntidad(String nombre, EntidadYAridad entidadYAridad, String tipo) {
		this.entidadYAridad = entidadYAridad;
		this.nombre = nombre;
		this.tipo = new tipoDeEntidad(tipo);
	}
	public EntidadYAridad getEntidadYAridad() {
		return entidadYAridad;
	}
	@Override
	public String toString() {
		return nombre;
	}
	public String getRango() {
		int prango = this.entidadYAridad.getPrincipioRango();
		int frango = this.entidadYAridad.getFinalRango();
		String rango = " ( ";
		rango += (prango == 2147483647)?"N":prango;
		rango +=" - ";
		rango += (frango == 2147483647)?"N":frango;
		rango +=" )";
		return rango;
	}
	public boolean esNormal() {
		return tipo.esNormal();
	}
	public boolean esPadre() {
		return tipo.esPadre();
	}
	public boolean esHija() {
		return tipo.esHija();
	}
}

class tipoDeEntidad{
	private boolean[] tipo;
	public tipoDeEntidad(String t) {
		tipo = new boolean[2];
		switch(t) {
		case "padre":tipo[1]=false;tipo[0]=true;break;
		case "hija":tipo[1]=true;tipo[0]=false;break;
		default: tipo[1]=false;tipo[0]=false;
		}
	}
	public boolean esNormal() {
		return !tipo[1] && !tipo[0];
	}
	public boolean esPadre() {
		return !tipo[1] && tipo[0];
	}
	public boolean esHija() {
		return tipo[1] && !tipo[0];
	}
}
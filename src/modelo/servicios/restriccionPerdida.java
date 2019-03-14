package modelo.servicios;

public class restriccionPerdida {

	private String relacion;
	private String entidad;
	private int[] card;
	
	public restriccionPerdida(String relacion, String entidad, int from, int to) {
		this.card = new int[2];
		this.relacion = relacion;
		this.entidad = entidad;
		this.card[0] = from;
		this.card[1] = to;
	}
	//Genera el codigo en HTML
	@Override
	public String toString() {
		String s = "<p>";
		s += (card[0] == 2147483647)?"N":String.valueOf(card[0]);
		s +=" &le; ";
		s += entidad + "(" + relacion + ")";
		s +=" &le; ";
		s += (card[1] == 2147483647)?"N":String.valueOf(card[1]);
		s += "</p>";
		return s;
	}
}

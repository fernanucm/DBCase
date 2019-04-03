package modelo.servicios;

public class restriccionPerdida {

	private String relacion;
	private String clave;
	private String entidad;
	private String restriccion;
	private int[] card;
	private int tipo;
	public static final int TOTAL = 1;
	public static final int CANDIDATA = 2;
	public static final int TABLA = 3;
	
	//Para tipo candidata y tabla
	public restriccionPerdida(String par1, String par2, int tipo) {
		this.card = new int[2];
		switch(tipo) {
		case CANDIDATA:
			this.clave = par1;
			this.relacion = par2;
			break;
		case TABLA:
			this.entidad = par1;
			this.restriccion = par2;
			break;
		}
		this.tipo = tipo;
	}
	
	//Para tipo total
	public restriccionPerdida(String relacion, String entidad, int from, int to, int tipo) {
		this.card = new int[2];
		this.relacion = relacion;
		this.entidad = entidad;
		this.card[0] = from;
		this.card[1] = to;
		this.tipo = tipo;
	}
	
	public int getTipo() {
		return tipo;
	}
	
	//Genera el codigo en HTML
	@Override
	public String toString() {
			String s = "<p> ";
			switch(tipo) {
			case TOTAL:
				s += (card[0] == 2147483647)?"N":String.valueOf(card[0]);
				s +=" &le; ";
				s += entidad + "(" + relacion + ")";
				s +=" &le; ";
				s += (card[1] == 2147483647)?"N":String.valueOf(card[1]);
				break;
			case CANDIDATA:
				s+= clave + " es una clave candidata de la relacion ";
				s+= relacion;
				break;
			case TABLA:
				s += entidad + ": ";
				s += restriccion.replace("<", "&lt;") + " ";
				break;
			default:break;
			}
			s += "</p>";
			return s;
	}
}
package modelo.servicios;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class RestriccionesPerdidas extends ArrayList<restriccionPerdida>{
	
	@Override
	public String toString() {
		String total="";
		String candidata="";
		String tabla="";
		for(restriccionPerdida r : this) {
			switch(r.getTipo()) {
			case restriccionPerdida.TOTAL:total += r;break;
			case restriccionPerdida.CANDIDATA:candidata += r;break;
			case restriccionPerdida.TABLA:tabla += r;break;
			default:break;
			}
		}
		String res = "";
		res += (candidata!="")?"<h3>Claves candidatas</h3>"+candidata:"";
		res += (total!="")?"<h3>Participacion total</h3>"+total:"";
		res += (tabla!="")?"<h3>Restricciones de tabla</h3>"+tabla:"";
		return res;
	}
	
}

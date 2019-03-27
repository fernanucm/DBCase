package modelo.servicios;

import java.util.Vector;
import controlador.Controlador;
import controlador.TC;
import modelo.persistencia.DAOAtributos;
import modelo.persistencia.DAODominios;
import modelo.persistencia.DAOEntidades;
import modelo.persistencia.DAORelaciones;
import modelo.persistencia.EntidadYAridad;
import modelo.tools.TipoDominio;
import modelo.transfers.Transfer;
import modelo.transfers.TransferAtributo;
import modelo.transfers.TransferDominio;
import modelo.transfers.TransferEntidad;
import modelo.transfers.TransferRelacion;
import vista.lenguaje.Lenguaje;

@SuppressWarnings({ "unchecked", "rawtypes" ,"unlikely-arg-type"})
public class ValidadorBD extends GeneradorEsquema{
	private String mensaje;
	private static ValidadorBD INSTANCE;
	
	public ValidadorBD() {}
	
	public static ValidadorBD getInstancia() {
		if(INSTANCE==null) INSTANCE = new ValidadorBD();
		return INSTANCE;
	}
	
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
	//Dado un transfer y un texto, genera un error (formato html)
	private String error(Transfer t, String texto) {
		if(t instanceof TransferEntidad) texto = "The entity "+t.getNombre()+texto;
		else if(t instanceof TransferRelacion) texto = "The relation "+t.getNombre()+texto;
		else if(t instanceof TransferAtributo) texto = "The atribute "+t.getNombre()+texto;
		else if(t instanceof TransferDominio) texto = "The domain "+t.getNombre()+texto;
		return "<div class='error'><strong>" + Lenguaje.getMensaje(Lenguaje.ERROR)+"</strong><br>"+texto+"</div>";
	}
	//Dado un transfer y un texto, genera un warning (formato html)
	private String warning(Transfer t, String texto) {
		if(t instanceof TransferEntidad) texto = "The entity "+t.getNombre()+texto;
		else if(t instanceof TransferRelacion) texto = "The relation "+t.getNombre()+texto;
		else if(t instanceof TransferAtributo) texto = "The atribute "+t.getNombre()+texto;
		else if(t instanceof TransferDominio) texto = "The domain "+t.getNombre()+texto;
		return "<div class='warning'><strong>" + Lenguaje.getMensaje(Lenguaje.WARNING)+"</strong><br>"+texto+"</div>";
	}
	/*
	 * metodo principal
	 * boolean modelo: diferencia entre esquema logico y fisico
	 */
	protected boolean validaBaseDeDatos(boolean modelo, StringBuilder warnings){
		mensaje = "";
		boolean valido=true;
		valido &= this.validaDominios();
		valido &= this.validaAtributos();
		valido &= this.validaEntidades();
		valido &= this.validaRelaciones();
		// Mostrar el texto
		if (!valido) 
			if(modelo)controlador.mensajeDesde_SS(TC.SS_ValidacionM,mensaje);
			else controlador.mensajeDesde_SS(TC.SS_ValidacionC,mensaje);
		warnings.append(mensaje);
		return valido;
	}

	private boolean validaEntidades(){
		DAOEntidades daoEntidades= new DAOEntidades(controlador.getPath());
		Vector <TransferEntidad> entidades =daoEntidades.ListaDeEntidades();
		boolean valido=true;
		int i=0;
		TransferEntidad t = new TransferEntidad();
		while (i<entidades.size()){
			t=entidades.elementAt(i);
			//por ahora validamos las claves y avisamos de si es padre de varias isA
			valido &= validaKey(t) && this.validaNombresAtributosEntidad(t);
			validaFidelidadEntidadEnIsA(t);
			i++;
		}
		return valido;
	}
	
	private boolean validaNombresAtributosEntidad(TransferEntidad te){
		//comprueba que una entidad tenga atributos con nombres distintos.
		Vector<TransferAtributo> ats= dameAtributosEnTransfer(te.getListaAtributos());
		if (ats.size() < 1){
			mensaje += error(te,Lenguaje.getMensaje(Lenguaje.NO_ATTRIB));
			return false;
		}
		TransferAtributo ti; //= new TransferAtributo();
		TransferAtributo tj; //= new TransferAtributo();
		int i=0;
		int j=1;
		boolean valido=true;
		while (i<ats.size()){
			ti=ats.elementAt(i);
			while (j<ats.size()){
				tj=ats.elementAt(j);
				if(ti.getNombre().toLowerCase().equals(tj.getNombre().toLowerCase())) {
					valido=false;
					mensaje += error(tj,Lenguaje.getMensaje(Lenguaje.IS_REPEATED_IN_ENTITY)+te.getNombre());
				}j++;
			}
			i++;
			j=i+1;
		}
		return valido;
	}
	
	private boolean validaComponentesRelacionDebil(TransferRelacion tr){
		boolean valida=true;
		Vector<EntidadYAridad> veya =tr.getListaEntidadesYAridades();
		int tam =veya.size();
		int contD=0,contF=0;
		switch (tam){
		case 0: mensaje += error(tr,Lenguaje.getMensaje(Lenguaje.NO_ENT_RELATION));
			valida=false;
		break;
		case 1: mensaje += error(tr,Lenguaje.getMensaje(Lenguaje.ONE_ENT_WEAK_REL));
			valida=false;
		break;
		default:
			//en una relacion debil, necesitamos que haya como minimo una entidad fuerte y una debil.
			contD = this.dameNumEntidadesDebiles(tr);
			contF= tam-contD;
		}
		if(contD<1){
			mensaje += error(tr,Lenguaje.getMensaje(Lenguaje.NO_WEAK_ENT_REL));
			valida=false;
		}
		if (contD > 1){
			mensaje += error(tr,Lenguaje.getMensaje(Lenguaje.MANY_WEAK_ENT_REL));
			valida=false;
		}
		if(contF<1){
			mensaje += error(tr,Lenguaje.getMensaje(Lenguaje.NO_STRONG_ENT_REL));
			valida=false;
		}
		return valida;
	}
	
	private void validaFidelidadEntidadEnIsA(TransferEntidad te){
		DAORelaciones daoRelaciones= new DAORelaciones(controlador.getPath());
		Vector <TransferRelacion> relaciones =daoRelaciones.ListaDeRelaciones();
		//si la entidad es padre de una relacion isA comprueba que solo lo sea de una, sino, dara un aviso.
		int i=0;
		int papi=0;
		while (i<relaciones.size()){
			TransferRelacion tr=(TransferRelacion)relaciones.elementAt(i);
			if (tr.isIsA()){
				Vector<EntidadYAridad> veya =tr.getListaEntidadesYAridades();
				if (!veya.isEmpty()&& veya.elementAt(0).getEntidad()==te.getIdEntidad()) papi++;
			}i++;
		}
		if (papi>1)	mensaje += warning(te,Lenguaje.getMensaje(Lenguaje.ENT_PARENT_IN)+papi+Lenguaje.getMensaje(Lenguaje.ISA_RELATIONS));
	}

	//metodos privados de validacion de relaciones.
	private boolean validaComponentesRelacionIsA(TransferRelacion tr){
		boolean valida=true;
		if(dameNumEntidadesDebiles(tr)>0){
			valida= false;
			mensaje += error(tr,Lenguaje.getMensaje(Lenguaje.NO_WEAK_ENT_RELAT));
		}
		else{
			Vector<EntidadYAridad> veya =tr.getListaEntidadesYAridades();
			int tam =veya.size();
			switch (tam){
			case 0: mensaje += error(tr,Lenguaje.getMensaje(Lenguaje.NO_PARENT_REL));
					valida=false;
			break;
			case 1: mensaje += error(tr,Lenguaje.getMensaje(Lenguaje.NO_CHILD_RELATION));
					valida=false;
			break;
			default: break;
			
			}
		}
		return valida;
	}

	private int dameNumEntidadesDebiles (TransferRelacion tr){
		DAOEntidades daoEntidades= new DAOEntidades(controlador.getPath());
		TransferEntidad aux = new TransferEntidad();
		Vector<EntidadYAridad> veya =tr.getListaEntidadesYAridades();
		int cont=0;
		for (int k =0;k<veya.size();k++){
			aux.setIdEntidad(veya.elementAt(k).getEntidad());
			aux=daoEntidades.consultarEntidad(aux);
			if (aux.isDebil()) cont++;
		}
		return cont;
	}

	private boolean misDebilesEstanEnDebiles(TransferRelacion rel){
		DAORelaciones daoRelaciones = new DAORelaciones(this.getControlador().getPath());
		DAOEntidades daoEntidades = new DAOEntidades(this.getControlador().getPath());
		Vector<TransferRelacion> relaciones= daoRelaciones.ListaDeRelaciones();
		boolean enDebil=false;
		boolean encontrada=false;
		TransferEntidad entDebil= new TransferEntidad();
		int k=0;
		Vector<EntidadYAridad> veyaRel=rel.getListaEntidadesYAridades();
		while(k<veyaRel.size()&&!encontrada){
			EntidadYAridad eya = veyaRel.elementAt(k);
			entDebil.setIdEntidad(eya.getEntidad());
			entDebil=daoEntidades.consultarEntidad(entDebil);
			if(entDebil.isDebil()) encontrada=true;
			k++;
		}
		int i=0;
		int j=0;
		while(i<relaciones.size()&& !enDebil){
			TransferRelacion tr=relaciones.elementAt(i);
			Vector<EntidadYAridad> veya=tr.getListaEntidadesYAridades();
			j=0;
			while(j<veya.size()&&!enDebil){
				EntidadYAridad eya = veya.elementAt(j);
				if (eya.getEntidad()==entDebil.getIdEntidad() && tr.getTipo().equals("Debil")) enDebil=true;
				j++;
			}
			i++;
		}

		return enDebil;	
	}
	
	private boolean validaComponentesRelacionNormal(TransferRelacion tr){
		boolean valida=true;
		if(dameNumEntidadesDebiles(tr)>0 && !this.misDebilesEstanEnDebiles(tr)){
			valida= false;
			mensaje += error(tr,Lenguaje.getMensaje(Lenguaje.NO_WEAK_ENT_RELAT));
		}
		else{
			Vector<EntidadYAridad> veya =tr.getListaEntidadesYAridades();
			int tam =veya.size();
			switch (tam){
			case 0: mensaje += error(tr,Lenguaje.getMensaje(Lenguaje.NO_ENT_RELATION));
					valida = false;
			break;
			case 1: mensaje += error(tr,Lenguaje.getMensaje(Lenguaje.ONE_ENT_REL));
					valida = false;
			break;
			default: break;
			}
		}
		return valida;
	}

	private boolean validaRelaciones(){
		DAORelaciones daoRelaciones= new DAORelaciones(controlador.getPath());
		Vector <TransferRelacion> relaciones =daoRelaciones.ListaDeRelaciones();
		boolean valido=true;
		int i=0;
		TransferRelacion t = new TransferRelacion();
		while (i<relaciones.size()){
			t=relaciones.elementAt(i);
			if(t.isIsA()) valido&= validaComponentesRelacionIsA(t);
			else if (t.getTipo().equals("Normal")) 
				valido&=validaComponentesRelacionNormal(t);
			else valido&=validaComponentesRelacionDebil(t);
			i++;
		}
		return valido;		
	}

	private boolean validaDominios(){
		DAODominios daoDominios= new DAODominios(super.controlador.getPath());
		Vector <TransferDominio> dominios = daoDominios.ListaDeDominios();
		boolean valido=true;
		int i=0;
		TransferDominio t;
		while (i<dominios.size()){
			t=dominios.get(i);
			boolean encontrado = false;
			int j = i+1;
			while (!encontrado && j<dominios.size()){
				encontrado = t.getNombre().equals(dominios.get(j).getNombre());
				j++;
			}
			
			if (encontrado){
				valido=false;
				mensaje += error(t,Lenguaje.getMensaje(Lenguaje.REPEATED_DOM_NAMES));				
			}
			// Validar que tiene valores y son distintos
			Vector<String> valores = t.getListaValores();
			if (valores == null || valores.size() < 1){
				valido=false;
				mensaje += error(t,Lenguaje.getMensaje(Lenguaje.NO_VALUE_DOM));
			}else{
				String valorComprobado = null;
				int k = 0;
				boolean seRepite = false;
				while (k < valores.size() && !seRepite){
					valorComprobado = valores.get(k);
					int m = k + 1;
					while (m < valores.size() && !seRepite){
						seRepite = (valorComprobado.equals(valores.get(m)));
						m++;
					}
					k++;
				}
				if (seRepite){
					valido=false;
					mensaje += error(t,Lenguaje.getMensaje(Lenguaje.THE_VALUE) + valorComprobado +Lenguaje.getMensaje(Lenguaje.IS_REPEATED));
				}
			}
			// Comprobar si se usa (esto solo da un aviso si falla)
			DAOAtributos daoAtributos= new DAOAtributos(controlador.getPath());
			Vector <TransferAtributo> atributos =daoAtributos.ListaDeAtributos();
			boolean esta=false;
			int k=0;
			while(!esta && k<atributos.size()){
				esta = atributos.get(k).getDominio().equalsIgnoreCase(t.getNombre());
				k++;
			}
			if (!esta) mensaje +=  warning(t,Lenguaje.getMensaje(Lenguaje.NO_USE_DOM));
			i++;
		}
		return valido;
	}
	
	//metodos privados de validacion de entidades
	private boolean validaKey(TransferEntidad te){
		DAOAtributos daoAtributos= new DAOAtributos(controlador.getPath());
		//valida si la entidad tiene clave y si esta dentro de sus atributos.
		//ademas si la entidad es debil, debe tener un atributo discriminante.
		boolean valido=true;
		boolean noMulti = true;
		boolean compuesto=false;
		Vector atbs = te.getListaAtributos();
		Vector keys = te.getListaClavesPrimarias();
		int contador=0;
		TransferAtributo aux= new TransferAtributo();
		Vector<int[]> resultados =entidadPerteneceAisA(te);
		int enIsA = 0;

		switch (resultados.size()){
		case 0: enIsA=-1; break; //no aparece
		case 1: enIsA= resultados.elementAt(0)[1]; break; //aparece una vez nos quedamos con lo que haya
		default:  //si aparece mas nos quedamos con la que tenga como padre
			for (int m=0;m<resultados.size();m++){
				if(resultados.elementAt(m)[1]==1) enIsA=1;
			}
		break;

		}

		// si no tiene clave primaria
		boolean relacionada = false;
		if (keys.isEmpty()&& enIsA<=0) {
			// Comprobar que no esta asociada a ninguna relacion
			DAORelaciones daoRelaciones= new DAORelaciones(controlador.getPath());
			Vector<TransferRelacion> rels = daoRelaciones.ListaDeRelaciones();
			int k = 0;
			while (k<rels.size() && !relacionada){
				Vector<EntidadYAridad> ents = rels.get(k).getListaEntidadesYAridades();
				int m = 0;
				while (!relacionada && m < ents.size()){
					relacionada = ents.get(m).getEntidad() == te.getIdEntidad();
					m++;
				}
				k++;
			}
			if (relacionada){
				if (te.isDebil()) mensaje += error(te, Lenguaje.getMensaje(Lenguaje.NOKEY_WEAK_ENTITY));
				else mensaje += error(te,Lenguaje.getMensaje(Lenguaje.NOKEY_ENTITY_RELATION));
				valido=false;
			}
		}
		else
			// si tiene clave primaria, que esten dentro de sus atributos
			relacionada = true;
			if (!te.isDebil())
				if(!this.vectorEnterosContenidoEnVector(keys, atbs)) {
					//dos casos. que la clave sea un atbto compuesto o que no haya clave.
					//comprobamos que hay un atbto compuesto y si lo hay, lo comprobamos.
					while (contador<atbs.size()){
						aux.setIdAtributo(this.objectToInt(atbs.elementAt(contador)));
						aux=daoAtributos.consultarAtributo(aux);
						if(aux.getCompuesto()){
							if (compruebaClaveCompuesto(keys,aux)) compuesto=true; 

						}
						contador++;
					}
					if (compuesto) mensaje += warning(te,Lenguaje.getMensaje(Lenguaje.ALL_CHILDREN_KEYS)); 
					else{
						valido=false;
						mensaje += error(te,Lenguaje.getMensaje(Lenguaje.NO_ATTRIB_KEY));
					}
				}
				else{ // comprobamos que no haya una clave que sea un atributo multivalorado.
					while (contador<keys.size()&& noMulti){
						aux.setIdAtributo(this.objectToInt(keys.elementAt(contador)));
						aux=daoAtributos.consultarAtributo(aux);
						if(aux.isMultivalorado()){
							valido=false;
							noMulti=false;
							mensaje += error(aux,Lenguaje.getMensaje(Lenguaje.MULTIVALUE_KEY));
						}
						contador++;
					}
				}
		if (valido && !relacionada) mensaje += warning(te,Lenguaje.getMensaje(Lenguaje.NO_PRIMARY_KEY));
		return valido;
	}
	
	private boolean vectorEnterosContenidoEnVector(Vector subVector, Vector vector){
		// comprueba si un vector de enteros esta dentro de otro si estos no estan parametrizados (no funciona contains)
		boolean esta=false;
		boolean contiene=true;
		for (int i =0; i<subVector.size();i++){
			esta=false;
			for (int j=0;j<vector.size();j++)
				if (objectToInt(subVector.elementAt(i))==objectToInt(vector.elementAt(j))) esta=true;
			contiene= esta && contiene;
		}
		return contiene;
	}
	
	/*lo que hay que tener en cuenta para ver si una entidad pertenece a una relacion isA (como hija) segun nuestro
	 disenio es que sea como minimo la segunda de la lista de entidades y aridades de la relacion. Esto es porque 
	 la primera indica que es el padre y este si tiene que tener clave.
	 El metodo devuelve lo siguiente:
	 en la primera componente: 
	 la relacion isA en la que actua.
	 en la segunda componente: 
	 si no pertenece a una isA -> -1
	 sino: si es padre -> 0
	 si es hija -> 1
	 */
	private Vector<int[]> entidadPerteneceAisA(TransferEntidad te){
		DAORelaciones daoRelaciones= new DAORelaciones(controlador.getPath());
		Vector <TransferRelacion> relaciones =daoRelaciones.ListaDeRelaciones();
		Vector<int[]> resultados = new Vector<int[]>();

		int i=0;
		int j=0;
		while (i<relaciones.size()){
			TransferRelacion tr=(TransferRelacion)relaciones.elementAt(i);
			Vector<EntidadYAridad> veya =tr.getListaEntidadesYAridades();
			int[] parejaResultados = new int [2];
			parejaResultados[0]=tr.getIdRelacion();
			while (j<veya.size()){
				EntidadYAridad eya = veya.elementAt(j);
				if(eya.getEntidad()==te.getIdEntidad()&& tr.isIsA()){
					if(j==0){
						parejaResultados[1]=0;
						resultados.add(parejaResultados);
					}else{
						parejaResultados[1]=1;
						resultados.add(parejaResultados);
					}
				}
				j++;
			}
			j=0;
			i++;
		}
		return resultados;
	}
	
	private boolean compruebaClaveCompuesto(Vector clavesEntidad,TransferAtributo ta) {
		DAOAtributos daoAtributos= new DAOAtributos(controlador.getPath());
		int i=0;
		boolean todosBien=true;
		Vector subs = ta.getListaComponentes();
		TransferAtributo aux= new TransferAtributo();
		if (!ta.getCompuesto()){
			if (estaEnVectorDeEnteros(clavesEntidad, ta.getIdAtributo())) return true;
			else return false;
		}
		else{
			while (i<subs.size() && todosBien){
				aux.setIdAtributo(objectToInt(subs.elementAt(i)));
				aux=daoAtributos.consultarAtributo(aux);
				todosBien=todosBien && compruebaClaveCompuesto(clavesEntidad, aux);
				i++;
			}
			return todosBien;
		}	
	}
	
	/* hemos de validar lo siguiente:
	 * - Cada atributo pertenece a una sola entidad
	 * - Cada atributo tiene un dominio definido
	 * - Los atributos multivalorados no son clave
	 */
	private boolean validaAtributos(){
		
		DAOAtributos daoAtributos= new DAOAtributos(controlador.getPath());
		Vector <TransferAtributo> atributos =daoAtributos.ListaDeAtributos();
		boolean valido=true;
		int i=0;
		TransferAtributo t = new TransferAtributo();
		while (i<atributos.size()){
			t=atributos.elementAt(i);
			valido&=validaFidelidadAtributo(t)&& validaDominioDeAtributo(t);
			if (t.getCompuesto()) valido &= validaCompuesto(t); 
			i++;
		}
		return valido;
	}
	// comprueba si el atributo pertenece solo a una entidad.
	private boolean validaFidelidadAtributo(TransferAtributo ta){
		DAOAtributos daoAtributos= new DAOAtributos(controlador.getPath());
		DAOEntidades daoEntidades= new DAOEntidades(controlador.getPath());
		DAORelaciones daoRelaciones= new DAORelaciones(controlador.getPath());
		Vector <TransferAtributo> atributos =daoAtributos.ListaDeAtributos();
		Vector <TransferEntidad> entidades =daoEntidades.ListaDeEntidades();
		Vector <TransferRelacion> relaciones =daoRelaciones.ListaDeRelaciones();
		boolean valido=true;
		boolean enEntidad=false;
		TransferEntidad te= new TransferEntidad();
		TransferRelacion tr= new TransferRelacion();
		int cont =0;
		int i=0;
		while (i<entidades.size()&& cont<=1){
			te= entidades.elementAt(i);
			if (estaEnVectorDeEnteros(te.getListaAtributos(),ta.getIdAtributo())){
				cont++;
				enEntidad=true;
			}
			i++;
		}
		if (!enEntidad){
			cont=0;
			i=0;
			while (i<relaciones.size()&& cont<=1){
				tr= relaciones.elementAt(i);
				if (estaEnVectorDeEnteros(tr.getListaAtributos(),ta.getIdAtributo())) cont++;
				i++;
			}
		}

		if (cont==0){
			//entonces es un subatributo, comprobamos q no esta repetido entre los subatributos
			i=0;
			int contSubAtrib=0;
			TransferAtributo aux= new TransferAtributo();
			while (i<atributos.size()&& contSubAtrib<=1){
				aux= atributos.elementAt(i);
				if(aux.getCompuesto())
					if (estaEnVectorDeEnteros(aux.getListaComponentes(),ta.getIdAtributo()))
						contSubAtrib++;
				i++;
			}
			if (contSubAtrib!=1){
				mensaje += error(ta,Lenguaje.getMensaje(Lenguaje.MANY_ATTRIB_SUBATTRIB));
				valido=false;
			}
		}
		else if (cont!=1){ 
			mensaje += error(ta,Lenguaje.getMensaje(Lenguaje.MANY_ENTITIES));
			valido=false;
		}
		return valido; 
	}
	
	private boolean validaCompuesto(TransferAtributo ta){
		boolean valido=true;
		int numSubs=ta.getListaComponentes().size();
		switch (numSubs) {
		case 0: valido=false;
				mensaje += error(ta, Lenguaje.getMensaje(Lenguaje.NO_SUBATTRIB));
		break;
		case 1: mensaje += warning(ta,Lenguaje.getMensaje(Lenguaje.ONE_SUBATTRIB));
		break;
		}

		return valido;
	}

	private String quitaParenDominio(String dominio){
		int c=dominio.indexOf("(");
		return dominio.substring(0,c);
	}
	
	private boolean validaDominioDeAtributo(TransferAtributo ta){
		//comprueba que tenga dominio
		boolean valido=true;
		String dom = ta.getDominio();
		if (ta.getCompuesto())
			if(!dom.equals("null")){
				valido=false;
				mensaje +=  error(ta, Lenguaje.getMensaje(Lenguaje.COMPOSED_ATTRIBUTE));
			}
		else{
			if (dom.equals("")||dom.equals("null")){ 
				valido=false;
				mensaje += error(ta, Lenguaje.getMensaje(Lenguaje.NO_DOMAIN));
			}
			else{
				if (dom.contains("(")) dom=quitaParenDominio(dom);
				int i=0;
				while(i<TipoDominio.values().length && !TipoDominio.values()[i].equals(dom))i++;
				if (i>TipoDominio.values().length){
					mensaje += error(ta, Lenguaje.getMensaje(Lenguaje.UNKNOWN_DOMAIN));
					valido=false;
				}
			}
		}
		return  valido;
	}
}
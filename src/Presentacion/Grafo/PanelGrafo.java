 package Presentacion.Grafo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;
import Controlador.Controlador;
import Controlador.TC;
import LogicaNegocio.Transfers.Transfer;
import LogicaNegocio.Transfers.TransferAtributo;
import LogicaNegocio.Transfers.TransferEntidad;
import LogicaNegocio.Transfers.TransferRelacion;
import Persistencia.EntidadYAridad;
import Presentacion.Lenguajes.Lenguaje;
import Presentacion.Theme.Theme;
import Utilidades.ImagePath;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeIndexFunction;
import edu.uci.ics.jung.visualization.DefaultVertexLabelRenderer;
import edu.uci.ics.jung.visualization.EdgeLabelRenderer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.PickableVertexPaintTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.picking.PickedState;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class PanelGrafo extends JPanel implements Printable, KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2455701194153996759L;

	Graph<Transfer,Object> graph;

	VisualizationViewer<Transfer,Object> vv;

	GrafoLayout<Transfer,Object> layout;

	private Controlador controlador;

	
	// Mapas para el acceso correcto a los nodos
	protected Map<Integer,TransferEntidad> entidades;
	protected Map<Integer,TransferAtributo> atributos;
	protected Map<Integer,TransferRelacion> relaciones;

	private Theme theme;
		
	@SuppressWarnings("unchecked")
	public PanelGrafo(Vector<TransferEntidad> entidades, Vector<TransferAtributo> atributos,
			Vector<TransferRelacion> relaciones, Theme theme){
		this.theme = theme;
		this.setLayout(new GridLayout(1,1));
		//Para que los grafos admitan paralelas el tipo de grafo debe ser este:
		graph = new UndirectedSparseMultigraph<Transfer, Object>();
		// Inicializa el layout, el visualizador y el renderer
		//layout = new FRLayout<Transfer, Double>(graph);
		layout = new GrafoLayout<Transfer, Object>(graph);

		// Inserta las entidades, atributos, relaciones al grafo
		this.generaTablasNodos(entidades, atributos, relaciones);
		Collection<TransferEntidad> entities = this.entidades.values();
		for (Iterator<TransferEntidad> it = entities.iterator(); it.hasNext();){
			TransferEntidad entidad = it.next();
			graph.addVertex(entidad);
			layout.setLocation(entidad, entidad.getPosicion());
		}
		Collection<TransferAtributo> attributes = this.atributos.values();
		for (Iterator<TransferAtributo> it = attributes.iterator(); it.hasNext();){
			TransferAtributo atributo = it.next();
			graph.addVertex(atributo);
			layout.setLocation(atributo, atributo.getPosicion());
		}
		Collection<TransferRelacion> relations = this.relaciones.values();
		for (Iterator<TransferRelacion> it = relations.iterator(); it.hasNext();){
			TransferRelacion relation = it.next();
			graph.addVertex(relation);
			layout.setLocation(relation, relation.getPosicion());
		}
		
		// Añade las aristas al grafo
		// Aristas entre relaciones y entidades
		for (Iterator<TransferRelacion> it = relations.iterator(); it.hasNext();){
			TransferRelacion relation = it.next();
			if (!relation.getListaAtributos().isEmpty()){
				// Añado sus atributos
				for (Iterator<String> it2 = relation.getListaAtributos().iterator(); it2.hasNext();){
					Integer id = Integer.parseInt(it2.next());
					graph.addEdge(new Double(Math.random()), 
							relation,
							this.atributos.get(id));
				}
			}
			if (!relation.getListaEntidadesYAridades().isEmpty()){
				for (Iterator<EntidadYAridad> it3 = relation.getListaEntidadesYAridades().iterator(); it3.hasNext();){
					EntidadYAridad data = it3.next();
					graph.addEdge(data, 
							relation, this.entidades.get(data.getEntidad()));
				}
			}
		}
		// Aristas entre entidades y atributos
		for (Iterator<TransferEntidad> it = entities.iterator(); it.hasNext();){
			TransferEntidad entity = it.next();
			if (!entity.getListaAtributos().isEmpty()){
				for (Iterator<String> it2 = entity.getListaAtributos().iterator(); it2.hasNext();){
					Integer id = Integer.parseInt(it2.next());
					graph.addEdge(new Double(Math.random()), 
							entity,
							this.atributos.get(id));
				}
			}
			// Señala la/s claves primarias como tales
			if (!entity.getListaClavesPrimarias().isEmpty()){
				for (Iterator<String> it2 = entity.getListaClavesPrimarias().iterator(); it2.hasNext();){
					Integer id = Integer.parseInt(it2.next());
					this.atributos.get(id).setClavePrimaria(true);
				}
			}
		}
		// Aristas entre atributos compuestos y sus valores
		for (Iterator<TransferAtributo> it = attributes.iterator(); it.hasNext();){
			TransferAtributo atrib = it.next();
			if (atrib.getCompuesto()){
				for (Iterator<String> it2 = atrib.getListaComponentes().iterator(); it2.hasNext();){
					Integer id = Integer.parseInt(it2.next());
					graph.addEdge(new Double(Math.random()), 
							atrib,
							this.atributos.get(id));
				}
			}
		}

		vv = new VisualizationViewer<Transfer, Object>(layout);
		// this class will provide both label drawing and vertex shapes
		LabelRenderer<Transfer,Object> vlasr = new LabelRenderer<Transfer, Object>();
		vv.setDoubleBuffered(true); // Incrementa rendimiento
		
		
		
		//Color del panel grande sobre el que se pinta
		vv.setBackground(theme.background());
		
		//Renderiza las lineas que unen los elementos
		vv.getRenderer().setEdgeLabelRenderer(new MiBasicEdgeLabelRenderer<Transfer, Object>());

		RenderContext<Transfer, Object> rc = vv.getRenderContext();
		rc.getEdgeLabelRenderer();
		rc.setLabelOffset(10);
		rc.getParallelEdgeIndexFunction();
		
		EdgeIndexFunction<Transfer, Object> mp = MiParallelEdgeIndexFunction.getInstance(); 
		//PredicatedParallelEdgeIndexFunction<Transfer,Object> otrop = rc.getParallelEdgeIndexFunction();
		//otrop.get
		
		
		rc.setParallelEdgeIndexFunction( mp );  
		//rc.setEdgeLabelRenderer((EdgeLabelRenderer) new MiBasicEdgeLabelRenderer());
		
		
		// Escribe el texto del elemento
		vv.getRenderContext().setVertexLabelTransformer(new Transformer<Transfer, String>(){
			public String transform(Transfer input) {
				if (input instanceof TransferEntidad) {
					return "<html><center>"+input+"<p>";
				}
				if (input instanceof TransferAtributo) {
					TransferAtributo atributo = (TransferAtributo) input;
					String texto = new String();
					texto += "<html><center>";
					if (atributo.isClavePrimaria()){
						texto += "<U>";
					}
					texto += input;
					if (atributo.isClavePrimaria()){
						texto += "</U>";
					}
					texto += "<p>";
					return texto;
				}
				if (input instanceof TransferRelacion) {
					return "<html><center><font color=\"white\">"+input+"<p></font>";
				}
				return "<html><center>"+input+"<p>";
			}

		});
		

		vv.getRenderContext().setVertexShapeTransformer(vlasr);
		//Color de la letra al pinchar
		vv.getRenderContext().setVertexLabelRenderer(new DefaultVertexLabelRenderer(Color.black));
		//Ancho de las aristas
		vv.getRenderContext().setEdgeStrokeTransformer(new ConstantTransformer<Stroke>(new BasicStroke(0.5f)));
		//Hace las lineas rectas
		vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line());
		//Escribe datos sobre las líneas
		vv.getRenderContext().setEdgeLabelTransformer(new Transformer<Object, String>(){
			public String transform(Object input) {
				if (input instanceof EntidadYAridad){
					EntidadYAridad dato = (EntidadYAridad)input;
					String strPRango;
					String strfRango;
					String strRol;
					if (dato.getPrincipioRango() == 0 && // Si es IsA no escribe 
						dato.getFinalRango() == 0){
						return null;
					}
					if (dato.getPrincipioRango() == Integer.MAX_VALUE)
						strPRango = "n";
					else strPRango = Integer.toString(dato.getPrincipioRango());
					if (dato.getFinalRango() == Integer.MAX_VALUE)
						strfRango = "n";
					else strfRango = Integer.toString(dato.getFinalRango());
					strRol = dato.getRol();
					return "<html><center><font size=\"5\" face=\"avenir\">"+ strPRango + "   -   "+ strfRango + "   "+ strRol+"<p>";
				}
				else return null; // Si no es una relación no escribe la aridad
			}
		});
		
		//Color elementos
		vv.getRenderer().setVertexRenderer(new VertexRenderer<Transfer,Object>(theme.entity(),theme.entity(), true, theme));
		//Etiquetas de los vertices
		vv.getRenderer().setVertexLabelRenderer(vlasr);
		
		//Ancho del borde de los elementos
		vv.getRenderContext().setVertexStrokeTransformer(new Transformer<Transfer,Stroke>(){
			@Override
			public Stroke transform(Transfer arg0) {
			
				return new BasicStroke(0.5f);
		}});
		
		vv.getRenderer().setEdgeRenderer(new EdgeRenderer<Transfer,Object>());
		// add a listener for ToolTips
		vv.setVertexToolTipTransformer(new ToStringLabeller<Transfer>());

		final DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();		

		final PopUpMenu clickDerecho = new PopUpMenu();

		
		// El evento se dispara al terminar de mover un nodo
		graphMouse.add(new PickingGraphMousePlugin<Transfer, Double>(){
			@Override
			public void mouseReleased(MouseEvent e) {
				
				SwingUtilities.invokeLater(doFocus);
				VisualizationViewer<Transfer,Double> vv2 = (VisualizationViewer<Transfer,Double>)e.getSource();
				//Aqui empieza lo de cambiar la fuente
				final PickedState<Transfer> ps = vv2.getPickedVertexState();
				
				//Marca los elementos seleccionados con un borde más ancho
				vv2.getRenderContext().setVertexStrokeTransformer(new Transformer<Transfer,Stroke>(){
					@Override
					public Stroke transform(Transfer arg0) {
						for (Transfer t : ps.getPicked()) 							
							if(arg0.equals(t)) {
								System.out.println(t.getPosicion().getX() + " - " + t.getPosicion().getY());
								return new BasicStroke(3f);
							}
						
						return new BasicStroke(0.5f);
				}});
				
				final int bt = e.getButton();
				/*Esto es para que cuando pinches en el botón derecho no se quede en negrita lo que seleccionastes
				 * pinchando con el botón izquierdo*/
				Transformer<Transfer, Font> vertexFontAux = new Transformer<Transfer,Font>() {
		          public Font transform(Transfer arg0) {
		            	if( ps.getPicked().contains(arg0) || (bt == 3)){
			          		//ps.pick(arg0, false);
		            		return new Font("Helvetica", Font.PLAIN, 12);
		            	}
		            	else{
		            		//ps.pick(arg0, false);
		            		return new Font("Helvetica", Font.PLAIN, 12);
		            	}
		            	
		            }
				}
		        ;
		        vv2.getRenderContext().setVertexFontTransformer(vertexFontAux);
				//Aquí termina lo de quitar lo seleccionado con el botón izquierdo
		        
		        
				if(e.getModifiers() == modifiers) {
					if(down != null) {
						Point2D out = obtenerPuntoExacto(e);
						if(vertex == null && heyThatsTooClose(down, out, 5) == false) {
							pickContainedVertices(vv2, down, out, true);
														
							/////esto sirve para marcar en negrita los nodos que queden dentro del cuadrado de seleccion 
							Transformer<Transfer, Font> vertexFontAux2 = new Transformer<Transfer,Font>() {
						          public Font transform(Transfer arg0) {
						            	if( ps.getPicked().contains(arg0) || (bt == 3)){
							          		//ps.pick(arg0, false);
						            		return new Font("Arial", Font.BOLD, 15);
						            	}
						            	else{
						            		//ps.pick(arg0, false);
						            		return new Font("Helvetica", Font.PLAIN, 12);
						            	}
						            }
								};
								vv2.getRenderContext().setVertexFontTransformer(vertexFontAux2);
							/////
						}
					
						if (vertex != null){	
							//TODO Con esto se cambia la fuente del elemento seleccionado con el botón izq :)
							Transformer<Transfer, Font> vertexFont = new Transformer<Transfer,Font>() {
					            public Font transform(Transfer arg0) {
					            	//if((ps.getSelectedObjects().length!=0)&&( ps.getSelectedObjects()[0].equals(arg0))){
					               		//String aux1= ps.getPicked().toString();
					            		//System.out.println("Está seleccionado el nodo"+ aux1);
					            		String aux="";
					            	for (Transfer t : ps.getPicked()){	
					            		aux= t.toString();
					            		aux="[".concat(aux);
					            		aux=aux.concat("]");
					            		break;
					            	}
					            	boolean encontrado=false;
					            	int i=0;
					            	if(ps.getSelectedObjects().length!=0){
						            	while ((i<ps.getSelectedObjects().length)&&(!encontrado)){
						            		if(ps.getSelectedObjects()[i].equals(arg0))
						            			encontrado =true;
						            		i++;
						            	}
						            	if(ps.getPicked().toString().equals(aux)&&(encontrado) || ps.getPicked().contains(arg0) && encontrado){					    
						            		return new Font("Arial", Font.BOLD, 15);
						            		
						            	}
						            	else{
						            		return new Font("Helvetica", Font.PLAIN, 12);	
						            		
						            	}
					            	}
					            	else{
						            		return new Font("Helvetica", Font.PLAIN, 12);					            	
						            }
					            	
								}
					        };
							vv2.getRenderContext().setVertexFontTransformer(vertexFont);
							//Aquí termina lo de cambiar la fuente del elemento seleccionado con el botón izq!!!
														
							for (Transfer t : ps.getPicked()){
								Point2D actual = layout.getLocation(t);
								// Ha pulsado en un nodo -> Obtenemos la información primero
								if (ps.getSelectedObjects().length == 1){	
									System.out.println("Se ha pulsado en el nodo " + t);
									EnviaInformacionNodo(t);
								}
								// Si se ha movido lo guardamos
								if (!actual.equals(t.getPosicion())){
									if (t instanceof TransferEntidad) {
										TransferEntidad entidad = (TransferEntidad) t;
										TransferEntidad clon = entidad.clonar();
										clon.setPosicion(actual);
										controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_MoverEntidad, clon);
									}
									if (t instanceof TransferAtributo) {
										TransferAtributo atributo = (TransferAtributo) t;
										TransferAtributo clon = atributo.clonar();
										clon.setPosicion(actual);
										controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_MoverAtributo, clon);
									}
									if (t instanceof TransferRelacion) {
										TransferRelacion relacion = (TransferRelacion) t;
										TransferRelacion clon = relacion.clonar();
										clon.setPosicion(actual);
										controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_MoverRelacion, clon);
									}
								}
							}
						} else { // Si se ha pulsado en el vacío
							System.out.println("Se ha pulsado en espacio vacío");
							EnviaInformacionNodo(null);
						}
					}
				} else if(e.getModifiers() == this.addToSelectionModifiers) {
									
					if(down != null) {
						Point2D out = obtenerPuntoExacto(e);

						if(vertex == null && heyThatsTooClose(down,out,5) == false) {
							pickContainedVertices(vv2, down, out, false);
						}
					}
				}
				down = null;
				vertex = null;
				edge = null;
				rect.setFrame(0,0,0,0);
				vv2.removePostRenderPaintable(lensPaintable);
				vv2.repaint();
			}
			private boolean heyThatsTooClose(Point2D p, Point2D q, double min) {
				return Math.abs(p.getX()-q.getX()) < min &&
				Math.abs(p.getY()-q.getY()) < min;
			}
		});

		// Plugin para el acceso al menú desplegable
		graphMouse.add(new AbstractPopupGraphMousePlugin(){
			@Override
			protected void handlePopup(final MouseEvent e) {
				final VisualizationViewer<Transfer,Double> vv =
					(VisualizationViewer<Transfer,Double>)e.getSource();
				Point2D p = e.getPoint();
				
				GraphElementAccessor<Transfer,Double> pickSupport = vv.getPickSupport();
				if(pickSupport != null) {
					final Transfer v = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
					if(v != null) { // Se ha pinchado en un nodo
						clickDerecho.Reinicializa(v,p);
						clickDerecho.show(vv, e.getX(), e.getY());
					}else{ // Se ha pinchado en el área
						p = obtenerPuntoExacto(e);
						clickDerecho.Reinicializa(null,p);
						clickDerecho.show(vv, e.getX(), e.getY());
					}
				}
			}

		});

		vv.setGraphMouse(graphMouse);
		vv.addKeyListener(this);

    	//Container content = this; //getRootPane();
		//GraphZoomScrollPane gzsp = new GraphZoomScrollPane(vv);
		vv.setDoubleBuffered(true);
		this.add(vv);
		//content.add(gzsp);
		//content.setSize(this.getHeight(),this.getWidth());
		graphMouse.setMode(ModalGraphMouse.Mode.PICKING);		
	}
	
		
	public Controlador getControlador() {
		return controlador;
	}

	public void setControlador(Controlador controlador) { 
		this.controlador = controlador;
	}

	private void generaTablasNodos(Vector<TransferEntidad> entidades, Vector<TransferAtributo> atributos,
			Vector<TransferRelacion> relaciones){
		this.entidades = new HashMap<Integer, TransferEntidad>();
		this.atributos = new HashMap<Integer, TransferAtributo>();
		this.relaciones = new HashMap<Integer, TransferRelacion>();
		// Inserta las entidades con su id como clave
		for (Iterator<TransferEntidad> it = entidades.iterator(); it.hasNext();){
			TransferEntidad entidad = it.next();
			this.entidades.put(entidad.getIdEntidad(), entidad);
		}
		// Inserta los atributos con su id como clave
		for (Iterator<TransferAtributo> it = atributos.iterator(); it.hasNext();){
			TransferAtributo atributo = it.next();
			this.atributos.put(atributo.getIdAtributo(), atributo);
		}
		// Inserta las relaciones con su id como clave
		for (Iterator<TransferRelacion> it = relaciones.iterator(); it.hasNext();){
			TransferRelacion relacion = it.next();
			this.relaciones.put(relacion.getIdRelacion(), relacion);
		}
		
	}
	
	/*
	 * Oyentes teclado
	 */
	private Runnable doFocus = new Runnable() {
	     public void run() {
	    	vv.grabFocus();
	     }
	};
	
	public void keyPressed( KeyEvent e ){
		switch (e.getKeyCode()){
			case 127: {
				suprimir();
				break;
			}case 83://CTRL S
				if (e.isControlDown()){
					System.out.println("control s");
					this.controlador.mensajeDesde_GUIPrincipal(TC.GUI_Principal_Click_Submenu_Guardar, null);
				}
					
				break;
		}	
	}
	
	private void suprimir(){
		PickedState<Transfer> p = vv.getPickedVertexState();
		int seleccionados=0;
		for (@SuppressWarnings("unused") Transfer t : p.getPicked()){
			seleccionados++;
		}
		int respuesta=1;
		if (seleccionados>1){
			/*Object[] options = {Lenguaje.getMensaje(Lenguaje.YES),Lenguaje.getMensaje(Lenguaje.NO)};
			respuesta = JOptionPane.showOptionDialog(
					null,
					Lenguaje.getMensaje(Lenguaje.DELETE_ALL_NODES)+"\n"+
					Lenguaje.getMensaje(Lenguaje.WISH_CONTINUE),
					Lenguaje.getMensaje(Lenguaje.DBCASE_DELETE),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					new ImageIcon(getClass().getClassLoader().getResource(ImagePath.WARNING)),
					options,
					options[1]);*/
			respuesta = this.controlador.getPanelOpciones().setActiva(
					Lenguaje.getMensaje(Lenguaje.DELETE_ALL_NODES)+"\n"+
					Lenguaje.getMensaje(Lenguaje.WISH_CONTINUE),
					Lenguaje.getMensaje(Lenguaje.DBCASE_DELETE),
					false,
					new ImageIcon(getClass().getClassLoader().getResource(ImagePath.WARNING)));	
		}	
		if (respuesta==0 || seleccionados==1){
			PickedState<Transfer> ps = vv.getPickedVertexState();
			for (Transfer t : ps.getPicked()){
				Vector<Object> v= new Vector<Object>();
				v.add(t);
				v.add(respuesta==1);
				if (t instanceof TransferEntidad) {
					controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EliminarEntidad, v);
				}
				if (t instanceof TransferAtributo) {
					controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EliminarAtributo, v);
				}
				if (t instanceof TransferRelacion) {
					if( ((TransferRelacion) t).getTipo().equals("IsA")){
						controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EliminarRelacionIsA, v);
					}else{
						controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EliminarRelacionNormal, v);
					}
				}
			}
		}	
	}
	 
	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}

	/**
	 * Este método enviará la información referente al nodo t
	 * contenida en un JTree para su exposición en el panel de
	 * información.
	 * 
	 * @param t Transfer que se ha pulsado
	 */
	
	public void EnviaInformacionNodo(Transfer t){
		if (t == null){
			// Envia mensaje al controlador para que vacíe el panel
			controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_LimpiarPanelInformacion, null);
			return;
		}
				
		// Envía el Tree con los datos del nodo pulsado
		if (t instanceof TransferEntidad) {
			TransferEntidad entidad = (TransferEntidad) t;
			JTree arbolEntidad = generaArbolEntidad(entidad);
			controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_MostrarDatosEnPanelDeInformacion, arbolEntidad);			
		}
		if (t instanceof TransferAtributo) {
			TransferAtributo atributo = (TransferAtributo) t;
			JTree arbolAtributo = generaArbolAtributo(atributo);
			controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_MostrarDatosEnPanelDeInformacion, arbolAtributo);
		}
		if (t instanceof TransferRelacion) {
			TransferRelacion relacion = (TransferRelacion) t;
			JTree arbolRelacion = generaArbolRelacion(relacion);
			controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_MostrarDatosEnPanelDeInformacion, arbolRelacion);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private JTree generaArbolRelacion (TransferRelacion tr){
		DefaultMutableTreeNode root = null;
		// Si es una relacion IsA
		if (tr.getTipo().equals("IsA")){
			root = new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.ISA_RELATION)+" ("+tr.getIdRelacion()+")");
			Vector lista = tr.getListaEntidadesYAridades();
			// Si la relación IsA no tiene definido un padre
			if (lista.isEmpty()) root.add(new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.NO_ENTITY_INVOLVED)));
			// Si tiene padre
			if (lista.size()>0){
				DefaultMutableTreeNode nodoPadre = new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.PARENT_ENTITY));
				root.add(nodoPadre);
				Integer idPadre = Integer.parseInt(String.valueOf((((EntidadYAridad) lista.get(0)).getEntidad())));
				TransferEntidad ent = this.entidades.get(idPadre);
				nodoPadre.add(new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.ENTITY)+" \""+ent.getNombre()+"\""));
				// Si solamente tiene padre
				if (lista.size()==1) root.add(new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.NO_CHILDS_ENTITIES)));
			}
			// Si tiene hijas
			if (lista.size()>1){
				DefaultMutableTreeNode nodoHijas = new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.CHILDREN_ENTITIES));
				root.add(nodoHijas);
				for(int cont=1; cont<lista.size(); cont++){
					Integer idHija = Integer.parseInt(String.valueOf((((EntidadYAridad) lista.get(cont)).getEntidad())));
					TransferEntidad ent = this.entidades.get(idHija);
					nodoHijas.add(new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.ENTITY)+" \""+ent.getNombre()+"\""));
				}
			}
		}
		// Si es una relacion normal
		else{
			root = new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.RELATION)+" \""+tr.getNombre()+"\"");
			Vector lista = tr.getListaEntidadesYAridades();
			// Si no intervienen entidades
		   if(lista.isEmpty()) root.add(new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.NO_ENTITY_INVOLVED)));
			// Si intervienen entidades
			else{
				DefaultMutableTreeNode nodoEntidades = new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.ENTITIES));
				root.add(nodoEntidades);
				for(int cont=0; cont<lista.size(); cont++){
					EntidadYAridad eya = (EntidadYAridad) lista.get(cont);
					Integer idEnt = Integer.parseInt(String.valueOf((eya.getEntidad())));
					TransferEntidad ent = this.entidades.get(idEnt);
					DefaultMutableTreeNode nodoEnt = new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.ENTITY)+" \""+ent.getNombre()+"\"");
					nodoEntidades.add(nodoEnt);
					String finalRango = ""; 
					if(eya.getFinalRango()== Integer.MAX_VALUE) finalRango = "n";
					else finalRango = String.valueOf(eya.getFinalRango());
					String card = Lenguaje.getMensaje(Lenguaje.ENTITY)+" ("+eya.getPrincipioRango()+","+finalRango+")" + " " +eya.getRol();
					nodoEnt.add(new DefaultMutableTreeNode(card));
				}
			}
			// Si tiene atributos
			lista = tr.getListaAtributos();
			if(!lista.isEmpty()){
				DefaultMutableTreeNode nodoAtributos = new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.ATTRIBUTES));
				root.add(nodoAtributos);
				for (int cont=0; cont<lista.size(); cont++){
					Integer id_atributo =  Integer.parseInt((String)lista.get(cont));
					TransferAtributo atrib = this.atributos.get(id_atributo);
					String cad = atrib.getNombre()+" ";
					if (atrib.getCompuesto()) cad += "("+Lenguaje.getMensaje(Lenguaje.COMPOSED)+")";
					else cad += "["+atrib.getDominio()+"]";
					DefaultMutableTreeNode nodo_nombreAtributo = new DefaultMutableTreeNode(cad);
					nodoAtributos.add(nodo_nombreAtributo);		
				}
			}
			if (tr.getListaRestricciones().size()>0){
				DefaultMutableTreeNode nodoRestricciones=new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.RESTRICTIONS));
				root.add(nodoRestricciones);
				for (int i=0; i<tr.getListaRestricciones().size();i++){
					DefaultMutableTreeNode nodoRestriccion = new DefaultMutableTreeNode(tr.getListaRestricciones().get(i));
					nodoRestricciones.add(nodoRestriccion);
				}
				
			}
			if (tr.getListaUniques().size()>0){
				DefaultMutableTreeNode nodoUniques=new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.UNIQUE));
				root.add(nodoUniques);
				for (int i=0; i<tr.getListaUniques().size();i++){
					DefaultMutableTreeNode nodoUnique= new DefaultMutableTreeNode(tr.getListaUniques().get(i));
					nodoUniques.add(nodoUnique);
				}
			}
		}
		// Creamos el arbol
		JTree arbol = new JTree(root);
		arbol.setFont(new java.awt.Font("SansSerif", 0, 11));
		arbol.setShowsRootHandles(true);
		arbol.setToggleClickCount(1);
		// Expandimos todas las ramas
		for(int cont=0; cont<arbol.getRowCount(); cont++) arbol.expandRow(cont);
		return arbol;

	}
	
	@SuppressWarnings("unchecked")
	private JTree generaArbolAtributo(TransferAtributo ta){
		// Entidad, relacion o atributo al que pertenece.
		int id =ta.getIdAtributo();
		int pertenece=-1;//pertenence =0 entidad, 2 relacion, 3 atributo
		
		String entidad="";
		Collection<TransferEntidad> entities = this.entidades.values();
		for (Iterator<TransferEntidad> it = entities.iterator(); it.hasNext();){
			TransferEntidad te = it.next();
			for (int j=0; j<te.getListaAtributos().size(); j++){
				if (id == Integer.parseInt((String)te.getListaAtributos().get(j))     ){
					entidad= te.getNombre();
					pertenece=0;
				}
			}
		}
		int atributo=-1;
		Collection<TransferAtributo> attributes = this.atributos.values();
		for (Iterator<TransferAtributo> it = attributes.iterator(); it.hasNext();){
			TransferAtributo tat = it.next();
			for (int j=0; j<tat.getListaComponentes().size(); j++){
				if (id == Integer.parseInt((String)tat.getListaComponentes().get(j))     ){
					atributo= tat.getIdAtributo();
					pertenece=2;
				}
			}
		}
		String relacion="";
		Collection<TransferRelacion> relations = this.relaciones.values();
		for (Iterator<TransferRelacion> it = relations.iterator(); it.hasNext();){
			TransferRelacion tr = it.next();
			for (int j=0; j<tr.getListaAtributos().size(); j++){
				if (id == Integer.parseInt((String)tr.getListaAtributos().get(j))     ){
					relacion= tr.getNombre();
					pertenece=1;
				}
			}
		}
		
		DefaultMutableTreeNode root;
		if(pertenece==0){
			// Nombre del atributo
			root = new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.ATTRIBUTE)+" \""+ta.getNombre()+"\""+
					" (ent: "+'"'+entidad+'"'+")");
		}
		else if(pertenece==1){
			// Nombre del atributo
			root = new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.ATTRIBUTE)+" \""+ta.getNombre()+"\""+
					" (rel: "+'"'+relacion+'"'+")");
		}else{
			// Nombre del atributo
			root = new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.ATTRIBUTE)+" \""+ta.getNombre()+"\""+
					" (attr: "+atributo+")");
		}
		
		
		// Si es un aributo multivalorado
		if (ta.isMultivalorado()) root.add(new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.IS_MULTIVALUATED)));
		// Si es un aributo not null
		if (ta.getNotnull()) root.add(new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.IS_NOT_NULL)));
		// Si es un aributo unique
		if (ta.getUnique()) root.add(new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.IS_UNIQUE)));
		// Si no es un atributo compuesto
		if(!ta.getCompuesto()) root.add(new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.DOMAIN)+" ["+ta.getDominio()+"]"));
		// Si es compuesto
		else{
			root.add(new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.IS_COMPOSED)));
			if (ta.getListaComponentes().isEmpty())
				root.add(new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.NO_COMPONENTS)));
			else{
				DefaultMutableTreeNode nodoComponentes =  new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.COMPONENTS));
				root.add(nodoComponentes);
				Vector lista = ta.getListaComponentes();
				for (int cont=0; cont<lista.size(); cont++){
					Integer id_atributo =  Integer.parseInt((String)lista.get(cont));
					TransferAtributo comp = this.atributos.get(id_atributo);
					String cad = comp.getNombre()+" ";
					if (comp.getCompuesto()) cad += "("+Lenguaje.getMensaje(Lenguaje.COMPOSED)+")";
					else cad += "["+comp.getDominio()+"]";
					DefaultMutableTreeNode nodo_nombreAtributo = new DefaultMutableTreeNode(cad);
					nodoComponentes.add(nodo_nombreAtributo);		
				}
			}
		}																	
		if (ta.getListaRestricciones().size()>0){
			DefaultMutableTreeNode nodoRestricciones=new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.RESTRICTIONS));
			root.add(nodoRestricciones);
			for (int i=0; i<ta.getListaRestricciones().size();i++){
				DefaultMutableTreeNode nodoRestriccion = new DefaultMutableTreeNode(ta.getListaRestricciones().get(i));
				nodoRestricciones.add(nodoRestriccion);
			}
		}
		// Creamos el arbol
		JTree arbol = new JTree(root);
		arbol.setFont(new java.awt.Font("SansSerif", 0, 11));
		arbol.setShowsRootHandles(true);
		arbol.setToggleClickCount(1);
		// Expandimos todas las ramas
		for(int cont=0; cont<arbol.getRowCount(); cont++) arbol.expandRow(cont);
		return arbol;
	}
	
	@SuppressWarnings("unchecked")
	private JTree generaArbolEntidad(TransferEntidad te){
		// Nombre de la entidad
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.ENTITY)+" \""+te.getNombre()+"\"");
		// Si es una entidad debil
		if(te.isDebil()) root.add(new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.IS_WEAK_ENT)));
		// Atributos
		if (te.getListaAtributos().size()>0){
			DefaultMutableTreeNode nodo_atributos = new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.ATTRIBUTES));
			root.add(nodo_atributos);
			Vector lista = te.getListaAtributos();
			for (int cont=0; cont<lista.size(); cont++ ){
				Integer id_atributo =  Integer.parseInt((String)lista.get(cont));
				TransferAtributo ta = this.atributos.get(id_atributo);
				String cad = ta.getNombre()+" ";
				if (ta.getCompuesto()) cad += "("+Lenguaje.getMensaje(Lenguaje.COMPOSED)+")";
				else cad += "["+ta.getDominio()+"]";
				DefaultMutableTreeNode nodo_nombreAtributo = new DefaultMutableTreeNode(cad);
				nodo_atributos.add(nodo_nombreAtributo);
			}
		}
		// Claves primarias
		if (te.getListaClavesPrimarias().size()>0){
			DefaultMutableTreeNode nodo_claves = new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.PRIMARY_KEYS));
			root.add(nodo_claves);
			Vector lista = te.getListaClavesPrimarias();
			for (int cont=0; cont<lista.size(); cont++ ){
				Integer id_atributo =  Integer.parseInt((String)lista.get(cont));
				TransferAtributo ta = this.atributos.get(id_atributo);
				String cad = ta.getNombre()+" ";
				if (ta.getCompuesto()) cad += "("+Lenguaje.getMensaje(Lenguaje.COMPOSED)+")";
				else cad += "["+ta.getDominio()+"]";
				DefaultMutableTreeNode nodo_nombreAtributo = new DefaultMutableTreeNode(cad);
				nodo_claves.add(nodo_nombreAtributo);
			}
		}
		if (te.getListaRestricciones().size()>0){
			DefaultMutableTreeNode nodoRestricciones=new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.RESTRICTIONS));
			root.add(nodoRestricciones);
			for (int i=0; i<te.getListaRestricciones().size();i++){
				DefaultMutableTreeNode nodoRestriccion = new DefaultMutableTreeNode(te.getListaRestricciones().get(i));
				nodoRestricciones.add(nodoRestriccion);
			}
		}
		if (te.getListaUniques().size()>0){
			DefaultMutableTreeNode nodoUniques=new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.UNIQUE));
			root.add(nodoUniques);
			for (int i=0; i<te.getListaUniques().size();i++){
				DefaultMutableTreeNode nodoUnique= new DefaultMutableTreeNode(te.getListaUniques().get(i));
				nodoUniques.add(nodoUnique);
			}
		}
		JTree arbol = new JTree(root);
		arbol.setFont(new java.awt.Font("SansSerif", 0, 11));
		arbol.setShowsRootHandles(true);
		arbol.setToggleClickCount(1);
		// Expandimos todas las ramas
		for(int cont=0; cont<arbol.getRowCount(); cont++) arbol.expandRow(cont);
		return arbol;
	}

	
	/**
	 * Este método modificará el transfer que se envíe en el grafo.
	 * Es necesario para actualizar contenidos por parte del Controlador.
	 * 
	 * @param object Dato que actualizará en el grafo
	 */
	@SuppressWarnings("unchecked")
	public Transfer ModificaValorInterno(Transfer object){
		// Si es entidad se actualiza
		if (object instanceof TransferEntidad) {
			TransferEntidad entidad = (TransferEntidad) object;
			TransferEntidad antigua = entidades.get(entidad.getIdEntidad());
			antigua.CopiarEntidad(entidad);
			if (!antigua.getListaAtributos().isEmpty()){
				// Añado sus atributos
				for (Iterator<String> it2 = antigua.getListaAtributos().iterator(); it2.hasNext();){
					Integer id = Integer.parseInt(it2.next());
					if (!graph.areNeighbors(antigua, this.atributos.get(id))){ // Añade aristas que no existiesen
						graph.addEdge(new Double(Math.random()), 
								antigua,
								this.atributos.get(id));
					}
				}
			}
			vv.repaint(); // Se redibuja todo el grafo actualizado
			return antigua;
		}
		// Si es atributo se actualiza
		if (object instanceof TransferAtributo) {
			TransferAtributo atributo = (TransferAtributo) object;
			TransferAtributo antigua = atributos.get(atributo.getIdAtributo());
			antigua.CopiarAtributo(atributo);
			if (!antigua.getListaComponentes().isEmpty()){
				// Añado sus atributos
				for (Iterator<String> it2 = antigua.getListaComponentes().iterator(); it2.hasNext();){
					Integer id = Integer.parseInt(it2.next());
					if (!graph.areNeighbors(antigua, this.atributos.get(id))){ // Añade aristas que no existiesen
						graph.addEdge(new Double(Math.random()), 
								antigua,
								this.atributos.get(id));
					}
				}
			}
			vv.repaint(); // Se redibuja todo el grafo actualizado
			return antigua;
		}	
		// Si es relacion se actualiza
		if (object instanceof TransferRelacion) {
			TransferRelacion relacion = (TransferRelacion) object;
			TransferRelacion antigua = relaciones.get(relacion.getIdRelacion());
			//TODO revisar si es necesario el parametro rolRepe
			Boolean rolRepe =false;
			antigua.CopiarRelacion(relacion,relacion.getIdRelacion(),rolRepe);
			graph.removeVertex(antigua);
			graph.addVertex(antigua);
			layout.setLocation(antigua, antigua.getPosicion());
			if (!antigua.getListaAtributos().isEmpty()){
				// Añado sus atributos
				for (Iterator<String> it2 = antigua.getListaAtributos().iterator(); it2.hasNext();){
					Integer id = Integer.parseInt(it2.next());
					graph.addEdge(new Double(Math.random()), 
							antigua,
							this.atributos.get(id));
				}
			}
			//Añado las aristas del grafo con las aridades.
			if (!antigua.getListaEntidadesYAridades().isEmpty()){
				for (Iterator<EntidadYAridad> it3 = antigua.getListaEntidadesYAridades().iterator(); it3.hasNext();){
					EntidadYAridad data = it3.next();
					graph.addEdge(data, 
							antigua, this.entidades.get(data.getEntidad()));
				}
			}
			vv.repaint(); // Se redibuja todo el grafo actualizado
			return antigua;
		}
		
		return null;
	}
	
	
	/**
	 * Este método es parecido a ModificaVAlorInterno.
	 * Es necesario para actualizar contenidos por parte del Controlador.
	 * 
	 * @param object Dato que actualizará en el grafo
	 */
	@SuppressWarnings("unchecked")
	public Transfer ModificaValorInterno1a1(Vector v){
		// Si es relacion se actualiza
			TransferRelacion antigua = relaciones.get(v.get(0));
			antigua.CopiarRelacionUnoUno(v);
			graph.removeVertex(antigua);
			graph.addVertex(antigua);
			layout.setLocation(antigua, antigua.getPosicion());
			if (!antigua.getListaAtributos().isEmpty()){
				// Añado sus atributos
				for (Iterator<String> it2 = antigua.getListaAtributos().iterator(); it2.hasNext();){
					Integer id = Integer.parseInt(it2.next());
					graph.addEdge(new Double(Math.random()), 
							antigua,
							this.atributos.get(id));
				}
			}
			//Añado las aristas del grafo con las aridades.
			if (!antigua.getListaEntidadesYAridades().isEmpty()){
				for (Iterator<EntidadYAridad> it3 = antigua.getListaEntidadesYAridades().iterator(); it3.hasNext();){
					EntidadYAridad data = it3.next();
					graph.addEdge(data, 
							antigua, this.entidades.get(data.getEntidad()));
				}
			}
			vv.repaint(); // Se redibuja todo el grafo actualizado
			return antigua;
	}

	/**
	 * Añade el nodo al grafo
	 * 
	 * @param arg0 Objeto entidad que se añade
	 */
	public void anadirNodo(Transfer arg0){
		if (arg0 instanceof TransferEntidad) {
			TransferEntidad entidad = (TransferEntidad) arg0;
			entidades.put(entidad.getIdEntidad(), entidad);
			layout.anadeVertice(entidad);
		}
		if (arg0 instanceof TransferAtributo) {
			TransferAtributo atributo = (TransferAtributo) arg0;
			atributos.put(atributo.getIdAtributo(), atributo);
			layout.anadeVertice(atributo);
		}
		if (arg0 instanceof TransferRelacion) {
			TransferRelacion relacion = (TransferRelacion) arg0;
			relaciones.put(relacion.getIdRelacion(), relacion);
			layout.anadeVertice(relacion);
		}
		
		vv.repaint();
	}

	/** 
	 * Elimina la entidad que se referencie
	 * 
	 * @param arg0 Objeto que se elimina del grafo
	 */
	public void eliminaNodo(Transfer arg0){
		if (arg0 instanceof TransferEntidad) {
			TransferEntidad entidad = (TransferEntidad) arg0;
			entidad = entidades.get(entidad.getIdEntidad());
			graph.removeVertex(entidad);
			entidades.remove(entidad.getIdEntidad());
		}
		if (arg0 instanceof TransferAtributo) {
			TransferAtributo atributo = (TransferAtributo) arg0;
			atributo = atributos.get(atributo.getIdAtributo());
			graph.removeVertex(atributo);
			atributos.remove(atributo.getIdAtributo());
		}
		if (arg0 instanceof TransferRelacion) {
			TransferRelacion relacion = (TransferRelacion) arg0;
			relacion = relaciones.get(relacion.getIdRelacion());
			graph.removeVertex(relacion);
			relaciones.remove(relacion.getIdRelacion());
		}
		
		vv.repaint();
	}

	/** Añade la arista entre los nodos especificados
	 *
	 */
	public void anadirArista(Transfer arg0, Transfer arg1){
		graph.addEdge(new String(), arg0, arg1);
		vv.repaint();
	}
	
	
	/**
	 * Guarda el grafo en un fichero gráfico JPEG
	 * @param file Fichero a guardar la imagen
	 */
	public void writeJPEGGraph(File file) {
        int width = vv.getWidth();
        int height = vv.getHeight();

        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bi.createGraphics();
        vv.paint(graphics);
        graphics.dispose();
        
        try {
            ImageIO.write(bi, "jpeg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * Imprime el grafo presente en el panel.
	 * Muestra el dialog para seleccionar impresora.
	 */
	public void printGraph(){
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable(this);
		try{
			if (printJob.printDialog()) {
	            try {
	                printJob.print();
	            } catch (PrinterException e) {
					System.out.println("Error en la impresión");
				}
	        }
		} catch (NullPointerException e) {
        	System.out.println("Opciones de impresión inválidas");
		}
		
	}
	
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		if (pageIndex > 0) {
            return (Printable.NO_SUCH_PAGE);
        } else {
            java.awt.Graphics2D g2d = (java.awt.Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            vv.setDoubleBuffered(false);
            vv.paint(g2d);
            vv.setDoubleBuffered(true);
            return (Printable.PAGE_EXISTS);
        }
	}
	
	public class PopUpMenu extends JPopupMenu{

		/**
		 * 
		 */
		private static final long serialVersionUID = -1855939762977980663L;
		public Transfer nodo; // Nodo sobre el que se ha pulsado
		public Point2D punto; // Punto del plano donde se ha pulsado

		/**
		 * Constructor público
		 *
		 */
		public PopUpMenu(){
			this.removeAll();
			/*this.addMouseListener(new MouseAdapter(){
			});*/
		}

		public void Reinicializa(Transfer nodo, Point2D punto){
			this.punto = punto;
			this.removeAll();
			if (nodo == null){ // Si se pide null es que ha pinchado en el área libre

				// Insertar una entidad
				JMenuItem j1 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.ADD_ENTITY));
				j1.setFont(new java.awt.Font("SansSerif", 0, 11));
				j1.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
						Point2D p = menu.punto;
						System.out.println("Insertar Entidad en: " + p.getX() +","+ p.getY());
						controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_InsertarEntidad,p);
					}
				});
				this.add(j1);
				this.add(new JSeparator());
				
				// Insertar una relacion normal
				
				JMenuItem j2 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.ADD_RELATION));
				j2.setFont(new java.awt.Font("SansSerif", 0, 11));
				j2.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
						Point2D p = menu.punto;
						System.out.println("Insertar Relación en: " + p.getX() +","+ p.getY());
						controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_InsertarRelacionNormal, p);
					}
				});
				this.add(j2);
				this.add(new JSeparator());

				// Insertar una relacion IsA
				
				JMenuItem j3 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.ADD_ISARELATION));
				j3.setFont(new java.awt.Font("SansSerif", 0, 11));
				j3.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
						Point2D p = menu.punto;
						System.out.println("Insertar Relación IsA en: " + p.getX() +","+ p.getY());
						controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_InsertarRelacionIsA, p);
					}
				});
				this.add(j3);
				this.add(new JSeparator());
				
				// 	Insertar un dominio
				
				JMenuItem j4 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.ADD_DOMAIN));
				j4.setFont(new java.awt.Font("SansSerif", 0, 11));
				j4.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
						Point2D p = menu.punto;
						System.out.println("Insertar Dominio");
						controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_CrearDominio, p);
					}
				});
				this.add(j4);
				
				return;
			}
			// Se ha pinchado sobre un nodo
			this.nodo = nodo;
			System.out.println("Vertice " + nodo.toString() + " fue pulsado");

			if (nodo instanceof TransferEntidad) { // Si es entidad
				// Anadir un atributo a una entidad
				JMenuItem j3 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.ADD_ATTRIBUTE));
				j3.setFont(new java.awt.Font("SansSerif", 0, 11));
				j3.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
						TransferEntidad entidad = (TransferEntidad)menu.nodo;
						System.out.println("Añadir un nuevo atributo a la entidad: " + entidad);
						TransferEntidad clon_entidad = entidad.clonar();
						controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_AnadirAtributoEntidad,clon_entidad);
					}	
				});
				this.add(j3);
				this.add(new JSeparator());
				
				// Renombrar la entidad
				JMenuItem j1 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.RENAME_ENTITY));
				j1.setFont(new java.awt.Font("SansSerif", 0, 11));
				j1.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
						TransferEntidad entidad = (TransferEntidad)menu.nodo;
						System.out.println("Cambiar nombre a entidad: " + entidad);
						TransferEntidad clon_entidad = entidad.clonar();
						controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_RenombrarEntidad,clon_entidad);
					}	
				});
				this.add(j1);

				// Eliminar una entidad 
				//Si sólo está seleccionada la entidad..
				PickedState<Transfer> p = vv.getPickedVertexState();
				int seleccionados=0;
				for (@SuppressWarnings("unused") Transfer t : p.getPicked()){
					seleccionados++;
				}
				if (seleccionados<2){
					JMenuItem j4 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.DELETE_ENT));
					j4.setFont(new java.awt.Font("SansSerif", 0, 11));
					j4.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
							TransferEntidad entidad = (TransferEntidad)menu.nodo;
							System.out.println("Eliminar la entidad: " + entidad);
							TransferEntidad clon_entidad = entidad.clonar();
							Vector<Object> v = new Vector<Object>();
							v.add(clon_entidad);
							v.add(true);
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EliminarEntidad,v);
						}	
					});
					this.add(j4);
				}
				else{
					JMenuItem j4 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.DELETE));
					j4.setFont(new java.awt.Font("SansSerif", 0, 11));
					j4.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							suprimir();
						}	
					});
					this.add(j4);
				}
				this.add(new JSeparator());
				
				//Añadir restricciones			
				JMenuItem j5 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.RESTRICTIONS));
				j5.setFont(new java.awt.Font("SansSerif", 0, 11));
				j5.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
						TransferEntidad entidad = (TransferEntidad)menu.nodo;
						System.out.println("Añadir una restriccion a la entidad: " + entidad);
						TransferEntidad clon_entidad = entidad.clonar();
						controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_AnadirRestriccionAEntidad,clon_entidad);
					}	
				});
				this.add(j5);
				
				//Añadir tablaUnique			
				JMenuItem j6 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.TABLE_UNIQUE));
				j6.setFont(new java.awt.Font("SansSerif", 0, 11));
				j6.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
						TransferEntidad entidad = (TransferEntidad)menu.nodo;
						TransferEntidad clon_entidad = entidad.clonar();
						controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_TablaUniqueAEntidad,clon_entidad);
					}	
				});
				this.add(j6);
			}

			if (nodo instanceof TransferAtributo) { // Si es atributo
				// Editar el dominio del atributo
				JMenuItem j2 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.EDIT_DOMAIN));
				j2.setFont(new java.awt.Font("SansSerif", 0, 11));
				j2.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
						TransferAtributo atributo = (TransferAtributo)menu.nodo;
						System.out.println("Cambiar el dominio del atributo: " + atributo);
						TransferAtributo clon_atributo = atributo.clonar();
						controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarDominioAtributo,clon_atributo);
					}	
				});
				this.add(j2);
				
				// Renombrar un atributo
				JMenuItem j1 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.RENAME_ATTRIB));
				j1.setFont(new java.awt.Font("SansSerif", 0, 11));
				j1.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
						TransferAtributo atributo = (TransferAtributo)menu.nodo;
						System.out.println("Cambiar nombre del atributo: " + atributo);
						TransferAtributo clon_atributo = atributo.clonar();
						controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_RenombrarAtributo,clon_atributo);
					}	
				});
				this.add(j1);
				
				// Eliminar un atributo
				//Si sólo está seleccionado el atributo..
				PickedState<Transfer> p = vv.getPickedVertexState();
				int seleccionados=0;
				for (@SuppressWarnings("unused") Transfer t : p.getPicked()){
					seleccionados++;
				}
				if (seleccionados<2){
					JMenuItem j7 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.DELETE_ATTRIB));
					j7.setFont(new java.awt.Font("SansSerif", 0, 11));
					j7.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
							TransferAtributo atributo = (TransferAtributo)menu.nodo;
							System.out.println("Eliminar atributo: " + atributo);
							TransferAtributo clon_atributo = atributo.clonar();
							Vector<Object> v = new Vector<Object>();
							v.add(clon_atributo);
							v.add(true);
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EliminarAtributo,v);
						}	
					});
					this.add(j7);
				}
				else{
					JMenuItem j7 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.DELETE));
					j7.setFont(new java.awt.Font("SansSerif", 0, 11));
					j7.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							suprimir();
						}	
					});
					this.add(j7);
				}
				
				this.add(new JSeparator());

				// Establecer clave primaria
				// Solamente estara activo cuando sea un atributo directo de una entidad
				final TransferEntidad ent = esAtributoDirecto((TransferAtributo)nodo);
				if (ent != null){
					JCheckBoxMenuItem j6 = new JCheckBoxMenuItem(Lenguaje.getMensaje(Lenguaje.IS_PRIMARY_KEY)+" \""+ent.getNombre()+"\"");
					j6.setFont(new java.awt.Font("SansSerif", 0, 11));
					if (((TransferAtributo)nodo).isClavePrimaria()) j6.setSelected(true);
					j6.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
							TransferAtributo atributo = (TransferAtributo)menu.nodo;
							System.out.println("Editar clave primaria etributo: " + atributo);
							TransferAtributo clon_atributo = atributo.clonar();
							TransferEntidad clon_entidad = ent.clonar();
							Vector<Transfer> vector = new Vector<Transfer>();
							vector.add(clon_atributo);
							vector.add(clon_entidad);
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarClavePrimariaAtributo,vector);	
						}	
					});
					this.add(j6);
				}
				// Es un atributo compuesto
				JCheckBoxMenuItem j3 = new JCheckBoxMenuItem(Lenguaje.getMensaje(Lenguaje.COMPOSED));
				j3.setFont(new java.awt.Font("SansSerif", 0, 11));
				final boolean notnul= ((TransferAtributo)nodo).getNotnull();
				final boolean unique = ((TransferAtributo)nodo).getUnique();
				if (((TransferAtributo)nodo).getCompuesto()) j3.setSelected(true);
				else j3.setSelected(false);
				j3.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
						TransferAtributo atributo = (TransferAtributo)menu.nodo;
						System.out.println("Cambiar el carácter de compuesto del atributo: " + atributo);
						TransferAtributo clon_atributo = atributo.clonar();
						if (notnul){
						controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarNotNullAtributo,clon_atributo);
						}
						if (unique){
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarUniqueAtributo,clon_atributo);
						}
						controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarCompuestoAtributo,clon_atributo);	
					}	
				});
				this.add(j3);
				
				// Si es compuesto
				if (((TransferAtributo)nodo).getCompuesto()){
					JMenuItem j4 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.ADD_SUBATTRIBUTE));
					j4.setFont(new java.awt.Font("SansSerif", 0, 11));
					j4.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
							TransferAtributo atributo = (TransferAtributo)menu.nodo;
							System.out.println("Anadir subatributo: " + atributo);
							TransferAtributo clon_atributo = atributo.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_AnadirSubAtributoAAtributo,clon_atributo);
						}	
					});
					this.add(j4);
				}
				//this.add(new JSeparator());

				// Es un atributo NotNull
				if(!((TransferAtributo)nodo).getCompuesto() && !((TransferAtributo)nodo).isClavePrimaria()){
					JCheckBoxMenuItem j3a = new JCheckBoxMenuItem(Lenguaje.getMensaje(Lenguaje.NOT_NULL));
					j3a.setFont(new java.awt.Font("SansSerif", 0, 11));
					if (((TransferAtributo)nodo).getNotnull()) j3a.setSelected(true);
					else j3a.setSelected(false);
					j3a.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
							TransferAtributo atributo = (TransferAtributo)menu.nodo;
							System.out.println("Cambiar el carácter de 'Not Null' del atributo: " + atributo);
							TransferAtributo clon_atributo = atributo.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarNotNullAtributo,clon_atributo);	
						}	
					});
					this.add(j3a);
					//this.add(new JSeparator());
				}
				// Es un atributo Unique
				if(!((TransferAtributo)nodo).getCompuesto() && !((TransferAtributo)nodo).isClavePrimaria()){
				JCheckBoxMenuItem j3b = new JCheckBoxMenuItem(Lenguaje.getMensaje(Lenguaje.UNIQUE));
				j3b.setFont(new java.awt.Font("SansSerif", 0, 11));
				if (((TransferAtributo)nodo).getUnique()) j3b.setSelected(true);
				else j3b.setSelected(false);
				j3b.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
						TransferAtributo atributo = (TransferAtributo)menu.nodo;
						System.out.println("Cambiar el carácter de 'Unique' del atributo: " + atributo);
						TransferAtributo clon_atributo = atributo.clonar();
						controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarUniqueAtributo,clon_atributo);	
					}	
				});
				this.add(j3b);
				//this.add(new JSeparator());
				}
				
				// Es un atributo multivalorado
				if( !((TransferAtributo)nodo).isClavePrimaria()){
					JCheckBoxMenuItem j5 = new JCheckBoxMenuItem(Lenguaje.getMensaje(Lenguaje.IS_MULTIVALUATED));
					j5.setFont(new java.awt.Font("SansSerif", 0, 11));
					if (((TransferAtributo)nodo).isMultivalorado()) j5.setSelected(true);
					else j5.setSelected(false);
					j5.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
							TransferAtributo atributo = (TransferAtributo)menu.nodo;
							System.out.println("Cambiar el carácter de multivalorado: " + atributo);
							TransferAtributo clon_atributo = atributo.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarMultivaloradoAtributo,clon_atributo);	
						}	
					});
					this.add(j5);
				}	
				//this.add(new JSeparator());
				
				
					
				
				this.add(new JSeparator());
				//Añadir restricciones			
				JMenuItem j8 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.RESTRICTIONS));
				j8.setFont(new java.awt.Font("SansSerif", 0, 11));
				j8.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
						TransferAtributo atributo = (TransferAtributo)menu.nodo;
						System.out.println("Añadir una restriccion al atributo: " + atributo);
						TransferAtributo clon_atributo = atributo.clonar();
						controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_AnadirRestriccionAAtributo,clon_atributo);
					}	
				});
				this.add(j8);			
			}

			
			if (nodo instanceof TransferRelacion) { // Si es relación
				// Si es una relacion IsA
				if (((TransferRelacion)nodo).getTipo().equals("IsA")){

					this.add(new JMenu().add(new AbstractAction(Lenguaje.getMensaje(Lenguaje.SET_PARENT_ENT)){
						private static final long serialVersionUID = 8766595520619916135L;
						public void actionPerformed(ActionEvent e) {
							PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
							TransferRelacion relacion = (TransferRelacion)menu.nodo;
							System.out.println("Establecer entidad padre: " + relacion);
							TransferRelacion clon_relacion = relacion.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EstablecerEntidadPadre,clon_relacion);
						}
					}));
					this.add(new JMenu().add(new AbstractAction(Lenguaje.getMensaje(Lenguaje.REMOVE_PARENT_ENT)){
						private static final long serialVersionUID = 8766595520619916135L;
						public void actionPerformed(ActionEvent e) {
							PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
							TransferRelacion relacion = (TransferRelacion)menu.nodo;
							System.out.println("Quitar entidad padre: " + relacion);
							TransferRelacion clon_relacion = relacion.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_QuitarEntidadPadre,clon_relacion);
						}
					}));

					this.add(new JSeparator());

					this.add(new JMenu().add(new AbstractAction(Lenguaje.getMensaje(Lenguaje.ADD_CHILD_ENT)){
						private static final long serialVersionUID = 8766595520619916135L;
						public void actionPerformed(ActionEvent e) {
							PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
							TransferRelacion relacion = (TransferRelacion)menu.nodo;
							System.out.println("Añadir entidad hija: " + relacion);
							TransferRelacion clon_relacion = relacion.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_AnadirEntidadHija,clon_relacion);
						}
					}));

					this.add(new JMenu().add(new AbstractAction(Lenguaje.getMensaje(Lenguaje.REMOVE_CHILD_ENT)){
						private static final long serialVersionUID = 8766595520619916135L;
						public void actionPerformed(ActionEvent e) {
							PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
							TransferRelacion relacion = (TransferRelacion)menu.nodo;
							System.out.println("Quitar entidad hija: " + relacion);
							TransferRelacion clon_relacion = relacion.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_QuitarEntidadHija,clon_relacion);
						}
					}));

					this.add(new JSeparator());
					//Eliminar la relacion
					//Si sólo está seleccionada la relacion..
					PickedState<Transfer> p = vv.getPickedVertexState();
					int seleccionados=0;
					for (@SuppressWarnings("unused") Transfer t : p.getPicked()){
						seleccionados++;
					}
					if (seleccionados<2){
						this.add(new JMenu().add(new AbstractAction(Lenguaje.getMensaje(Lenguaje.DELETE_REL)){
							private static final long serialVersionUID = -218800914185538588L;
							public void actionPerformed(ActionEvent e) {
								PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
								TransferRelacion relacion = (TransferRelacion)menu.nodo;
								System.out.println("Eliminar la relación: " + relacion);
								TransferRelacion clon_relacion = relacion.clonar();
								Vector<Object> v = new Vector<Object>();
								v.add(clon_relacion);
								v.add(true);
								controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EliminarRelacionIsA,v);
							}
						}));

					}
					else{
						JMenuItem j6 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.DELETE));
						j6.setFont(new java.awt.Font("SansSerif", 0, 11));
						j6.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								suprimir();
							}	
						});
						this.add(j6);
					}

				}	

				// Si es una relacion "Normal" 
				else{
					// Anadir una entidad
					JMenuItem j3 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.ADD_ENT));
					j3.setFont(new java.awt.Font("SansSerif", 0, 11));
					if (((TransferRelacion)nodo).getTipo().equals("Debil")){
						j3.setEnabled(false);
					}
					else{
						j3.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
								TransferRelacion relacion = (TransferRelacion)menu.nodo;
								System.out.println("Añadir una entidad a la relación: " + relacion);
								TransferRelacion clon_relacion = relacion.clonar();
								controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_AnadirEntidadARelacion,clon_relacion);	
							}	
						});
					}
					this.add(j3);

					// Quitar una entidad
					JMenuItem j4 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.REMOVE_ENTITY));
					j4.setFont(new java.awt.Font("SansSerif", 0, 11));
					//Si es débil
					if (((TransferRelacion)nodo).getTipo().equals("Debil")){
						j4.setEnabled(false);
					}
					//Si es normal
					else{
					j4.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
							TransferRelacion relacion = (TransferRelacion)menu.nodo;
							System.out.println("Quitar una entidad a la relación: " + relacion);
							TransferRelacion clon_relacion = relacion.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_QuitarEntidadARelacion,clon_relacion);	
						}	
					});
					}
					this.add(j4);

					// Editar la aridad de una entidad
					
					JMenuItem j5 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.EDIT_CARD_ROL));
					j5.setFont(new java.awt.Font("SansSerif", 0, 11));
					if (((TransferRelacion)nodo).getTipo().equals("Debil"))
						j5.setEnabled(false);
					else{
						j5.setEnabled(true);
						j5.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
								TransferRelacion relacion = (TransferRelacion)menu.nodo;
								System.out.println("Editar aridad de entidad de la relación: " + relacion);
								TransferRelacion clon_relacion = relacion.clonar();
								controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarCardinalidadEntidad,clon_relacion);	
							}	
						});
					}
					this.add(j5);
					this.add(new JSeparator());
					

					// Anadir un atributo a la relacion
					JMenuItem j6 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.ADD_ATTRIBUTE));
					j6.setFont(new java.awt.Font("SansSerif", 0, 11));
					if (((TransferRelacion)nodo).getTipo().equals("Debil"))
						j6.setEnabled(false);
					else{
						j6.setEnabled(true);
						j6.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
								TransferRelacion relacion = (TransferRelacion)menu.nodo;
								System.out.println("Añadir atributo a la relación: " + relacion);
								TransferRelacion clon_relacion = relacion.clonar();
								controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_AnadirAtributoRelacion,clon_relacion);	
							}	
						});
					}
					this.add(j6);	
					this.add(new JSeparator());

					// Renombrar la relacion
					JMenuItem j1 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.RENAME_RELATION));
					j1.setFont(new java.awt.Font("SansSerif", 0, 11));
					j1.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
							TransferRelacion relacion = (TransferRelacion)menu.nodo;
							System.out.println("Renombrar la relación: " + relacion);
							TransferRelacion clon_relacion = relacion.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_RenombrarRelacion,clon_relacion);	
						}	
					});
					this.add(j1);

					// Eliminar la relacion
					//Si sólo está seleccionada la relacion..
					PickedState<Transfer> p = vv.getPickedVertexState();
					int seleccionados=0;
					for (@SuppressWarnings("unused") Transfer t : p.getPicked()){
						seleccionados++;
					}
					if (seleccionados<2){
						JMenuItem j7 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.DELETE_REL));
						j7.setFont(new java.awt.Font("SansSerif", 0, 11));
						j7.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
								TransferRelacion relacion = (TransferRelacion)menu.nodo;
								System.out.println("Eliminar la relación: " + relacion);
								TransferRelacion clon_relacion = relacion.clonar();
								Vector<Object> v = new Vector<Object>();
								v.add(clon_relacion);
								v.add(true);
								controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EliminarRelacionNormal,v);	
							}	
						});
						this.add(j7);				
					}
					else{
						JMenuItem j7 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.DELETE));
						j7.setFont(new java.awt.Font("SansSerif", 0, 11));
						j7.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								suprimir();
							}	
						});
						this.add(j7);
					}
					this.add(new JSeparator());					
					
					//Añadir restricciones			
					JMenuItem j8 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.RESTRICTIONS));
					j8.setFont(new java.awt.Font("SansSerif", 0, 11));
					j8.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
							TransferRelacion relacion = (TransferRelacion)menu.nodo;
							System.out.println("Añadir una restriccion a la relación: " + relacion);
							TransferRelacion clon_relacion = relacion.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_AnadirRestriccionARelacion,clon_relacion);
						}	
					});
					this.add(j8);
					//Añadir tablaUnique			
					JMenuItem j9 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.TABLE_UNIQUE));
					j9.setFont(new java.awt.Font("SansSerif", 0, 11));
					j9.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							PopUpMenu menu = (PopUpMenu)((JMenuItem)e.getSource()).getParent();
							TransferRelacion relacion = (TransferRelacion)menu.nodo;
							TransferRelacion clon_relacion = relacion.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_TablaUniqueARelacion,clon_relacion);
						}	
					});
					this.add(j9);
							
				} // else
			}

		}
	}
	
	/**
	 * Metodo auxiliar para saber si hay que mostrar en el popup de los atributos la opcion de "Es clave primaria".
	 * @param ta - Atributo que se quiere consultar
	 * @return  te -> la entidad a la que pertenece
	 * 			null -> en otro caso (sera un subatributo o sera un atributo de una relacion)
	 */
	private TransferEntidad esAtributoDirecto(TransferAtributo ta){
		Collection<TransferEntidad> listaEntidades = this.entidades.values();
		for (Iterator<TransferEntidad> it = listaEntidades.iterator(); it.hasNext();){
			TransferEntidad te = it.next();
			if (te.getListaAtributos().contains(String.valueOf(ta.getIdAtributo()))) return te;
		}
		return null;
	}
	
	/**
	 * Método auxiliar para determinar la posición relativa en que el usuario hizo click
	 * dentro del panel.
	 * 
	 * @param ev Evento de ratón qdel que se quiere conocer la posición
	 * 
	 * @return El lugar del click, relativo a los scrolls y al zoom.
	 */
	private Point2D obtenerPuntoExacto (MouseEvent ev){
		return vv.getRenderContext().getMultiLayerTransformer().inverseTransform(ev.getPoint());
	}

}

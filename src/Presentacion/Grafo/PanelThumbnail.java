package Presentacion.Grafo;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JPanel;

import LogicaNegocio.Transfers.Transfer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.SatelliteVisualizationViewer;
import edu.uci.ics.jung.visualization.control.ScalingControl;

public class PanelThumbnail extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6459573104877244360L;
	
	/* Tendrá una referencia al panel de diseño para 
	 * conocer todos los datos
	 */
	PanelGrafo panelDiseno;

	VisualizationViewer<Transfer,Object> vv;
	
	public PanelThumbnail(){
		panelDiseno = null;
	}
	
	public PanelThumbnail(PanelGrafo arg0){
		panelDiseno = arg0;
		Inicializar();
	}
	
		
	private void Inicializar(){
		this.setLayout(new GridLayout(1,1));
		vv = new SatelliteVisualizationViewer<Transfer, Object>(panelDiseno.vv);
		
		LabelRenderer<Transfer,Double> vlasr = new LabelRenderer<Transfer, Double>();
		vv.setDoubleBuffered(true); // Incrementa rendimiento
		vv.setBackground(Color.white); 
		
		// customize the render context
		/*vv.getRenderContext().setVertexLabelTransformer(new Transformer<Transfer, String>(){
			public String transform(Transfer input) {
				if (input instanceof TransferEntidad) {
					//TransferEntidad entidad = (TransferEntidad) input;
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
					//return "<html><center>"+input+"<p>";
				}
				if (input instanceof TransferRelacion) {
					//TransferRelacion relacion = (TransferRelacion) input;
					return "<html><center>"+input+"<p>";
				}
				return "<html><center>"+input+"<p>";
			}

		});*/

		vv.getRenderContext().setVertexShapeTransformer(vlasr);
		//vv.getRenderContext().setVertexLabelRenderer(new DefaultVertexLabelRenderer(Color.blue));
		//vv.getRenderContext().setEdgeDrawPaintTransformer(new ConstantTransformer(Color.black));
		//vv.getRenderContext().setEdgeStrokeTransformer(new ConstantTransformer(new BasicStroke(2.5f)));
		//vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line());

		// customize the renderer
		//Esto cambia el color con el que se marcan las cosas en el mini panel de la izquierda
		vv.getRenderer().setVertexRenderer(new VertexRenderer<Transfer,Object>(new java.awt.Color(240, 240, 240), Color.white, true));
		//vv.getRenderer().setVertexLabelRenderer(vlasr);
		

		// add a listener for ToolTips
		//vv.setVertexToolTipTransformer(new ToStringLabeller<Transfer>());
		
		ScalingControl vv2Scaler = new CrossoverScalingControl();
		vv2Scaler.scale(vv,0.06f,new Point(0,0));
		this.add(vv);
		
	}

}

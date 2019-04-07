package vista.diagrama;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.JPanel;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.SatelliteVisualizationViewer;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import modelo.transfers.Transfer;

public class PanelThumbnail extends JPanel{

	private static final long serialVersionUID = -6459573104877244360L;
	
	/* Tendrá una referencia al panel de diseño para 
	 * conocer todos los datos
	 */
	PanelGrafo panelDiagrama;

	VisualizationViewer<Transfer,Object> vv;

	
	public PanelThumbnail(){
		panelDiagrama = null;
	}
	
	public PanelThumbnail(PanelGrafo arg0){
		panelDiagrama = arg0;
		Inicializar();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void Inicializar(){
		this.setLayout(new GridLayout(1,1));
		vv = new SatelliteVisualizationViewer<Transfer, Object>(panelDiagrama.vv);
		LabelRenderer<Transfer,Double> vlasr = new LabelRenderer<Transfer, Double>();
		vv.setDoubleBuffered(true); // Incrementa rendimiento
		vv.getRenderContext().setVertexShapeTransformer(vlasr);
		//Hace las lineas rectas
		vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line());
		// customize the renderer
		//Esto cambia el color con el que se marcan las cosas en el mini panel de la izquierda
		vv.getRenderer().setVertexRenderer(new VertexRenderer<Transfer,Object>(Color.white, Color.white, true));
		ScalingControl vv2Scaler = new CrossoverScalingControl();
		vv2Scaler.scale(vv,0.15f,new Point(0,0));
		this.add(vv);
		
	}

}

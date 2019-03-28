package vista;

import java.awt.Container;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;


/*
 * Clase que maneja que paneles se ven y cuales no
 * */
public class ViewDealer {
	
	private Container mainPanel;
	private JPanel diagrama;
	private JPanel codigos;
	private JSplitPane diagramaSplitCode;
	private JSplitPane splitCodigos;
	private JSplitPane infoSplitMapa;
	private JSplitPane programmerSplit;
	private JTabbedPane infoPanel;
	private byte modo;
	
	public ViewDealer(Container mainPanel, JPanel diagrama, JPanel codigos, JTabbedPane infoPanel){
		this.mainPanel = mainPanel;
		this.diagrama = diagrama;
		this.codigos = codigos;
		this.infoPanel = infoPanel;
		this.splitCodigos = ((JSplitPane) codigos.getComponent(0));
		this.infoSplitMapa = (JSplitPane) (((JSplitPane) diagrama.getComponent(0)).getComponent(2));
		this.diagramaSplitCode = new JSplitPane();
		this.diagramaSplitCode.setResizeWeight(0.8);
		this.programmerSplit = new JSplitPane();
		this.programmerSplit.setResizeWeight(0.2);
		this.programmerSplit.setDividerSize(10);
	}
	
	/*
	 * Muestra todos los paneles
	 * */
	public void modoVerTodo() {
		mainPanel.removeAll();
		programmerSplit.removeAll();
		infoSplitMapa.add(infoPanel, JSplitPane.RIGHT);
		mainPanel.add(diagramaSplitCode);
		splitCodigos.setOrientation(JSplitPane.VERTICAL_SPLIT);
		diagramaSplitCode.add(codigos, JSplitPane.RIGHT);
		diagramaSplitCode.add(diagrama, JSplitPane.LEFT);
		diagramaSplitCode.setVisible(true);
		programmerSplit.setVisible(false);
		mainPanel.revalidate();
		mainPanel.repaint();
		this.modo = 0;
	}
	
	/*
	 * Muestra solo los paneles de diseno del diagrama
	 * */
	public void modoDiseno() {
		mainPanel.removeAll();
		programmerSplit.removeAll();
		infoSplitMapa.add(infoPanel, JSplitPane.RIGHT);
		mainPanel.add(diagrama);
		diagramaSplitCode.setVisible(false);
		programmerSplit.setVisible(false);
		mainPanel.revalidate();
		mainPanel.repaint();
		this.modo = 1;
	}
	
	/*
	 * Muestra solo los paneles de edicion de codigo
	 * */
	public void modoProgramador() {
		mainPanel.removeAll();
		splitCodigos.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		mainPanel.add(programmerSplit);
		programmerSplit.add(infoPanel, JSplitPane.LEFT);
		programmerSplit.add(codigos, JSplitPane.RIGHT);
		programmerSplit.setDividerSize(10);
		diagramaSplitCode.setVisible(false);
		programmerSplit.setVisible(true);
		mainPanel.revalidate();
		mainPanel.repaint();
		this.modo = 2;
	}
	
	/*
	 * Muestra / oculta el panel de diseño
	 * */
	public void toggleDiseno() {
		if(modo == 2) modoVerTodo();
		else if(modo == 0) modoProgramador();
	}
	
	/*
	 * Muestra / oculta el panel de codigos
	 * */
	public void toggleCodigos() {
		if(modo == 1) modoVerTodo();
		else if(modo == 0) modoDiseno();
	}
}

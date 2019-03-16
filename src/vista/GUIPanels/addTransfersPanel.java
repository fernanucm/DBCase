package vista.GUIPanels;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controlador.Controlador;
import controlador.TC;
import vista.Theme.Theme;
import vista.icons.attributeIcon;
import vista.icons.entityIcon;
import vista.icons.isaIcon;
import vista.icons.relationIcon;
import vista.lenguaje.Lenguaje;

@SuppressWarnings("serial")
public class addTransfersPanel extends JPanel{
	private Controlador controlador;
	public addTransfersPanel(Controlador c){
		super();
		Theme theme = Theme.getInstancia();
		this.controlador = c;
		JLabel anadirEntidad = new JLabel(new entityIcon());
		anadirEntidad.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		anadirEntidad.setText(Lenguaje.getMensaje(Lenguaje.ENTITY));
		anadirEntidad.setVerticalTextPosition(JLabel.BOTTOM);
		anadirEntidad.setHorizontalTextPosition(JLabel.CENTER);
		JLabel anadirRelacion = new JLabel(new relationIcon());
		anadirRelacion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		anadirRelacion.setText(Lenguaje.getMensaje(Lenguaje.RELATION));
		anadirRelacion.setVerticalTextPosition(JLabel.BOTTOM);
		anadirRelacion.setHorizontalTextPosition(JLabel.CENTER);
		JLabel anadirIsa = new JLabel(new isaIcon());
		anadirIsa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		anadirIsa.setText(Lenguaje.getMensaje(Lenguaje.ISA_RELATION));
		anadirIsa.setVerticalTextPosition(JLabel.BOTTOM);
		anadirIsa.setHorizontalTextPosition(JLabel.CENTER);
		JLabel anadirAttribute = new JLabel(new attributeIcon());
		anadirAttribute.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		anadirAttribute.setText(Lenguaje.getMensaje(Lenguaje.ATTRIBUTE));
		anadirAttribute.setVerticalTextPosition(JLabel.BOTTOM);
		anadirAttribute.setHorizontalTextPosition(JLabel.CENTER);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createVerticalGlue());
		this.add(anadirEntidad);
		this.add(Box.createRigidArea(new Dimension(0,30)));
		this.add(anadirRelacion);
		this.add(Box.createRigidArea(new Dimension(0,30)));
		this.add(anadirIsa);
		this.add(Box.createRigidArea(new Dimension(0,30)));
		this.add(anadirAttribute);
		this.add(Box.createVerticalGlue());
		this.setBackground(theme.toolBar());
		this.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
		
		anadirEntidad.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	Point2D p = new Point2D.Double(100,100);
				System.out.println("Insertar Entidad en: " + p.getX() +","+ p.getY());
				controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_InsertarEntidad,p);
            }
        });	
		anadirRelacion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	Point2D p = new Point2D.Double(100,100);
				System.out.println("Insertar Relacion en: " + p.getX() +","+ p.getY());
				controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_InsertarRelacionNormal,p);
            }

        });
		anadirIsa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	Point2D p = new Point2D.Double(100,100);
				System.out.println("Insertar ISA en: " + p.getX() +","+ p.getY());
				controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_InsertarRelacionIsA,p);
            }
        });
		anadirAttribute.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	Point2D p = new Point2D.Double(50,50);
				System.out.println("Insertar Atributo en: " + p.getX() +","+ p.getY());
				controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_InsertarEntidad,p);
            }

        });		
	}
	
}

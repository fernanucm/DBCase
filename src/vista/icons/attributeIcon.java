package vista.icons;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import vista.tema.Theme;

public class attributeIcon extends icon{
  
	public attributeIcon() {
		super();
	}
    public attributeIcon(String tipo) {
		super(tipo);
	}

	@Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
    	Graphics2D g2d = (Graphics2D) g;
    	Theme theme = Theme.getInstancia();
    	g2d.setColor(theme.atribute());
    	g2d.fill(new Ellipse2D.Double(x, y,getIconWidth()*.9, getIconHeight()*.8));
    	g2d.setColor(theme.lines());
    	g2d.draw(new Ellipse2D.Double(x, y,getIconWidth()*.9, getIconHeight()*.8));
    	if(pintarMas()) {
	    	g2d.draw(new Line2D.Double(getIconWidth()*.4,getIconHeight()*.4,getIconWidth()*.5,getIconHeight()*.4));
	        g2d.draw(new Line2D.Double(getIconWidth()*.45,getIconHeight()*.3,getIconWidth()*.45,getIconHeight()*.5));
    	}
    }

}

package Presentacion.icons;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import Presentacion.Theme.Theme;

public class isaIcon extends icon{    
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
    	Graphics2D g2d = (Graphics2D) g;
    	Theme theme = Theme.getInstancia();
    	g2d.setColor(theme.relation());
    	Polygon s = new Polygon();
        s.addPoint((int) (getIconWidth()*.2), 0);
        s.addPoint((int) (getIconWidth()*.5), (int) (getIconHeight()*.8));
        s.addPoint((int) (getIconWidth()*.8), 0);
        g.fillPolygon(s);
        g2d.setColor(theme.lines());
        g.drawPolygon(s);
        g2d.draw(new Line2D.Double(getIconWidth()*.45,getIconHeight()*.35,getIconWidth()*.55,getIconHeight()*.35));
        g2d.draw(new Line2D.Double(getIconWidth()*.5,getIconHeight()*.25,getIconWidth()*.5,getIconHeight()*.45));
    }
}

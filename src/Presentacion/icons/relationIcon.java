package Presentacion.icons;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;

import javax.swing.Icon;

import Presentacion.Theme.Theme;

public class relationIcon implements Icon{

	private int width = 140;
	private int height = 50;
	
	@Override
    public int getIconWidth() {
      return width;
    }
    @Override
    public int getIconHeight() {
      return height;
    }
    
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
    	Graphics2D g2d = (Graphics2D) g;
    	Theme theme = Theme.getInstancia();
    	g2d.setColor(theme.relation());
    	Polygon s = new Polygon();
        s.addPoint(0, 20);
        s.addPoint(50, 0);
        s.addPoint(100, 20);
        s.addPoint(50, 40);
        g.fillPolygon(s);
        g2d.setColor(theme.lines());
        g.drawPolygon(s);
        g2d.draw(new Line2D.Double(45,20,55,20));
        g2d.draw(new Line2D.Double(50,15,50,25));
    }
}

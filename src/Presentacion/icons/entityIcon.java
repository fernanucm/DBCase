package Presentacion.icons;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;

import Presentacion.Theme.Theme;

public class entityIcon implements Icon{
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
    	RoundRectangle2D rect = new RoundRectangle2D.Double(x, y,100, 40, 10, 10);
    	Theme theme = Theme.getInstancia();
    	g2d.setColor(theme.entity());
    	g2d.fill(rect);
    	g2d.setColor(theme.lines());
    	g2d.draw(rect);
    	g2d.draw(new Line2D.Double(45,20,55,20));
        g2d.draw(new Line2D.Double(50,15,50,25));
    }	
}

package Presentacion.Grafo;


/*
 * Copyright (c) 2005, the JUNG Project and the Regents of the University of
 * California All rights reserved.
 *
 * This software is open-source under the BSD license; see either "license.txt"
 * or http://jung.sourceforge.net/license.txt for a description.
 *
 * Created on Aug 23, 2005
 */

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JComponent;

import LogicaNegocio.Transfers.TransferAtributo;
import LogicaNegocio.Transfers.TransferEntidad;
import LogicaNegocio.Transfers.TransferRelacion;
import Persistencia.EntidadYAridad;
import Presentacion.Theme.Theme;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.util.Context;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeIndexFunction;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.EdgeShape.IndexedRendering;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.transform.LensTransformer;
import edu.uci.ics.jung.visualization.transform.MutableTransformer;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;


public class EdgeRenderer<V,E> implements Renderer.Edge<V, E> {
	public EdgeRenderer(Theme theme) {
		this.theme = theme;
	}
	private Theme theme;

	public void paintEdge(RenderContext<V,E> rc, Layout<V, E> layout, E e) {
		GraphicsDecorator g2d = rc.getGraphicsContext();
		g2d.setColor(theme.lines());
        Graph<V,E> graph = layout.getGraph();
        
        if (!rc.getEdgeIncludePredicate().evaluate(Context.<Graph<V,E>,E>getInstance(graph,e)))
            return;
        // don't draw edge if either incident vertex is not drawn
        Pair<V> endpoints = graph.getEndpoints(e);
        V v1 = endpoints.getFirst(); //Relación
        V v2 = endpoints.getSecond(); //Entidad
        Collection<E> aris= graph.getEdges();
        ArrayList<Pair<V>> lista= new ArrayList<Pair<V>>();//Lista que representa el grafo
        Pair<V> par;
        int numApariciones= 0;//Numero de veces que aparece la arista a dibujar en el grafo
        String nom1="";
        String nom2="";
        int tipo1=0, tipo2=0;
        
        for (E o : aris){
        	par = graph.getEndpoints(o);
        	if (endpoints.equals(par))//Lo que voy a añadir en la lista es igual que la arista que voy a pintar
        		numApariciones++;
        	lista.add(par);
        	
        	//Para la distribución de las lineas de los roles
        	if(par.getFirst() instanceof TransferRelacion){
        		TransferRelacion nombre1 = (TransferRelacion)par.getFirst();
        		nom1 = nombre1.toString();
        		tipo1=1;//Relación
        	}
        	if(par.getFirst() instanceof TransferEntidad){
        		TransferEntidad nombre1 = (TransferEntidad)par.getFirst();
        		nom1 = nombre1.toString();
        		tipo1=2;//Entidad
        	}
        	if(par.getFirst() instanceof TransferAtributo){
        		TransferAtributo nombre1 = (TransferAtributo)par.getFirst();
        		nom1 = nombre1.toString();
        		tipo1=3;//Atributo
        	}
        
        	//-------------------------------
        	if(par.getSecond() instanceof TransferRelacion){
        		TransferRelacion nombre2 = (TransferRelacion)par.getSecond();
        		nom2 = nombre2.toString();
        		tipo2=1;//Relación
        	}
        	if(par.getSecond() instanceof TransferEntidad){
        		TransferEntidad nombre2 = (TransferEntidad)par.getSecond();
        		nom2 = nombre2.toString();
        		tipo2=2;//Entidad
        	}
        	if(par.getSecond() instanceof TransferAtributo){
        		TransferAtributo nombre2 = (TransferAtributo)par.getSecond();
        		nom2 = nombre2.toString();
        		tipo2=3;//Atributo
        	}
        } //Fin del bucle      
              	
        if (!rc.getVertexIncludePredicate().evaluate(Context.<Graph<V,E>,V>getInstance(graph,v1)) || 
            !rc.getVertexIncludePredicate().evaluate(Context.<Graph<V,E>,V>getInstance(graph,v2)))
            return;
        
        Stroke new_stroke = rc.getEdgeStrokeTransformer().transform(e);
        Stroke old_stroke = g2d.getStroke();
        if (new_stroke != null)
            g2d.setStroke(new_stroke);
        
        //Dibujo tantas aristas como asociaciones haya entre los nodos 
        for (int i=numApariciones; i>0;i--){
        	drawSimpleEdge(rc, layout, e,nom1,nom2,tipo1,tipo2,numApariciones,i);
        }

        // restore paint and stroke
        if (new_stroke != null)
            g2d.setStroke(old_stroke);
        
}
	private int minimo(int a,int b){
		if (a>=b) return b;
    	else return a;
	}

    /**
     * Draws the edge <code>e</code>, whose endpoints are at <code>(x1,y1)</code>
     * and <code>(x2,y2)</code>, on the graphics context <code>g</code>.
     * The <code>Shape</code> provided by the <code>EdgeShapeFunction</code> instance
     * is scaled in the x-direction so that its width is equal to the distance between
     * <code>(x1,y1)</code> and <code>(x2,y2)</code>.
     */
   @SuppressWarnings("unchecked")
protected void drawSimpleEdge(RenderContext<V,E> rc, Layout<V,E> layout, E e,String nombre1, 
						String nombre2,int tipo1,int tipo2,int numApariciones, int vuelta) {
        
        GraphicsDecorator g = rc.getGraphicsContext();
        g.setColor(theme.lines());
        Graphics2D graf2d = g.getDelegate();//NUEVO!!!
        Graph<V,E> graph = layout.getGraph();
        Pair<V> endpoints = graph.getEndpoints(e);
        V v1 = endpoints.getFirst();//Relación
        V v2 = endpoints.getSecond();//Entidad
        
        Point2D p1 = layout.transform(v1);//Coordenadas de la relacion
        Point2D p2 = layout.transform(v2);//Coordenadas de la entidad
        p1 = rc.getMultiLayerTransformer().transform(Layer.LAYOUT, p1);
        p2 = rc.getMultiLayerTransformer().transform(Layer.LAYOUT, p2);
        
        //Ancho del rectángulo donde va la entidad. Sólo importa su tamaño en IsA->Hija
        int anchoRect=0;
        
        //A las coordenadas se le suma el parametro noTocan para evitar que se superpongan las lineas si hay que pintar muchas
        float xIsA = (float) p1.getX();//Coordenada x de la IsA
       	float yIsA = (float) p1.getY();//Coordenada y de la IsA
       	float xEnti = (float) p2.getX();//Coordenada x de la entidad	        	        
       	float yEnti= (float) p2.getY();//Coordenada y de la entidad
       	
       	//Variables para calcular el centro desde donde se pinta la arista, y la inclinación
       	float dx = 0;
        float dy = 0;            
        float thetaRadians = 0;
        
        boolean diagonal=false;
       
       	//Si la relacion es IsA en lugar de una arista normal se pintará una flecha	
        if((endpoints.getFirst() instanceof TransferRelacion) && 
        		(((TransferRelacion)endpoints.getFirst()).getTipo().equals("IsA")) ){
	         
        		TransferRelacion rela =(TransferRelacion)endpoints.getFirst();
        		EntidadYAridad padre = (EntidadYAridad)rela.getListaEntidadesYAridades().get(0);
        		int idPadre =padre.getEntidad();        		
        		TransferEntidad enti =(TransferEntidad)endpoints.getSecond();
        		//Flecha del padre a la relación IsA
        		if(enti.getIdEntidad()==idPadre){
        			Arrow miFlecha= new Arrow();
        			miFlecha.createArrow(yEnti,yIsA,xEnti,xIsA,true,anchoRect);
        			miFlecha.paintComponent(graf2d,xIsA,yIsA,xEnti,yEnti,true,anchoRect);
        		}
        		//Flecha de la relación IsA al hijo
        		else{
        			anchoRect = enti.getNombre().length();
        			Arrow miFlecha= new Arrow();
        			miFlecha.createArrow(yEnti,yIsA,xEnti,xIsA,false,anchoRect);
        			miFlecha.paintComponent(graf2d,xIsA,yIsA,xEnti,yEnti,false,anchoRect);
        		}
	        diagonal= false;
        }
        //Si la relacion es IsA en lugar de una arista normal se pintará una flecha
        else if((endpoints.getSecond() instanceof TransferRelacion) && 
        		(((TransferRelacion)endpoints.getSecond()).getTipo().equals("IsA")) ){	        
        	TransferRelacion rela =(TransferRelacion)endpoints.getSecond();
    		EntidadYAridad padre = (EntidadYAridad)rela.getListaEntidadesYAridades().get(0);
    		int idPadre =padre.getEntidad();
    		TransferEntidad enti =(TransferEntidad)endpoints.getFirst();
    		//Flecha del padre a la relación IsA
    		if(enti.getIdEntidad()==idPadre){
    			Arrow miFlecha= new Arrow();
    			miFlecha.createArrow(yEnti,yIsA,xEnti,xIsA,true,anchoRect);
    			miFlecha.paintComponent(graf2d,xIsA,yIsA,xEnti,yEnti,true,anchoRect);
    		}
    		//Flecha de la relación IsA al hijo
    		else{
    			anchoRect = enti.getNombre().length();
    			Arrow miFlecha= new Arrow();
    			miFlecha.createArrow(yIsA,yEnti,xIsA,xEnti,false,anchoRect);
    			miFlecha.paintComponent(graf2d,xIsA,yIsA,xEnti,yEnti,false,anchoRect);
    		}
    		diagonal=false;
        }        
        //Si no es IsA se pinta siempre una arista normal no dirigida
        else{
	        boolean isLoop = v1.equals(v2);
	        Shape s2 = rc.getVertexShapeTransformer().transform(v2);
	        Shape edgeShape = rc.getEdgeShapeTransformer().transform(Context.<Graph<V,E>,E>getInstance(graph, e));      
	        boolean edgeHit = true;
	        boolean arrowHit = true;
	        Rectangle deviceRectangle = null;
	        JComponent vv = rc.getScreenDevice();
	        if(vv != null) {
	            Dimension d = vv.getSize();
	            deviceRectangle = new Rectangle(0,0,d.width,d.height);
	        }
	        //parte de código nueva para las líneas de los roles
	        float xCentro;
	       	float yCentro;
	       	float xNoCentro;
	       	float yNoCentro;	       	
	       	
	       	int alto = 0,anchoNoCentro=0, altoNoCentro=0;
	        //Calculo el ancho mínimo entre la relación y la entidad.
	        int ancho = minimo (nombre1.length(),nombre2.length());
	        //Hay que saber si el ancho mínimo es de la entidad o de la relación
	        if(ancho == nombre1.length()){
	        	if (tipo1==1){
	        		xCentro = xIsA;
	        		yCentro = yIsA;
	        		xNoCentro = xEnti;
	        		yNoCentro = yEnti;
	        		anchoNoCentro = nombre2.length();
	        	}
	        	else if(tipo1==3){
	        		xCentro = xIsA;
	        		yCentro = yIsA;
	        		xNoCentro = xEnti;
	        		yNoCentro = yEnti;
	        		anchoNoCentro = nombre2.length();
	        	}
	        	else{
	        		xCentro = xIsA;
	        		yCentro = yIsA;
	        		xNoCentro = xEnti;
	        		yNoCentro = yEnti;
	        		anchoNoCentro = nombre2.length();
	        	}
	        }
	        else{
	        	//Es una relacion
	        	if (tipo2==1){
	        		xCentro = xEnti;
	        		yCentro = yEnti;
	        		xNoCentro = xIsA;
	        		yNoCentro = yIsA;
	        		anchoNoCentro=nombre1.length();
	        	}
	        	//Es un atributo
	        	else if(tipo2==3){
	        		xCentro = xIsA;
	        		yCentro = yIsA;
	        		xNoCentro = xEnti;
	        		yNoCentro = yEnti;
	        		anchoNoCentro=nombre2.length();
	        	}
	        	//Es una entidad
	        	else{
	        		xCentro = xIsA;
	        		yCentro = yIsA;
	        		xNoCentro = xEnti;
	        		yNoCentro = yEnti;
	        		anchoNoCentro=nombre2.length();
	        	}
	        	//anchoNoCentro=nombre2.length();//habia un 1
	        }
	        //Si el ancho  es menor que 8 la figura tiene un tamaño fijo
	        if(ancho < 8){
	        	ancho = 45;
	        	alto = 20;
	        }
	        //Si no el ancho es proporcional a la longitud del nombre
	        else{
	        	ancho = (ancho *5) +5;
	        	alto = 25;
	        }
	        //Si el ancho de la otra figura es menor que 8 la figura tiene un tamaño fijo
	        if(anchoNoCentro < 8){
	        	anchoNoCentro = 45;
	        	altoNoCentro = 20;
	        }
	        //Si no el ancho de la otra figura es proporcional a la longitud del nombre
	        else{
	        	anchoNoCentro = (anchoNoCentro *5) +5;
	        	altoNoCentro = 20;
	        }
	             
	        
	        //Dependiendo de la situación relativa entre la 
	       AffineTransform xform = null;
	       
	        //Si no hay que ajustar las líneas de los roles para que se vean correctamente
	        if (numApariciones > 1){
	        	double incrementoX = 0;
	        	double incrementoY = 0;
	        	int epsilon = ancho /4;
	        	/*La separación entre las líneas de los roles es proporcional al número de veces que participe la entidad en la relación
	        	 * y la posición relativa entre la entidad y la relacion*/
	        	if ((((xNoCentro+anchoNoCentro)>=(xCentro-epsilon))&&((xNoCentro-anchoNoCentro) <= xCentro+epsilon)) ||
	        		((xNoCentro>=(xCentro-epsilon))&&(xNoCentro <= xCentro+epsilon))){
	        		//Están a distintas alturas pero en la misma franja de las xs
	        		incrementoY = 0;
	        		if (numApariciones > 3){
	        			incrementoX = (ancho*2)/(numApariciones+1);
	        			xform = AffineTransform.getTranslateInstance(xCentro+ancho-(vuelta*incrementoX), yCentro+(vuelta*incrementoY)); 
	        		}
	        		else if ((numApariciones == 2)||(numApariciones == 3)){
	        			if(vuelta==1)
	        				xform = AffineTransform.getTranslateInstance(xCentro+ancho-7, yCentro+(vuelta*incrementoY)); 
	        			else if (vuelta ==2)
	        				xform = AffineTransform.getTranslateInstance(xCentro-ancho+7, yCentro+(vuelta*incrementoY)); 
	        			
	        			else if (vuelta==3)
	        				xform = AffineTransform.getTranslateInstance(xCentro, yCentro+(vuelta*incrementoY));	
	        		}
	        		diagonal=false;
	        	}
	        	else if((((yNoCentro+altoNoCentro)>=(yCentro-epsilon))&&((yNoCentro-altoNoCentro)<=(yCentro+epsilon))) ||
	        			((yNoCentro>=(yCentro-epsilon))&&(yNoCentro<=(yCentro+epsilon)))){
	        	//Están en la misma franja de las ys
	        		incrementoX = 0;
	        		if (numApariciones > 3){
	        			incrementoY = (alto*2)/(numApariciones+1);
	        			xform = AffineTransform.getTranslateInstance(xCentro+(vuelta*incrementoX), yCentro+alto-(vuelta*incrementoY)); 
	        		}
	        		else if ((numApariciones == 2)||(numApariciones == 3)){
	        			if(vuelta==1)
	        				xform = AffineTransform.getTranslateInstance(xCentro+(vuelta*incrementoX), yCentro+alto-7); 
	        			else if (vuelta ==2)
	        				xform = AffineTransform.getTranslateInstance(xCentro+(vuelta*incrementoX), yCentro-alto+7); 
	        			
	        			else if (vuelta==3)
	        				xform = AffineTransform.getTranslateInstance(xCentro+(vuelta*incrementoX), yCentro+(vuelta*incrementoY));	
	        		}
	        		diagonal= false;	        		
	        	}
	        	//Diagonal inferior izquierda
	        	else if(((yNoCentro-altoNoCentro)>(yCentro+epsilon)) && ((xNoCentro-anchoNoCentro)<(xCentro-epsilon))){
	        		incrementoX = ancho/(numApariciones+1);
	        		incrementoY = alto /(numApariciones+1);
	        		xform = AffineTransform.getTranslateInstance(xCentro-(vuelta*incrementoX), yCentro+alto-(vuelta*incrementoY));	
	        		diagonal=true;
	        	}
	        	//Diagonal superior izquierda
	        	else if(((yNoCentro+altoNoCentro)<(yCentro-epsilon))&&(xNoCentro-anchoNoCentro)<=(xCentro+epsilon)){
	        		incrementoX = ancho/(numApariciones+1);
	        		incrementoY = alto /(numApariciones+1);
	        		xform = AffineTransform.getTranslateInstance(xCentro-ancho+(vuelta*incrementoX), yCentro-(vuelta*incrementoY));	
	        		diagonal=true;
	        	}
	        	//Diagonal superior derecha
	        	else if(((xNoCentro-anchoNoCentro)>(xCentro+epsilon))&& (yNoCentro+altoNoCentro)<(yCentro-epsilon)){
	        		incrementoX = ancho/(numApariciones+1);
	        		incrementoY = alto /(numApariciones+1);
	        		xform = AffineTransform.getTranslateInstance(xCentro+(vuelta*incrementoX), yCentro-alto+(vuelta*incrementoY));	
	        		diagonal=true;
	        	}
	        	//Diagonal inferior derecha
	        	else if(((xNoCentro-anchoNoCentro)>(xCentro+epsilon)&&((yNoCentro-altoNoCentro)>yCentro+epsilon))){
	        		incrementoX = ancho/(numApariciones+1);
	        		incrementoY = alto /(numApariciones+1);
	        		xform = AffineTransform.getTranslateInstance(xCentro+ancho-(vuelta*incrementoX), yCentro+(vuelta*incrementoY));	
	        		diagonal=true;
	        	}
	        	else{
	        		xform = AffineTransform.getTranslateInstance(xCentro, yCentro); 
	        	}
	        }
	        //Si la entidad sólo participa una vez en la relación no hay que hacer más cálculos
	        else{
	        	xform = AffineTransform.getTranslateInstance(xCentro, yCentro); 
	        }
	        //Fin de lo nuevo
	        //AffineTransform xform = AffineTransform.getTranslateInstance(xIsA+numApariciones, yIsA+numApariciones);  //ESTO ESTABA DESCOMENTADO     
	        if(isLoop) {
	            // this is a self-loop. scale it is larger than the vertex
	            // it decorates and translate it so that its nadir is
	            // at the center of the vertex.
	            Rectangle2D s2Bounds = s2.getBounds2D();
	            xform.scale(s2Bounds.getWidth(),s2Bounds.getHeight());
	            xform.translate(0, -edgeShape.getBounds2D().getWidth()/2);
	        } else 
	         if(rc.getEdgeShapeTransformer() instanceof EdgeShape.Orthogonal) {//NO ENTRO POR AQUI!!!!!!!!!!!!!!!!!!!!
	        	dx = xEnti-xIsA;
	            dy = yEnti-yIsA;
	            int index = 0;
	            if(rc.getEdgeShapeTransformer() instanceof IndexedRendering) {
	            	EdgeIndexFunction<V,E> peif = 
	            		((IndexedRendering<V,E>)rc.getEdgeShapeTransformer()).getEdgeIndexFunction();
	            	index = peif.getIndex(graph, e);
	            	index *= 20;            	
	            }
	            GeneralPath gp = new GeneralPath();
	            gp.moveTo(0,0);// the xform will do the translation to x1,y1
	            if(xIsA > xEnti) {
	            	if(yIsA > yEnti) {
	            		gp.lineTo(0, index);
	            		gp.lineTo(dx-index, index);
	            		gp.lineTo(dx-index, dy);
	            		gp.lineTo(dx, dy);
	            	} else {
	            		gp.lineTo(0, -index);
	            		gp.lineTo(dx-index, -index);
	            		gp.lineTo(dx-index, dy);
	            		gp.lineTo(dx, dy);
	            	}
	
	            } else {
	            	if(yIsA > yEnti) {
	            		gp.lineTo(0, index);
	            		gp.lineTo(dx+index, index);
	            		gp.lineTo(dx+index, dy);
	            		gp.lineTo(dx, dy);
	            		
	            	} else {
	            		gp.lineTo(0, -index);
	            		gp.lineTo(dx+index, -index);
	            		gp.lineTo(dx+index, dy);
	            		gp.lineTo(dx, dy);	            		
	            	}	            	
	            }	
	            edgeShape = gp;
	        } else {
	            // this is a normal edge. Rotate it to the angle between
	            // vertex endpoints, then scale it to the distance between
	            // the vertices 
	            dx = xEnti-xIsA;
	            dy = yEnti-yIsA;
	            thetaRadians = (float) Math.atan2(dy, dx);//Pasa de coordenadas cartesianas a coordenadas polares
	            xform.rotate(thetaRadians);// Concatenates this transform with a rotation transformation.
	            float dist = (float) Math.sqrt(dx*dx + dy*dy);
	            if(diagonal)
	            	dist = dist -15;
	            xform.scale(dist, 1.0);//Concatenates this transform with a scaling transformation.
	        }	        
	        edgeShape = xform.createTransformedShape(edgeShape);
	        
	        MutableTransformer vt = rc.getMultiLayerTransformer().getTransformer(Layer.VIEW);
	        if(vt instanceof LensTransformer) {
	        	vt = ((LensTransformer)vt).getDelegate();
	        }
	        edgeHit = vt.transform(edgeShape).intersects(deviceRectangle);	
	        if(edgeHit == true) {	            
	            Paint oldPaint = g.getPaint();
	            
	            // get Paints for filling and drawing
	            // (filling is done first so that drawing and label use same Paint)
	            Paint fill_paint = rc.getEdgeFillPaintTransformer().transform(e); 
	            if (fill_paint != null)
	            {
	                g.setPaint(fill_paint);
	                g.fill(edgeShape);	
	            }
	            Paint draw_paint = rc.getEdgeDrawPaintTransformer().transform(e);
	            if (draw_paint != null)
	            {
	                g.setPaint(draw_paint);
	                g.draw(edgeShape);
	            }
	            
	            float scalex = (float)g.getTransform().getScaleX();
	            float scaley = (float)g.getTransform().getScaleY();
	            // see if arrows are too small to bother drawing
	            if(scalex < .3 || scaley < .3) return;
	            
	            if (rc.getEdgeArrowPredicate().evaluate(Context.<Graph<V,E>,E>getInstance(graph, e))) {
	            	
	                //Stroke new_stroke = rc.getEdgeArrowStrokeTransformer().transform(e);
	            	Stroke new_stroke = rc.getEdgeStrokeTransformer().transform(e);
	                Stroke old_stroke = g.getStroke();
	                if (new_stroke != null)
	                    g.setStroke(new_stroke);	
	                
	                Shape destVertexShape = 
	                    rc.getVertexShapeTransformer().transform(graph.getEndpoints(e).getSecond());
	
	                AffineTransform xf = AffineTransform.getTranslateInstance(xEnti, yEnti);
	                destVertexShape = xf.createTransformedShape(destVertexShape);                
	                arrowHit = rc.getMultiLayerTransformer().getTransformer(Layer.VIEW).transform(destVertexShape).intersects(deviceRectangle);
	                if(arrowHit) {                    
	                    AffineTransform at = 
	                        getArrowTransform(rc, new GeneralPath(edgeShape), destVertexShape);
	                    if(at == null) return;
	                    Shape arrow = rc.getEdgeArrowTransformer().transform(Context.<Graph<V,E>,E>getInstance(graph, e));
	                    arrow = at.createTransformedShape(arrow);
	                    g.setPaint(rc.getArrowFillPaintTransformer().transform(e));
	                    g.fill(arrow);
	                    g.setPaint(rc.getArrowDrawPaintTransformer().transform(e));
	                    g.draw(arrow);
	                }
	                if (graph.getEdgeType(e) == EdgeType.UNDIRECTED) {
	                    Shape vertexShape = 
	                        rc.getVertexShapeTransformer().transform(graph.getEndpoints(e).getFirst());
	                    xf = AffineTransform.getTranslateInstance(xIsA, yIsA);
	                    vertexShape = xf.createTransformedShape(vertexShape);
	                    
	                    arrowHit = rc.getMultiLayerTransformer().getTransformer(Layer.VIEW).transform(vertexShape).intersects(deviceRectangle);
	                    
	                    if(arrowHit) {
	                        AffineTransform at = getReverseArrowTransform(rc, new GeneralPath(edgeShape), vertexShape, !isLoop);
	                        if(at == null) return;
	                        Shape arrow = rc.getEdgeArrowTransformer().transform(Context.<Graph<V,E>,E>getInstance(graph, e));
	                        arrow = at.createTransformedShape(arrow);
	                        g.setPaint(rc.getArrowFillPaintTransformer().transform(e));
	                        g.fill(arrow);
	                        g.setPaint(rc.getArrowDrawPaintTransformer().transform(e));
	                        g.draw(arrow);
	                    }
	                }
	                // restore paint and stroke
	                if (new_stroke != null)
	                    g.setStroke(old_stroke);
	            }            
	            // restore old paint
	            g.setPaint(oldPaint);
	        }
        }//Else del no es isa 
    }
    /**
     * Returns a transform to position the arrowhead on this edge shape at the
     * point where it intersects the passed vertex shape.
     */
    public AffineTransform getArrowTransform(RenderContext<V,E> rc, GeneralPath edgeShape, Shape vertexShape) {
        float[] seg = new float[6];
        Point2D p1=null;
        Point2D p2=null;
        AffineTransform at = new AffineTransform();
        // when the PathIterator is done, switch to the line-subdivide
        // method to get the arrowhead closer.
        for(PathIterator i=edgeShape.getPathIterator(null,1); !i.isDone(); i.next()) {
            int ret = i.currentSegment(seg);
            if(ret == PathIterator.SEG_MOVETO) {
                p2 = new Point2D.Float(seg[0],seg[1]);
            } else if(ret == PathIterator.SEG_LINETO) {
                p1 = p2;
                p2 = new Point2D.Float(seg[0],seg[1]);
                if(vertexShape.contains(p2)) {
                    at = getArrowTransform(rc, new Line2D.Float(p1,p2),vertexShape);
                    break;
                }
            } 
        }
        return at;
    }

    /**
     * Returns a transform to position the arrowhead on this edge shape at the
     * point where it intersects the passed vertex shape.
     */
    public AffineTransform getReverseArrowTransform(RenderContext<V,E> rc, GeneralPath edgeShape, Shape vertexShape) {
        return getReverseArrowTransform(rc, edgeShape, vertexShape, true);
    }
            
    /**
     * <p>Returns a transform to position the arrowhead on this edge shape at the
     * point where it intersects the passed vertex shape.</p>
     * 
     * <p>The Loop edge is a special case because its staring point is not inside
     * the vertex. The passedGo flag handles this case.</p>
     * 
     * @param edgeShape
     * @param vertexShape
     * @param passedGo - used only for Loop edges
     */
   public AffineTransform getReverseArrowTransform(RenderContext<V,E> rc, GeneralPath edgeShape, Shape vertexShape,
            boolean passedGo) {
        float[] seg = new float[6];
        Point2D p1=null;
        Point2D p2=null;

        AffineTransform at = new AffineTransform();
        for(PathIterator i=edgeShape.getPathIterator(null,1); !i.isDone(); i.next()) {
            int ret = i.currentSegment(seg);
            if(ret == PathIterator.SEG_MOVETO) {
                p2 = new Point2D.Float(seg[0],seg[1]);
            } else if(ret == PathIterator.SEG_LINETO) {
                p1 = p2;
                p2 = new Point2D.Float(seg[0],seg[1]);
                if(passedGo == false && vertexShape.contains(p2)) {
                    passedGo = true;
                 } else if(passedGo==true &&
                        vertexShape.contains(p2)==false) {
                     at = getReverseArrowTransform(rc, new Line2D.Float(p1,p2),vertexShape);
                    break;
                }
            } 
        }
        return at;
    }

    /**
     * This is used for the arrow of a directed and for one of the
     * arrows for non-directed edges
     * Get a transform to place the arrow shape on the passed edge at the
     * point where it intersects the passed shape
     * @param edgeShape
     * @param vertexShape
     * @return
     */
    public AffineTransform getArrowTransform(RenderContext<V,E> rc, Line2D edgeShape, Shape vertexShape) {
        float dx = (float) (edgeShape.getX1()-edgeShape.getX2());
        float dy = (float) (edgeShape.getY1()-edgeShape.getY2());
        // iterate over the line until the edge shape will place the
        // arrowhead closer than 'arrowGap' to the vertex shape boundary
        while((dx*dx+dy*dy) > rc.getArrowPlacementTolerance()) {
            try {
                edgeShape = getLastOutsideSegment(edgeShape, vertexShape);
            } catch(IllegalArgumentException e) {
                System.err.println(e.toString());
                return null;
            }
            dx = (float) (edgeShape.getX1()-edgeShape.getX2());
            dy = (float) (edgeShape.getY1()-edgeShape.getY2());
        }
        double atheta = Math.atan2(dx,dy)+Math.PI/2;
        AffineTransform at = 
            AffineTransform.getTranslateInstance(edgeShape.getX1(), edgeShape.getY1());
        at.rotate(-atheta);
        return at;
    }

    /**
     * This is used for the reverse-arrow of a non-directed edge
     * get a transform to place the arrow shape on the passed edge at the
     * point where it intersects the passed shape
     * @param edgeShape
     * @param vertexShape
     * @return
     */
    protected AffineTransform getReverseArrowTransform(RenderContext<V,E> rc, Line2D edgeShape, Shape vertexShape) {
        float dx = (float) (edgeShape.getX1()-edgeShape.getX2());
        float dy = (float) (edgeShape.getY1()-edgeShape.getY2());
        // iterate over the line until the edge shape will place the
        // arrowhead closer than 'arrowGap' to the vertex shape boundary
        while((dx*dx+dy*dy) > rc.getArrowPlacementTolerance()) {
            try {
                edgeShape = getFirstOutsideSegment(edgeShape, vertexShape);
            } catch(IllegalArgumentException e) {
                System.err.println(e.toString());
                return null;
            }
            dx = (float) (edgeShape.getX1()-edgeShape.getX2());
            dy = (float) (edgeShape.getY1()-edgeShape.getY2());
        }
        // calculate the angle for the arrowhead
        double atheta = Math.atan2(dx,dy)-Math.PI/2;
        AffineTransform at = AffineTransform.getTranslateInstance(edgeShape.getX1(),edgeShape.getY1());
        at.rotate(-atheta);
        return at;
    }
    
    /**
     * Passed Line's point2 must be inside the passed shape or
     * an IllegalArgumentException is thrown
     * @param line line to subdivide
     * @param shape shape to compare with line
     * @return a line that intersects the shape boundary
     * @throws IllegalArgumentException if the passed line's point1 is not inside the shape
     */
    protected Line2D getLastOutsideSegment(Line2D line, Shape shape) {
        if(shape.contains(line.getP2())==false) {
            String errorString =
                "line end point: "+line.getP2()+" is not contained in shape: "+shape.getBounds2D();
            throw new IllegalArgumentException(errorString);
            //return null;
        }
        Line2D left = new Line2D.Double();
        Line2D right = new Line2D.Double();
        // subdivide the line until its left segment intersects
        // the shape boundary
        do {
            subdivide(line, left, right);
            line = right;
        } while(shape.contains(line.getP1())==false);
        // now that right is completely inside shape,
        // return left, which must be partially outside
        return left;
    }
   
    /**
     * Passed Line's point1 must be inside the passed shape or
     * an IllegalArgumentException is thrown
     * @param line line to subdivide
     * @param shape shape to compare with line
     * @return a line that intersects the shape boundary
     * @throws IllegalArgumentException if the passed line's point1 is not inside the shape
     */
    protected Line2D getFirstOutsideSegment(Line2D line, Shape shape) {
        
        if(shape.contains(line.getP1())==false) {
            String errorString = 
                "line start point: "+line.getP1()+" is not contained in shape: "+shape.getBounds2D();
            throw new IllegalArgumentException(errorString);
        }
        Line2D left = new Line2D.Float();
        Line2D right = new Line2D.Float();
        // subdivide the line until its right side intersects the
        // shape boundary
        do {
            subdivide(line, left, right);
            line = left;
        } while(shape.contains(line.getP2())==false);
        // now that left is completely inside shape,
        // return right, which must be partially outside
        return right;
    }

    /**
     * divide a Line2D into 2 new Line2Ds that are returned
     * in the passed left and right instances, if non-null
     * @param src the line to divide
     * @param left the left side, or null
     * @param right the right side, or null
     */
    protected void subdivide(Line2D src,
            Line2D left,
            Line2D right) {
        double x1 = src.getX1();
        double y1 = src.getY1();
        double x2 = src.getX2();
        double y2 = src.getY2();
        
        double mx = x1 + (x2-x1)/2.0;
        double my = y1 + (y2-y1)/2.0;
        if (left != null) {
            left.setLine(x1, y1, mx, my);
        }
        if (right != null) {
            right.setLine(mx, my, x2, y2);
        }
    }

}

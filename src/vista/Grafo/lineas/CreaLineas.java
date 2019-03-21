package vista.Grafo.lineas;


/*
 * Copyright (c) 2005, the JUNG Project and the Regents of the University of
 * California All rights reserved.
 *
 * This software is open-source under the BSD license; see either "license.txt"
 * or http://jung.sourceforge.net/license.txt for a description.
 *
 * Created on Aug 23, 2005
 */

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.util.Context;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import modelo.persistencia.EntidadYAridad;
import modelo.transfers.TransferAtributo;
import modelo.transfers.TransferEntidad;
import modelo.transfers.TransferRelacion;
import vista.Theme.Theme;


public class CreaLineas<V,E> implements Renderer.Edge<V, E> {
	private Theme theme;
	
	public CreaLineas() {
		this.theme = Theme.getInstancia();
	}
	

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
        if (new_stroke != null) g2d.setStroke(new_stroke);
        
        //Dibujo tantas aristas como asociaciones haya entre los nodos 
        for (int i=numApariciones; i>0;i--)
        	drawSimpleEdge(rc, layout, e,nom1,nom2,tipo1,tipo2,numApariciones,i);
        
        // restore paint and stroke
        if (new_stroke != null) g2d.setStroke(old_stroke);
	}


    /**
     * Draws the edge <code>e</code>, whose endpoints are at <code>(x1,y1)</code>
     * and <code>(x2,y2)</code>, on the graphics context <code>g</code>.
     * The <code>Shape</code> provided by the <code>EdgeShapeFunction</code> instance
     * is scaled in the x-direction so that its width is equal to the distance between
     * <code>(x1,y1)</code> and <code>(x2,y2)</code>.
     */
   @SuppressWarnings({ "unchecked", "rawtypes" })
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
        			Flecha miFlecha= new Flecha();
        			miFlecha.createArrow(yEnti,yIsA,xEnti,xIsA,true,anchoRect);
        			miFlecha.paintComponent(graf2d,xIsA,yIsA,xEnti,yEnti,true,anchoRect);
        		}
        		//Flecha de la relación IsA al hijo
        		else{
        			anchoRect = enti.getNombre().length();
        			Flecha miFlecha= new Flecha();
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
    			Flecha miFlecha= new Flecha();
    			miFlecha.createArrow(yEnti,yIsA,xEnti,xIsA,true,anchoRect);
    			miFlecha.paintComponent(graf2d,xIsA,yIsA,xEnti,yEnti,true,anchoRect);
    		}
    		//Flecha de la relación IsA al hijo
    		else{
    			anchoRect = enti.getNombre().length();
    			Flecha miFlecha= new Flecha();
    			miFlecha.createArrow(yIsA,yEnti,xIsA,xEnti,false,anchoRect);
    			miFlecha.paintComponent(graf2d,xIsA,yIsA,xEnti,yEnti,false,anchoRect);
    		}
    		diagonal=false;
        }
        //Si es linea de relacion a entidad
        else if(endpoints.getFirst() instanceof TransferRelacion && endpoints.getSecond() instanceof TransferEntidad){	        
        	TransferRelacion rela =(TransferRelacion)endpoints.getFirst();
        	EntidadYAridad ent = new EntidadYAridad();
        	for(int i=0;i<rela.getListaEntidadesYAridades().size();i++)
        		if(((EntidadYAridad) rela.getListaEntidadesYAridades().get(i)).getEntidad() == ((TransferEntidad) endpoints.getSecond()).getIdEntidad()) {
        			ent = (EntidadYAridad)rela.getListaEntidadesYAridades().get(i);
        			break;
        		}
    		//cardinalidad 0 .. 1
    		if(ent.getPrincipioRango()==0 && ent.getFinalRango()==1){
    			Flecha miFlecha= new Flecha();
    			miFlecha.createArrow(yEnti,yIsA,xEnti,xIsA,false,anchoRect);
    			miFlecha.paintComponent(graf2d,xIsA,yIsA,xEnti,yEnti,false,anchoRect);
    		}
    		//cardinalidad a .. b : a>=1
    		else if(ent.getPrincipioRango()>=1)
    			new DobleLinea(rc, e, nombre1, graph, diagonal, nombre2, thetaRadians, xIsA, yIsA, xEnti, yEnti, g);
    		else new LineaRecta(rc, layout, e, nombre1, graph, diagonal, nombre2, tipo1, tipo2, numApariciones, vuelta, thetaRadians, v1, v2, xIsA, yIsA, xEnti, yEnti, dx, dy, g);
    		diagonal=false;
        }
        //Para el resto se pinta siempre una arista normal no dirigida
        else new LineaRecta(rc, layout, e, nombre1, graph, diagonal, nombre2, tipo1, tipo2, numApariciones, vuelta, thetaRadians, v1, v2, xIsA, yIsA, xEnti, yEnti, dx, dy, g);
    }
}

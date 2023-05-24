package entrega1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ServicioDFS<T> {
	private Grafo<?> grafo;
    private HashMap<Integer, Integer> tiemposDescubrimiento;
    private HashMap<Integer, Integer> tiemposFinalizacion;
    private HashMap<Integer, Color> colores;

    public ServicioDFS(Grafo<?> grafo) {
        this.grafo = grafo;
        this.tiemposDescubrimiento = new HashMap<>();
        this.tiemposFinalizacion = new HashMap<>();
        this.colores = new HashMap<>();
    }
  
    public List<Integer> dfsForest() {
        List<Integer> ordenDescubrimiento = new ArrayList<>();
        int tiempo = 0;
        
        Iterator<Integer> vertices = grafo.obtenerVertices();
        while (vertices.hasNext()) {
            int vertice = vertices.next();
            colores.put(vertice, Color.BLANCO);
        }
   
        vertices = grafo.obtenerVertices();
        while (vertices.hasNext()) {
            int vertice = vertices.next();
            if (colores.get(vertice) == Color.BLANCO) {
            	dfs(vertice, tiempo, ordenDescubrimiento);
            }
        }
        return ordenDescubrimiento;
    }
    
    private void dfs(int vertice, int tiempo, List<Integer> ordenDescubrimiento) {
        tiemposDescubrimiento.put(vertice, tiempo++);
        colores.put(vertice, Color.AMARILLO);
        ordenDescubrimiento.add(vertice);
        
        Iterator<Integer> adyacentes = grafo.obtenerAdyacentes(vertice);
        while (adyacentes.hasNext()) {
            int adyacente = adyacentes.next();
            if (colores.get(adyacente) == Color.BLANCO) {
            	dfs(adyacente, tiempo, ordenDescubrimiento);
            }
        }
        colores.put(vertice, Color.NEGRO);
        tiemposFinalizacion.put(vertice, tiempo++);
    }
    
    public enum Color {
        BLANCO,
        AMARILLO,
        NEGRO
    }
    
    
  //EJERCICIO 3
  	public boolean tieneCiclo() {
  	    Map<Integer, Boolean> visitado = new HashMap<>();
  	    Map<Integer, Boolean> pilaRecursion = new HashMap<>();
  	    //Inicializar visitado y pilaRecursion
  	    Iterator<Integer> vertices = this.grafo.obtenerVertices();
  	    while(vertices.hasNext()) {
  	    	Integer vertice = vertices.next();
  	    	visitado.put(vertice, false);
  	        pilaRecursion.put(vertice, false);
;  	    }
  	    //Recursion
  	    vertices = this.grafo.obtenerVertices();
  	    while(vertices.hasNext()) {
  	    	Integer vertice = vertices.next();
  	    	if(!visitado.get(vertice)) {
  	    		if (dfsTieneCiclo(vertice, visitado, pilaRecursion)) {
  	                return true;
  	            }
  	    	}
  	    }
  	  return false;
  	}
  	private boolean dfsTieneCiclo(Integer vertice, Map<Integer, Boolean> visitado, Map<Integer, Boolean> pilaRecursion) {
  	    // Marcar el vértice como visitado y en la pila de recursión
  	    visitado.put(vertice, true);
  	    pilaRecursion.put(vertice, true);
  	    // Recorrer todos los vértices adyacentes al vértice actual
  	    Iterator<Integer> adyacentes = this.grafo.obtenerAdyacentes(vertice);
  	    while (adyacentes.hasNext()) {
  	        Integer adyacente = adyacentes.next();
  	        // Si el vértice adyacente no ha sido visitado, hace la recursión
  	        if (!visitado.get(adyacente)) {
  	            if (dfsTieneCiclo(adyacente, visitado, pilaRecursion)) {
  	                return true;
  	            }
  	        }
  	        // Si el vértice adyacente ya ha sido visitado y está en la pila de recursión, hay un ciclo
  	        else if (pilaRecursion.get(adyacente)) {
  	            return true;
  	        }
  	    }
  	    pilaRecursion.put(vertice, false);
  	    return false;
  	}
  	
  	//EJERCICIO 4

  	
}

package entrega1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class GrafoDirigido<T> implements Grafo<T> {

	private Map<Integer, ArrayList<Arco<T>>> vertices;
	
	public GrafoDirigido() {
		this.vertices = new HashMap<>();
	}
	
	public void agregarVertice(int verticeId) {
	    if (!vertices.containsKey(verticeId)) {
	        vertices.put(verticeId, new ArrayList<>());
	    }
	}

	@Override
	public void borrarVertice(int verticeId) {
        // Verificar si el vértice existe en el grafo
        if (!vertices.containsKey(verticeId)) {
            return; // No hay nada que borrar
        }

        // Eliminar el vértice de las listas de adyacencia de otros vértices
        for (ArrayList<Arco<T>> adyacentes : vertices.values()) {
            adyacentes.removeIf(arco -> arco.getVerticeDestino() == verticeId);
        }

        // Eliminar el vértice del mapa de vértices
        vertices.remove(verticeId);
    }
	
	@Override
	public void agregarArco(int verticeId1, int verticeId2, T etiqueta) {
        if (!vertices.containsKey(verticeId1) || !vertices.containsKey(verticeId2)) {
            return; // No se puede agregar un arco de un vértice que no existe
        }

        Arco<T> nuevoArco = new Arco<>(verticeId1, verticeId2, etiqueta);
        vertices.get(verticeId1).add(nuevoArco);
    }

	@Override
	public void borrarArco(int verticeId1, int verticeId2) {
	    // Verificar si los vértices existen en el grafo
	    if (!vertices.containsKey(verticeId1) || !vertices.containsKey(verticeId2)) {
	        return;
	    }
	    
	    // Obtener la lista de adyacencia del vértice 1
	    ArrayList<Arco<T>> adyacentes = vertices.get(verticeId1);
	 
	    // Buscar si existe un arco que va hacia el vértice 2
	    for (Arco<T> arco : adyacentes) {
	        if (arco.getVerticeDestino() == verticeId2) {
	            adyacentes.remove(arco);
	            return;
	        }
	    }		
	}

	@Override
	public boolean contieneVertice(int verticeId) {
		 return vertices.containsKey(verticeId);
	}

	@Override
	public boolean existeArco(int verticeId1, int verticeId2) {
	    // Verificar si los vértices existen en el grafo
	    if (!vertices.containsKey(verticeId1) || !vertices.containsKey(verticeId2)) {
	        return false;
	    }
	    // Buscar si existe un arco que va hacia el vértice 2
	    for (Arco<T> arco : vertices.get(verticeId1)) {
	        if (arco.getVerticeDestino() == verticeId2) {
	            return true;
	        }
	    }
	    return false;
	}

	@Override
	public Arco<T> obtenerArco(int verticeId1, int verticeId2) {
		// Verificar si los vértices existen en el grafo
	    if (!vertices.containsKey(verticeId1) || !vertices.containsKey(verticeId2)) {
	        return null;
	    }
	    // Buscar el arco que va hacia el vértice 2
	    for (Arco<T> arco : vertices.get(verticeId1)) {
	        if (arco.getVerticeDestino() == verticeId2) {
	            return arco;
	        }
	    }
	    return null;
	}

	@Override
	public int cantidadVertices() {
		return vertices.size();
	}

	@Override
	public int cantidadArcos() {
		 int totalArcos = 0;
		 for (ArrayList<Arco<T>> adyacentes : vertices.values()) {
		        totalArcos += adyacentes.size();
		 }
		 return totalArcos;
	}

	@Override
	public Iterator<Integer> obtenerVertices() {
		return vertices.keySet().iterator();
	}

	@Override
	public Iterator<Integer> obtenerAdyacentes(int verticeId) {
		ArrayList<Integer> adyacentes = new ArrayList<>();
		if (vertices.containsKey(verticeId)) {
			for (Arco<T> arco : vertices.get(verticeId)) {
				adyacentes.add(arco.getVerticeDestino());
			}
		}
		return adyacentes.iterator();
	}

	@Override
	public Iterator<Arco<T>> obtenerArcos() {
		ArrayList<Arco<T>> arcos = new ArrayList<>();
		for (ArrayList<Arco<T>> listaArcos : vertices.values()) {
	        arcos.addAll(listaArcos);
	    }
	    return arcos.iterator();
	}

	@Override
	public Iterator<Arco<T>> obtenerArcos(int verticeId) {
		ArrayList<Arco<T>> arcos = new ArrayList<>();
		if (vertices.containsKey(verticeId)) {
	        for (Arco<T> arco : vertices.get(verticeId)) {
	            arcos.add(arco);
	        }
	    }
	    return arcos.iterator();
	}
}

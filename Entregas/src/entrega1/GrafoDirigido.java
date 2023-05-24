package entrega1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.*;

public class GrafoDirigido<T> implements Grafo<T> {

	private Map<Integer, ArrayList<Arco<T>>> vertices;
	
	public GrafoDirigido() {
		this.vertices = new HashMap<>();
	}
	
	/**
	* Complejidad: O(n) en el peor de los casos, en el caso de que haya una colision de hash.
	* La complejidad es O(1) en la mayoria de los casos debido a que la operacion put() sobre un HashMap
	* tiene esta complejidad en el caso de que no se produzca una colision de hash.
	*/
	@Override
	public void agregarVertice(int verticeId) {
		if (!vertices.containsKey(verticeId)) {
			this.vertices.put(verticeId, new ArrayList<Arco<T>>());
		}
	}
	
	/**
	* Complejidad: O(n), siendo n el numero de vertices del grafo ya que en el peor de los casos hay que 
	* recorrer todas las listas de adyacencia de cada vertice para corroborar que no existan arcos apuntando
	* al vertice borrado.
	*/
	@Override
	public void borrarVertice(int verticeId) {
        if (!vertices.containsKey(verticeId)) {
            return; // No hay nada que borrar
        }

        // Eliminar el vértice de las listas de adyacencia de otros vértices
        for (ArrayList<Arco<T>> adyacentes : this.vertices.values()) {
            adyacentes.removeIf(arco -> arco.getVerticeDestino() == verticeId);
        }

        vertices.remove(verticeId);
    }

	/**
	* Complejidad: O(1), ya que solo se le pregunta a la lista de adyacencia de el vertice origen si no 
	* contiene un arco igual al que se va a agregar, y en el caso de que no lo tenga, se agrega. En este 
	* caso la lista es un ArrayList, y ambos procedimientos, tanto encontrar un elemento, o agregar un elemento,
	* tienen complejidad O(1). 
	*/
	@Override
	public void agregarArco(int verticeId1, int verticeId2, T etiqueta) {
		if (vertices.containsKey(verticeId1) && vertices.containsKey(verticeId2)) {//Si contiene los dos vertices
			Arco<T> nuevoArco = new Arco<T>(verticeId1, verticeId2, etiqueta);
			
			if(!vertices.get(verticeId1).contains(nuevoArco)) {//Comprueba que no haya otro igual
				vertices.get(verticeId1).add(nuevoArco); 
			}
		}
	}
	
	/**
	* Complejidad: O(n), siendo n el numero de arcos del verticeOrigen, ya que en el peor de los casos se
	* borraria el ultimo arco de la lista de adyacencia de este.
	*/
	@Override
	public void borrarArco(int verticeOrigen, int verticeDestino) {
	    if (!vertices.containsKey(verticeOrigen) || !vertices.containsKey(verticeDestino)) {
	        return;
	    }
	    
		List<Arco<T>> arcosAsociados = this.vertices.get(verticeOrigen); 
		for(int i = 0; i < arcosAsociados.size(); i++) {
			if(arcosAsociados.get(i).getVerticeDestino() == verticeDestino) {
				arcosAsociados.remove(i);
				return;
			}
		}
	}

	/**
	* Complejidad: O(1), ya que solo se realiza una consulta al HashMap que guarda los vertices si
	* el que fue ingresado existe con el metodo containsKey(key) que tiene complejidad O(1).
	*/
	@Override
	public boolean contieneVertice(int verticeId) {
		return this.vertices.containsKey(verticeId);
	}

	/**
	* Complejidad: O(n), siendo n el numero de arcos del verticeOrigen, ya que en el peor de los casos se 
	* recorreria toda la lista de adyacencia sin encontrar el arcoBuscado
	*/	
	@Override
	public boolean existeArco(int verticeOrigen, int verticeDestino) {
	    if (!vertices.containsKey(verticeOrigen) || !vertices.containsKey(verticeDestino)) {
	        return false;
	    }
		
	    List<Arco<T>> arcosAsociados = this.vertices.get(verticeOrigen); 
		for(int i = 0; i < arcosAsociados.size(); i++) {
			if(arcosAsociados.get(i).getVerticeDestino() == verticeDestino) {
				return true;
			}
		}
		
	    return false;
	}

	/**
	* Complejidad: O(n), siendo n el numero de arcos del verticeOrigen, ya que en el peor de los casos se 
	* recorreria toda la lista de adyacencia sin encontrar el arcoBuscado
	*/	
	@Override
	public Arco<T> obtenerArco(int verticeOrigen, int verticeDestino) {
	    if (!vertices.containsKey(verticeOrigen) || !vertices.containsKey(verticeDestino)) {
	        return null;
	    }
	    
	    for (Arco<T> arco : vertices.get(verticeOrigen)) {
	        if (arco.getVerticeDestino() == verticeDestino) {
	            return arco;
	        }
	    }
	    
	    return null;
	}
	
	
	/**
	* Complejidad: O(1), ya que encontrar el tamanio de un HashMap es una operacion constante, no importa 
	* cuantos elementos tenga
	*/	
	@Override
	public int cantidadVertices() {
		return this.vertices.size();
	}

	/**
	* Complejidad: O(n), siendo n la cantidad de arcos de el grafo, ya que se tienen que pasar por
	* todos los arcos de cada vertice para obtener su total.
	*/	
	@Override
	public int cantidadArcos() {
		 int totalArcos = 0;
		 for (ArrayList<Arco<T>> adyacentes : vertices.values()) {
		        totalArcos += adyacentes.size();
		 }
		 return totalArcos;
	}
	
	/**
	* Complejidad: O(1), ya que se realiza una copia de las claves del mapa "vertices" 
	* y las almacena en un nuevo ArrayList llamado "itVertices" con el metodo keySet de HashMap que 
	* es un metodo constante O(1), y lo devuelve como iterador
	*/	
	@Override
	public Iterator<Integer> obtenerVertices() {
		ArrayList<Integer> itVertices = new ArrayList<>(this.vertices.keySet());
		return itVertices.iterator();
	}

	
	/**
	* Complejidad: O(n), siendo n la cantidad de adyacentes del vertice ingresado, ya que se tienen
	* que agregar en nuevo arreglo los n adyacentes, y luego devolver esta lista en forma de iterador
	*/	
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
	
	
	/**
	* Complejidad: O(n), siendo n la cantidad de arcos del grafo, ya que se tienen que agregar los n
	* arcos a un nuevo arreglo y luego devolverlo en forma de iterador
	*/	
	@Override
	public Iterator<Arco<T>> obtenerArcos() {
		ArrayList<Arco<T>> arcos = new ArrayList<>();
		for (ArrayList<Arco<T>> listaArcos : vertices.values()) {
	        arcos.addAll(listaArcos);
	    }
	    return arcos.iterator();
	}

	/**
	* Complejidad: O(n), siendo n la cantidad de arcos del vertice ingresado, ya que se tienen que
	* agregar los n arcos en un nuevo arreglo y devolverlos como un iterador
	*/	
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

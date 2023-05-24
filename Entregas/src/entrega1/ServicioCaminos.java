package entrega1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServicioCaminos<T> {
    private Grafo<?> grafo;
    private int origen;
    private int destino;
    private int limite;
    private List<List<Integer>> caminos;

    public ServicioCaminos(Grafo<?> grafo, int origen, int destino, int limite) {
        this.grafo = grafo;
        this.origen = origen;
        this.destino = destino;
        this.limite = limite;
        this.caminos = new ArrayList<>();
    }

    public List<List<Integer>> caminos() {
        List<Integer> caminoActual = new ArrayList<>();
        caminoActual.add(origen);
        dfsCaminos(origen, caminoActual, 0);
        return caminos;
    }

    private void dfsCaminos(int vertice, List<Integer> caminoActual, int arcosRecorridos) {
        if (vertice == destino && arcosRecorridos <= limite) {
            caminos.add(new ArrayList<>(caminoActual)); // Agregar el camino actual a la lista de caminos encontrados
        }

        if (arcosRecorridos < limite) {
            Iterator<Integer> adyacentes = grafo.obtenerAdyacentes(vertice);
            while (adyacentes.hasNext()) {
                int adyacente = adyacentes.next();
                if (!caminoActual.contains(adyacente)) { // Evitar ciclos en el camino
                    caminoActual.add(adyacente);
                    dfsCaminos(adyacente, caminoActual, arcosRecorridos + 1);
                    caminoActual.remove(caminoActual.size() - 1);
                }
            }
        }
    }
}

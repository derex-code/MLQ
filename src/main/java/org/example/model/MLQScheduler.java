/**
 * @autor Olman Alexander Silva 2343025-2724
 * @version 1.0
 */
package org.example.model;

import java.util.*;

/**
 * Clase que implementa el algoritmo de planificación Multilevel Queue (MLQ).
 */
public class MLQScheduler {
    private List<Process> procesos;

    /**
     * Constructor que inicializa la lista de procesos.
     * @param procesos Lista de procesos a planificar.
     */
    public MLQScheduler(List<Process> procesos) {
        this.procesos = procesos;
    }

    /**
     * Simula la ejecución de los procesos según el algoritmo MLQ.
     * @return Un String con los resultados de la simulación.
     */
    public String simular() {
        StringBuilder resultados = new StringBuilder();
        Map<Integer, Queue<Process>> colas = new TreeMap<>();

        // Clasifica los procesos en colas según su nivel de prioridad.
        for (Process p : procesos) {
            colas.putIfAbsent(p.getQueue(), new LinkedList<>());
            colas.get(p.getQueue()).add(p);
        }

        int tiempoActual = 0;
        for (Queue<Process> cola : colas.values()) {
            while (!cola.isEmpty()) {
                Process proceso = cola.poll();
                int tiempoEspera = Math.max(0, tiempoActual - proceso.getArrivalTime());
                int tiempoCompletado = tiempoActual + proceso.getBurstTime();

                resultados.append(String.format("%s;%d;%d;%d;%d;%d;%d\n",
                        proceso.getEtiqueta(), proceso.getBurstTime(), proceso.getArrivalTime(),
                        proceso.getQueue(), proceso.getPriority(), tiempoEspera, tiempoCompletado));

                tiempoActual = tiempoCompletado;
            }
        }

        return resultados.toString();
    }
}

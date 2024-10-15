package org.example.model;
import java.util.*;

public class MLQScheduler {
    private List<Process> procesos;

    public MLQScheduler(List<Process> procesos) {
        this.procesos = procesos;
    }

    public String simular() {
        StringBuilder resultados = new StringBuilder();
        Map<Integer, Queue<Process>> colas = new TreeMap<>(); // Tres niveles de prioridad

        // Clasificar procesos en sus respectivas colas por prioridad
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

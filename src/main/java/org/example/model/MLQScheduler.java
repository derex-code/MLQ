/**
 * @autor Olman Alexander Silva 2343025-2724
 * @version 1.0
 */
package org.example.model;

import java.util.*;

/**
 * Clase que implementa el algoritmo de planificación Multilevel Queue (MLQ).
 * Se encarga de gestionar procesos distribuidos en múltiples colas con diferentes políticas,
 * como Round Robin (RR), Shortest Job First (SJF), First Come First Serve (FCFS),
 * y Shortest Time to Completion First (STCF).
 */
public class MLQScheduler {
    private List<Process> procesos;  // Lista de procesos a planificar
    private int quantum1;            // Quantum para la primera cola (RR)
    private int quantum2;            // Quantum para la segunda cola (RR)
    private String politicaCola3;    // Política de la tercera cola (SJF, FCFS, o STCF)

    /**
     * Constructor que inicializa los procesos, los quantums, y la política de la tercera cola.
     *
     * @param procesos Lista de procesos a planificar.
     * @param quantum1 Quantum para la primera cola con política Round Robin (RR).
     * @param quantum2 Quantum para la segunda cola con política Round Robin (RR).
     * @param politicaCola3 Política para la tercera cola (SJF, FCFS, o STCF).
     */
    public MLQScheduler(List<Process> procesos, int quantum1, int quantum2, String politicaCola3) {
        this.procesos = procesos;
        this.quantum1 = quantum1;
        this.quantum2 = quantum2;
        this.politicaCola3 = politicaCola3;
    }

    /**
     * Simula la ejecución de los procesos según las políticas asignadas a cada cola.
     *
     * @param nombreArchivo Nombre del archivo de entrada.
     * @return Un String con los resultados de la simulación.
     */
    public String simular(String nombreArchivo) {
        Map<Integer, Queue<Process>> colas = new TreeMap<>();
        List<Process> finalizados = new ArrayList<>();

        // Clasificar los procesos por cola
        for (Process p : procesos) {
            colas.putIfAbsent(p.getQueue(), new LinkedList<>());
            colas.get(p.getQueue()).add(p);
        }

        int tiempoActual = 0;

        // Ejecutar Cola 1 (Round Robin con quantum1)
        if (colas.containsKey(1)) {
            tiempoActual = ejecutarRoundRobin(colas.get(1), tiempoActual, quantum1, colas, 2, finalizados);
        }

        // Ejecutar Cola 2 (Round Robin con quantum2)
        if (colas.containsKey(2)) {
            tiempoActual = ejecutarRoundRobin(colas.get(2), tiempoActual, quantum2, colas, 3, finalizados);
        }

        // Ejecutar Cola 3 con la política seleccionada
        if (colas.containsKey(3)) {
            switch (politicaCola3) {
                case "SJF":
                    tiempoActual = ejecutarSJF(colas.get(3), tiempoActual, finalizados);
                    break;
                case "FCFS":
                    tiempoActual = ejecutarFCFS(colas.get(3), tiempoActual, finalizados);
                    break;
                case "STCF":
                    tiempoActual = ejecutarSTCF(colas.get(3), tiempoActual, finalizados);
                    break;
            }
        }

        return generarResultados(finalizados, nombreArchivo);
    }

    /**
     * Ejecuta la política Round Robin (RR) en la cola especificada y mueve los procesos incompletos
     * a la siguiente cola si es necesario.
     *
     * @param cola Cola de procesos a ejecutar.
     * @param tiempoActual Tiempo actual del sistema.
     * @param quantum Quantum asignado para esta cola.
     * @param colas Estructura que contiene todas las colas.
     * @param siguienteCola La siguiente cola donde se moverán los procesos incompletos.
     * @param finalizados Lista de procesos que ya han sido completados.
     * @return El tiempo actual después de ejecutar todos los procesos.
     */
    private int ejecutarRoundRobin(Queue<Process> cola, int tiempoActual, int quantum,
                                   Map<Integer, Queue<Process>> colas, int siguienteCola,
                                   List<Process> finalizados) {

        Queue<Process> pendientes = new LinkedList<>(cola);
        Map<Process, Integer> tiemposRestantes = new HashMap<>();

        // Inicializar tiempos restantes para cada proceso
        for (Process p : pendientes) {
            tiemposRestantes.put(p, p.getBurstTime());
        }

        while (!pendientes.isEmpty()) {
            Process proceso = pendientes.poll();
            int tiempoRestante = tiemposRestantes.get(proceso);

            // Registrar el tiempo de respuesta (RT) si es la primera vez que se ejecuta
            if (proceso.getResponseTime() == -1) {
                proceso.setResponseTime(tiempoActual);
            }

            // Ejecutar por quantum o tiempo restante, lo que sea menor
            int tiempoEjecutado = Math.min(quantum, tiempoRestante);
            tiempoActual += tiempoEjecutado;
            tiempoRestante -= tiempoEjecutado;
            tiemposRestantes.put(proceso, tiempoRestante);

            if (tiempoRestante > 0 && tiempoRestante < quantum) {
                // Mover a la siguiente cola si el proceso no terminó y tiene tiempo restante
                colas.putIfAbsent(siguienteCola, new LinkedList<>());
                colas.get(siguienteCola).add(proceso);
            } else if (tiempoRestante > 0) {
                // Reinserta el proceso si aún tiene tiempo suficiente para otro ciclo de RR
                pendientes.add(proceso);
            } else {
                // Si terminó, registrar CT y mover a finalizados
                proceso.setCompletionTime(tiempoActual);
                finalizados.add(proceso);
            }
        }
        return tiempoActual;
    }

    private int ejecutarSJF(Queue<Process> cola, int tiempoActual, List<Process> finalizados) {
        List<Process> procesosOrdenados = new ArrayList<>(cola);
        procesosOrdenados.sort(Comparator.comparingInt(Process::getBurstTime));

        for (Process proceso : procesosOrdenados) {
            if (proceso.getResponseTime() == -1) {
                proceso.setResponseTime(tiempoActual);
            }
            tiempoActual += proceso.getBurstTime();
            proceso.setCompletionTime(tiempoActual);
            finalizados.add(proceso);
        }
        return tiempoActual;
    }

    private int ejecutarFCFS(Queue<Process> cola, int tiempoActual, List<Process> finalizados) {
        while (!cola.isEmpty()) {
            Process proceso = cola.poll();
            if (proceso.getResponseTime() == -1) {
                proceso.setResponseTime(tiempoActual);
            }
            tiempoActual += proceso.getBurstTime();
            proceso.setCompletionTime(tiempoActual);
            finalizados.add(proceso);
        }
        return tiempoActual;
    }

    private int ejecutarSTCF(Queue<Process> cola, int tiempoActual, List<Process> finalizados) {
        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(Process::getBurstTime));

        while (!cola.isEmpty() || !pq.isEmpty()) {
            while (!cola.isEmpty() && cola.peek().getArrivalTime() <= tiempoActual) {
                pq.add(cola.poll());
            }

            if (!pq.isEmpty()) {
                Process proceso = pq.poll();
                if (proceso.getResponseTime() == -1) {
                    proceso.setResponseTime(tiempoActual);
                }
                tiempoActual += proceso.getBurstTime();
                proceso.setCompletionTime(tiempoActual);
                finalizados.add(proceso);
            } else {
                tiempoActual++;
            }
        }
        return tiempoActual;
    }

    /**
     * Genera los resultados finales de los procesos ejecutados.
     *
     * @param finalizados Lista de procesos finalizados.
     * @param nombreArchivo Nombre del archivo de entrada.
     * @return Un String con los resultados formateados.
     */
    private String generarResultados(List<Process> finalizados, String nombreArchivo) {
        StringBuilder resultados = new StringBuilder();
        resultados.append(String.format("# archivo: %s\n", nombreArchivo));
        resultados.append("# etiqueta; BT; AT; Q; Pr; WT; CT; RT; TAT\n");

        double totalWT = 0, totalTAT = 0, totalRT = 0, totalCT = 0;

        for (Process proceso : finalizados) {
            int CT = proceso.getCompletionTime();
            int TAT = CT - proceso.getArrivalTime();
            int WT = TAT - proceso.getBurstTime();
            int RT = proceso.getResponseTime() - proceso.getArrivalTime();

            totalWT += WT;
            totalTAT += TAT;
            totalRT += RT;
            totalCT += CT;

            resultados.append(String.format("%s;%d;%d;%d;%d;%d;%d;%d;%d\n",
                    proceso.getEtiqueta(), proceso.getBurstTime(), proceso.getArrivalTime(),
                    proceso.getQueue(), proceso.getPriority(), WT, CT, RT, TAT));
        }

        int numProcesos = finalizados.size();
        resultados.append(String.format("WT=%.2f; CT=%.2f; RT=%.2f; TAT=%.2f;\n",
                totalWT / numProcesos, totalCT / numProcesos, totalRT / numProcesos, totalTAT / numProcesos));

        return resultados.toString();
    }
}


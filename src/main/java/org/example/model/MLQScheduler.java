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
    private int quantum1;
    private int quantum2;
    private String politicaCola3;

    /**
     * Constructor que inicializa los procesos, los quantums y la política de la tercera cola.
     * @param procesos Lista de procesos.
     * @param quantum1 Quantum para la primera cola (RR).
     * @param quantum2 Quantum para la segunda cola (RR).
     * @param politicaCola3 Política para la tercera cola (SJF, FCFS, o STCF).
     */
    public MLQScheduler(List<Process> procesos, int quantum1, int quantum2, String politicaCola3) {
        this.procesos = procesos;
        this.quantum1 = quantum1;
        this.quantum2 = quantum2;
        this.politicaCola3 = politicaCola3;
    }

    /**
     * Simula la ejecución de los procesos según el algoritmo MLQ.
     * @return Un String con los resultados de la simulación.
     */
    public String simular() {
        StringBuilder resultados = new StringBuilder();
        Map<Integer, Queue<Process>> colas = new TreeMap<>();
        List<Process> finalizados = new ArrayList<>();

        // Clasificar procesos en colas según su nivel de prioridad
        for (Process p : procesos) {
            colas.putIfAbsent(p.getQueue(), new LinkedList<>());
            colas.get(p.getQueue()).add(p);
        }

        int tiempoActual = 0;

        // Ejecutar las colas 1 y 2 con Round Robin
        if (colas.containsKey(1)) {
            tiempoActual = ejecutarRoundRobin(colas.get(1), tiempoActual, quantum1, finalizados);
        }
        if (colas.containsKey(2)) {
            tiempoActual = ejecutarRoundRobin(colas.get(2), tiempoActual, quantum2, finalizados);
        }

        // Ejecutar la tercera cola con la política seleccionada
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

        // Generar los resultados finales
        resultados.append(generarResultados(finalizados));
        return resultados.toString();
    }

    /**
     * Ejecuta la política Round Robin.
     */
    private int ejecutarRoundRobin(Queue<Process> cola, int tiempoActual, int quantum, List<Process> finalizados) {
        Queue<Process> pendientes = new LinkedList<>(cola);
        Map<Process, Integer> tiemposRestantes = new HashMap<>();

        // Inicializa los tiempos restantes de cada proceso
        for (Process p : pendientes) {
            tiemposRestantes.put(p, p.getBurstTime());
        }

        while (!pendientes.isEmpty()) {
            Process proceso = pendientes.poll();
            int tiempoRestante = tiemposRestantes.get(proceso);
            int tiempoEjecutado = Math.min(quantum, tiempoRestante);

            int tiempoEspera = Math.max(0, tiempoActual - proceso.getArrivalTime());
            tiempoActual += tiempoEjecutado;
            tiempoRestante -= tiempoEjecutado;

            // Actualiza el tiempo restante del proceso
            tiemposRestantes.put(proceso, tiempoRestante);

            // Si el proceso terminó, lo movemos a la lista de finalizados
            if (tiempoRestante == 0) {
                proceso.setCompletionTime(tiempoActual);
                finalizados.add(proceso);
            } else {
                // Si no ha terminado, lo volvemos a añadir a la cola
                pendientes.add(proceso);
            }
        }
        return tiempoActual;
    }

    /**
     * Ejecuta la política SJF (Shortest Job First).
     */
    private int ejecutarSJF(Queue<Process> cola, int tiempoActual, List<Process> finalizados) {
        List<Process> procesosOrdenados = new ArrayList<>(cola);
        procesosOrdenados.sort(Comparator.comparingInt(Process::getBurstTime));

        for (Process proceso : procesosOrdenados) {
            int tiempoEspera = Math.max(0, tiempoActual - proceso.getArrivalTime());
            tiempoActual += proceso.getBurstTime();
            proceso.setCompletionTime(tiempoActual);
            finalizados.add(proceso);
        }
        return tiempoActual;
    }

    /**
     * Ejecuta la política FCFS (First Come First Serve).
     * @param cola Cola de procesos a ejecutar.
     * @param tiempoActual El tiempo actual del sistema.
     * @param finalizados Lista de procesos finalizados.
     * @return El tiempo actual después de ejecutar todos los procesos de la cola.
     */
    private int ejecutarFCFS(Queue<Process> cola, int tiempoActual, List<Process> finalizados) {
        while (!cola.isEmpty()) {
            Process proceso = cola.poll();
            int tiempoEspera = Math.max(0, tiempoActual - proceso.getArrivalTime());
            tiempoActual += proceso.getBurstTime();
            proceso.setCompletionTime(tiempoActual);
            finalizados.add(proceso);
        }
        return tiempoActual;
    }

    /**
     * Ejecuta la política STCF (Shortest Time to Completion First).
     * @param cola Cola de procesos a ejecutar.
     * @param tiempoActual El tiempo actual del sistema.
     * @param finalizados Lista de procesos finalizados.
     * @return El tiempo actual después de ejecutar todos los procesos.
     */
    private int ejecutarSTCF(Queue<Process> cola, int tiempoActual, List<Process> finalizados) {
        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(Process::getBurstTime));

        while (!cola.isEmpty() || !pq.isEmpty()) {
            // Añadir procesos que hayan llegado al sistema al priority queue
            while (!cola.isEmpty() && cola.peek().getArrivalTime() <= tiempoActual) {
                pq.add(cola.poll());
            }

            if (!pq.isEmpty()) {
                Process proceso = pq.poll();
                tiempoActual += proceso.getBurstTime();
                proceso.setCompletionTime(tiempoActual);
                finalizados.add(proceso);
            } else {
                // Si no hay procesos listos, avanzar el tiempo
                tiempoActual++;
            }
        }
        return tiempoActual;
    }


    /**
     * Genera los resultados finales de los procesos ejecutados.
     * @param finalizados Lista de procesos finalizados.
     * @return Un String con los resultados formateados según el archivo de salida esperado.
     */
    private String generarResultados(List<Process> finalizados) {
        StringBuilder resultados = new StringBuilder();

        // Añadir la cabecera del archivo
        resultados.append("# archivo: mlq001.txt\n");
        resultados.append("# etiqueta; BT; AT; Q; Pr; WT; CT; RT; TAT\n");

        double totalWT = 0, totalTAT = 0, totalRT = 0, totalCT = 0;

        for (Process proceso : finalizados) {
            int CT = proceso.getCompletionTime();  // Completion Time
            int TAT = CT - proceso.getArrivalTime();  // Turnaround Time (TAT)
            int WT = TAT - proceso.getBurstTime();  // Waiting Time (WT)
            int RT = proceso.getCompletionTime() - proceso.getBurstTime();  // Response Time (RT)

            // Sumar para los promedios
            totalWT += WT;
            totalTAT += TAT;
            totalRT += RT;
            totalCT += CT;

            // Agregar los resultados de cada proceso
            resultados.append(String.format("%s;%d;%d;%d;%d;%d;%d;%d;%d\n",
                    proceso.getEtiqueta(), proceso.getBurstTime(), proceso.getArrivalTime(),
                    proceso.getQueue(), proceso.getPriority(), WT, CT, RT, TAT));
        }

        // Calcular los promedios
        int numProcesos = finalizados.size();
        resultados.append(String.format("WT=%.2f; CT=%.2f; RT=%.2f; TAT=%.2f;\n",
                totalWT / numProcesos, totalCT / numProcesos, totalRT / numProcesos, totalTAT / numProcesos));

        return resultados.toString();
    }
}

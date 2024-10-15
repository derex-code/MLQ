/**
 * @autor Olman Alexander Silva 2343025-2724
 * @version 1.0
 */
package org.example.model;

/**
 * Representa un proceso con atributos como Burst Time (BT) y Arrival Time (AT).
 */
public class Process {
    private String etiqueta;
    private int burstTime, arrivalTime, queue, priority;

    /**
     * Constructor que inicializa un proceso con sus atributos.
     * @param etiqueta Nombre del proceso.
     * @param burstTime Tiempo que necesita el proceso para ejecutarse.
     * @param arrivalTime Tiempo de llegada al sistema.
     * @param queue Cola a la que pertenece el proceso.
     * @param priority Prioridad del proceso.
     */
    public Process(String etiqueta, int burstTime, int arrivalTime, int queue, int priority) {
        this.etiqueta = etiqueta;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.queue = queue;
        this.priority = priority;
    }

    public String getEtiqueta() { return etiqueta; }
    public int getBurstTime() { return burstTime; }
    public int getArrivalTime() { return arrivalTime; }
    public int getQueue() { return queue; }
    public int getPriority() { return priority; }
}

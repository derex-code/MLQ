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
    private int completionTime; // CT: Tiempo de finalización del proceso

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

    /**
     * Establece el tiempo de finalización (Completion Time) del proceso.
     * @param completionTime El tiempo en el que el proceso finaliza.
     */
    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    /**
     * Obtiene el tiempo de finalización (Completion Time) del proceso.
     * @return El tiempo en el que el proceso finalizó.
     */
    public int getCompletionTime() {
        return completionTime;
    }
}

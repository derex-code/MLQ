/**
 * @autor Olman Alexander Silva 2343025-2724
 * @version 1.0
 */
package org.example.model;

/**
 * Representa cada proceso con atributos como Burst Time (BT), Arrival Time (AT), etc.
 */
public class Process {
    private String etiqueta;
    private int burstTime, arrivalTime, queue, priority;

    public Process(String etiqueta, int burstTime, int arrivalTime, int queue, int priority) {
        this.etiqueta = etiqueta;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.queue = queue;
        this.priority = priority;
    }

    // Getters y Setters
    public String getEtiqueta() { return etiqueta; }
    public int getBurstTime() { return burstTime; }
    public int getArrivalTime() { return arrivalTime; }
    public int getQueue() { return queue; }
    public int getPriority() { return priority; }
}

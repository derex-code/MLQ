package org.example.model;

public class Proceso {
    private String etiqueta;
    private int burstTime, arrivalTime, queue, priority;

    public Proceso(String etiqueta, int burstTime, int arrivalTime, int queue, int priority) {
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

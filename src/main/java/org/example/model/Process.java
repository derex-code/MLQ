/**
 * @autor Olman Alexander Silva 2343025-2724
 * @version 1.0
 */
package org.example.model;

/**
 * Representa un proceso con atributos relevantes para la planificación,
 * como Burst Time (BT), Arrival Time (AT), y prioridad.
 * Almacena también los tiempos de respuesta y finalización.
 */
public class Process {
    private String etiqueta;   // Nombre del proceso
    private int burstTime;     // Tiempo que necesita el proceso para ejecutarse
    private int arrivalTime;   // Tiempo en el que el proceso llega al sistema
    private int queue;         // Cola a la que pertenece el proceso
    private int priority;      // Prioridad del proceso (menor valor = mayor prioridad)
    private int completionTime; // CT: Tiempo de finalización del proceso
    private int responseTime = -1;  // RT: Tiempo de respuesta; inicializado en -1 para identificar si no se ha registrado

    /**
     * Constructor que inicializa un proceso con sus atributos básicos.
     *
     * @param etiqueta El nombre o identificador del proceso.
     * @param burstTime El tiempo que necesita el proceso para ejecutarse (BT).
     * @param arrivalTime El tiempo de llegada del proceso al sistema (AT).
     * @param queue La cola a la que pertenece el proceso.
     * @param priority La prioridad del proceso (menor valor indica mayor prioridad).
     */
    public Process(String etiqueta, int burstTime, int arrivalTime, int queue, int priority) {
        this.etiqueta = etiqueta;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.queue = queue;
        this.priority = priority;
    }

    /**
     * Obtiene la etiqueta o nombre del proceso.
     *
     * @return La etiqueta del proceso.
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Obtiene el Burst Time (BT) del proceso, es decir,
     * el tiempo que necesita para completarse.
     *
     * @return El Burst Time del proceso.
     */
    public int getBurstTime() {
        return burstTime;
    }

    /**
     * Obtiene el Arrival Time (AT), es decir, el momento en que el proceso llegó al sistema.
     *
     * @return El tiempo de llegada del proceso.
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Obtiene la cola a la que pertenece el proceso.
     *
     * @return La cola del proceso.
     */
    public int getQueue() {
        return queue;
    }

    /**
     * Obtiene la prioridad del proceso. Un menor valor indica mayor prioridad.
     *
     * @return La prioridad del proceso.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Establece el tiempo de finalización (Completion Time - CT) del proceso.
     *
     * @param completionTime El tiempo en el que el proceso finalizó su ejecución.
     */
    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    /**
     * Obtiene el tiempo de finalización (CT) del proceso.
     *
     * @return El tiempo en el que el proceso finalizó.
     */
    public int getCompletionTime() {
        return completionTime;
    }

    /**
     * Obtiene el tiempo de respuesta (Response Time - RT) del proceso.
     * El RT es el momento en que el proceso se ejecuta por primera vez.
     *
     * @return El tiempo de respuesta del proceso.
     */
    public int getResponseTime() {
        return responseTime;
    }

    /**
     * Establece el tiempo de respuesta (Response Time - RT) del proceso.
     * Este valor solo se registra la primera vez que el proceso se ejecuta.
     *
     * @param responseTime El tiempo en el que el proceso comenzó a ejecutarse.
     */
    public void setResponseTime(int responseTime) {
        if (this.responseTime == -1) {  // Solo se registra una vez
            this.responseTime = responseTime;
        }
    }
}


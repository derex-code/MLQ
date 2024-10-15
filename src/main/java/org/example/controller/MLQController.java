/**
 * @autor Olman Alexander Silva 2343025-2724
 * @version 1.0
 */
package org.example.controller;

import org.example.model.MLQScheduler;
import org.example.model.Process;
import org.example.views.MLQView;

import javax.swing.*;
        import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase que gestiona las acciones del usuario, como cargar archivos y ejecutar la simulación.
 */
public class MLQController implements ActionListener {
    private MLQView vista;
    private ArrayList<Process> procesos;

    public MLQController(MLQView vista) {
        this.vista = vista;
        this.procesos = new ArrayList<>();
        this.vista.setControlador(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Cargar Archivo")) {
            JFileChooser fileChooser = new JFileChooser();
            int seleccion = fileChooser.showOpenDialog(null);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                cargarProcesos(archivo);
                JOptionPane.showMessageDialog(null, "Procesos cargados correctamente.");
            }
        } else if (e.getActionCommand().equals("Ejecutar Simulación")) {
            String resultados = ejecutarSimulacion();
            vista.mostrarResultados(resultados);
        }
    }

    private void cargarProcesos(File archivo) {
        try (Scanner scanner = new Scanner(archivo)) {
            procesos.clear();
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                if (!linea.startsWith("#") && !linea.isBlank()) {
                    String[] partes = linea.split(";");
                    String etiqueta = partes[0];
                    int burstTime = Integer.parseInt(partes[1].trim());
                    int arrivalTime = Integer.parseInt(partes[2].trim());
                    int queue = Integer.parseInt(partes[3].trim());
                    int priority = Integer.parseInt(partes[4].trim());

                    procesos.add(new Process(etiqueta, burstTime, arrivalTime, queue, priority));
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + ex.getMessage());
        }
    }

    private String ejecutarSimulacion() {
        MLQScheduler scheduler = new MLQScheduler(procesos);
        return scheduler.simular();
    }
}

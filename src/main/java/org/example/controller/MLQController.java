/**
 * @autor Olman Alexander Silva 2343025-2724
 * @version 1.0
 */
package org.example.controller;

import org.example.model.Process;
import org.example.views.MLQView;

import javax.swing.*;
        import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

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
            }
        } else if (e.getActionCommand().equals("Ejecutar Simulación")) {
            String resultados = ejecutarSimulacion();
            vista.mostrarResultados(resultados);
        }
    }

    private void cargarProcesos(File archivo) {
        // Implementar lógica para leer el archivo y crear objetos Proceso
    }

    private String ejecutarSimulacion() {
        // Implementar la lógica de MLQ y retornar resultados como String
        return "Resultados de la simulación (ejemplo)";
    }
}

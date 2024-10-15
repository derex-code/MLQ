/**
 * @autor Olman Alexander Silva 2343025-2724
 * @version 1.0
 */
package org.example;

import org.example.controller.MLQController;
import org.example.views.MLQView;

import javax.swing.*;

/**
 * Clase que configura la Interfaz gráfica llamando al controlador desde una clase principal
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MLQView vista = new MLQView();
            new MLQController(vista);
            vista.setVisible(true);
        });
    }
}

/**
 * @autor Olman Alexander Silva 2343025-2724
 * @version 1.0
 */
package org.example.views;

import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionListener;

/**
 * Clase MLQView donde se encuentra la ventana gráfica con botones para
 * seleccionar archivos y mostrar los resultados.
 */
public class MLQView extends JFrame {
    private JButton btnCargarArchivo = new JButton("Cargar Archivo");
    private JButton btnEjecutar = new JButton("Ejecutar Simulación");
    private JTextArea textAreaResultados = new JTextArea(20, 40);

    public MLQView() {
        setTitle("Simulador MLQ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(btnCargarArchivo);
        add(btnEjecutar);
        add(new JScrollPane(textAreaResultados));
        pack();
        setLocationRelativeTo(null);
    }

    public void setControlador(ActionListener controlador) {
        btnCargarArchivo.addActionListener(controlador);
        btnEjecutar.addActionListener(controlador);
    }

    public void mostrarResultados(String resultados) {
        textAreaResultados.setText(resultados);
    }
}

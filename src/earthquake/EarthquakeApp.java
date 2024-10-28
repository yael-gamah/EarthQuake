/** EarthquakeApp.java
 *  Tecnológico Nacional de México en León Campus 1.
 *  Ingeniería en Sistemas Computacionales.
 *  Tópicos para el Despliegue de Aplicaciones.
 * -------------------------------------------------
 *  Jesús Yael Gama Hernández.
 * -------------------------------------------------
 *  Aplicación simple de Swing para recuperar datos de terremotos 
 *  basados en un rango de fechas ingresado por el usuario.
 */

package earthquake;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EarthquakeApp extends JFrame {
    // Campo de texto para ingresar la fecha de inicio
    private JTextField startDateField;
    // Campo de texto para ingresar la fecha de fin
    private JTextField endDateField;
    // Botón para iniciar la búsqueda de datos
    private JButton fetchButton;
    // Área de texto para mostrar los resultados de la búsqueda
    private JTextArea resultArea;

    /**
     * Constructor para inicializar la aplicación.
     * Configura el marco y sus componentes.
     */
    public EarthquakeApp() {
        // Título de la ventana
        setTitle("Recuperador de datos de terremotos");
        // Tamaño de la ventana
        setSize(400, 300);
        // Cerrar la aplicación al cerrar la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Centrando la ventana en la pantalla
        setLocationRelativeTo(null);

        // Inicialización de los componentes
        startDateField = new JTextField(10); // Campo de fecha de inicio
        endDateField = new JTextField(10); // Campo de fecha final
        fetchButton = new JButton("Buscar"); // Botón de búsqueda
        resultArea = new JTextArea(10, 30); // Área de resultados
        resultArea.setLineWrap(true); // Activar el ajuste de línea
        resultArea.setWrapStyleWord(true); // Ajuste de palabra por línea
        resultArea.setEditable(false); // No permitir la edición

        // Configuración del diseño de los componentes
        JPanel panel = new JPanel();
        panel.add(new JLabel("Fecha Inicial (yyyy-MM-dd):")); // Etiqueta para la fecha inicial
        panel.add(startDateField); // Añadir campo de fecha inicial
        panel.add(new JLabel("Fecha Final (yyyy-MM-dd):")); // Etiqueta para la fecha final
        panel.add(endDateField); // Añadir campo de fecha final
        panel.add(fetchButton); // Añadir botón de búsqueda
        panel.add(new JScrollPane(resultArea)); // Añadir área de resultados con desplazamiento

        // Añadir el panel al marco
        add(panel);

        // Configuración del listener del botón
        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamada al método para buscar datos de terremotos
                fetchData();
            }
        });
    }

    /**
     * Método para recuperar datos de terremotos basados en el rango de fechas ingresado.
     * Verifica que ambos campos de fecha no estén vacíos antes de realizar la búsqueda.
     */
    private void fetchData() {
        // Obtener las fechas ingresadas por el usuario
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();

        // Validar que ambas fechas hayan sido ingresadas
        if (startDate.isEmpty() || endDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese ambas fechas."); // Mostrar mensaje de error
            return; // Terminar la ejecución si falta alguna fecha
        }

        // Crear una instancia del fetcher con las fechas ingresadas
        EarthquakeFetcher fetcher = new EarthquakeFetcher(startDate, endDate);
        // Realizar la búsqueda de datos y almacenar el resultado
        String result = fetcher.fetch();
        // Mostrar el resultado en el área de texto
        resultArea.setText(result);
    }

    /**
     * Método principal para iniciar la aplicación.
     * Crea una instancia de EarthquakeApp y la hace visible.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EarthquakeApp().setVisible(true);
            }
        });
    }
}


/** EarthquakeApp.java
 *  Tecnológico Nacional de México en León Campus 1.
 *  Ingeniería en Sistemas Computacionales.
 *  Tópicos para el Despliegue de Aplicaciones.
 * -------------------------------------------------
 *  Jesús Yael Gama Hernández.
 * -------------------------------------------------
 * Clase que se encarga de recuperar datos de terremotos de la API
 * de USGS (United States Geological Survey) en formato GeoJSON.
 */
package earthquake;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * EarthquakeFetcher es una 
 */
public class EarthquakeFetcher {
    // Variables para almacenar las fechas de inicio y fin de la consulta
    private final String startDate;
    private final String endDate;

    /**
     * Constructor para inicializar la clase EarthquakeFetcher con el rango de fechas.
     *
     * @param startDate La fecha de inicio de la consulta en formato yyyy-MM-dd.
     * @param endDate La fecha final de la consulta en formato yyyy-MM-dd.
     */
    public EarthquakeFetcher(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Método para recuperar datos de terremotos desde la API de USGS.
     *
     * @return Una cadena con los resultados de la consulta, incluyendo lugar, magnitud y hora del terremoto.
     */
    public String fetch() {
        // StringBuilder para almacenar los resultados
        StringBuilder result = new StringBuilder();
        try {
            // Construcción de la URL para la consulta a la API
            String apiUrl = String.format(
                "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=%s&endtime=%s",
                startDate, endDate);

            // Creación de la conexión a la URL
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Método HTTP GET

            // Lectura de la respuesta de la API
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            // Lectura de cada línea de la respuesta y almacenamiento en el StringBuilder
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Cierre del BufferedReader y desconexión
            in.close();
            connection.disconnect();

            // Parseo del JSON de la respuesta
            JsonObject json = JsonParser.parseString(content.toString()).getAsJsonObject();
            JsonArray features = json.getAsJsonArray("features");

            // Iteración a través de cada evento de terremoto en la respuesta
            for (int i = 0; i < features.size(); i++) {
                JsonObject feature = features.get(i).getAsJsonObject();
                JsonObject properties = feature.getAsJsonObject("properties");

                // Construcción de la salida para cada evento
                result.append("Place: ").append(properties.get("place").getAsString()).append("\n");
                result.append("Magnitude: ").append(properties.get("mag").getAsDouble()).append("\n");
                result.append("Time: ").append(properties.get("time").getAsLong()).append("\n");
                result.append("-------------------------------\n");
            }

        } catch (Exception e) {
            // Captura de errores durante la recuperación de datos y el parseo
            result.append("Error fetching data: ").append(e.getMessage());
        }

        // Retorno de los resultados como una cadena
        return result.toString();
    }
}
import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;


public class Main {
    static String rain_id = "0";
    static String temp_id = "0";

    public static void POST_FORECAST(int rain, int temp) throws URISyntaxException {
        String endpoint = "http://localhost:3003/";

        try {
            HttpClient client = HttpClient.newHttpClient();
            String json = "{\"location_id\": \"1\", \"forecast_date\": \"2024-12-05\", \"rain_value\": \"" + rain + "\", \"temp_value\": \"" + temp + "\"}";

            System.out.println(json);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint + "forecast"))  // Replace with your API URL
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() > 200) {
                System.out.println("ADDED FORECAST");
                System.out.println(response.body());
            }else {
                System.out.println("Response Code: " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) throws URISyntaxException {
        SerialPort[] ports = SerialPort.getCommPorts();

        System.out.println("Available Ports:");
        for (int i = 0; i < ports.length; i++) {
            System.out.println(i + ": " + ports[i].getSystemPortName());
        }

        SerialPort serialPort = ports[0];
        serialPort.openPort();
        serialPort.setBaudRate(9600);


        if (serialPort.isOpen()) {
            System.out.println("Port opened successfully.");

            int rain_value = -1;
            int temperature_value = -1;

            int prev_rain = -1;
            int prev_temp = -1;

            while (true) {
                if (serialPort.bytesAvailable() > 0) {
                    byte[] buffer = new byte[serialPort.bytesAvailable()];
                    serialPort.readBytes(buffer, buffer.length);
                    String readVal = new String(buffer).trim();

                    System.out.println(readVal);

                    if(readVal.startsWith("T:")) {
                        double temperature_value_dbl = Double.parseDouble(readVal.substring(2));
                        temperature_value = (int) temperature_value_dbl;
                    }
                    if(readVal.startsWith("R:")) {
                        rain_value = Integer.parseInt(readVal.substring(2));
                    }

                    if(rain_value != prev_rain && temperature_value != prev_temp) {
                        POST_FORECAST(rain_value, temperature_value);
                        prev_rain = rain_value;
                        prev_temp = temperature_value;

                    }
                }
            }
        } else {
            System.out.println("Failed to open port.");
        }
    }
}

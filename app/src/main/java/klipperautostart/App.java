package klipperautostart;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class App {
    public static void main(String[] args) {
        
        System.out.println("AutoStart Running...");
        
        String apiCall;
        
        while (true) {

            String temp = getKlipperStatus();
            System.out.println(temp);

            File printerPath = new File("/dev/serial/by-id/usb-Klipper_stm32h723xx_170035001651313238353730-if00");
            if(printerPath.exists()){
                apiCall = getKlipperStatus();
                //System.out.println(apiCall);
                if (apiCall != "ready") {
                    connectKlipper();
                }
            }

            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static String getKlipperStatus() {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://httpbin.org/anything")) //SET PRINTER URL HERE
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        return response.body().subSequence(4, 7).toString();
	}

    public static void connectKlipper(){
        //Restart Klipper Code
    }
}
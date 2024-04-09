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
        
        boolean printerConnected = false;
        boolean klipperOnline = false;
        
        while (true) {
        
            File printerPath = new File("/dev/serial/by-id/usb-Klipper_stm32h723xx_170035001651313238353730-if00");

            String apiCall = getKlipperStatus();
            if (apiCall != "ready") {
                connectKlipper();
            }

            if(printerPath.exists()){
                printerConnected = true;
            }
            
            if(printerConnected && klipperOnline){
                connectKlipper();
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
				.uri(URI.create("https://httpbin.org/anything"))
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
package dk.sdu.mmmi.cbse.common.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ScoreService {

    private static final String SCORE_URL = "http://localhost:8080/score"; // adjust if needed

    public static int sendScore(int point) {
        try {
            URL url = new URL(SCORE_URL + "?point=" + point);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Get response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();

            return Integer.parseInt(response);
        } catch (Exception e) {
            System.out.println("Error sending score: " + e.getMessage());
            return -1;
        }
    }
}

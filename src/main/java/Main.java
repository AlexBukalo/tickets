import com.google.gson.Gson;

import java.io.*;


public class Main {

    private static final String file = "src/main/resources/tickets.json";

    public static void main(String[] args) {
        Gson gson = new Gson();


        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            Tickets tickets = gson.fromJson(br, Tickets.class);
            System.out.println(tickets.avgDuration());
            System.out.println(tickets.percentile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

package server;

import helpers.Helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {

    private static String currentCity = "???";
    private static int port = 8900;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(port);) {

            System.out.println("Сервер запущен");

            while (true) {
                try (Socket clientSocket = serverSocket.accept(); // ждем подключения
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ) {
                    System.out.println("Новое входящее соединение от " + clientSocket.getInetAddress());
                    out.println(currentCity);
                    System.out.println("Текущий город: " + Helpers.getCorrectCityName(currentCity));

                    String cityFromClient = in.readLine();
                    cityFromClient = cityFromClient.toLowerCase();

                    if(currentCity.equals("???")){
                        changeCity(cityFromClient, out);
                    } else {
                        char lastChar = currentCity.charAt(currentCity.length() - 1);
                        char firstChar = cityFromClient.charAt(0);

                        if(lastChar == firstChar){
                            changeCity(cityFromClient, out);
                        } else {
                            abortChangeCity(out);
                        }
                    }
                }
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private static void changeCity(String cityFromClient, PrintWriter out){
        changeCity(cityFromClient);
        out.println("OK");
    }

    private static void abortChangeCity(PrintWriter out){
        out.println("NOT OK");
    }

    private static void changeCity(String newCity){
        currentCity = newCity;
        System.out.println("Город изменён на " + Helpers.getCorrectCityName(newCity));
    }
}

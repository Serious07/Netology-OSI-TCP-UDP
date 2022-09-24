package client;

import helpers.Helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClient {

    private static int port = 8900;
    private static String ip = "localhost";

    public static void main(String[] args) {
        System.out.println("Игра в города");
        System.out.println("-------------");

        try(Socket clientSocket = new Socket(ip, port);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));){

            // Получаем текущий город с сервера, формируем правильное наименование города
            String currentCity = Helpers.getCorrectCityName(in.readLine());

            if(currentCity.equalsIgnoreCase("???")){
                System.out.println("Вы первый участник игры, придумайте и введите в консоль первый город:");
            } else {
                System.out.println("Последний введёный город: " + currentCity);
                System.out.println("Введите город который начинается на букву: '" + Helpers.getLastWordCharacter(currentCity) + "':");
            }

            BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));

            String cityToSend = reader.readLine();

            out.println(cityToSend);

            String serverAnswer = in.readLine();

            if(serverAnswer.equalsIgnoreCase("OK")){
                System.out.println("Город " + Helpers.getCorrectCityName(cityToSend) + " сохранен на сервере!");
            } else if (serverAnswer.equalsIgnoreCase("NOT OK")){
                System.out.println("Город " + Helpers.getCorrectCityName(cityToSend) +
                                    " отклонён, так как не начинается с буквы " + Helpers.getLastWordCharacter(currentCity));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

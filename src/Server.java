import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static int port = 8900;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(port);) {

            System.out.println("Сервер запущен");

            try (Socket clientSocket = serverSocket.accept(); // ждем подключения
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                // ваш код
                System.out.println("Новое входящее соединение от " + clientSocket.getInetAddress());
                final String name = in.readLine();
                out.println(String.format("Привет %s, Ваш порт %d", name, clientSocket.getPort()));
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


}

package Client;

import javafx.application.Platform;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client implements Runnable {

    private static Socket ClientSocket;
    private static OutputStream streamOut;
    private static InputStream streamIn;
    private static final int PORT = 9987;
    private String IP;
    private WindowClientController wcc;
    private static String message = null;

    public static void send(String message) {
        Client.message = message;
    }

    public Client(WindowClientController wcc, String IP) {
        this.wcc = wcc;
        this.IP = IP;
    }

    private void toAnswer(String s) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                wcc.setAnswer(s);
            }
        });
    }

    //метод закрытия соединения
    public void CloseSocket(){
        try {
            streamOut.close();
            streamIn.close();
            ClientSocket.close();
        } catch (IOException e) {
            toAnswer("ERROR close connection");
        } catch (NullPointerException ex){
            toAnswer("Conntection close! Reopen window");
        }
    }

    @Override
    public void run() {
        byte buf[] = new byte[64 * 1024];
        try {

            ClientSocket = new Socket(IP, PORT);
            streamOut = ClientSocket.getOutputStream();
            streamIn = ClientSocket.getInputStream();

            toAnswer("You are connected to server");

            while (true) {
                if (message != null) {
                    System.out.println(message);
                    streamOut.write(message.getBytes());
                    String answer = new String(buf, 0, streamIn.read(buf));
                    toAnswer(answer);
                    message = null;
                }
                Thread.sleep(500);
            }

        } catch (IOException e) {
            toAnswer("Error connecting to server");
            wcc.closeThread();
        } catch (InterruptedException e) {
        }catch (StringIndexOutOfBoundsException e){}
    }
}

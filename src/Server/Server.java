package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Server {

    private static ServerSocket socket;
    private static Socket socketclient;
    private static OutputStream out;
    private static InputStream in;
    private static final int PORT = 9987;
    private static ArrayList<String> array;
    private static WriteToFile wfile;
    private static boolean concat = false;
    private static String ofConcat = "";

    public static void main(String[] args) {
        ServerGO();
    }

    private static void ServerGO(){
        array = new ArrayList<>();
        byte buf[] = new byte[64*1024];
        boolean b;
        try {
            socket = new ServerSocket(PORT);

            while(true) {
                System.out.println("Watiting for clients");
                socketclient = socket.accept();
                wfile = new WriteToFile();
                System.out.println("Client has been connected : " + socketclient.getInetAddress().getHostAddress() + ":" + socketclient.getPort());
                wfile.AddToFile("IP: " + socketclient.getInetAddress().getHostAddress() + " PORT: " + socketclient.getPort());
                wfile.AddToFile("");

                b = true;

                out = socketclient.getOutputStream();
                in = socketclient.getInputStream();

                while (b) {

                    int r = in.read(buf);
                    String messageFrom = new String(buf, 0, r);
                    String command = messageFrom.substring(0, messageFrom.lastIndexOf('|')),
                            message = messageFrom.substring(messageFrom.lastIndexOf('|')+1);


                    switch (command) {
                        case ("111"):
                            concat = false;
                            ofConcat ="";
                            out.write(addString(message).getBytes());
                            break;
                        case ("222"):
                            concat = false;
                            ofConcat ="";
                            out.write(removeRepeated().getBytes());
                            break;
                        case ("333"):
                            concat = false;
                            ofConcat ="";
                            out.write(Clear().getBytes());
                            break;
                        case ("444"):
                            concat = true;
                            out.write(concatination(message).getBytes());
                            break;
                    }
                }
            }
        }catch (SocketException ex){
            System.out.println("ERROR Connection interrupted!");
            wfile.close();
        }catch (IOException e) {
            System.out.println("ERROR Can't create ServerSocket!");
            wfile.close();
        }
    }

    private static String concatination(String message) {

        if(concat) {
            ofConcat = ofConcat + message;
            wfile.AddToFile("Клиент запросил складывать символы . Результат на данный момент : " + ofConcat);
            wfile.AddToFile("");
        }
        return "результат сложения : "+ofConcat;
    }

    private static String removeRepeated() {
        ArrayList<String> result = new ArrayList<String>(new HashSet<String>(array));
        Collections.sort(result);

        array = result;
        String res="";
        for(String k:array)
            res = res+" "+k;

        wfile.AddToFile("Клиент запросил удалить повторяющиеся элементы.");
        wfile.AddToFile("");
        outArray();

        return "Repeated elements deleted. Your array : "+res;

    }


    private static String Clear(){
        array.clear();

        wfile.AddToFile("Клиент запросил очистить массив . Ответ сервера : List was cleaned");
        wfile.AddToFile("");
        outArray();

        return "List was cleaned";
    }


    private static String addString(String s){
        array.add(s);
        String res="";
        for(String k:array)
            res = res+" "+k;

        wfile.AddToFile("Клиент запросил добавить строку : <<"+s+">> В массив . Ответ сервера : String added to array");
        wfile.AddToFile("");
        outArray();

        return s+" added to array. Your array = "+res;
    }


    private static void outArray(){
        wfile.AddToFile("Эллементы массива на данный момент : ");
        for(String s:array)
            wfile.AddToFile(s);
        wfile.AddToFile("");
    }

}

package Client;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class WindowClientController implements Initializable {

    @FXML
    private TextField text;

    @FXML
    private CheckBox boxDel;

    @FXML
    private CheckBox boxSum;

    @FXML
    private CheckBox boxClear;

    @FXML
    private CheckBox boxSend;

    @FXML
    private TextArea answer;

    private Thread server;

    private Client client;

    //111 - отправить
    //222 - удалить повторяющиеся
    //333 - очистить массив
    //444 - сложить

    public void closeThread() {
        client.CloseSocket();
        server.stop();
    }

    public void setAnswer(String s){

        answer.setText(answer.getText() + "\n" + s+"\n-----------------------------");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = new Client(this, "127.0.0.1");
        server = new Thread(client);
        server = new Thread(client);
        server.start();
    }

    public void btnSend() {
        if (text.getText().length() == 1) {
            if(boxSend.isSelected())
                client.send("111|"+text.getText());
            if(boxDel.isSelected())
                client.send("222|"+text.getText());
            if(boxClear.isSelected())
                client.send("333|"+text.getText());
            if(boxSum.isSelected())
                client.send("444|"+text.getText());
        } else {
            setAnswer("ENTER A SYMBOL!");
        }
    }

    public void bsend() {
        boxSend.setSelected(true);
        boxDel.setSelected(false);
        boxClear.setSelected(false);
        boxSum.setSelected(false);
    }

    public void bsum() {
        boxSend.setSelected(false);
        boxDel.setSelected(false);
        boxClear.setSelected(false);
        boxSum.setSelected(true);
    }

    public void bc() {
        boxSend.setSelected(false);
        boxDel.setSelected(false);
        boxClear.setSelected(true);
        boxSum.setSelected(false);
    }

    public void bd() {
        boxSend.setSelected(false);
        boxDel.setSelected(true);
        boxClear.setSelected(false);
        boxSum.setSelected(false);
    }
}
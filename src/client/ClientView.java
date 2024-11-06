package client;

import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientView {
    private Label labelBan;
    private TextArea textAreaTrucTuyen;
    private TextArea textAreaNoiDung;
    private TextField textFieldSoanThao;
    private ComboBox<String> comboBoxChonNguoiNhan;

    public ClientView(Label labelBan, TextArea textAreaTrucTuyen, TextArea textAreaNoiDung,
                      TextField textFieldSoanThao, ComboBox<String> comboBoxChonNguoiNhan) {
        this.labelBan = labelBan;
        this.textAreaTrucTuyen = textAreaTrucTuyen;
        this.textAreaNoiDung = textAreaNoiDung;
        this.textFieldSoanThao = textFieldSoanThao;
        this.comboBoxChonNguoiNhan = comboBoxChonNguoiNhan;
    }

    // Method to update user ID display
    public void updateUserID(int userID) {
        Platform.runLater(() -> {
            labelBan.setText(String.valueOf(userID));
        });
    }

    // Method to update online users
    public void updateOnlineUsers(List<String> onlineUsers) {
        // Clear the existing items and add the new online users
        comboBoxChonNguoiNhan.getItems().clear();
        comboBoxChonNguoiNhan.getItems().add("Mọi người");
        for (String user : onlineUsers) {
            comboBoxChonNguoiNhan.getItems().add(user);
        }
        textAreaTrucTuyen.setText(String.join("\n", onlineUsers));
    }

    // Method to add a message to the message area
    public void addMessage(String message) {
        textAreaNoiDung.appendText(message + "\n");
    }

    
    public TextField getTextFieldSoanThao() {
        return textFieldSoanThao;
    }

    public ComboBox<String> getComboBoxChonNguoiNhan() {
        return comboBoxChonNguoiNhan;
    }

    public TextArea getTextAreaNoiDung() {
        return textAreaNoiDung;
    }

    public Label getLabelBan() {
        return labelBan;
    }

    public TextArea getTextAreaTrucTuyen() {
        return textAreaTrucTuyen;
    }
}
package client;

import java.util.List;
import java.util.stream.Collectors;

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
    private String currentUsername;

    public ClientView(Label labelBan, TextArea textAreaTrucTuyen, TextArea textAreaNoiDung,
            TextField textFieldSoanThao, ComboBox<String> comboBoxChonNguoiNhan) {
        this.labelBan = labelBan;
        this.textAreaTrucTuyen = textAreaTrucTuyen;
        this.textAreaNoiDung = textAreaNoiDung;
        this.textFieldSoanThao = textFieldSoanThao;
        this.comboBoxChonNguoiNhan = comboBoxChonNguoiNhan;
        
        // Thêm "Mọi người" làm lựa chọn mặc định
        this.comboBoxChonNguoiNhan.getItems().add("Mọi người");
    }

    public void updateUserID(int userID) {
        Platform.runLater(() -> {
            labelBan.setText("Xin chào: " + String.valueOf(userID));
        });
    }
    
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    public void updateOnlineUsers(List<String> onlineUsers) {
        Platform.runLater(() -> {
            try {
                // Lưu lại lựa chọn hiện tại
                String currentSelection = comboBoxChonNguoiNhan.getValue();
                
                // Xóa và cập nhật danh sách, loại bỏ người dùng hiện tại
                comboBoxChonNguoiNhan.getItems().clear();
                comboBoxChonNguoiNhan.getItems().add("Mọi người");
                List<String> filteredUsers = onlineUsers.stream()
                    .filter(user -> !user.equals(currentUsername))
                    .collect(Collectors.toList());
                comboBoxChonNguoiNhan.getItems().addAll(filteredUsers);
                
                // Khôi phục lại lựa chọn nếu vẫn còn trong danh sách
                if (currentSelection != null && 
                    (comboBoxChonNguoiNhan.getItems().contains(currentSelection) || 
                     "Mọi người".equals(currentSelection))) {
                    comboBoxChonNguoiNhan.setValue(currentSelection);
                } else {
                    comboBoxChonNguoiNhan.setValue("Mọi người");
                }
                
                // Cập nhật TextArea hiển thị người dùng trực tuyến
                textAreaTrucTuyen.setText(String.join("\n", onlineUsers));
            } catch (Exception e) {
                System.out.println("Lỗi khi cập nhật danh sách người dùng: " + e.getMessage());
            }
        });
    }

    public void addMessage(String message) {
        Platform.runLater(() -> {
            textAreaNoiDung.appendText(message + "\n");
        });
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
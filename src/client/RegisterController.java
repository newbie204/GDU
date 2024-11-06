package client;

import java.io.IOException;

import database.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnBack;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void handleBack(ActionEvent event) {
        try {
            // Đóng form hiện tại
            Stage currentStage = (Stage) btnBack.getScene().getWindow();
            
            // Load form đăng nhập
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            // Thiết lập và hiển thị form đăng nhập
            currentStage.setTitle("Đăng nhập");
            currentStage.setScene(scene);
            currentStage.setResizable(false);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRegister(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        if (userDAO.registerUser(username, password)) {
            System.out.println("Đăng ký thành công!");
            thongBao("Đăng ký thành công!");
            closeRegister(event);
        } else {
            thongBao("Tài khoản đã tồn tại");
        }
    }

    @FXML
    public void closeRegister(ActionEvent event) {
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close(); 
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent giaoDien = loader.load();
            Scene canhVat = new Scene(giaoDien);
            stage.setTitle("Đăng nhập");
            stage.setScene(canhVat);
            stage.show();
            stage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void thongBao(String tb) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(tb);
        alert.showAndWait();
    }
}
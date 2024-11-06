package client;

import java.io.IOException;

import database.User;
import database.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        User user = userDAO.loginUser(username, password);
        
        if (user != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("miniZalo.fxml"));
                Parent root = loader.load();
                
                // Lấy controller và set thông tin user
                ClientController clientController = loader.getController();
                clientController.initUser(user); // Thêm phương thức này vào ClientController
                
                Stage stage = new Stage();
                stage.setTitle("Mini Zalo - " + user.getUsername());
                stage.setScene(new Scene(root));
                stage.show();
                
                ((Stage) txtUsername.getScene().getWindow()).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            thongBao("Đăng nhập thất bại");
        }
    }

    @FXML
    public void openRegister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Đăng Ký");
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage) txtUsername.getScene().getWindow()).close(); 
        } catch (IOException e) {
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
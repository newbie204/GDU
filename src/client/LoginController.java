package client;

import java.io.IOException;

import database.User;
import database.UserDAO;
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
    public void handleLogin() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            thongBao("Tên đăng nhập và mật khẩu không được để trống");
            return;
        }
        
        User user = userDAO.loginUser(username, password);

        if (user != null) {
            try {
                // Load giao diện chat
                FXMLLoader loader = new FXMLLoader(getClass().getResource("miniZalo.fxml"));
                Parent root = loader.load();

                // Lấy controller và set thông tin user
                ClientController clientController = loader.getController();
                clientController.initUser(user);

                // Tạo và hiển thị cửa sổ chat mới
                Stage stage = new Stage();
                stage.setTitle("Mini Zalo - " + user.getUsername());
                stage.setScene(new Scene(root));
                stage.show();

                // Đóng cửa sổ đăng nhập
                ((Stage) txtUsername.getScene().getWindow()).close();
            } catch (IOException e) {
                e.printStackTrace();
                thongBao("Có lỗi xảy ra khi mở cửa sổ chat");
            }
        } else {
            // Kiểm tra xem người dùng đã đăng nhập ở nơi khác chưa
            User existingUser = userDAO.getUserByUsername(username);
            if (existingUser != null && userDAO.isUserLoggedIn(existingUser.getId())) {
                thongBao("Tài khoản đã được đăng nhập ở nơi khác");
            } else {
                thongBao("Sai tên đăng nhập hoặc mật khẩu");
            }
        }
    }

    @FXML
    public void openRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Mini Zalo");
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
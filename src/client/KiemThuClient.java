package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class KiemThuClient extends Application {
    @Override
    public void start(Stage sanKhau) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent giaoDien = loader.load();
            Scene canhVat = new Scene(giaoDien);
            sanKhau.setTitle("Đăng nhập");
            sanKhau.setScene(canhVat);
            sanKhau.show();
            sanKhau.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
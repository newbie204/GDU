package client;


import java.net.URL;
import java.util.ResourceBundle;

import database.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientController implements Initializable {
    @FXML
    private Label labelBan;
    @FXML
    private TextArea textAreaTrucTuyen;
    @FXML
    private TextArea textAreaNoiDung;
    @FXML
    private TextField textFieldSoanThao;
    @FXML
    private ComboBox<String> comboBoxChonNguoiNhan;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;

    private ClientModel model;
    private ClientView view;
    private ThreadNhapXuat threadNhapXuat;
    
    
    public ClientController(ClientModel model, ClientView view) {
		this.model = new ClientModel();
		this.view = new ClientView(labelBan, textAreaTrucTuyen, textAreaNoiDung, textFieldSoanThao, comboBoxChonNguoiNhan);
	}
    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	 try {
    	        model = new ClientModel();
    	        view = new ClientView(labelBan, textAreaTrucTuyen, textAreaNoiDung, textFieldSoanThao, comboBoxChonNguoiNhan);
    	        
    	        
    	        comboBoxChonNguoiNhan.setOnAction(event -> {
    	            String selected = comboBoxChonNguoiNhan.getValue();
    	            if (selected == null) {
    	                comboBoxChonNguoiNhan.setValue("Mọi người");
    	            }
    	        });
    	        
    	        ketNoiMayChu();
    	        
    	        Platform.runLater(() -> {
    	            Stage stage = (Stage) labelBan.getScene().getWindow();
    	            stage.setOnCloseRequest(event -> {
    	                closeConnection();
    	                Platform.exit();
    	                System.exit(0);
    	            });
    	        });
    	        
    	    } catch (Exception e) {
    	        System.out.println("Lỗi khởi tạo: " + e.getMessage());
    	        e.printStackTrace();
    	    }
    }
    
    @FXML
    public void hanhDongGui() {
        if (threadNhapXuat != null) {
            threadNhapXuat.gui();
        } else {
            System.out.println("Chưa kết nối đến server.");
        }
    }

    public void ketNoiMayChu() {
        try {
            threadNhapXuat = new ThreadNhapXuat("192.168.1.29", 3333, model, view);
            threadNhapXuat.start();
        } catch (Exception e) {
            System.out.println("Không thể kết nối đến server: " + e.getMessage());
        }
    }
    
    public void closeConnection() {
        if (threadNhapXuat != null) {
            threadNhapXuat.closeConnection();
        }
    }

    public void initUser(User user) {
        model.setUserID(user.getId());
        model.setUsername(user.getUsername());
        view.setCurrentUsername(user.getUsername());
        labelBan.setText(user.getUsername());
        // Gửi thông tin user ID từ database lên server
        if (threadNhapXuat != null) {
            threadNhapXuat.setUserInfo(user.getId(), user.getUsername());
        }
    }
    
}
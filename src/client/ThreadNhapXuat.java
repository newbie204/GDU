package client;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ThreadNhapXuat extends Thread {
    private String serverName;
    private int port;
    private ClientModel model;
    private ClientView view;

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public ThreadNhapXuat(String serverName, int port, ClientModel model, ClientView view) {
        this.serverName = serverName;
        this.port = port;
        this.model = model;
        this.view = view;
    }
    
    

    @Override
    public void run() {
        try {
            socket = new Socket(serverName, port);
            System.out.println("Kết nối thành công!");

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // Nhận dữ liệu từ server
            nhan();

        } catch (IOException e) {
            System.out.println("Không thể kết nối hoặc xảy ra lỗi giao tiếp: " + e.getMessage());
        } finally {
            // Đảm bảo tài nguyên được đóng
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (bufferedWriter != null) bufferedWriter.close();
                if (socket != null && !socket.isClosed()) socket.close();
            } catch (IOException e) {
                System.out.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }

    public void nhan() {
        String thongDiepNhan;
        while (true) {
            try {
                thongDiepNhan = bufferedReader.readLine();
                if (thongDiepNhan == null) break;
                String[] phanTachThongDiep = thongDiepNhan.split("#~");

                switch (phanTachThongDiep[0]) {
                    case "userID" -> {
                        int userID = Integer.parseInt(phanTachThongDiep[2]);
                        model.setUserID(userID);
                        view.updateUserID(userID);
                    }
                    case "capNhatDSOnline" -> capNhatDSOnline(phanTachThongDiep[2]);
                    case "capNhatDangNhapDangXuat", "guiMoiNguoi" -> {
                        String message = phanTachThongDiep[2];
                        model.addMessage(message);
                        view.addMessage(message);
                    }
                    case "guiMotNguoi" -> {
                        String nguoiGui = phanTachThongDiep[1];
                        String noiDung = phanTachThongDiep[2];
                        String message = nguoiGui + ": " + noiDung;
                        model.addMessage(message);
                        view.addMessage(message);
                    }
                }
            } catch (IOException e) {
                System.out.println("Lỗi khi nhận dữ liệu: " + e.getMessage());
                break;
            }
        }
    }

    public void capNhatDSOnline(String danhSachOnline) {
        String[] tachDanhSachOnline = danhSachOnline.split("-");
        List<String> dsOnline = new ArrayList<>();

        for (String i : tachDanhSachOnline) {
            String[] parts = i.split(":");
            if (parts.length == 2) {
                dsOnline.add(parts[1]); 
            }
        }

        model.setOnlineUsers(dsOnline);
        view.updateOnlineUsers(dsOnline);
    }

    public void gui() {
        try {
            String thongDiep = view.getTextFieldSoanThao().getText();
            String diaChiDich = view.getComboBoxChonNguoiNhan().getValue();

            if (thongDiep == null || thongDiep.isBlank()) {
                thongBao("Bạn chưa nhập thông điệp");
                return;
            }

            if (diaChiDich == null) {
                thongBao("Bạn chưa chọn người nhận");
                return;
            }

            String message;
            if ("Mọi người".equals(diaChiDich)) {
                message = "guiMoiNguoi#~" + model.getUserID() + "#~" + model.getUsername() + ": " + thongDiep;
                Platform.runLater(() -> view.addMessage("Bạn (gửi mọi người): " + thongDiep));
            } else {
                if (diaChiDich.contains(" ")) {
                    String[] diaChiDichSplit = diaChiDich.split(" ");
                    if (diaChiDichSplit.length > 1) {
                        message = "guiMotNguoi#~" + model.getUserID() + "#~" + thongDiep + "#~" + diaChiDichSplit[1];
                        Platform.runLater(() -> view.addMessage("Bạn (gửi " + diaChiDichSplit[0] + "): " + thongDiep));
                    } else {
                        thongBao("Định dạng người nhận không hợp lệ");
                        return;
                    }
                } else {
                    thongBao("Định dạng người nhận không hợp lệ");
                    return;
                }
            }

            xuat(message);
            Platform.runLater(() -> view.getTextFieldSoanThao().clear());
        } catch (Exception e) {
            System.out.println("Lỗi khi gửi tin nhắn: " + e.getMessage());
            e.printStackTrace();
            thongBao("Có lỗi xảy ra khi gửi tin nhắn");
        }
    }

    
    public void setUserInfo(int userID, String username) {
        this.model.setUserID(userID);
        this.model.setUsername(username);
        // Gửi thông tin userID và username lên server
        xuat("setUserInfo#~" + userID + "#~" + username);
    }
    
    public void xuat(String thongDiep) {
        try {
            bufferedWriter.write(thongDiep);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println("Lỗi khi gửi dữ liệu: " + e.getMessage());
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
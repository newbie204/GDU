package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import database.UserDAO;

public class ThreadNhapXuat extends Thread {
	private Socket socket;
    private int userID;
    private String username;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public ThreadNhapXuat(Socket socket) {
        this.socket = socket;
    }
    
    public int getUserID() {
		return userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUserInfo(int userID, String username) {
        this.userID = userID;
        this.username = username;
    }

    @Override
    public void run() {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String message;
            while ((message = nhap()) != null) {
                String[] parts = message.split("#~");
                
                if (parts[0].equals("logout")) {
                    
                    UserDAO userDAO = new UserDAO();
                    userDAO.updateLoggedOut(Integer.parseInt(parts[1]));
                    break;
                }
                
                if (parts[0].equals("setUserInfo")) {
                    
                    this.userID = Integer.parseInt(parts[1]);
                    this.username = parts[2];
                    Server.xuLy.guiDanhSachUserDangOnline();
                    Server.xuLy.guiMoiNguoi("capNhatDangNhapDangXuat#~server#~***user " + username + " đã đăng nhập***");
                } else {
                    Server.xuLy.chuyenTiepThongDiep(message, userID);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi giao tiếp với userID " + userID + ": " + e.getMessage());
        } finally {
            closeResources();
            Server.xuLy.loaiRa(userID);
            System.out.println("User ID " + userID + " đã thoát.");
            Server.xuLy.guiMoiNguoi("capNhatDangNhapDangXuat#~server#~***user " + username + " đã thoát***");
            Server.xuLy.guiDanhSachUserDangOnline();
        }
    }
    
    
    private String nhap() throws IOException {
        return bufferedReader.readLine();
    }
    
    public void xuat(String thongDiep) throws IOException {
        bufferedWriter.write(thongDiep);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
    

    private void closeResources() {
        try {
            // Đóng các resources
            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.out.println("Lỗi khi đóng kết nối: " + e.getMessage());
        }
    }

   
}
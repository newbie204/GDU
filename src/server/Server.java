package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static volatile XuLy xuLy;
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            xuLy = new XuLy();
        } catch (IOException e) {
            System.out.println("Lỗi khởi tạo server: " + e.getMessage());
            throw e; 
        }
    }

    public void chay() {
        try {
            while (true) {
                System.out.println("Server đang chờ kết nối từ client...");
                Socket incomingSocket = serverSocket.accept();
                System.out.println("Client đã kết nối: " + incomingSocket.getRemoteSocketAddress());
                ThreadNhapXuat threadNhapXuat = new ThreadNhapXuat(incomingSocket);
                threadNhapXuat.start();
                xuLy.themVao(threadNhapXuat);
                System.out.println("Số lượng client đang kết nối: " + xuLy.getKichThuoc());
            }
        } catch (IOException e) {
            System.out.println("Lỗi trong quá trình chạy server: " + e.getMessage());
        }
    }
}
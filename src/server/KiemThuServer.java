package server;

import java.io.IOException;

public class KiemThuServer extends Thread {
    public static void main(String[] args) {
        int port = 3333;

        try {
            Server server = new Server(port);
            server.chay();
        } catch (IOException e) {
            System.out.println("Lỗi rồi: " + e.getMessage());
        }
    }
}

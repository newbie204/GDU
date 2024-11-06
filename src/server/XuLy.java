package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XuLy {
    private List<ThreadNhapXuat> dsTheadNhapXuat;

    public XuLy() {
        dsTheadNhapXuat = new ArrayList<>();
    }

    public int getKichThuoc() {
        return dsTheadNhapXuat.size();
    }

    public void themVao(ThreadNhapXuat threadNhapXuat) {
        dsTheadNhapXuat.add(threadNhapXuat);
    }

    public synchronized void loaiRa(int userID) {
        dsTheadNhapXuat.removeIf(threadNhapXuat -> threadNhapXuat.getUserID() == userID);
        guiDanhSachUserDangOnline(); // Cập nhật danh sách online cho các client còn lại
    }

    public void guiUserIDChoClient(int userID) {
        for (ThreadNhapXuat threadNhapXuat : dsTheadNhapXuat) {
            if (threadNhapXuat.getUserID() == userID) {
                try {
                    threadNhapXuat.xuat("userID#~server#~" + userID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void guiDanhSachUserDangOnline() {
        StringBuilder dsOnline = new StringBuilder("capNhatDSOnline#~server#~");
        for (ThreadNhapXuat thread : dsTheadNhapXuat) {
            dsOnline.append(thread.getUserID()).append(":").append(thread.getUsername()).append("-");
        }
        guiMoiNguoi(dsOnline.toString());
    }

    public void guiMoiNguoi(String thongDiep) {
        for (ThreadNhapXuat threadNhapXuat : dsTheadNhapXuat) {
            try {
                threadNhapXuat.xuat(thongDiep);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void chuyenTiepThongDiep(String thongDiep, int guiTuUserID) {
        String[] parts = thongDiep.split("#~");
        String loaiThongDiep = parts[0];

        if ("guiMotNguoi".equals(loaiThongDiep)) {
            int nhanToUserID = Integer.parseInt(parts[3]);
            guiMotNguoi(thongDiep, nhanToUserID);
        } else if ("guiMoiNguoi".equals(loaiThongDiep)) {
            guiMoiNguoi(thongDiep);
        }
    }

    private void guiMotNguoi(String thongDiep, int userID) {
        String[] parts = thongDiep.split("#~");
        int guiTuUserID = Integer.parseInt(parts[1]);
        String noiDung = parts[2];
        
        for (ThreadNhapXuat threadNhapXuat : dsTheadNhapXuat) {
            if (threadNhapXuat.getUserID() == userID) {
                try {
                    String guiTuUsername = threadNhapXuat.getUsername();
                    String thongDiepMoi = "guiMotNguoi#~" + guiTuUserID + "#~" + guiTuUsername + ": " + noiDung;
                    threadNhapXuat.xuat(thongDiepMoi);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
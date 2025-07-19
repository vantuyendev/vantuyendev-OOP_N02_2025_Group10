package com.shopmanagement;

import java.lang.*;
import com.shopmanagement.activity.*;

/**
 * Main class khởi tạo ứng dụng quản lý cửa hàng
 * Class này chứa phương thức main để khởi chạy ứng dụng
 */
public class Start {
	/**
	 * Phương thức main - điểm khởi đầu của ứng dụng
	 * Tạo và hiển thị cửa sổ đăng nhập
	 */
	public static void main(String args[]) {
		LoginActivity la = new LoginActivity();
		la.setVisible(true);
	}
}

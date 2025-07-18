# Hệ thống quản lý cửa hàng
----------------------

## **Mô tả:**

Hệ thống quản lý cửa hàng được xây dựng bằng __Java__ và __MySQL__

## **Cấu trúc dự án:**

### **Thư mục `attribute/`** - Chứa các class model chính:
- `User.java` - Abstract class cha cho Customer và Employee
- `Customer.java` - Class khách hàng
- `Employee.java` - Class nhân viên  
- `Product.java` - Class sản phẩm
- `Database.java` - Cấu hình kết nối database
- `Theme.java` - Quản lý giao diện và màu sắc
- `ThemeManager.java` - Quản lý thay đổi theme

### **Thư mục `activity/`** - Chứa các class giao diện:
- `Start.java` - Main class khởi chạy ứng dụng
- `LoginActivity.java` - Giao diện đăng nhập
- `SignupActivity.java` - Giao diện đăng ký
- `CustomerActivity.java` - Dashboard khách hàng
- `EmployeeActivity.java` - Dashboard nhân viên
- `ManageProduct.java` - Quản lý sản phẩm
- Các file View và Add khác cho từng chức năng

## **Tính năng:**
* Đăng nhập cho Manager, Employee và Customer
* Admin có thể thêm Employee và Customer mới với mật khẩu tự động tạo
* Manager có thể chỉnh sửa thông tin Employee và Customer
* Manager có thể thêm, sửa và xóa sản phẩm
* Employee có thể bán sản phẩm
* Customer có thể xem sản phẩm và lịch sử mua hàng
* Hệ thống theme đa dạng với nhiều màu sắc




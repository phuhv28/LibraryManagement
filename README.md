
# **Library Management System**

## **Mô tả**
Ứng dụng **Library Management System** giúp quản lý sách, tài liệu, và hoạt động mượn trả sách trong một thư viện. Ứng dụng được xây dựng với mục tiêu đơn giản hóa quy trình quản lý thư viện, tăng hiệu quả và đảm bảo chính xác trong quản lý dữ liệu.

---

## **Tính năng chính**
- **Quản lý tài liệu:**
    - Thêm, sửa, xóa thông tin tài liệu.
- **Quản lý người dùng:**
    - Thêm, sửa, xóa thông tin độc giả.
    - Phân quyền người dùng (Quản trị viên, Độc giả).
- **Quản lý mượn/trả sách:**
    - Lưu trữ thông tin mượn sách (ngày mượn, ngày trả dự kiến).
    - Theo dõi tình trạng sách (sẵn sàng/mượn hết).
- **Tìm kiếm:**
    - Tìm kiếm sách/tài liệu theo ID, tiêu đề, tác giả, hoặc ISBN.
- **Thông báo:**
    - Thông báo sách quá hạn hoặc sách không còn trong kho.

---

## **Công nghệ sử dụng**
- **Ngôn ngữ lập trình:** Java
- **Giao diện:** JavaFX
- **Cơ sở dữ liệu:** SQLite
- **Quản lý phụ thuộc:** Maven
- **API:** Sử dụng Google Books API để tra cứu thông tin sách.

---

## **Cài đặt**

### **Yêu cầu hệ thống**
- **Java JDK:** 23 hoặc cao hơn.
- **Maven:** 4.0.0 hoặc cao hơn.
- **IDE khuyến nghị:** IntelliJ IDEA.

### **Hướng dẫn cài đặt**
- Clone repository
- Mở dự án bằng IntelliJ IDEA
- Download OpenJFX SDK
- Config project SDK thành OpenJDK/Oracle JDK 23 (File -> Project Structure -> Project Settings -> Project)
- Mở maven tool window (View -> Tool Windows -> Maven) và chạy `clean` and `install` với "Skip Tests" mode
- Navigate to [AppStart.java](./src/main/java/librarymanagement/gui/AppStart.java)
- Tạo Run/Debug Configuration
- Click New Application rồi chọn JDK phù hợp
- Save và chạy AppStart.
---

## **Hướng dẫn sử dụng**

1. **Đăng nhập:**
    - Đăng nhập với vai trò phù hợp (quản trị viên/độc giả).
2. **Thao tác chính:**
    - **Tìm tài liệu:** Ấn chọn nút Search Document, chọn thuộc tính bạn muốn search (ISBN, ID, Title, …), điền giá trị bạn muốn search và ấn nút search.
    - **Quản lí danh sách tài liệu đang mượn:** Ấn chọn nút My Document sẽ hiện ra danh sách document đang mượn, ấn return để trả, ấn borrow để mượn thêm.
    - **Quản lí các tài khoản (Chỉ dành cho admin):** Ấn Manage Users sẽ hiện thị danh sách các user, ấn chọn vào 1 user để xem thông tin chi tiết về user đó. Ấn nút Add để thêm Account mới.
    - **Sửa tài liệu (Chỉ dành cho admin):** Ấn chọn Edit Document, điền ID Document muốn sửa, chỉnh sửa thông tin, ấn save để lưu.
    - **Xóa tài liệu (Chỉ dành cho admin):** Ấn chọn Delete Document, điền ID Document muốn xóa, ấn Delete.
    - **Thêm tài liệu (Chỉ dành cho admin):** Ấn chọn Add Document, điền thông tin Document muốn add (Có thể dùng ISBN để autofill), ấn Add.
---

## **Cấu trúc dự án**

```
src/
├── main/
│   ├── java/
│   │   ├── librarymanagement/
│   │   │   ├── entity/             # Các lớp tượng trưng cho các thực thể.
│   │   │   ├── gui/                # Các lớp liên quan đến giao diện người dùng.
│   │   │   ├── utils/              # Các lớp tiện ích.
│   └── resources/
│       ├── FXML/                   # Các tệp FXML (giao diện JavaFX)
│       └── images                  # Các ảnh dùng trong app
└── test/
    ├── java/                       # Kiểm thử
```

---

## **Đóng góp**
1. **Báo cáo lỗi:** Nếu bạn phát hiện lỗi, vui lòng mở issue tại [GitHub Issues](https://github.com/phuhv28/LibraryManagement/issues).
2. **Đóng góp mã nguồn:** Fork repository, tạo branch mới, và gửi pull request.

---

## **Liên hệ**
Nếu có bất kỳ câu hỏi hoặc thắc mắc nào, vui lòng liên hệ:
- **Email:** phuhv102@gmail.com
- **GitHub:** [phuhv28](https://github.com/phuhv28)

---

Hy vọng bạn thấy ứng dụng này hữu ích! 🚀

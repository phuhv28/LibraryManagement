package librarymanagement.gui.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddController {

    @FXML
    private Button btAddDocument; // Button để thêm tài liệu

    @FXML
    private TextField tfDocumentID; // TextField để nhập ID tài liệu

    @FXML
    private TextField tfTitle; // TextField để nhập tiêu đề tài liệu

    @FXML
    private TextField tfPublisher; // TextField để nhập nhà xuất bản

    @FXML
    private TextField tfAuthor; // TextField để nhập tác giả

    @FXML
    private TextField tfCategory; // TextField để nhập thể loại

    @FXML
    private TextField tfRentalPrice; // TextField để nhập giá thuê

    @FXML
    private TextField tfTotalQuantity; // TextField để nhập tổng số lượng

    @FXML
    public void initialize() {
        // Phương thức này sẽ được gọi tự động khi controller được khởi tạo.
        // Bạn có thể đặt bất kỳ mã khởi tạo nào ở đây.
        btAddDocument.setOnMousePressed(event -> {
            // Thay đổi style để thu nhỏ kích thước nút
            btAddDocument.setStyle("-fx-scale-x: 1.0; -fx-scale-y: 1.0; -fx-background-radius: 15; -fx-font-weight: bold;");
        });

        // Khi nhả nút
        btAddDocument.setOnMouseReleased(event -> {
            // Khôi phục kích thước nút
            btAddDocument.setStyle("-fx-scale-x: 1.0; -fx-scale-y: 1.0; -fx-background-radius: 15; -fx-font-weight: bold;");
        });

        // Khi chuột rời khỏi nút
        btAddDocument.setOnMouseExited(event -> {
            // Đảm bảo kích thước về lại ban đầu nếu chuột rời khỏi
            btAddDocument.setStyle("-fx-scale-x: 1.0; -fx-scale-y: 1.0; -fx-background-radius: 15; -fx-font-weight: bold;");
        });

        // Sự kiện khi bấm nút
        btAddDocument.setOnAction(event -> handleAddDocument());
    }

    private void handleAddDocument() {
        // Lấy dữ liệu từ các TextField
        String documentID = tfDocumentID.getText();
        String title = tfTitle.getText();
        String publisher = tfPublisher.getText();
        String author = tfAuthor.getText();
        String category = tfCategory.getText();
        String rentalPrice = tfRentalPrice.getText();
        String totalQuantity = tfTotalQuantity.getText();

        // Xử lý dữ liệu, ví dụ như thêm tài liệu vào cơ sở dữ liệu
        System.out.println("Adding Document:");
        System.out.println("ID: " + documentID);
        System.out.println("Title: " + title);
        System.out.println("Publisher: " + publisher);
        System.out.println("Author: " + author);
        System.out.println("Category: " + category);
        System.out.println("Rental Price: " + rentalPrice);
        System.out.println("Total Quantity: " + totalQuantity);

        // Xóa nội dung các TextField sau khi thêm
        clearFields();
    }

    private void clearFields() {
        tfDocumentID.clear();
        tfTitle.clear();
        tfPublisher.clear();
        tfAuthor.clear();
        tfCategory.clear();
        tfRentalPrice.clear();
        tfTotalQuantity.clear();
    }
}

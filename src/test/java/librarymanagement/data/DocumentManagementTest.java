package librarymanagement.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DocumentManagementTest {

    private DocumentManagement documentManagement;
    private Connection mockConnection;

    @BeforeEach
    public void setUp() {
        // Tạo mock cho Connection
        mockConnection = Mockito.mock(Connection.class);
        documentManagement = new DocumentManagement(mockConnection); // Sử dụng mock connection
    }

    @Test
    public void testAddDocument() throws SQLException {
        // Giả lập PreparedStatement
        PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        Book mockBook = new Book("978-0-123456-47-2", "Test Book", "Fiction", 1000, 10, 10, "Author", "History");
        documentManagement.addDocument(mockBook);

        // Xác nhận rằng executeUpdate() đã được gọi một lần
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteDocument() throws SQLException {
        // Giả lập PreparedStatement
        PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1); // Giả lập trả về 1 hàng bị ảnh hưởng

        documentManagement.deleteDocument("978-0-123456-47-2");

        // Xác nhận rằng executeUpdate() đã được gọi
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    public void testSearchDocumentByName() throws SQLException {
        // Giả lập PreparedStatement và ResultSet
        PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);
        ResultSet mockResultSet = Mockito.mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        // Giả lập kết quả trả về từ ResultSet
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Chỉ có 1 kết quả
        when(mockResultSet.getString("Book_title")).thenReturn("Test Book");

        List<String> result = documentManagement.searchDocumentByName("Test");

        // Xác nhận kết quả
        assert(result.contains("Test Book"));

        // Xác nhận rằng executeQuery() đã được gọi
        verify(mockStatement, times(1)).executeQuery();
    }

    @Test
    public void testSearchDocumentByISBN() throws SQLException {
        // Giả lập PreparedStatement và ResultSet
        PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);
        ResultSet mockResultSet = Mockito.mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Chỉ có 1 kết quả
        when(mockResultSet.getString("ISBN")).thenReturn("978-0-123456-47-2");
        when(mockResultSet.getString("Book_title")).thenReturn("Test Book");

        List<String> result = documentManagement.searchDocumentByISBN("978-0-123456-47-2");

        // Xác nhận kết quả
        assert(result.contains("978-0-123456-47-2 - Test Book"));

        // Xác nhận rằng executeQuery() đã được gọi
        verify(mockStatement, times(1)).executeQuery();
    }

    @Test
    public void testSearchDocumentByCategory() throws SQLException {
        // Giả lập PreparedStatement và ResultSet
        PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);
        ResultSet mockResultSet = Mockito.mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Chỉ có 1 kết quả
        when(mockResultSet.getString("Book_title")).thenReturn("Category Book");

        List<String> result = documentManagement.searchDocumentByCategory("Fiction");

        // Xác nhận kết quả
        assert(result.contains("Category Book"));

        // Xác nhận rằng executeQuery() đã được gọi
        verify(mockStatement, times(1)).executeQuery();
    }

    @Test
    public void testSearchDocumentByAuthor() throws SQLException {
        // Giả lập PreparedStatement và ResultSet
        PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);
        ResultSet mockResultSet = Mockito.mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Chỉ có 1 kết quả
        when(mockResultSet.getString("Book_title")).thenReturn("Author Book");

        List<String> result = documentManagement.searchDocumentByAuthor("Author");

        // Xác nhận kết quả
        assert(result.contains("Author Book"));

        // Xác nhận rằng executeQuery() đã được gọi
        verify(mockStatement, times(1)).executeQuery();
    }

    @Test
    public void testIssueBook() throws SQLException {
        // Giả lập PreparedStatement và ResultSet
        PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);
        ResultSet mockResultSet = Mockito.mock(ResultSet.class);

        // Giả lập cho truy vấn kiểm tra tình trạng sách
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("Status")).thenReturn("Yes");

        // Kiểm tra xem sách có thể được mượn không
        String result = documentManagement.issueBook("978-0-123456-47-2", "C101");

        // Xác nhận rằng sách đã được mượn
        assert(result.equals("Book has been issued successfully."));

        // Xác nhận rằng executeUpdate() đã được gọi
        verify(mockStatement, times(2)).executeUpdate(); // Một lần cho cập nhật số lượng sách và một lần cho thêm vào BookTransactions
    }

    @Test
    public void testReturnBook() throws SQLException {
        // Giả lập PreparedStatement và ResultSet
        PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);
        ResultSet mockResultSet = Mockito.mock(ResultSet.class);

        // Giả lập cho truy vấn tìm kiếm giao dịch
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("Book_ISBN")).thenReturn("978-0-123456-47-2");

        // Kiểm tra xem sách có thể trả không
        documentManagement.returnBook("IS1");

        // Xác nhận rằng executeUpdate() đã được gọi cho các câu lệnh cập nhật
        verify(mockStatement, times(2)).executeUpdate(); // Một lần cho BookTransactions và một lần cho sách
    }
}

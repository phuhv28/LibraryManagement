package librarymanagement.gui.viewmodels;

import librarymanagement.data.*;
import java.util.List;

public class HomeViewModels {
    private final DocumentService<Book> bookService = DocumentServiceFactory.getDocumentService(DocumentType.BOOK);

    public List<Book> getListAllBook() {
        return ((BookService) bookService).getAllDocument();
    }

    public List<Book> getListNewestBooks() {
        return ((BookService) bookService).getRecentlyAddedBooks();
    }

    public List<Book> getListMostBorrowedBooks() {
        return ((BookService) bookService).searchBookByTitle("JAVA");
    }
}

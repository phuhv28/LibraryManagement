package librarymanagement.gui.models;


import librarymanagement.entity.Document;
import librarymanagement.entity.DocumentType;

/**
 * Factory for Document services.
 */
public class DocumentServiceFactory {
    private static BookService bookService = null;
    private static ThesisService thesisService = null;

    @SuppressWarnings("unchecked")
    public static <T extends Document> DocumentService<T> getDocumentService(DocumentType type) {
        return switch (type) {
            case BOOK -> {
                if (bookService == null) {
                    bookService = new BookService();
                }
                yield (DocumentService<T>) bookService;
            }
            case THESIS -> {
                if (thesisService == null) {
                    thesisService = new ThesisService();
                }
                yield (DocumentService<T>) thesisService;
            }
            default -> throw new IllegalArgumentException("Unknown type");
        };
    }
}


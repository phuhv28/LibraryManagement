package librarymanagement.gui.models;


import librarymanagement.entity.Document;
import librarymanagement.entity.DocumentType;

/**
 * Factory for Document services.
 */
public class DocumentServiceFactory {
    private static BookService bookService = null;
    private static MagazineService magazineService = null;

    @SuppressWarnings("unchecked")
    public static <T extends Document> DocumentService<T> getDocumentService(DocumentType type) {
        return switch (type) {
            case BOOK -> {
                if (bookService == null) {
                    bookService = BookService.getInstance();
                }
                yield (DocumentService<T>) bookService;
            }
            case MAGAZINE -> {
                if (magazineService == null) {
                    magazineService = MagazineService.getInstance();
                }
                yield (DocumentService<T>) magazineService;
            }
            default -> throw new IllegalArgumentException("Unknown type");
        };
    }
}


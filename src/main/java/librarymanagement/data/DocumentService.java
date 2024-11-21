package librarymanagement.data;

import java.util.List;

public interface DocumentService<T extends Document> {
    String generateNewID();

    void addDocument(T document);

    void updateDocument(T document);

    void deleteDocument(String id);

    T findDocumentById(String id);

//    List<T> searchDocuments(String keyword);
}

package librarymanagement.data;

public interface DocumentService<T extends Document> {
    void addDocument(T document);

    void updateDocument(T document);

    void deleteDocument(String id);

    T findDocumentById(String id);

//    List<T> searchDocuments(String keyword);
}

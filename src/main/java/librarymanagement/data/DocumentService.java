package librarymanagement.data;

public interface DocumentService<T extends Document> {
    boolean addDocument(T document);

    void updateDocument(T document);

    boolean deleteDocument(String id);

    T findDocumentById(String id);

//    List<T> searchDocuments(String keyword);
}

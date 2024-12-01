package librarymanagement.gui.models;


import librarymanagement.entity.Document;

/** Interface for handling document types operations.*/
public interface DocumentService<T extends Document> {
    /** Add a new document to database.*/
    boolean addDocument(T document);

    /** Edit a document and save to database.*/
    void updateDocument(T document);

    /** Delete a document from database.*/
    boolean deleteDocument(String id);

    /** Find a document by id from database.*/
    T findDocumentById(String id);

//    List<T> searchDocuments(String keyword);
}

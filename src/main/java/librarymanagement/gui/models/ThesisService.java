package librarymanagement.gui.models;

import librarymanagement.entity.Thesis;

public class ThesisService implements DocumentService<Thesis> {
    private String generateNewID() {
        return "";
    }

    @Override
    public boolean addDocument(Thesis document) {

        return false;
    }

    @Override
    public void updateDocument(Thesis document) {

    }

    @Override
    public boolean deleteDocument(String id) {
        return true;
    }

    @Override
    public Thesis findDocumentById(String id) {
        return null;
    }
}

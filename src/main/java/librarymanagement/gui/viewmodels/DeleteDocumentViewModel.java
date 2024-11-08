package librarymanagement.gui.viewmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import librarymanagement.data.*;

public class DeleteDocumentViewModel {
    private StringProperty IDProperty = new SimpleStringProperty();

    public String getID() {
        return IDProperty.get();
    }

    public StringProperty IDProperty() {
        return IDProperty;
    }

    public void deleteDocument(){
        DocumentManagement.getInstance().deleteBook(getID());
    }

    public boolean checkIdDocument(){
        return true;
    }
}

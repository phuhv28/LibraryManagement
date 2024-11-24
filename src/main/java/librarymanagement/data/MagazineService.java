package librarymanagement.data;

import java.util.List;

public class MagazineService implements DocumentService<Magazine> {
    private static final SQLiteInstance sqLiteInstance = SQLiteInstance.getInstance();

    private String generateNewID() {
        String newId;

        List<List<Object>> result = sqLiteInstance.findNotCondition("Book", "Max(id)");
        if (result.getFirst().getFirst() == null) {
            newId = "M101";
        } else {
            String temp = result.getFirst().getFirst().toString().substring(1);
            newId = "M" + (Integer.parseInt(temp) + 1);
        }

        return newId;
    }

    @Override
    public boolean addDocument(Magazine magazine) {

        return false;
    }

    @Override
    public void updateDocument(Magazine magazine) {

    }

    @Override
    public boolean deleteDocument(String id) {
        return true;
    }

    @Override
    public Magazine findDocumentById(String id) {
        return null;
    }
}

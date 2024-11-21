package librarymanagement.data;

import java.util.List;

public class MagazineService implements DocumentService<Magazine> {
    private static final SQLiteInstance sqLiteInstance = SQLiteInstance.getInstance();

    @Override
    public String generateNewID() {
        String newId;

        List<List<Object>> result = sqLiteInstance.findNotCondition("Book", "Max(id)");
        if (result.get(0).get(0) == null) {
            newId = "M101";
        } else {
            String temp = result.get(0).get(0).toString().substring(1);
            newId = "M" + (Integer.parseInt(temp) + 1);
        }

        return newId;
    }

    @Override
    public void addDocument(Magazine magazine) {

    }

    @Override
    public void updateDocument(Magazine magazine) {

    }

    @Override
    public void deleteDocument(String id) {

    }

    @Override
    public Magazine findDocumentById(String id) {
        return null;
    }
}

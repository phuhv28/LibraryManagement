package baanhemsieunhan.librarymanagement.data;

class Newspaper extends Document {
    private String date;

    public Newspaper() {}

    public Newspaper(String documentID, String title, String publisher, int publicationYear, int numberAll, int numberAvailable, String date) {
        super(documentID, title, publisher, publicationYear, numberAll, numberAvailable);
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + "\n" +
                "Date: " + date;
    }
}

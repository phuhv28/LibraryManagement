package baanhemsieunhan.librarymanagement.data;

public class Document {
    private String documentID;
    private String title;
    private String publisher;
    private int publicationYear;
    private int numberAll;
    private int numberAvailable;

    Document(){};

    Document(String ID, String tt, String pls, int year, int all, int available) {
        documentID = ID;
        title = tt;
        publisher = pls;
        publicationYear = year;
        numberAll = all;
        numberAvailable = available;
    }

    public void setDocumentID(String ID) {
        this.documentID = ID;
    }

    public void setNumberAll(int numberAll) {
        this.numberAll = numberAll;
    }

    public void setNumberAvailable(int numberAvailable) {
        this.numberAvailable = numberAvailable;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocumentID() {
        return documentID;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTitle() {
        return title;
    }

    public int getNumberAll() {
        return numberAll;
    }

    public int getNumberAvailable() {
        return numberAvailable;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void getInfo() {
        System.out.println("ID: " + documentID);
        System.out.println("Title: " + title);
        System.out.println("Publisher: " + publisher);
        System.out.println("Publication year: " + publicationYear);
        System.out.println("Total book: " + numberAll);
        System.out.println("Remaining book: " + numberAvailable);
    }
}

class Book extends Document {
    Person author;
    int numberOfPage;

    Book() {};

    Book(String ID, String tt, String pls, int year,
         int all, int available, Person au, int page) {
        super(ID, tt, pls, year, all, available);
        author = au;
        numberOfPage = page;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public Person getAuthor() {
        return author;
    }

    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public int getNumberOfPage() {
        return numberOfPage;
    }

    @Override
    public void getInfo() {
        super.getInfo();
        System.out.println("Number of page: " + numberOfPage);
        //To do
        //Print information of author
    }
}

class Magazine extends Document {
    private int issueNumber;
    private String month;

    Magazine() {}

    Magazine(String ID, String tt, String pls, int year, int all, int available, int issue, String month) {
        super(ID, tt, pls, year, all, available);
        this.issueNumber = issue;
        this.month = month;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public void getInfo() {
        super.getInfo();
        System.out.println("Issue Number: " + issueNumber);
        System.out.println("Month: " + month);
    }
}

class Newspaper extends Document {
    private String date;

    Newspaper() {}

    Newspaper(String ID, String tt, String pls, int year, int all, int available, String date) {
        super(ID, tt, pls, year, all, available);
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void getInfo() {
        super.getInfo();
        System.out.println("Date: " + date);
    }
}



package librarymanagement.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleBooksAPI {

    private static final String API_KEY = "AIzaSyCDqQRwC5jM_KWFHGkkyDupSPbfAo9KvO8";
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";

    private static List<Book> handleAPIResult(String requestUrl) {
        List<Book> books = new ArrayList<>();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(content.toString());
            JSONArray items = jsonResponse.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo");

                String id = items.getJSONObject(i).getString("id");
                String title = volumeInfo.optString("title", "N/A");
                String publisher = volumeInfo.optString("publisher", "N/A");
                String publishedDate = volumeInfo.optString("publishedDate", "N/A");
                int pageCount = volumeInfo.optInt("pageCount", 0);
                double averageRating = volumeInfo.optDouble("averageRating", 0.0);
                int ratingsCount = volumeInfo.optInt("ratingsCount", 0);
                String ISBN = "N/A";
                String categories = volumeInfo.has("categories") ? volumeInfo.getJSONArray("categories").join(", ") : "N/A";
                String author = volumeInfo.has("authors") ? volumeInfo.getJSONArray("authors").join(", ") : "N/A";
                String description = volumeInfo.optString("description", "N/A");

                if (volumeInfo.has("industryIdentifiers")) {
                    JSONArray identifiers = volumeInfo.getJSONArray("industryIdentifiers");
                    for (int j = 0; j < identifiers.length(); j++) {
                        JSONObject identifier = identifiers.getJSONObject(j);
                        if (identifier.getString("type").equals("ISBN_13")) {
                            ISBN = identifier.getString("identifier");
                            break;
                        }
                    }
                }

                Random rand = new Random();
                int copies = rand.nextInt();
                Book book = new Book(null, title, publisher, LocalDate.parse(publishedDate), pageCount, copies, averageRating, ratingsCount, ISBN, categories, author, description);
                books.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public static List<Book> searchBooks(String query) {
        String requestUrl = BASE_URL + "?q=" + query.replace(" ", "+") + "&key=" + API_KEY;
        return handleAPIResult(requestUrl);
    }

    public static Book searchBookByISBN(String isbn) {
        String requestUrl = BASE_URL + "?q=isbn:" + isbn + "&key=" + API_KEY;
        return handleAPIResult(requestUrl).getFirst();
    }

    public static void main(String[] args) {
        Book book = searchBookByISBN("9780134693903");
    }
}

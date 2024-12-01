package librarymanagement.data;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONArray;
import org.json.JSONObject;


/** Class for using Google Books API.*/
public class GoogleBooksAPI {
    private static final String API_KEY = "AIzaSyCDqQRwC5jM_KWFHGkkyDupSPbfAo9KvO8";
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";

    /**
     * @return null if not found.
     */
    private static List<Book> handleAPIResult(String requestUrl) {
        List<Book> books = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<byte[]>> futures = new ArrayList<>();

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(content.toString());
            JSONArray items = jsonResponse.optJSONArray("items");

            if (items == null) return books;

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                String id = item.optString("id", "N/A");
                String title = volumeInfo.optString("title", "N/A");
                String publisher = volumeInfo.optString("publisher", "N/A");
                String publishedDateStr = volumeInfo.optString("publishedDate", "N/A");
                int pageCount = volumeInfo.optInt("pageCount", 0);
                double averageRating = volumeInfo.optDouble("averageRating", 0.0);
                int ratingsCount = volumeInfo.optInt("ratingsCount", 0);
                String ISBN = "N/A";
                String categories = volumeInfo.has("categories")
                        ? volumeInfo.getJSONArray("categories").join(", ").replace("\"", "")
                        : "N/A";
                String author = volumeInfo.has("authors")
                        ? volumeInfo.getJSONArray("authors").join(", ").replace("\"", "")
                        : "N/A";
                String description = volumeInfo.optString("description", "N/A");
                String linkToAPI = item.optString("selfLink", "N/A");

                LocalDate publishedDate = null;
                try {
                    if (!"N/A".equals(publishedDateStr)) {
                        if (publishedDateStr.length() == 4) {
                            publishedDate = LocalDate.of(Integer.parseInt(publishedDateStr), 1, 1);
                        } else if (publishedDateStr.length() == 7) {
                            String[] split = publishedDateStr.split("-");
                            String year = split[0];
                            String month = split[1];
                            publishedDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (volumeInfo.has("industryIdentifiers")) {
                    JSONArray identifiers = volumeInfo.getJSONArray("industryIdentifiers");
                    for (int j = 0; j < identifiers.length(); j++) {
                        JSONObject identifier = identifiers.getJSONObject(j);
                        if ("ISBN_13".equals(identifier.optString("type"))) {
                            ISBN = identifier.optString("identifier");
                            break;
                        }
                    }
                }

                byte[] imageBytes = null;
                if (volumeInfo.has("imageLinks")) {
                    String thumbnailURL = volumeInfo.getJSONObject("imageLinks").optString("thumbnail", null);
                    if (thumbnailURL != null) {
                        Future<byte[]> future = executor.submit(() -> {
                            try {
                                URL imageUrl = new URL(thumbnailURL);
                                HttpURLConnection imageConnection = (HttpURLConnection) imageUrl.openConnection();
                                InputStream thumbnailImageStream = imageConnection.getInputStream();
                                return convertInputStreamToByteArray(thumbnailImageStream);
                            } catch (Exception e) {
                                e.printStackTrace();
                                return null;
                            }
                        });
                        futures.add(future);
                    }
                }

                Book book = new Book(
                        id,
                        title,
                        publisher,
                        publishedDate,
                        pageCount,
                        5,
                        averageRating,
                        ratingsCount,
                        ISBN,
                        categories,
                        author,
                        description,
                        linkToAPI,
                        imageBytes
                );
                books.add(book);
            }

            int index = 0;
            for (Future<byte[]> future : futures) {
                try {
                    byte[] imageBytes = future.get();
                    if (imageBytes != null && index < books.size()) {
                        books.get(index).setThumbnailImage(imageBytes);
                    }
                    index++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        if (books.isEmpty()) {
            return null;
        }

        return books;
    }

    private static List<Magazine> handleAPIResultMageZine(String requestUrl) {
        List<Magazine> magazines = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<byte[]>> futures = new ArrayList<>();

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(content.toString());
            JSONArray items = jsonResponse.optJSONArray("items");

            if (items == null) return magazines;

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                String id = item.optString("id", "N/A");
                String title = volumeInfo.optString("title", "N/A");
                String publisher = volumeInfo.optString("publisher", "N/A");
                String publishedDateStr = volumeInfo.optString("publishedDate", "N/A");
                int pageCount = volumeInfo.optInt("pageCount", 0);
                double averageRating = volumeInfo.optDouble("averageRating", 0.0);
                int ratingsCount = volumeInfo.optInt("ratingsCount", 0);
                String ISSN = "N/A";
                String categories = volumeInfo.has("categories")
                        ? volumeInfo.getJSONArray("categories").join(", ").replace("\"", "")
                        : "N/A";

                LocalDate publishedDate = null;
                try {
                    if (!"N/A".equals(publishedDateStr)) {
                        if (publishedDateStr.length() == 4) {
                            publishedDate = LocalDate.of(Integer.parseInt(publishedDateStr), 1, 1);
                        } else if (publishedDateStr.length() == 7) {
                            String[] split = publishedDateStr.split("-");
                            publishedDate = LocalDate.of(
                                    Integer.parseInt(split[0]),
                                    Integer.parseInt(split[1]),
                                    1
                            );
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (volumeInfo.has("industryIdentifiers")) {
                    JSONArray identifiers = volumeInfo.getJSONArray("industryIdentifiers");
                    for (int j = 0; j < identifiers.length(); j++) {
                        JSONObject identifier = identifiers.getJSONObject(j);
                        if ("ISSN".equals(identifier.optString("type"))) {
                            ISSN = identifier.optString("identifier");
                            break;
                        }
                    }
                }

                String linkToAPI = item.optString("selfLink", "N/A");

                byte[] imageBytes = null;
                if (volumeInfo.has("imageLinks")) {
                    String thumbnailURL = volumeInfo.getJSONObject("imageLinks").optString("thumbnail", null);
                    if (thumbnailURL != null) {
                        Future<byte[]> future = executor.submit(() -> {
                            try {
                                URL imageUrl = new URL(thumbnailURL);
                                HttpURLConnection imageConnection = (HttpURLConnection) imageUrl.openConnection();
                                InputStream thumbnailImageStream = imageConnection.getInputStream();
                                return convertInputStreamToByteArray(thumbnailImageStream);
                            } catch (Exception e) {
                                e.printStackTrace();
                                return null;
                            }
                        });
                        futures.add(future);
                    }
                }

                Magazine magazine = new Magazine(
                        id,
                        title,
                        publisher,
                        publishedDate,
                        pageCount,
                        5,
                        averageRating,
                        ratingsCount,
                        ISSN,
                        categories,
                        linkToAPI,
                        imageBytes
                );
                magazines.add(magazine);
            }

            for (int i = 0; i < magazines.size(); i++) {
                try {
                    byte[] imageBytes = futures.get(i).get();
                    magazines.get(i).setThumbnailImage(imageBytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        if (magazines.isEmpty()) {
            return null;
        }

        return magazines;
    }


    public static byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }

        return byteArrayOutputStream.toByteArray();
    }

    public static List<Book> searchBooks(String query) {
        String requestUrl = BASE_URL + "?q=" + query.replace(" ", "+") + "&key=" + API_KEY;
        return handleAPIResult(requestUrl);
    }

    public static Book searchBookByISBN(String isbn) {
        String requestUrl = BASE_URL + "?q=isbn:" + isbn + "&key=" + API_KEY;
        List<Book> books = handleAPIResult(requestUrl);
        if (books == null) {
            return null;
        }
        return books.getFirst();
    }

    public static void main(String[] args) {
        BookService bookService = new BookService();
        List<Book> books = searchBooks("Love");
        for (Book book : books) {
            bookService.addDocument(book);
        }
    }
}

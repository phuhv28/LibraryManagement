package librarymanagement.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import librarymanagement.entity.Book;
import librarymanagement.entity.Magazine;
import librarymanagement.gui.models.BookService;
import librarymanagement.gui.models.MagazineService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A utility class to interact with the Google Books API.
 * This class provides methods to search for books and magazines using the Google Books API,
 * process the results, and convert relevant data into Book and Magazine objects.
 */
public class GoogleBooksAPI {

    private static final String API_KEY = "AIzaSyCDqQRwC5jM_KWFHGkkyDupSPbfAo9KvO8";
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";

    /**
     * Handles the response from the Google Books API and processes it into a list of books.
     *
     * @param requestUrl the URL used to request the Google Books API
     * @return a list of {@link Book} objects if found, or null if no books are found
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

    /**
     * Handles the response from the Google Books API and processes it into a list of magazines.
     *
     * @param requestUrl the URL used to request the Google Books API
     * @return a list of {@link Magazine} objects if found, or an empty list if no magazines are found
     */
    private static List<Magazine> handleAPIResultMagazine(String requestUrl) {
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

            if (items == null || items.length() == 0) {
                return magazines;
            }

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                if (!volumeInfo.has("categories") || !volumeInfo.getJSONArray("categories").toString().contains("Magazine")) {
                    continue;
                }

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

        return magazines;
    }

    /**
     * Converts an InputStream to a byte array.
     *
     * @param inputStream the InputStream to be converted
     * @return a byte array representing the data from the InputStream
     * @throws IOException if an I/O error occurs during conversion
     */
    public static byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }

        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Searches for books by query using the Google Books API.
     *
     * @param query the search query string
     * @return a list of {@link Book} objects matching the query, or null if no results are found
     */
    public static List<Book> searchBooks(String query) {
        String requestUrl = BASE_URL + "?q=" + query.replace(" ", "+") + "&key=" + API_KEY;
        return handleAPIResult(requestUrl);
    }

    /**
     * Searches for magazines by query using the Google Books API.
     *
     * @param query the search query string
     * @return a list of {@link Magazine} objects matching the query
     */
    public static List<Magazine> searchMagazines(String query) {
        String requestUrl = BASE_URL + "?q=" + query.replace(" ", "+") + "&key=" + API_KEY;
        return handleAPIResultMagazine(requestUrl);
    }

    /**
     * Searches for a book by its ISBN using the Google Books API.
     *
     * @param isbn the ISBN of the book to search for
     * @return a {@link Book} object if found, or null if not found
     */
    public static Book searchBookByISBN(String isbn) {
        String requestUrl = BASE_URL + "?q=isbn:" + isbn + "&key=" + API_KEY;
        List<Book> books = handleAPIResult(requestUrl);
        if (books == null) {
            return null;
        }
        return books.get(0);
    }
}

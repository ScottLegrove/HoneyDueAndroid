package pragmaticdevelopment.com.honeydue.DBSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class APIConsumer {
    private final static String CHARSET = "UTF-8";
    private final static String MASTER_ENDPOINT = "http://honeydew.pragmaticdevelopment.net";
    private final static String LOGIN_ENDPOINT_SUFFIX = "/login";
    private final static String LISTS_ENDPOINT_SUFFIX = "/lists";
    private final static String LISTS_I_ENDPOINT_SUFFIX = "/lists/%d";
    private final static String LISTS_I_USERS_ENDPOINT_SUFFIX = "/lists/%d/users";
    private final static String LISTS_I_USERS_I_ENDPOINT_SUFFIX = "/lists/%d/users/%d";
    private final static String LISTS_I_ITEMS_ENDPOINT_SUFFIX = "/lists/%d/items";
    private final static String LISTS_I_ITEMS_I_ENDPOINT_SUFFIX = "/lists/%d/items/%d";

    private static String doGetRequest(URL url, String token) throws IOException
    {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "text/plain");
        connection.setRequestProperty("charset", "utf-8");

        if(token != null)
            connection.setRequestProperty("X-Token", token);

        connection.connect();

        int responseCode = connection.getResponseCode();

        if(responseCode == -1)
            return null;

        InputStream response = connection.getErrorStream();
        if(response == null)
            response = connection.getInputStream();

        String retString = "";
        InputStreamReader isr = new InputStreamReader(response, CHARSET);
        BufferedReader reader = new BufferedReader(isr);
        String inputLine;
        while ((inputLine = reader.readLine()) != null)
            retString += (inputLine);

        reader.close();
        isr.close();

        return retString;
    }

    private static String doDeleteRequest(URL url, String token) throws IOException
    {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Content-Type", "text/plain");
        connection.setRequestProperty("charset", "utf-8");

        if(token != null)
            connection.setRequestProperty("X-Token", token);

        connection.connect();

        int responseCode = connection.getResponseCode();

        if(responseCode == -1)
            return null;

        InputStream response = connection.getErrorStream();
        if(response == null)
            response = connection.getInputStream();

        String retString = "";
        InputStreamReader isr = new InputStreamReader(response, CHARSET);
        BufferedReader reader = new BufferedReader(isr);
        String inputLine;
        while ((inputLine = reader.readLine()) != null)
            retString += (inputLine);

        reader.close();
        isr.close();

        return retString;
    }

    private static String doPostRequest(URL url, String parameters, String token) throws IOException
    {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept-Charset", CHARSET);

        if(token != null)
            connection.setRequestProperty("X-Token", token);

        connection.connect();
        OutputStream output = connection.getOutputStream();
        output.write(parameters.getBytes(CHARSET));
        output.flush();
        output.close();


        int responseCode = connection.getResponseCode();

        if(responseCode == -1)
            return null;

        InputStream response = connection.getErrorStream();
        if(response == null)
            response = connection.getInputStream();

        String retString = "";
        InputStreamReader isr = new InputStreamReader(response, CHARSET);
        BufferedReader reader = new BufferedReader(isr);
        String inputLine;
        while ((inputLine = reader.readLine()) != null)
            retString += (inputLine);

        reader.close();
        isr.close();

        return retString;
    }

    public static String getLoginJson(String username, String password)
    {
        try
        {
            String encodedUsername = URLEncoder.encode(username, CHARSET);
            String encodedPassword = URLEncoder.encode(password, CHARSET);

            String parameters = String.format("username=%s&password=%s", encodedUsername, encodedPassword);
            URL url = new URL(String.format("%s%s?%s", MASTER_ENDPOINT, LOGIN_ENDPOINT_SUFFIX, parameters));
            return doGetRequest(url, null);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static String doRegisterJson(String username, String password, String email)
    {
        try
        {
            String encodedUsername = URLEncoder.encode(username, CHARSET);
            String encodedPassword = URLEncoder.encode(password, CHARSET);
            String encodedEmail = URLEncoder.encode(email, CHARSET);

            String parameters = String.format("username=%s&password=%s&email=%s", encodedUsername, encodedPassword, encodedEmail);

            URL url = new URL(String.format("%s%s", MASTER_ENDPOINT, LOGIN_ENDPOINT_SUFFIX));
            return doPostRequest(url, parameters, null);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return "";
        }
    }

    public static String getListsJson(String token)
    {
        try
        {
            String encodedToken = URLEncoder.encode(token, CHARSET);

            URL url = new URL(String.format("%s%s", MASTER_ENDPOINT, LISTS_ENDPOINT_SUFFIX));
            return doGetRequest(url, encodedToken);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static String getListJson(int id, String token)
    {
        try
        {
            String encodedToken = URLEncoder.encode(token, CHARSET);

            URL url = new URL(String.format("%s%s", MASTER_ENDPOINT, String.format(LISTS_I_ENDPOINT_SUFFIX, id)));
            return doGetRequest(url, encodedToken);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static String deleteListJson(int id, String token)
    {
        try
        {
            String encodedToken = URLEncoder.encode(token, CHARSET);

            URL url = new URL(String.format("%s%s", MASTER_ENDPOINT, String.format(LISTS_I_ENDPOINT_SUFFIX, id)));
            return doDeleteRequest(url, encodedToken);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static String createListJson(String title, String token)
    {
        try
        {
            String encodedTitle = URLEncoder.encode(title, CHARSET);

            String parameters = String.format("title=%s", encodedTitle);

            URL url = new URL(String.format("%s%s", MASTER_ENDPOINT, LISTS_ENDPOINT_SUFFIX));
            return doPostRequest(url, parameters, token);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return "";
        }
    }

    public static String getListItemsJson(int listId, String token)
    {
        try
        {
            String encodedToken = URLEncoder.encode(token, CHARSET);

            URL url = new URL(String.format("%s%s", MASTER_ENDPOINT, String.format(LISTS_I_ITEMS_ENDPOINT_SUFFIX, listId)));
            return doGetRequest(url, encodedToken);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static String getListItemJson(int listId, int itemId, String token)
    {
        try
        {
            String encodedToken = URLEncoder.encode(token, CHARSET);

            URL url = new URL(String.format("%s%s", MASTER_ENDPOINT, String.format(LISTS_I_ITEMS_I_ENDPOINT_SUFFIX, listId, itemId)));
            return doGetRequest(url, encodedToken);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static String deleteListItemJson(int listId, int itemId, String token)
    {
        try
        {
            String encodedToken = URLEncoder.encode(token, CHARSET);

            URL url = new URL(String.format("%s%s", MASTER_ENDPOINT, String.format(LISTS_I_ITEMS_I_ENDPOINT_SUFFIX, listId, itemId)));
            return doDeleteRequest(url, encodedToken);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static String createListItemJson(int listId, String title, String content, Date dueDate, String token)
    {
        try
        {
            String encodedTitle = URLEncoder.encode(title, CHARSET);
            String encodedContent = URLEncoder.encode(content, CHARSET);

            String parameters = String.format("title=%s&content=%s", encodedTitle, encodedContent);

            if(dueDate != null)
            {
                String encodedDueDate = URLEncoder.encode(new SimpleDateFormat("MM/dd/yyyy KK:mm:ss a Z").format(dueDate), CHARSET);
                parameters = String.format("%s&duedate=%s", parameters, encodedDueDate);
            }

            URL url = new URL(String.format("%s%s", MASTER_ENDPOINT, String.format(LISTS_I_ITEMS_ENDPOINT_SUFFIX, listId)));
            return doPostRequest(url, parameters, token);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return "";
        }
    }

    public static String getUsersJson(int listId, String token)
    {
        try
        {
            String encodedToken = URLEncoder.encode(token, CHARSET);

            URL url = new URL(String.format("%s%s", MASTER_ENDPOINT, String.format(LISTS_I_USERS_ENDPOINT_SUFFIX, listId)));
            return doGetRequest(url, encodedToken);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static String getUserJson(int listId, int userId, String token)
    {
        try
        {
            String encodedToken = URLEncoder.encode(token, CHARSET);

            URL url = new URL(String.format("%s%s", MASTER_ENDPOINT, String.format(LISTS_I_USERS_I_ENDPOINT_SUFFIX, listId, userId)));
            return doGetRequest(url, encodedToken);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static String removeUserJson(int listId, int userId, String token)
    {
        try
        {
            String encodedToken = URLEncoder.encode(token, CHARSET);

            URL url = new URL(String.format("%s%s", MASTER_ENDPOINT, String.format(LISTS_I_USERS_I_ENDPOINT_SUFFIX, listId, userId)));
            return doDeleteRequest(url, encodedToken);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static String addUserJson(int listId, String username, String token)
    {
        try
        {
            String encodedUsername = URLEncoder.encode(username, CHARSET);

            String parameters = String.format("username=%s", encodedUsername);

            URL url = new URL(String.format("%s%s", MASTER_ENDPOINT, String.format(LISTS_I_USERS_ENDPOINT_SUFFIX, listId)));
            return doPostRequest(url, parameters, token);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return "";
        }
    }
}

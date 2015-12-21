package ch09.sec03;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostDemo {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://esa.un.org/unpd/wpp/unpp/p2k0data_script.asp");
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("Accept-Charset", "UTF-8, ISO-8859-1");
        connection.setDoOutput(true);
        try (Writer out = new OutputStreamWriter(
                connection.getOutputStream(), "UTF-8")) {
            Map<String, String> postData = new HashMap<>();
            
            // View source of http://esa.un.org/unpd/wpp/unpp/panel_population.htm for these values            
            postData.put("Panel", "1");
            postData.put("Variable", "12");
            postData.put("Location", "156");
            postData.put("Varient", "2");
            postData.put("StartYear", "1950");
            postData.put("EndYear", "2100");
            boolean first = true;
            for (Map.Entry<String, String> entry : postData.entrySet()) {
                if (first) first = false;
                else out.write("&");
                out.write(URLEncoder.encode(entry.getKey(), "UTF-8"));
                out.write("=");
                out.write(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
        }
        
        Map<String, List<String>> headers = connection.getHeaderFields();
        System.out.println("Response headers: " + headers);
        try (InputStream in = connection.getInputStream()) {
            String contents = new String(readAllBytes(in));
            System.out.println(contents);
        }
    }
    
    public static byte[] readAllBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(in, out);
        return out.toByteArray();
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        final int BLOCKSIZE = 1024;
        byte[] bytes = new byte[BLOCKSIZE];
        int len;
        while ((len = in.read(bytes)) != -1) out.write(bytes, 0, len);
        in.close();
        out.close();
    }    
}

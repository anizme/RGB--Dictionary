package services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class TranslateAPI {

    public static String ggTransToJSON(String langFrom, String langTo, String text) throws Exception {
        // Xác định URL
        String apiUrl = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + langFrom
                + "&tl=" + langTo + "&dt=t&q=" + URLEncoder.encode(text, "UTF-8");

        // Tạo URL object
        URL url = new URL(apiUrl);

        // Tạo kết nối HTTP
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Đọc phản hồi từ API
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder responseContent = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            responseContent.append(line);
        }
        reader.close();

        // Xử lý và in phản hồi từ API
        return responseContent.toString();
    }

    public static String googleTranslate(String langFrom, String langTo, String text) throws Exception {
        return APItranslateJSON.getTransLang(ggTransToJSON(langFrom, langTo, text));
    }

    public static String googleDetect(String langFrom, String langTo, String text) throws Exception {
        return APItranslateJSON.getDetectLang(ggTransToJSON(langFrom, langTo, text));
    }

    public static void main(String[] args) throws Exception {
        String text = "Hello, my name Annie";
        System.out.println("Translated text: \n" + googleTranslate("auto", "ko", text));
    }
}

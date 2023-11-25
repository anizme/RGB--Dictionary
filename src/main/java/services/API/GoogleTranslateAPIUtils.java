package services.API;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GoogleTranslateAPIUtils implements APIbase {
    /**
     * use for GoogleTranslateAPI
     * @param jsonResponse file JSon that translate API returns
     * @return detected language in full name
     */
    public static String getDetectLang(String jsonResponse) {
        JSONArray jsonArray = new JSONArray(jsonResponse);
        //file JSON
        return switch (jsonArray.getString(2)) {
            case "vi" -> "Vietnamese";
            case "en" -> "English";
            case "ko" -> "Korea";
            case "ru" -> "Russian";
            case "zh" -> "Chinese";
            default -> "Nothing";
        };
    }

    /**
     * use for GoogleTranslateAPI
     * @param jsonResponse file JSon that translate API returns
     * @return translated text
     */
    public static String getTransLang(String jsonResponse) {
        JSONArray jsonArray = new JSONArray(jsonResponse);

        // file JSON
        JSONArray firstArray = jsonArray.getJSONArray(0);
        JSONArray innerArray = firstArray.getJSONArray(0);
        return innerArray.getString(0);
    }

    /**
     * short name: E.g: vi ~ vietnamese
     * @param langFrom : language to be translated in short name
     * @param langTo : language to translate into ub short name
     * @param text : text to be translated
     * @return translated JSON file in String
     * @throws Exception cannot connect to API url
     */
    public static String ggTransToJSON(String langFrom, String langTo, String text) throws Exception {
        //URL define
        String apiUrl = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + langFrom
                + "&tl=" + langTo + "&dt=t&q=" + URLEncoder.encode(text, "UTF-8");
        //Create URL object
        URL url = new URL(apiUrl);
        //HTTP connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        //Read and handle API response
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder responseContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            return responseContent.toString();
        }
    }

}

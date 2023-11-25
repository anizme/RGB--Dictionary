package services.API;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GoogleTranslateAPI implements TranslateAPI {
    private String detectLang = "";
    private String transLang = "";

    private static final String API_URL_BASE = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=";

    public GoogleTranslateAPI() {

    }

    @Override
    public String getDetectLang() {
        return detectLang;
    }

    @Override
    public String getTransLang() {
        return transLang;
    }

    //Handle query
    public void handleTranslate(String langFrom, String langTo, String text) throws Exception {
        String jsonResponse = transToJson(langFrom, langTo, text);
        transLang = getTranslatedText(jsonResponse);
        detectLang = getDetectedLang(jsonResponse);
    }

    @Override
    public String transToJson(String langFrom, String langTo, String text) throws Exception {
        String apiUrl = buildApiUrl(langFrom, langTo, text);
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder responseContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            return responseContent.toString();
        }
    }

    //Returns full google translate API URL
    private String buildApiUrl(String langFrom, String langTo, String text) throws Exception {
        String encodedText = URLEncoder.encode(text, "UTF-8");
        return API_URL_BASE + langFrom + "&tl=" + langTo + "&dt=t&q=" + encodedText;
    }

    /**
     * @param jsonResponse file JSon that translate API returns
     * @return translated text
     */
    private String getTranslatedText(String jsonResponse) {
        JSONArray jsonArray = new JSONArray(jsonResponse);

        // file JSON
        JSONArray firstArray = jsonArray.getJSONArray(0);
        JSONArray innerArray = firstArray.getJSONArray(0);
        return innerArray.getString(0);
    }

    /**
     * @param jsonResponse file JSon that translate API returns
     * @return detected language
     */
    private String getDetectedLang(String jsonResponse) {
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

}

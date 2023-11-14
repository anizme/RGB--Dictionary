package services;

import org.json.JSONArray;

public class APItranslateJSON {
    public static String getDetectLang(String jsonResponse) {
        JSONArray jsonArray = new JSONArray(jsonResponse);
        // Lấy giá trị từ JSON
        return switch (jsonArray.getString(2)) {
            case "vi" -> "Vietnamese";
            case "en" -> "English";
            case "ko" -> "Korea";
            case "ru" -> "Russian";
            case "zh" -> "Chinese";
            default -> "Nothing";
        };
    }

    public static String getTransLang(String jsonResponse) {
        JSONArray jsonArray = new JSONArray(jsonResponse);

        // Lấy giá trị từ JSON
        JSONArray firstArray = jsonArray.getJSONArray(0);
        JSONArray innerArray = firstArray.getJSONArray(0);
        return innerArray.getString(0);
    }
}

package services;

import org.json.JSONArray;

public class APItranslateJSON {
    public static String getDetectLang(String jsonResponse) {
        JSONArray jsonArray = new JSONArray(jsonResponse);
        // Lấy giá trị từ JSON
        switch (jsonArray.getString(2)) {
            case "vi":
                return "Vietnamese";
            case "en":
                return "English";
            case "ko":
                return "Korea";
            case "ru":
                return "Russian";
            case "zh":
                return "Chinese";
            default:
                return jsonArray.getString(2);
        }
    }

    public static String getTransLang(String jsonResponse) {
        JSONArray jsonArray = new JSONArray(jsonResponse);

        // Lấy giá trị từ JSON
        JSONArray firstArray = jsonArray.getJSONArray(0);
        JSONArray innerArray = firstArray.getJSONArray(0);
        return innerArray.getString(0);
    }
}

package services.API;

public interface TranslateAPI extends APIbase {

    /**
     * short name: E.g: vi ~ vietnamese
     * @param langFrom : language to be translated in short name
     * @param langTo : language to translate into ub short name
     * @param text : text to be translated
     * @return translated JSON file in String
     * @throws Exception cannot connect to API url
     */
    String transToJson(String langFrom, String langTo, String text) throws Exception;

    //Returns translated text
    String getTransLang();

    //Returns detected language
    String getDetectLang();
}

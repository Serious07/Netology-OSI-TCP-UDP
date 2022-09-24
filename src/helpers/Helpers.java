package helpers;

public class Helpers {
    public static String getCorrectCityName(String rawCityName){
        String currentCity = rawCityName.toLowerCase();
        currentCity = Character.toUpperCase(currentCity.charAt(0)) + currentCity.substring(1, currentCity.length());

        return currentCity;
    }

    public static char getLastWordCharacter(String word){
        return word.charAt(word.length() - 1);
    }
}

package eu.telecomnancy.codinglate.database.dataObject;

public class StringControlled {

    public static String correctedString(String string, int length) {
        if (string.length() > length) {
            string = string.substring(0, length);
        }
        return string;
    }


}

package exceptions;

public class WrongKeyValue extends Exception {

    public WrongKeyValue(String value, String key) {
        super(value + " dla klucza " + key);
    }
}

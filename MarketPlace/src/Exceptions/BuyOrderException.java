package Exceptions;

@SuppressWarnings("serial")
public class BuyOrderException extends Exception{
    public BuyOrderException() {
        super();
    }

    public BuyOrderException(String message) {
        super(message);
    }
}

package Service;

public class BibliotecaException extends Exception{
    public BibliotecaException(){}
    public BibliotecaException(String message) {
        super(message);
    }

    public BibliotecaException(String message, Throwable cause) {
        super(message, cause);
    }
}

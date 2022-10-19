package jpabook.jpaexmple1.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String message) {
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }

    public NotEnoughStockException() {
    }
}

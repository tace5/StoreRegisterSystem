package se.kth.iv1201.storeRegisterSystem.exceptions;

public class OperationFailedException extends Exception {
    public OperationFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

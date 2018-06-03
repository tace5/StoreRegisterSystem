package se.kth.iv1201.storeRegisterSystem.exceptions;

public class DatabaseFailureException extends Exception {
    public DatabaseFailureException() {
        super("Database is not responding.");
    }
}

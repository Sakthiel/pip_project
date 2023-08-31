package com.thoughtworks.sample.version.exceptions;

public class VersionNotAvailableException extends Exception{
    public VersionNotAvailableException(String message){
        super(message);
    }
}

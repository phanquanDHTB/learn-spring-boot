package com.learn_spring_boot.learn_spring_boot.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}

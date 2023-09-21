package com.learn_spring_boot.learn_spring_boot.exceptions;

public class DataNotFoundException extends Exception{
    public DataNotFoundException(String message){
        super(message);
    }
}

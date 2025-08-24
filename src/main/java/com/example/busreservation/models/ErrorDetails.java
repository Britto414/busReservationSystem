package com.example.busreservation.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private int errorCode;
    private  String errorMessage;
    private String devErrorMessage;
    private long timeStamp;

}

package com.github.drxaos.edu.instrumentation.lombok;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

import java.util.Calendar;

@Log
@Data
@Builder
@Accessors(chain = true, fluent = true)
public class Message {

    private Calendar date;
    private String username;
    private String text;

}

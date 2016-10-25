package com.github.drxaos.edu.instrumentation.logger;

import java.lang.instrument.Instrumentation;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) throws Exception {
        System.out.println("Starting the agent\n------------------\n");
        inst.addTransformer(new LoggerClassTransformer());
    }
}

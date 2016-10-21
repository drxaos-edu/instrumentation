package com.github.drxaos.edu.instrumentation.agent;

import java.lang.instrument.Instrumentation;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Starting the agent\n------------------\n");
        inst.addTransformer(new ClassTransformer());
    }
}

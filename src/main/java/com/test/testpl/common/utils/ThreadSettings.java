package com.test.testpl.common.utils;

public class ThreadSettings {
    private String threadName;
    private Integer NumThreads;
    private Integer RampUp;

    public ThreadSettings(String threadName, Integer numThreads, Integer rampUp) {
        this.threadName = threadName;
        this.NumThreads = numThreads;
        this.RampUp = rampUp;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public Integer getNumThreads() {
        return NumThreads;
    }

    public void setNumThreads(Integer numThreads) {
        this.NumThreads = numThreads;
    }

    public Integer getRampUp() {
        return RampUp;
    }

    public void setRampUp(Integer rampUp) {
        this.RampUp = rampUp;
    }
}

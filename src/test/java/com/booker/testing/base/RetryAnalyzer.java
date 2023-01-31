package com.booker.testing.base;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.concurrent.atomic.AtomicInteger;

public class RetryAnalyzer implements IRetryAnalyzer {
    private final AtomicInteger count = new AtomicInteger(5);

    @Override
    public boolean retry(ITestResult iTestResult) {
        return 0 < count.getAndDecrement();
    }
}

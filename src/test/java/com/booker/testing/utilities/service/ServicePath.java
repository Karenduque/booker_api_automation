package com.booker.testing.utilities.service;
import java.net.MalformedURLException;
import java.net.URL;

public class ServicePath {

    public static URL bookerLandaDevApi(String baseUrl) throws MalformedURLException {
        return new URL(String.format("%s", baseUrl));
    }
}


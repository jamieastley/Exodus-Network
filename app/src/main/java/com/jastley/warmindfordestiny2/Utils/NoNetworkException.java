package com.jastley.warmindfordestiny2.Utils;

import java.io.IOException;

public class NoNetworkException extends IOException {

    @Override
    public String getMessage() {
        return "No network connection";
    }
}

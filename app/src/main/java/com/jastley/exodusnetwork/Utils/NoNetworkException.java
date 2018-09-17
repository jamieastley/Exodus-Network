package com.jastley.exodusnetwork.Utils;

import java.io.IOException;

public class NoNetworkException extends IOException {

    @Override
    public String getMessage() {
        return "No network connection";
    }
}

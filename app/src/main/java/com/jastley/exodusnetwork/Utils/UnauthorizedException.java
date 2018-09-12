package com.jastley.exodusnetwork.Utils;

import java.io.IOException;

public class UnauthorizedException extends IOException {

    @Override
    public String getMessage() {
        return "Token expired";
    }
}

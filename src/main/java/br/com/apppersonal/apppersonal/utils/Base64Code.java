package br.com.apppersonal.apppersonal.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

public class Base64Code {

    public String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    public String decode(String value) {
        return new String(Base64.getDecoder().decode(value.getBytes()));
    }
}

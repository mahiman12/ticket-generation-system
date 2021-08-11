package com.example.ticketgenerationsystem.util;

import java.util.UUID;

public class UuidGenerator {
    public static String generate() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
}

package com.layerbyte.sslviewer;

import java.io.IOException;
import java.net.NetworkInterface;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.stream.Stream;

public final class App {
    private static final String PROJECT_NAME = "Sslviewer";
    private static final String PROJECT_FOCUS = "Certificate information viewer.";

    private App() {
    }

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                printUsage();
                return;
            }
            switch (args[0]) {
                case "file" -> inspectFile(Path.of(required(args, 1, "file path")));
                case "log" -> filterLog(Path.of(required(args, 1, "log path")), args.length > 2 ? args[2] : "");
                case "net" -> networkInfo();
                case "password" -> passwordScore(required(args, 1, "offline password text"));
                default -> printUsage();
            }
        } catch (Exception error) {
            System.err.println("Error: " + error.getMessage());
            System.exit(1);
        }
    }

    private static String required(String[] args, int index, String label) {
        if (args.length <= index || args[index].isBlank()) {
            throw new IllegalArgumentException("Missing " + label);
        }
        return args[index];
    }

    private static void printUsage() {
        System.out.println(PROJECT_NAME + " - " + PROJECT_FOCUS);
        System.out.println("Usage: java -jar app.jar file <path> | log <path> [keyword] | net | password <text>");
    }

    private static void inspectFile(Path path) throws Exception {
        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("Expected a local file.");
        }
        byte[] data = Files.readAllBytes(path);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        System.out.println("Path: " + path.toAbsolutePath());
        System.out.println("Size: " + data.length + " bytes");
        System.out.println("SHA-256: " + HexFormat.of().formatHex(digest.digest(data)));
    }

    private static void filterLog(Path path, String keyword) throws IOException {
        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("Expected a local log file.");
        }
        try (Stream<String> lines = Files.lines(path)) {
            lines.filter(line -> keyword.isBlank() || line.toLowerCase().contains(keyword.toLowerCase()))
                    .limit(200)
                    .forEach(System.out::println);
        }
    }

    private static void networkInfo() throws IOException {
        var interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            var item = interfaces.nextElement();
            System.out.println(item.getName() + " | up=" + item.isUp() + " | loopback=" + item.isLoopback());
        }
    }

    private static void passwordScore(String value) {
        int score = 0;
        if (value.length() >= 12) score += 2;
        if (value.matches(".*[a-z].*")) score++;
        if (value.matches(".*[A-Z].*")) score++;
        if (value.matches(".*\\d.*")) score++;
        if (value.matches(".*[^A-Za-z0-9].*")) score++;
        System.out.println("Offline score: " + score + "/6");
        System.out.println("The value is processed locally and is not transmitted.");
    }
}

package com.luetek.academy.storage.services;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * File Storage Implementation . There could be other implementation
 * like S3, and other cloud block storage.
 * */
@Component
public class StorageService {

    final private String baseUrl = Paths.get("data")
        .toAbsolutePath()
        .toString();
    @SneakyThrows
    public void streamTo(String fileUrl, OutputStream out) {
        try (var in = new FileInputStream(Paths.get(baseUrl, fileUrl).toFile())) {
            in.transferTo(out);
        }
    }

    @SneakyThrows
    public void upload(String fileUrl, InputStream in) {
        // TODO:: Create a temp file and then rename to the correct name
        try (var out = new FileOutputStream(Paths.get(baseUrl, fileUrl).toFile())) {
            in.transferTo(out);
        }
    }

    @SneakyThrows
    public void upload(String fileUrl, String in) {
        // TODO:: Create a temp file and then rename to the correct name
        try (var out = new FileOutputStream(Paths.get(baseUrl, fileUrl).toFile()); var writer = new OutputStreamWriter(out)) {
            writer.write(in);
        }
    }

    @SneakyThrows
    public void rename(String oldUrl, String newUrl) {
        File file = Paths.get(baseUrl, oldUrl).toFile();
        // File (or directory) with new name
        File file2 = Paths.get(baseUrl, newUrl).toFile();
        if (file2.exists())
            throw new java.io.IOException("file exists");
        // Rename file (or directory)
        boolean success = file.renameTo(file2);
        if (!success) {
            // File was not successfully renamed
            throw new java.io.IOException("Unable to rename file to " + newUrl);
        }
    }

    @SneakyThrows
    public void createFolder(String url) {
        Files.createDirectories(Paths.get(baseUrl, url));
    }


}

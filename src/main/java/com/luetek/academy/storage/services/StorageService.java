package com.luetek.academy.storage.services;

import com.luetek.academy.storage.dtos.PathUrlDto;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * File Storage Implementation . There could be other implementation
 * like S3, and other cloud block storage.
 * */
@Component
public class StorageService {
    @SneakyThrows
    public void streamTo(PathUrlDto fileDto, OutputStream out) {
        try (var in = new FileInputStream(fileDto.getUrl())) {
            in.transferTo(out);
        }
    }

    @SneakyThrows
    public void upload(PathUrlDto fileDto, InputStream in) {
        try (var out = new FileOutputStream(fileDto.getUrl())) {
            in.transferTo(out);
        }
    }
}

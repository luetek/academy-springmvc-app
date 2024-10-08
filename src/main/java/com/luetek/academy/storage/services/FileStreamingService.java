package com.luetek.academy.storage.services;

import org.springframework.stereotype.Component;

import java.io.OutputStream;

@Component
public class FileStreamingService {

    private final StorageService storageService;
    private final StorageUrlService storageUrlService;

    public FileStreamingService(StorageUrlService storageUrlService, StorageService storageService) {
        this.storageService = storageService;
        this.storageUrlService = storageUrlService;
    }

    public void streamTo(String url, OutputStream out) {
        var fileDto = this.storageUrlService.findStoragePathByUrl(url);
        if (fileDto.isEmpty()) {
            throw new RuntimeException("File not found");
        }
        this.storageService.streamTo(fileDto.get(), out);
    }

    public void streamTo(Long parentId, String relativeName, OutputStream out) {
        var fileDto = this.storageUrlService.findStoragePathByParentIdAndName(parentId, relativeName);

        if (fileDto.isEmpty()) {
            throw new RuntimeException("File not found");
        }

        this.storageService.streamTo(fileDto.get(), out);
    }
}

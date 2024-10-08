package com.luetek.academy.storage.services;

import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class FileUploadService {

    private final StorageService storageService;
    private final StorageUrlService storageUrlService;

    public FileUploadService(StorageService storageService, StorageUrlService storageUrlService) {
        this.storageService = storageService;
        this.storageUrlService = storageUrlService;
    }

    public void upload(String url, InputStream in) {
        var fileDto = this.storageUrlService.findStoragePathByUrl(url);
        if (fileDto.isEmpty()) {
            throw new RuntimeException("File not found");
        }
        this.storageService.upload(fileDto.get(), in);
    }

    public void upload(long parentId, String relativeUrl, InputStream in) {
        var fileDto = this.storageUrlService.findStoragePathByParentIdAndName(parentId, relativeUrl);
        if (fileDto.isEmpty()) {
            throw new RuntimeException("File not found");
        }
        this.storageService.upload(fileDto.get(), in);
    }
}

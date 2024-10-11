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

    public void upload(String fileUrl, InputStream in) {
        var fileDto = this.storageUrlService.findStoragePathByUrl(fileUrl);
        if (fileDto.isEmpty()) {
            throw new RuntimeException("File not found");
        }
        this.storageService.upload(fileUrl, in);
    }

    public void upload(long parentId, String fileName, InputStream in) {
        var folderDto = this.storageUrlService.findStoragePathById(parentId);
        var path =  (folderDto.isEmpty() ?  "/" : folderDto.get().getUrl()) + fileName;
        this.storageService.upload(path, in);
    }

    public void upload(long parentId, String fileName, String in) {
        var folderDto = this.storageUrlService.findStoragePathById(parentId);
        var path =  (folderDto.isEmpty() ?  "/" : folderDto.get().getUrl()) + fileName;
        this.storageService.upload(path, in);
    }

    public void rename(Long parentId, Object prevName, String newName) {
        var folderDto = this.storageUrlService.findStoragePathById(parentId);
        var path =  (folderDto.isEmpty() ?  "/" : folderDto.get().getUrl());
        var oldUrl = path + prevName;
        var newUrl = path + newName;
        this.storageService.rename(oldUrl, newUrl);
    }

    public void createFolder(Long parentId, String name) {
        var folderDto = this.storageUrlService.findStoragePathById(parentId);
        var path =  (folderDto.isEmpty() ?  "/" : folderDto.get().getUrl()) + name;
        this.storageService.createFolder(path);
    }
}

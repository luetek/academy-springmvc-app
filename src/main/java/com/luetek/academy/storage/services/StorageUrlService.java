package com.luetek.academy.storage.services;

import com.luetek.academy.storage.dtos.PathUrlDto;
import com.luetek.academy.storage.entities.File;
import com.luetek.academy.storage.entities.Folder;
import com.luetek.academy.storage.repositories.FileRepository;
import com.luetek.academy.storage.repositories.StoragePathRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;
/**
 * This need to provide caching and mapping url or path to actual files and folder.
 * */
@Component
public class StorageUrlService {
    private final StoragePathRepository storagePathRepository;

    public StorageUrlService(@Lazy StoragePathRepository storagePathRepository) {
        this.storagePathRepository = storagePathRepository;
    }

    public Optional<PathUrlDto> findStoragePathByUrl(String url) {
        return findStoragePathByParentAndName(null, url);
    }

    public Optional<PathUrlDto> findStoragePathById(Long id) {
        if (id == null)
            return Optional.empty();

        var currentFileOpt = storagePathRepository.findById(id);
        if (currentFileOpt.isEmpty())
            return Optional.empty();
        var currentFile = currentFileOpt.get();
        var isFolder = currentFileOpt.get() instanceof Folder;

        var current = currentFileOpt.get();
        var currentFileUrlDto = new PathUrlDto();
        currentFileUrlDto.setId(current.getId());
        currentFileUrlDto.setName(current.getName());
        currentFileUrlDto.setParentId(current.getParentId());
        currentFileUrlDto.setSubType(current.getSubType());
        // recursive call
        var parentUrlDtoOpt =  findStoragePathById(current.getParentId());

        var parentUrl =  parentUrlDtoOpt.isPresent() ? parentUrlDtoOpt.get().getUrl() : "";
        if (currentFile instanceof File) {
            currentFileUrlDto.setUrl(parentUrl  +  "/" +currentFileUrlDto.getName());
        } else {
            currentFileUrlDto.setUrl(parentUrl  + current.getSubType() +  "/" +currentFileUrlDto.getName() +  "/");
        }
        return Optional.of(currentFileUrlDto);
    }

    private Optional<PathUrlDto> findStoragePathByParentAndName(PathUrlDto parentFolder, String relativeUrl) {
        var firstSlash = relativeUrl.indexOf('/');
        var startingName = relativeUrl.substring(0, firstSlash);
        var restOfName = relativeUrl.substring(firstSlash + 1);

        var parentId = parentFolder != null? parentFolder.getId(): null;
        var currentFileOpt = storagePathRepository.findFileByParentIdAndName(parentId, startingName);
        if (currentFileOpt.isEmpty()) {
            return Optional.empty();
        }
        var current = currentFileOpt.get();
        var currentFileUrlDto = new PathUrlDto();
        currentFileUrlDto.setId(current.getId());
        currentFileUrlDto.setSubType(current.getSubType());
        currentFileUrlDto.setName(current.getName());
        currentFileUrlDto.setParentId(current.getParentId());
        var parentUrl =  parentFolder != null ? parentFolder.getUrl() : "";
        var isFolder = current instanceof Folder;
        if (isFolder) {
            currentFileUrlDto.setUrl(parentUrl + startingName +  "/");
        } else {
            currentFileUrlDto.setUrl(parentUrl + startingName);
        }


        if (StringUtils.isEmpty(restOfName)) {
            return Optional.of(currentFileUrlDto);
        }
        // Recursive calls
        return findStoragePathByParentAndName(currentFileUrlDto, restOfName);
    }


    public Optional<PathUrlDto> findStoragePathByParentIdAndName(Long parentId, String relativeName) {
        var currentFileOpt = findStoragePathById(parentId);
        if (currentFileOpt.isEmpty())
            return Optional.empty();
        return findStoragePathByParentAndName(currentFileOpt.get(), relativeName);
    }
}

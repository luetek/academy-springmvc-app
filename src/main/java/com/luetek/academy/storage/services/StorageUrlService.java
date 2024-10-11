package com.luetek.academy.storage.services;

import com.luetek.academy.storage.dtos.PathUrlDto;
import com.luetek.academy.storage.entities.Folder;
import com.luetek.academy.storage.repositories.FileRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;
/**
 * This need to provide caching and mapping url or path to actual files and folder.
 * */
@Component
public class StorageUrlService {
    private final FileRepository fileRepository;

    public StorageUrlService(@Lazy FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public Optional<PathUrlDto> findStoragePathByUrl(String url) {
        return findStoragePathByParentAndName(null, url);
    }

    public Optional<PathUrlDto> findStoragePathById(long id) {
        var currentFileOpt = fileRepository.findById(id);
        if (currentFileOpt.isEmpty())
            return Optional.empty();

        var isFolder = currentFileOpt.get() instanceof Folder;

        var current = currentFileOpt.get();
        var currentFileUrlDto = new PathUrlDto();
        currentFileUrlDto.setId(current.getId());
        currentFileUrlDto.setName(current.getName());
        currentFileUrlDto.setParentId(current.getParentId());

        // recursive call
        var parentUrlDtoOpt =  findStoragePathById(current.getParentId());

        var parentUrl =  parentUrlDtoOpt.isPresent() ? parentUrlDtoOpt.get().getUrl() : "";
        currentFileUrlDto.setUrl(parentUrl  + currentFileUrlDto.getName() + (isFolder ? "/": ""));

        return Optional.of(currentFileUrlDto);
    }

    private Optional<PathUrlDto> findStoragePathByParentAndName(PathUrlDto parentFolder, String relativeUrl) {
        var firstSlash = relativeUrl.indexOf('/');
        var startingName = relativeUrl.substring(0, firstSlash);
        var restOfName = relativeUrl.substring(firstSlash + 1);

        var parentId = parentFolder != null? parentFolder.getId(): null;
        var currentFileOpt = fileRepository.findFileByParentIdAndName(parentId, startingName);
        if (currentFileOpt.isEmpty()) {
            return Optional.empty();
        }
        var current = currentFileOpt.get();
        var currentFileUrlDto = new PathUrlDto();
        currentFileUrlDto.setId(current.getId());
        currentFileUrlDto.setName(current.getName());
        currentFileUrlDto.setParentId(current.getParentId());
        var parentUrl =  parentFolder != null ? parentFolder.getUrl() : "";
        var isFolder = current instanceof Folder;
        currentFileUrlDto.setUrl(parentUrl + startingName + (isFolder ? "/": ""));

        if (StringUtils.isEmpty(restOfName)) {
            return Optional.of(currentFileUrlDto);
        }
        // Recursive calls
        return findStoragePathByParentAndName(currentFileUrlDto, restOfName);
    }


    public Optional<PathUrlDto> findStoragePathByParentIdAndName(long parentId, String relativeName) {
        var currentFileOpt = findStoragePathById(parentId);
        if (currentFileOpt.isEmpty())
            return Optional.empty();
        return findStoragePathByParentAndName(currentFileOpt.get(), relativeName);
    }
}

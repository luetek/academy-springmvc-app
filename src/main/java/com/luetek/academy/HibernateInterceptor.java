package com.luetek.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luetek.academy.storage.entities.Folder;
import com.luetek.academy.storage.services.FileUploadService;
import jakarta.persistence.DiscriminatorValue;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class HibernateInterceptor implements Interceptor {

    private final FileUploadService fileUploadService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public HibernateInterceptor(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @SneakyThrows
    @Override
    public boolean onPersist(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) {
        if ( (entity instanceof Folder folderEntity)) {
            DiscriminatorValue val = folderEntity.getClass().getAnnotation( DiscriminatorValue.class );
            folderEntity.setSubType(val.value());
            log.info(entity.toString());
            // first time save only
            // Serialize folder metadata
            var folderData = objectMapper.writeValueAsString(entity);
            fileUploadService.createFolder(folderEntity.getParentId(), folderEntity.getName());
            fileUploadService.upload(folderEntity.getParentId(), folderEntity.getName() + "/meta.json", folderData);
        }
        return false;
    }

    @SneakyThrows
    @Override
    public boolean onFlushDirty(
        Object entity,
        Object id,
        Object[] currentState,
        Object[] previousState,
        String[] propertyNames,
        Type[] types) throws CallbackException {
        if (entity instanceof Folder folderEntity) {
            DiscriminatorValue val = folderEntity.getClass().getAnnotation( DiscriminatorValue.class );
            folderEntity.setSubType(val.value());
            log.info(entity.toString());
            // Handle folder rename
            var idx = ArrayUtils.indexOf(propertyNames, "name");
            var prevName = previousState[idx];
            if (!prevName.equals(folderEntity.getName())) {
                fileUploadService.rename(folderEntity.getParentId(), prevName, folderEntity.getName());
            }
            // Serialize folder metadata
            var folderData = objectMapper.writeValueAsString(entity);
            fileUploadService.upload(folderEntity.getParentId(), folderEntity.getName() + "/meta.json", folderData);
        }
        return false;
    }

}

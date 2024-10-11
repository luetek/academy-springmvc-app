package com.luetek.academy.storage.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/***
 * Folder represent an actual folder in the file system or cloud storage like aws s3.
 * ***
 * If a user has access to any of the folder then he has access to children.
 * ***
 * We are not storing url because when we rename a folder we will need to rename
 * all the child files and folders.
 * ***
 * Do we need to move folder - We don't have requirement to do that.
 * ***
 * Folder is abstract we will subclass it to create PostCollection, Article, etc.
 * which might have extra attributes that need to be stored in .meta.json file
 * and in db.
 * */

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public abstract class  Folder extends StoragePath {
    @OneToMany
    @ToString.Exclude
    @JoinColumn(name = "parent_id")
    private List<Folder> children;
}

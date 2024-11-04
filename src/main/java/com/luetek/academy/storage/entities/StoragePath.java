package com.luetek.academy.storage.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/***
 * File represent an actual file in the file system or cloud storage like aws s3.
 * File parent will be a folder whose parent can be another folder.
 * If a user has access to any of the parent folder then he has access to children.
 * ***
 * We are not storing url because when we rename a folder we will need to rename
 * all the child files and folders.
 * ***
 * File is abstract we will subclass it to create ImageFile, MarkdownFile etc.
 * which might have extra attributes that need to be stored in .meta.json file
 * and in db.
 * */
@Table(name = "storage_paths", uniqueConstraints = {
    @UniqueConstraint(name = "parentIdAndNameUnique", columnNames = {"name", "parentId"})})
@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "sub_type", discriminatorType = DiscriminatorType.STRING)
public abstract class StoragePath {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 3, max = 32)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Folder parent;

    @JsonIgnore
    @Column(name = "parent_id")
    private Long parentId;

    @Getter
    @Column(name="sub_type", insertable = false, updatable = false)
    protected String subType;
}

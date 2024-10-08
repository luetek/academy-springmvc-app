package com.luetek.academy.publication.entities;

import com.luetek.academy.storage.entities.Folder;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.SecondaryTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper=true)
@NoArgsConstructor
@Entity
@SecondaryTable(name = Article.TABLE_NAME)
@DiscriminatorValue("ARTICLE")
public class Article extends Folder {
    public static final String TABLE_NAME = "articles";

    @Column(table = TABLE_NAME)
    private String title;

    @Column(table = TABLE_NAME)
    private String description;

    @Column(table = TABLE_NAME)
    private int orderBy;

    @Column(table = TABLE_NAME)
    private boolean publish;
}

package com.luetek.academy.publication.entities;

import com.luetek.academy.storage.entities.File;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SecondaryTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SecondaryTable(name = MarkdownFile.TABLE_NAME)
public class MarkdownFile extends File {
    public static final String TABLE_NAME = "markdowns";
    @Column(table = TABLE_NAME)
    private int orderBy;

    @Column(table = TABLE_NAME)
    private boolean publish;
}

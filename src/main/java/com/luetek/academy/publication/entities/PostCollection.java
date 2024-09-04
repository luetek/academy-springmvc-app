package com.luetek.academy.publication.entities;

import com.luetek.academy.storage.entities.Folder;
import jakarta.persistence.Column;
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
@SecondaryTable(name = PostCollection.TABLE_NAME)
public class PostCollection extends Folder {
    public static final String TABLE_NAME = "postcollections";

    @Column(table = TABLE_NAME)
    private String title;

    @Column(table = TABLE_NAME)
    private String description;

    @Column(table = TABLE_NAME)
    private boolean publish;

}

package com.luetek.academy.authentication.entities;

import com.luetek.academy.storage.entities.Folder;
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
@SecondaryTable(name = AccountFolder.TABLE_NAME)
public class AccountFolder extends Folder {

    public static final String TABLE_NAME = "accounts";

    @Column(table = TABLE_NAME)
    private String displayName;

}

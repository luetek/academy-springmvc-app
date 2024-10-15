package com.luetek.academy.authentication.entities;

import com.luetek.academy.publication.entities.Article;
import com.luetek.academy.storage.entities.Folder;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@SecondaryTable(name = Account.TABLE_NAME)
@DiscriminatorValue("USER_ACCOUNT")
public class Account extends Folder {

    public static final String TABLE_NAME = "accounts";

    @Column(table = TABLE_NAME)
    private String displayName;

    @Column(table = TABLE_NAME, unique = true)
    private String email;

    @Column(table = TABLE_NAME)
    private boolean enabled;

    @Column(table = TABLE_NAME)
    private boolean expired;

    @Column(table = TABLE_NAME)
    private boolean locked;

}

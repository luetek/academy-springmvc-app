package com.luetek.academy.authentication.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.SecondaryTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SecondaryTable(name = UserAccount.TABLE_NAME)
public class UserAccount {
    public static final String TABLE_NAME = "user_accounts";
}

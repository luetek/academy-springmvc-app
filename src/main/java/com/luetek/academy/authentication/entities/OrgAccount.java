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
@SecondaryTable(name = OrgAccount.TABLE_NAME)
public class OrgAccount {
    public static final String TABLE_NAME = "org_accounts";
}

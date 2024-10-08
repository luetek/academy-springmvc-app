package com.luetek.academy.authentication.entities;

import jakarta.persistence.DiscriminatorValue;
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
@DiscriminatorValue("ORG_ACCOUNT")
public class OrgAccount extends Account {
    public static final String TABLE_NAME = "org_accounts";
}

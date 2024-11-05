package com.luetek.academy.admin.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ChildDescriptionDto {

    private Long id;

    private String name;

    private String subType;

    private String linkPath;

    private String title;

    private String description;

    private int orderBy;

    private boolean publish;
}

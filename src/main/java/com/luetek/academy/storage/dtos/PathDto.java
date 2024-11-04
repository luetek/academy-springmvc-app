package com.luetek.academy.storage.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PathDto {
    Long id;
    String name;
    Long parentId;
    String subType;
}

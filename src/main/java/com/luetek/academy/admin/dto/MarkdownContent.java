package com.luetek.academy.admin.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class MarkdownContent {
    private Long id;
    private Long parentId;
    private String fileName;
    private String content;
}

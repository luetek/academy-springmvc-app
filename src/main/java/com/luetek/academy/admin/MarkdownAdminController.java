package com.luetek.academy.admin;

import com.luetek.academy.admin.dto.MarkdownContent;
import com.luetek.academy.publication.entities.MarkdownFile;
import com.luetek.academy.publication.repositories.ArticleRepository;
import com.luetek.academy.storage.repositories.FileRepository;
import com.luetek.academy.storage.services.FileStreamingService;
import com.luetek.academy.storage.services.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;

@Slf4j
@Controller()
@RequestMapping("/admin/markdown")
public class MarkdownAdminController {

    private final FileRepository fileRepository;
    private final ArticleRepository articleRepository;
    private final FileUploadService fileUploadService;
    private final FileStreamingService fileStreamingService;
    public MarkdownAdminController(ArticleRepository articleRepository,
                                    FileRepository fileRepository,
                                    FileUploadService fileUploadService,
                                    FileStreamingService fileStreamingService) {
        this.articleRepository = articleRepository;
        this.fileUploadService = fileUploadService;
        this.fileRepository = fileRepository;
        this.fileStreamingService = fileStreamingService;
    }

    @GetMapping("{collectionId}/{articleName}/create")
    public String createForm(@PathVariable Long collectionId, @PathVariable String articleName,Model model) {
        log.info("Get createForm called");
        var optArticle = this.articleRepository.findByParentIdAndName(collectionId, articleName);
        if (optArticle.isEmpty()) {
            throw new RuntimeException("Article Not Found");
        }
        model.addAttribute("article", optArticle.get());
        var markdownContent = new MarkdownContent();
        markdownContent.setContent("");
        markdownContent.setParentId(optArticle.get().getId());
        model.addAttribute("markdownContent", markdownContent);
        return "admin/markdown.html";
    }

    @GetMapping("{articleId}/{fileName}/edit")
    public String getEditForm(@PathVariable Long articleId, @PathVariable String fileName, Model model) {
        log.info("Get EditForm  called");
        var optArticle = this.articleRepository.findById(articleId);
        if (optArticle.isEmpty()) {
            throw new RuntimeException("Article not Found");
        }
        var markdownFile =  this.fileRepository.findFileByParentIdAndName(optArticle.get().getId(), fileName);

        var markdownContent = new MarkdownContent();
        var outputStream = new ByteArrayOutputStream();
        this.fileStreamingService.streamTo(markdownFile.get().getId(), outputStream);

        markdownContent.setContent(outputStream.toString());
        markdownContent.setFileName(markdownFile.get().getName());
        markdownContent.setId(markdownFile.get().getId());
        markdownContent.setParentId(markdownFile.get().getParentId());

        model.addAttribute("markdownContent", markdownContent);
        model.addAttribute("article", optArticle.get());
        return "admin/markdown.html";
    }

    @PostMapping("/save")
    public String saveForm(@ModelAttribute MarkdownContent markdownContent) {
        log.info("Save Markdown called");
        log.info(markdownContent.toString());
        this.fileUploadService.upload(markdownContent.getParentId(), markdownContent.getFileName(), markdownContent.getContent());
        var file = new MarkdownFile();
        file.setParentId(markdownContent.getParentId());
        file.setName(markdownContent.getFileName());
        file.setSize(markdownContent.getContent().length());
        this.fileRepository.saveAndFlush(file);
        var optArticle = this.articleRepository.findById(markdownContent.getParentId());
        return "redirect:/admin/articles/" + optArticle.get().getName()+ "/edit";
    }
}

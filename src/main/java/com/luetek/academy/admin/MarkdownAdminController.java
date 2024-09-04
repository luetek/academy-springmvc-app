package com.luetek.academy.admin;

import com.luetek.academy.admin.dto.MarkdownContent;
import com.luetek.academy.publication.repositories.ArticleRepository;
import com.luetek.academy.publication.repositories.PostCollectionRepository;
import com.luetek.academy.storage.repositories.FolderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller()
@RequestMapping("/admin/markdown")
public class MarkdownAdminController {

    private final FolderRepository folderRepository;
    private final PostCollectionRepository postCollectionRepository;
    private final ArticleRepository articleRepository;
    public MarkdownAdminController( PostCollectionRepository postCollectionRepository,
                                   ArticleRepository articleRepository,
                                   FolderRepository folderRepository) {
        this.postCollectionRepository = postCollectionRepository;
        this.articleRepository = articleRepository;
        this.folderRepository = folderRepository;
    }

    @GetMapping()
    public String index(Model model) {
        var articles = this.articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "admin/article-list.html";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        log.info("Get createForm called");
        model.addAttribute("articles", this.articleRepository.findAll());
        return "admin/markdown.html";
    }

    @GetMapping("/{name}/edit")
    public String getEditForm(@PathVariable String articleName, Model model) {
        log.info("Get EditForm  called");
        var optionalCollection = this.folderRepository.findByName(articleName);
        model.addAttribute("postCollections", this.postCollectionRepository.findAll());
        model.addAttribute("article", optionalCollection.get());
        return "admin/article.html";
    }

    @PostMapping("/save")
    public String saveForm(@ModelAttribute MarkdownContent markdownContent) {
        log.info("Save Markdown called");
        log.info(markdownContent.toString());
        return "redirect:/admin/articles";
    }
}

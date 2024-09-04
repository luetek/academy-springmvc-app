package com.luetek.academy.admin;

import com.luetek.academy.publication.entities.Article;
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
@RequestMapping("/admin/articles")
public class ArticleAdminController {

	private final FolderRepository folderRepository;
	private final PostCollectionRepository postCollectionRepository;
	private final ArticleRepository articleRepository;
	public ArticleAdminController( PostCollectionRepository postCollectionRepository,
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
		model.addAttribute("postCollections", this.postCollectionRepository.findAll());
		model.addAttribute("article", new Article());
		return "admin/article.html";
	}
	
	@GetMapping("/{articleName}/edit")
	public String getEditForm(@PathVariable String articleName, Model model) {
		log.info("Get EditForm  called");
		var optionalCollection = this.folderRepository.findByName(articleName);
		model.addAttribute("postCollections", this.postCollectionRepository.findAll());
		model.addAttribute("article", optionalCollection.get());
		return "admin/article.html";
	}
	
	@PostMapping("/save")
	public String saveForm(@ModelAttribute Article article) {
		log.info("Save Article called");
		log.info(article.toString());
		this.folderRepository.saveAndFlush(article);
		return "redirect:/admin/articles";
	}
}

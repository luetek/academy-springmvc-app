package com.luetek.academy.admin;

import com.luetek.academy.admin.dto.ArticleDto;
import com.luetek.academy.publication.entities.Article;
import com.luetek.academy.publication.repositories.ArticleRepository;
import com.luetek.academy.publication.repositories.PostCollectionRepository;
import com.luetek.academy.storage.repositories.FolderRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String createForm(Model model, @RequestParam(required = false) String parentId) {
		log.info("Get createForm called");
		model.addAttribute("postCollections", this.postCollectionRepository.findAll());
		var article = new Article();
		article.setParentId(StringUtils.isEmpty(parentId) ? null : Long.parseLong(parentId));
		model.addAttribute("article", article);
		model.addAttribute("operation", "create");
		return "admin/article.html";
	}
	
	@GetMapping("/{articleName}/edit")
	public String getEditForm(@PathVariable String articleName, Model model) {
		log.info("Get EditForm  called");
		var optionalCollection = this.folderRepository.findByName(articleName);
		model.addAttribute("postCollections", this.postCollectionRepository.findAll());
		model.addAttribute("operation", "edit");
		model.addAttribute("article", optionalCollection.get());
		model.addAttribute("children", optionalCollection.get().getChildren().stream().map(folder -> {
			var article = new ArticleDto();
			var entity = (Article)folder;
			article.setId(entity.getId());
			article.setLinkType(getSubTypeToLinkTypeMapping(entity.getSubType()));
			article.setDescription(entity.getDescription());
			article.setSubType(entity.getSubType());
			article.setName(entity.getName());
			article.setTitle(entity.getTitle());
			article.setPublish(entity.isPublish());
			article.setOrderBy(entity.getOrderBy());
			return article;
		}).toList());

		return "admin/article.html";
	}

	private String getSubTypeToLinkTypeMapping(String subType) {
		if ("MARKDOWN".equals(subType))
			return  "markdown";
		throw new RuntimeException("Unknown mapping");
	}

	@PostMapping("/save")
	public String saveForm(@ModelAttribute Article article) {
		log.info("Save Article called");
		log.info(article.toString());
		this.folderRepository.saveAndFlush(article);
		return "redirect:/admin/articles";
	}
}

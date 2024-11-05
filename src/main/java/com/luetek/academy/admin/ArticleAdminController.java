package com.luetek.academy.admin;

import com.luetek.academy.admin.dto.ChildDescriptionDto;
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

	private final PostCollectionRepository postCollectionRepository;
	private final ArticleRepository articleRepository;
	public ArticleAdminController( PostCollectionRepository postCollectionRepository,
								   ArticleRepository articleRepository,
								   FolderRepository folderRepository) {
		this.postCollectionRepository = postCollectionRepository;
		this.articleRepository = articleRepository;
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
	
	@GetMapping("/{collectionId}/{articleName}/edit")
	public String getEditForm(@PathVariable Long collectionId, @PathVariable String articleName, Model model) {
		log.info("Get EditForm  called");
		var optionalArticle = this.articleRepository.findByParentIdAndName(collectionId, articleName);
		var article = optionalArticle.get();
		model.addAttribute("postCollections", this.postCollectionRepository.findAll());
		model.addAttribute("operation", "edit");
		model.addAttribute("article", article);
		model.addAttribute("children", article.getChildren().stream().map(item -> {
			var child = new ChildDescriptionDto();
			child.setId(item.getId());
			child.setLinkPath(getSubTypeToLinkMapping(item.getSubType(), item.getParentId(), item.getName()));
			child.setSubType(item.getSubType());
			child.setName(item.getName());
			return child;
		}).toList());

		return "admin/article.html";
	}

	private String getSubTypeToLinkMapping(String subType, Long parentId, String name) {
		if ("MARKDOWN".equals(subType))
			return  "markdown/" + parentId + "/" + name;
		throw new RuntimeException("Unknown mapping");
	}

	@PostMapping("/save")
	public String saveForm(@ModelAttribute Article article) {
		log.info("Save Article called");
		log.info(article.toString());
		this.articleRepository.saveAndFlush(article);
		return "redirect:/admin/articles";
	}
}

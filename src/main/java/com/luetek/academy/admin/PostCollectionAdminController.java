package com.luetek.academy.admin;

import com.luetek.academy.admin.dto.ArticleDto;
import com.luetek.academy.publication.entities.Article;
import com.luetek.academy.publication.repositories.PostCollectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luetek.academy.publication.entities.PostCollection;
import com.luetek.academy.storage.repositories.FolderRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
@Controller()
@RequestMapping("/admin/post-collections")
public class PostCollectionAdminController {
	
	private final PostCollectionRepository postCollectionRepository;
	
	public PostCollectionAdminController(PostCollectionRepository postCollectionRepository) {
		this.postCollectionRepository = postCollectionRepository;
	}

	@GetMapping()
	public String index(Model model) {
		var collections = this.postCollectionRepository.findAll();
		model.addAttribute("collections", collections);
		return "admin/post-collection-list.html";
	}
	
	@GetMapping("/create")
	public String createForm(Model model) {
		log.info("Get createForm called");
		model.addAttribute("postCollection", new PostCollection());
		return "admin/post-collection.html";
	}
	
	@GetMapping("/{collectionName}/edit")
	public String getEditForm(@PathVariable String collectionName, Model model) {
		log.info("Get EditForm  called");
		var optionalCollection = this.postCollectionRepository.findByName(collectionName);
		var collection = optionalCollection.get();
		model.addAttribute("postCollection", collection);
		model.addAttribute("operation", "edit");
		model.addAttribute("children", collection.getChildren().stream().map(folder -> {
			var article = new ArticleDto();
			var entity = (Article)folder;
			article.setId(entity.getId());
			article.setLinkType("articles");
			article.setDescription(entity.getDescription());
			article.setSubType(entity.getSubType());
			article.setName(entity.getName());
			article.setTitle(entity.getTitle());
			article.setPublish(entity.isPublish());
			article.setOrderBy(entity.getOrderBy());
			return article;
		}).toList());
		return "admin/post-collection.html";
	}
	
	@PostMapping("/save")
	public String saveForm(@ModelAttribute PostCollection postCollection) {
		log.info("Save PostCollection called");
		log.info(postCollection.toString());
		this.postCollectionRepository.saveAndFlush(postCollection);
		return "redirect:/admin/post-collections";
	}
}

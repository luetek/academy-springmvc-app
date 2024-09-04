package com.luetek.academy.admin;

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

@Slf4j
@Controller()
@RequestMapping("/admin/post-collections")
public class PostCollectionAdminController {
	
	private final FolderRepository folderRepository;
	
	public PostCollectionAdminController(FolderRepository folderRepository) {
		this.folderRepository = folderRepository;
	}

	@GetMapping()
	public String index(Model model) {
		var collections = this.folderRepository.findAll();
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
		var optionalCollection = this.folderRepository.findByName(collectionName);
		model.addAttribute("postCollection", optionalCollection.get());
		return "admin/post-collection.html";
	}
	
	@PostMapping("/save")
	public String saveForm(@ModelAttribute PostCollection postCollection) {
		log.info("Save PostCollection called");
		log.info(postCollection.toString());
		this.folderRepository.saveAndFlush(postCollection);
		return "redirect:/admin/post-collections";
	}
}

package com.luetek.academy.publication.entities;

import com.luetek.academy.storage.repositories.FileRepository;
import com.luetek.academy.storage.repositories.FolderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "showSql=false",
        "spring.h2.console.enabled=true"
})
public class PublicationJpaTest {
    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldBeAbleToSavePublicationInDb() {
        PostCollection collection = new PostCollection();
        collection.setName("javacript-guide");
        collection = folderRepository.saveAndFlush(collection);

        Article article1 = new Article();
        article1.setName("introduction");
        article1.setParent(collection);
        folderRepository.saveAndFlush(article1);

        Article article2 = new Article();
        article2.setName("datatypes");
        article2.setParent(collection);
        folderRepository.saveAndFlush(article2);

        var root = folderRepository.findById(collection.getId()).get();
        assertThat(root).isNotNull();
        entityManager.refresh(root);
        assertThat(root.getChildren().size()).isEqualTo(2);

    }

}

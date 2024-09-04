package com.luetek.academy.storage.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Table(name = "folders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class  Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true, nullable = false)
    @Size(min = 3, max = 20)
    private String name;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Folder parent;
    
    @Column(name = "parent_id")
    private Long parentId;

    @OneToMany
    @ToString.Exclude
    @JoinColumn(name = "parent_id")
    private List<Folder> children;

    @OneToMany
    @ToString.Exclude
    @JoinColumn(name = "parent_id")
    private List<File> files;
}

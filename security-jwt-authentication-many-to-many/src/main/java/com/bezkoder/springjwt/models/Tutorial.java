package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
/*
当我们使用JPA Repository从数据库中获取从父实体惰性加载的字段的数据时
Hibernate返回一个对象，其中包含类的所有字段，
这些字段与hibernateLazyInitializer一起映射到表中。
当我们以JSON String格式序列化这个实体时，所有字段和hibernateLazyInitializer都将被序列化。
因此，为了避免这种不必要的序列化，使用将其忽略@JsonIgnoreProperties。*/


@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@Entity
@Table(name = "tutorials")
public class Tutorial {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
   //@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tutorial_generator")
	private long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "published")
	private boolean published;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "tutorial_id")
	private Set<Comment> comments = new HashSet<>();

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public void removeComments() {
		this.comments.clear();
	}

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(name = "tutorial_tags",
			joinColumns = { @JoinColumn(name = "tutorial_id") },
			inverseJoinColumns = { @JoinColumn(name = "tag_id") })
	private Set<Tag> tags = new HashSet<>();

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public void addTag(Tag tag) {
		this.tags.add(tag);
		tag.getTutorials().add(this);
	}

	public void removeTag(long tagId) {
		Tag tag = this.tags.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
		if (tag != null) {
			this.tags.remove(tag);
			tag.getTutorials().remove(this);
		}
	}

	public Tutorial() {

	}


	public Tutorial(String title, String description, boolean published) {
		this.title = title;
		this.description = description;
		this.published = published;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean isPublished) {
		this.published = isPublished;
	}

	@Override
	public String toString() {
		return "Tutorial [id=" + id + ", title=" + title +
				", desc=" + description + ", published=" + published + "]";
	}

}

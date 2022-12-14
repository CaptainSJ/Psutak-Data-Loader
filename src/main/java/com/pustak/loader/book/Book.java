package com.pustak.loader.book;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * @author Saurabh
 *
 */

@Table(value = "book_by_id")
public class Book {

	@Id
	@PrimaryKeyColumn(name = "book_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String id;

	@Column(value = "book_name")
	@CassandraType(type = Name.TEXT)
	private String name;

	@Column(value = "description")
	@CassandraType(type = Name.TEXT)
	private String description;

	@Column(value = "published_date")
	@CassandraType(type = Name.DATE)
	private LocalDate publishedDate;

	@Column(value = "cover_ids")
	@CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
	private List<String> coverIds;

	@Column(value = "author_name")
	@CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
	private List<String> authorName;

	@Column(value = "author_ids")
	@CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
	private List<String> authorIds;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}

	public List<String> getCoverIds() {
		return coverIds;
	}

	public void setCoverIds(List<String> coverIds) {
		this.coverIds = coverIds;
	}

	public List<String> getAuthorName() {
		return authorName;
	}

	public void setAuthorName(List<String> authorName) {
		this.authorName = authorName;
	}

	public List<String> getAuthorIds() {
		return authorIds;
	}

	public void setAuthorIds(List<String> authorIds) {
		this.authorIds = authorIds;
	}

}

package com.rohit.elasticsearch.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.rohit.elasticsearch.ElasticSearchConstants;

/**
 * @author rohit
 *
 */
@Document(indexName = ElasticSearchConstants.SHOW_INDEX, type = "content")
@XmlRootElement
public class Show {
	private String id;
	private long date;
	private String status;
	private String title;
	
	@Field(type=FieldType.Object)
	private MetaData metaData;
	
	@Field(type=FieldType.Keyword)
	private String[] tags;

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public MetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

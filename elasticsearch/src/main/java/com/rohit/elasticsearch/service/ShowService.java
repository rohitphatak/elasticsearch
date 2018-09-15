package com.rohit.elasticsearch.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.data.domain.Page;

import com.rohit.elasticsearch.model.SearchShowRequest;
import com.rohit.elasticsearch.model.Show;

/**
 * @author rohit
 *
 */
public interface ShowService {
	public String addShow(Show show) throws IOException;
	
	public List<Show> search(SearchShowRequest searchShowRequest);
	
	public Result deleteShow(String id);
	
	public UpdateResponse updateShow(Show show) throws InterruptedException, ExecutionException;
	
	public GetResponse findById(String id) throws InterruptedException, ExecutionException;
	
	public IndexResponse createIndex(String index, String type, String id, Show show) throws InterruptedException, ExecutionException;

	public Iterable<Show> findAll();

	public Page<Show> findById(String id, int page, int size);

	public Page<Show> findByTitle(String title);
}


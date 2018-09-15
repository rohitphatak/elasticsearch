package com.rohit.elasticsearch.service.impl;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.rohit.elasticsearch.ElasticSearchConstants;
import com.rohit.elasticsearch.model.SearchShowRequest;
import com.rohit.elasticsearch.model.Show;
import com.rohit.elasticsearch.service.ShowRespository;
import com.rohit.elasticsearch.service.ShowService;

/**
 * @author rohit
 *
 */
@Service
public class ShowServiceImpl implements ShowService {
	@Autowired
	private Client client;
	
	@Autowired
	private ShowRespository showRespository;

	@Override
	public String addShow(Show show) throws IOException {
		IndexResponse response = client.prepareIndex(ElasticSearchConstants.SHOW_INDEX, "id", show.getId())
				.setSource(jsonBuilder().startObject().field("id", show.getId()).endObject()).get();

		return response.getResult().toString();
	}

	@Override
	public List<Show> search(SearchShowRequest searchShowRequest) {
		Page<Show> searchPage = showRespository.findByTagUsingDeclaredQuery(searchShowRequest.getTags().get(0), PageRequest.of(searchShowRequest.getPage(), searchShowRequest.getSize()));
		return searchPage.getContent();
	}

	@Override
	public Result deleteShow(String id) {
		DeleteResponse response = client.prepareDelete(ElasticSearchConstants.SHOW_INDEX, "id", id).get();
		return response.getResult();
	}

	@Override
	public UpdateResponse updateShow(Show show) throws InterruptedException, ExecutionException {
		UpdateRequest request = new UpdateRequest(ElasticSearchConstants.SHOW_INDEX, "id", show.getId());
		return client.update(request ).get();
	}

	@Override
	public GetResponse findById(String id) throws InterruptedException, ExecutionException {
//		GetResponse response = client.prepareGet(ElasticSearchConstants.SHOW_INDEX, "id", id).get();
		GetRequest request = new GetRequest(ElasticSearchConstants.SHOWINDEX, "id", id);
		ActionFuture<GetResponse> response = client.get(request );
		return response.get();

	}

	@Override
	public Page<Show> findById(String id, int page, int size) {
		return showRespository.findById(id, PageRequest.of(page, size));
	}

	@Override
	public IndexResponse createIndex(String index, String type, String id, Show show) throws InterruptedException, ExecutionException {
		IndexRequest request = new IndexRequest(index, type, id);
		request.source(new Gson().toJson(show));
		return client.index(request ).get();
	}

	@Override
	public Iterable<Show> findAll() {
		return showRespository.findAll();
	}
	
	@Override
	public Page<Show> findByTitle(String title) {
		return showRespository.findByTitle(title, PageRequest.of(0, 10));
	}

}

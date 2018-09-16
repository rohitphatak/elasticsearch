package com.rohit.elasticsearch.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.websocket.server.PathParam;

import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.elasticsearch.ElasticSearchConstants;
import com.rohit.elasticsearch.model.SearchShowRequest;
import com.rohit.elasticsearch.model.Show;
import com.rohit.elasticsearch.service.ShowService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

/**
 * @author rohit
 *
 */
@RestController
@RequestMapping("/rest/show")
@Api(value="/rest/show")
@SwaggerDefinition(info=@Info(description="API for Elasticsearch Implementation", title = "Elasticsearch API", version = "1.0"))
public class RestService {

	@Autowired
	private ShowService showService;

	@ApiOperation(value="Search Operation")
	@PostMapping("/search")
	public ResponseEntity<List<Show>> search(@RequestBody SearchShowRequest searchShowRequest) {
		return new ResponseEntity<List<Show>>(showService.search(searchShowRequest),HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GetResponse> findById(@PathVariable(value="id") String id) throws InterruptedException, ExecutionException {
		System.out.println(id);
		return new ResponseEntity<>(showService.findById(id),HttpStatus.OK);
	}

	@PostMapping("/add")
	public String add(@RequestBody Show show) throws IOException {
		return showService.addShow(show);
	}

	@PutMapping("/update")
	public ResponseEntity<UpdateResponse> update(@RequestBody Show show) throws InterruptedException, ExecutionException {
		return new ResponseEntity<UpdateResponse>(showService.updateShow(show),HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public Result deleteShow(@PathParam("id") String id) {
		return deleteShow(id);
	}
	
	@GetMapping("/{id}/{page}/{size}")
	public ResponseEntity<Page<Show>> findById(@PathVariable("id") String id, @PathVariable("page") int page, @PathVariable("size") int size) {
		return new ResponseEntity<>(showService.findById(id, page, size), HttpStatus.OK);
	}
	
	@PostMapping("/CreateIndex")
	public IndexResponse createIndex(@RequestBody Show show) throws InterruptedException, ExecutionException {
		return showService.createIndex(ElasticSearchConstants.SHOW_INDEX, "id", show.getId(), show);
	}
	
	@GetMapping("/FindAll")
	public ResponseEntity<Iterable<Show>> findAll() {
		return new ResponseEntity<>(showService.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity<Page<Show>> findByTitle(@PathVariable("title") String title) {
		return new ResponseEntity<>(showService.findByTitle(title),HttpStatus.OK);
	}
}

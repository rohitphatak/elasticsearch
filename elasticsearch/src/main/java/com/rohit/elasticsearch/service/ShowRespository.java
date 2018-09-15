package com.rohit.elasticsearch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.rohit.elasticsearch.model.Show;

/**
 * @author rohit
 *
 */
@Repository
public interface ShowRespository extends ElasticsearchRepository<Show, String> {
	@Query("{\"bool\": {\"must\": [{\"match\": {\"tags\": \"?0\"}}]}}")
	Page<Show> findByTagUsingDeclaredQuery(String tag, Pageable pageable);

	Page<Show> findById(String id, Pageable pageable);

	Page<Show> findByTitle(String title, Pageable pageable);
}

package com.twitter.repository;

import org.springframework.data.repository.CrudRepository;

import com.twitter.domain.TwitterData;

public interface TwitterDataRepository extends CrudRepository<TwitterData, Long>{

}

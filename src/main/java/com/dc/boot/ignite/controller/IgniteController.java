package com.dc.boot.ignite.controller;

import java.util.Collection;

import javax.cache.Cache.Entry;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.QueryEntity;
import org.apache.ignite.configuration.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dc.boot.ignite.annotation.Loggable;

@RestController
@RequestMapping("/ignite")
public class IgniteController {
	private final Logger logger = LoggerFactory.getLogger(IgniteController.class);

	@Autowired
	private Ignite ignite;

	@Loggable
	@RequestMapping("/entity")
	public void igniteEntities() {
		Collection<String> caches = ignite.cacheNames();
		for (String cacheName : caches) {
			IgniteCache<Object, Object> cacheItem = ignite.cache(cacheName);
			CacheConfiguration<Object, Object> cacheConfig = cacheItem.getConfiguration(CacheConfiguration.class);

			// Gets a collection of configured query entities.
			Collection<QueryEntity> queryEntities = cacheConfig.getQueryEntities();
			for (QueryEntity entityToQueryItem : queryEntities) {
				logger.info("=========== Query Details Start =============");
				logger.info(" entityToQueryItem " + entityToQueryItem);
				logger.info(" getTableName " + entityToQueryItem.getTableName());
				logger.info(" getIndexes " + entityToQueryItem.getIndexes());
				logger.info(" getFields " + entityToQueryItem.getFields());
				logger.info(" getKeyFields " + entityToQueryItem.getKeyFields());

				for (Entry<Object, Object> cacheData : cacheItem) {
					logger.info(" Data >> " + cacheData);
				}

				logger.info("=========== Query Details End =============");
			}
		}
	}
}

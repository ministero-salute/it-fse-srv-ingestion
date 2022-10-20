/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.repository.mongo.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoException;

import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;
import it.finanze.sanita.fse2.ms.srvingestion.repository.IDocumentRepo;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.StagingDocumentETY;
import it.finanze.sanita.fse2.ms.srvingestion.utility.ProfileUtility;

import org.springframework.data.mongodb.core.query.Query;

/** 
 * The implementation of the Document Repo 
 *
 */
@Repository
public class DocumentRepo implements IDocumentRepo, Serializable {

	/**
	 * Serial version UID 
	 */
	private static final long serialVersionUID = -8230326857671732231L;

	
	@Autowired
	private transient MongoTemplate mongoTemplate; 
	
	@Autowired
	private transient ProfileUtility profileUtility; 
		
	String collection = Constants.ComponentScan.COLLECTION_NAME; 

	
	@Override
	public StagingDocumentETY insert(StagingDocumentETY ety) throws OperationException {
		try {
			return mongoTemplate.insert(ety, getCollectionName()); 
		} catch(MongoException ex) {
			throw new OperationException(Constants.Logs.ERROR_MONGO_INSERT, ex); 
		}
	} 
	
	@Override
	public void deleteByIdentifier(String identifier) {
		Query query = Query.query(Criteria.where(Constants.App.IDENTIFIER).is(identifier)); 
		mongoTemplate.remove(query, getCollectionName()); 	
	}
	
	@Override
	public StagingDocumentETY findById(String id) {
		StagingDocumentETY ety = mongoTemplate.findById(id, StagingDocumentETY.class, getCollectionName());
		return ObjectUtils.isEmpty(ety) ? new StagingDocumentETY() : ety;
	} 
	
	@Override
	public List<StagingDocumentETY> findAll() {
		return mongoTemplate.findAll(StagingDocumentETY.class, getCollectionName());
	}

	public String getCollectionName() {
		return profileUtility.isTestProfile() ?  Constants.Profile.TEST_PREFIX + '_' + Constants.ComponentScan.COLLECTION_NAME : Constants.ComponentScan.COLLECTION_NAME;
	}
	

}

/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 * 
 * Copyright (C) 2023 Ministero della Salute
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package it.finanze.sanita.fse2.ms.srvingestion.repository.mongo.impl;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoException;

import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;
import it.finanze.sanita.fse2.ms.srvingestion.repository.IDocumentRepo;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.StagingDocumentETY;

/** 
 * The implementation of the Document Repo 
 */
@Repository
public class DocumentRepo implements IDocumentRepo {

	
	@Autowired
	private MongoTemplate mongoTemplate; 
	
	
	@Override
	public StagingDocumentETY insert(StagingDocumentETY ety) throws OperationException {
		try {
			return mongoTemplate.insert(ety); 
		} catch(MongoException ex) {
			throw new OperationException(Constants.Logs.ERROR_MONGO_INSERT, ex); 
		}
	}
	
	@Override
	public StagingDocumentETY findById(String id) {
		StagingDocumentETY ety = mongoTemplate.findById(id, StagingDocumentETY.class);
		return ObjectUtils.isEmpty(ety) ? new StagingDocumentETY() : ety;
	} 
	
	@Override
	public List<StagingDocumentETY> findAll() {
		return mongoTemplate.findAll(StagingDocumentETY.class);
	}

}

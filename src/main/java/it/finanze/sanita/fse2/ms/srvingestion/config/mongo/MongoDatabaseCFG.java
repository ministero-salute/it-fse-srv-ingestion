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
package it.finanze.sanita.fse2.ms.srvingestion.config.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 *	Configuration for MongoDB.
 */
@Configuration
public class MongoDatabaseCFG {

    final List<Converter<?, ?>> conversions = new ArrayList<>();

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(MongoPropertiesCFG mongoPropCFG){
        return new SimpleMongoClientDatabaseFactory(mongoPropCFG.getUri()); 
    }

    @Bean
    @Primary
    public MongoTemplate mongoTemplate(MongoDatabaseFactory factory,ApplicationContext appContext) {
        // Assign application context to mongo
        final MongoMappingContext mongoMappingContext = new MongoMappingContext();
        mongoMappingContext.setApplicationContext(appContext);
        // Apply default mapper
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(factory),mongoMappingContext);
        // Set the default type mapper (removes custom "_class" column)
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        // Return the new instance
        return new MongoTemplate(factory, converter);
    }
}
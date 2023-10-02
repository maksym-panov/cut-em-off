package com.panov.urireg;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URIRegistryRepository extends MongoRepository<FullLinkRecord, String> {}

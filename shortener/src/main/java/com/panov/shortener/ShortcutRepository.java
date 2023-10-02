package com.panov.shortener;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortcutRepository extends MongoRepository<Shortcut, String> {}

package com.panov.shortcutreg;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortcutRegistryRepository
        extends MongoRepository<Shortcut, String> {}

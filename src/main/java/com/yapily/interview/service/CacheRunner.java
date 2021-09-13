package com.yapily.interview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
/*
  This class gets called immediately after the Spring boot runs
  and hence the characters are present in the server cache
 */
public class CacheRunner implements CommandLineRunner {
    @Autowired
    private final CharacterService characterService;

    public CacheRunner(CharacterService characterService) {
        this.characterService = characterService;
    }

    @Override
    public void run(String...args) throws Exception {
        characterService.getCharacters();
    }
}

package com.yapily.interview.controller;

import com.yapily.interview.domain.Character;
import com.yapily.interview.service.CharacterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@RestController
public class CharacterRestController {
    Logger logger = LoggerFactory.getLogger(CharacterRestController.class);

    @Autowired
    CharacterService characterService;

    @GetMapping("/characters")
    public List<Integer> geCharacters()  {
        logger.info("Inside getCharacters Rest Controller");
        return characterService.getCharactersFromCache();
    }

    @GetMapping("/characters/{characterId}")
    public Optional<Character> geCharacterById(@PathVariable final int characterId,@RequestParam final Optional<String> language ) throws GeneralSecurityException, IOException {
        logger.info("Inside getCharacter Rest Controller");
        return characterService.getCharacterById(characterId,language);
    }
}

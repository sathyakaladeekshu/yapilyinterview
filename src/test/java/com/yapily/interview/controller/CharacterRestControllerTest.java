package com.yapily.interview.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yapily.interview.domain.Character;
import com.yapily.interview.service.CharacterService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CharacterRestController.class)
class CharacterRestControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CharacterService characterService;

    @Test
    void validateForSuccess() throws Exception {
        when(characterService.getCharactersFromCache()).thenReturn(Lists.newArrayList(1011334,1017100));
        JsonArray expectedCharacters = new JsonArray();
        expectedCharacters.add(1011334);
        expectedCharacters.add(1017100);
        this.mockMvc
            .perform(get("/characters"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.content().json(String.valueOf(expectedCharacters)));
    }

    @Test
    void validateForEmptyCharacters() throws Exception {
        when(characterService.getCharactersFromCache()).thenReturn(Lists.newArrayList());
        JsonArray expectedCharacters = new JsonArray();
        this.mockMvc
            .perform(get("/characters"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.content().json(String.valueOf(expectedCharacters)));
    }

    @Test
    void validateForNotFound() throws Exception {
        when(characterService.getCharactersFromCache()).thenReturn(Lists.newArrayList());
;        this.mockMvc
             .perform(get("/charactersd"))
             .andExpect(status().isNotFound());
    }

    @Test
    void validateForCharacterByIdSuccess() throws Exception {
        String description = "A Muslim-American teenager growing up in Jersey City, Kamala Khan gained shape-shifting powers when Inhumanity spread over the Earth. A fan of super heroes, in particular Carol Danvers, Kamala took up Captain Marvel's former identity, becoming the new Ms. Marvel. This up and coming hero works to protect her community and understand her place in the world.";

        when(characterService.getCharacterById(1017577, java.util.Optional.of("en"))).thenReturn(java.util.Optional.of(new Character(1017577,"Ms. Marvel (Kamala Khan)",description)));
        JsonObject expectedCharacters = new JsonObject();
        expectedCharacters.getAsJsonObject("{\"id\":1017577,\"name\":Ms. Marvel (Kamala Khan),\"description\":\""+description+"\"}");
        this.mockMvc
                .perform(get("/characters/1017577?language=en"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().json(String.valueOf(expectedCharacters)));
    }

    @Test
    void validateForCharacterByIdNotFound() throws Exception {
        when(characterService.getCharacterById(1234, java.util.Optional.of("en"))).thenReturn(java.util.Optional.of(new Character(1,"this is test","this is description")));
        this.mockMvc
                .perform(get("/charactersdd/1234?language=en"))
                .andExpect(status().isNotFound());
    }
}
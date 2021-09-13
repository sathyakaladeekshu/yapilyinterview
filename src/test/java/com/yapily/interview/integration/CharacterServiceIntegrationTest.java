package com.yapily.interview.integration;

import com.yapily.interview.InterviewApplication;
import com.yapily.interview.domain.Character;
import com.yapily.interview.domain.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootTest(classes = InterviewApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CharacterServiceIntegrationTest {
    @LocalServerPort
    private final int port;

    @Autowired
    private final TestRestTemplate restTemplate;

    public CharacterServiceIntegrationTest(int port, TestRestTemplate restTemplate) {
        this.port = port;
        this.restTemplate = restTemplate;
    }

    @Test
    public void testValidCharacters() {
       ResponseEntity<List<Integer>> response = restTemplate.exchange(
                "http://localhost:"+port+"/characters",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

       Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
       Assertions.assertNotNull(response.getBody());
       Assertions.assertTrue(response.getBody().size()>0);
       Assertions.assertTrue(response.getBody().contains(1017577));
       Assertions.assertEquals(MediaType.APPLICATION_JSON,response.getHeaders().getContentType());
    }
    @Test
    public void testNotFoundCharacters() {
        ResponseEntity<Object> response = restTemplate.exchange(
                "http://localhost:"+port+"/charactersddd",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        Assertions.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }
    @Test
    public void testValidCharactersByIdAndLanguage() {
        String expectedDesc = "Adolescente musulmane américaine ayant grandi à Jersey City, Kamala Khan a acquis des pouvoirs de métamorphose lorsque l&#39;inhumanité s&#39;est répandue sur la Terre. Fan de super-héros, en particulier de Carol Danvers, Kamala a repris l&#39;ancienne identité de Captain Marvel, devenant la nouvelle Mme Marvel. Ce héros prometteur travaille pour protéger sa communauté et comprendre sa place dans le monde.";
        ResponseEntity<Optional<Character>> response = restTemplate.exchange(
                "http://localhost:"+port+"/characters/1017577?language=fr",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        Character character = Objects.requireNonNull(response.getBody()).get();
        Image image = character.getThumbnail();
        String actualExtension = image.getExtension();
        String actualPath = image.getPath();

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode() );
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(1017577,character.getId());
        Assertions.assertEquals("Ms. Marvel (Kamala Khan)",character.getName());
        Assertions.assertEquals(expectedDesc,character.getDescription());
        Assertions.assertEquals("http://i.annihil.us/u/prod/marvel/i/mg/5/b0/548730dac2a40",actualPath);
        Assertions.assertEquals("jpg",actualExtension);
        Assertions.assertEquals(MediaType.APPLICATION_JSON,response.getHeaders().getContentType());
    }

    @Test
    public void testValidCharactersByIdAndLanguageNoTranslation() {
        String expectedDesc = "A Muslim-American teenager growing up in Jersey City, Kamala Khan gained shape-shifting powers when Inhumanity spread over the Earth. A fan of super heroes, in particular Carol Danvers, Kamala took up Captain Marvel's former identity, becoming the new Ms. Marvel. This up and coming hero works to protect her community and understand her place in the world.";
        ResponseEntity<Optional<Character>> response = restTemplate.exchange(
                "http://localhost:"+port+"/characters/1017577",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        Character character = Objects.requireNonNull(response.getBody()).get();
        Image image = character.getThumbnail();
        String actualExtension = image.getExtension();
        String actualPath = image.getPath();

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode() );
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(1017577,character.getId());
        Assertions.assertEquals("Ms. Marvel (Kamala Khan)",character.getName());
        Assertions.assertEquals(expectedDesc,character.getDescription());
        Assertions.assertEquals("http://i.annihil.us/u/prod/marvel/i/mg/5/b0/548730dac2a40",actualPath);
        Assertions.assertEquals("jpg",actualExtension);
        Assertions.assertEquals(MediaType.APPLICATION_JSON,response.getHeaders().getContentType());
    }

    @Test
    public void testValidCharactersByIdAndLanguageNoTranslationBadRequest() {
        ResponseEntity<Optional<Character>> response = restTemplate.exchange(
                "http://localhost:"+port+"/characters/1017ddd5747",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode() );
        Assertions.assertNotNull(response.getBody());
    }
}

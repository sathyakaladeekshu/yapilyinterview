package com.yapily.interview.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.model.TranslationsListResponse;
import com.google.api.services.translate.model.TranslationsResource;
import com.yapily.interview.domain.Character;
import com.yapily.interview.domain.CharacterDataContainer;
import com.yapily.interview.domain.CharacterDataWrapper;
import com.yapily.interview.entity.Key;
import com.yapily.interview.repository.KeysRepository;
import com.yapily.interview.utility.CommonUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;


import static com.yapily.interview.utility.CommonUtility.generateMD5Hash;

@Service
public class CharacterService {
    static Logger logger = LoggerFactory.getLogger(CharacterService.class);

    @Autowired
    KeysRepository keysRepository;

    @Autowired
    RestTemplate restTemplate;

    static List<Integer> characterIdList = new ArrayList<>();

    @Autowired
    CommonUtility commonUtility;

    /**
     * This method is used to get the character details based on the Character id and the
     * optional language parameter. If the language is passed then the description of the
     * Character will be translated to the given language.
     * @param characterId
     * @param language
     * @return Character - The details of the specific character will be returned
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public Optional<Character> getCharacterById(int characterId, Optional<String> language) throws GeneralSecurityException, IOException {
        String uuidString = commonUtility.generateUUIDAsString();
        Optional<Key> key = keysRepository.findById(1);
        String hash = generateMD5Hash(uuidString,key);

        String url = "http://gateway.marvel.com/v1/public/characters/"+characterId+"?ts="+uuidString+"&apikey="+key.get().getPublic_key()+"&hash="+hash;
        CharacterDataContainer characterDataContainer = getCharacterDetails(url);
        Optional< Character> character = Arrays.stream(characterDataContainer.getResults()).findFirst();

        // Only if the language field is present then translation is needed
        if (!language.isEmpty()) {
            Translate t = new Translate.Builder(
                    GoogleNetHttpTransport.newTrustedTransport()
                    , GsonFactory.getDefaultInstance(), null)
                    // Set your application name
                    .setApplicationName("Yapily Character Service")
                    .build();
            Translate.Translations.List list = t.new Translations().list(
                    //The character description is given as input for translation
                    Arrays.asList(character.get().getDescription()),
                    // Target language
                    language.get());

            list.setKey(keysRepository.findById(2).get().getPrivate_key());
            TranslationsListResponse response = list.execute();
            for (TranslationsResource translationsResource : response.getTranslations()) {
                //The translated text is set in the description
                character.get().setDescription(translationsResource.getTranslatedText());
            }
        }
        return character;
    }

    /**
     * This method is used to generate the character details.
     * All the character details fetched are maintained in the server
     * memory.
     * @throws NoSuchAlgorithmException
     */
     public void getCharacters() throws NoSuchAlgorithmException {
        String uuidString = commonUtility.generateUUIDAsString();
        Optional<Key> key = keysRepository.findById(1);
        String hash = generateMD5Hash(uuidString,key);

        int initialOffset = 0;
        int count;
         logger.info("Start of character cache creation");
         logger.info("In progress character cache creation....");

         do {
             //Marvel api with specific authentication to get access to the resources.
            String url = "http://gateway.marvel.com/v1/public/characters?limit=100&offset="+initialOffset+"&ts="+uuidString+"&apikey="+key.get().getPublic_key()+"&hash="+hash;

             CharacterDataContainer characterDataContainer = getCharacterDetails(url);
             Character[] characterArray = characterDataContainer.getResults();
             count = characterDataContainer.getCount();

            for (int i = 0; i< characterArray.length;i++) {
                Character character =  characterArray[i];
                characterIdList.add(character.getId());
            }
            //offset skips the records that are read already
            initialOffset = initialOffset+100;
        }while (count >0);// As long as there are more records to be fetched from the Marvel api
         logger.info("End of character cache creation.....");
    }

    /**
     * This method holds the details of the Characters feteched in memory
     * @return List of Character ids from the server memory
     */
    public List<Integer> getCharactersFromCache() {
        return characterIdList;
    }

    /**
     * This is a generic method to return the CharacterDataWrapper by making a rest call to the
     * Marvel apis
     * @param url
     * @return CharacterDataContainer from the Marvel api
     */
    private CharacterDataContainer getCharacterDetails(String url) {
        CharacterDataWrapper characterDataWrapper =restTemplate.getForObject(url,CharacterDataWrapper.class);
        CharacterDataContainer characterDataContainer = characterDataWrapper.getData();
        return characterDataContainer;
    }
}

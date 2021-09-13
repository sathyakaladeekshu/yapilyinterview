package com.yapily.interview.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.model.TranslationsListResponse;
import com.google.api.services.translate.model.TranslationsResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
/*
  This class provides the method to do language translation
 */
public class TranslationService {

    static final String APPLICATION_NAME ="Yapily Character Service";

    /**
     * This method translates the given string to the target language requested
     * @param targetLanguage
     * @param sourceString
     * @param key
     * @return Translated String
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public String translateString(String targetLanguage,String sourceString, String key) throws GeneralSecurityException, IOException {
        String translatedStr ="";
        Translate t = new Translate.Builder(
                GoogleNetHttpTransport.newTrustedTransport()
                , GsonFactory.getDefaultInstance(), null)
                // Set your application name
                .setApplicationName(APPLICATION_NAME)
                .build();
        Translate.Translations.List list = t.new Translations().list(
                //The character description is given as input for translation
                Collections.singletonList(sourceString),
                // Target language
                targetLanguage);

        list.setKey(key);
        TranslationsListResponse response = list.execute();
        for (TranslationsResource translationsResource : response.getTranslations()) {
            //The translated text is set in the description
            translatedStr= translationsResource.getTranslatedText();
        }
        return translatedStr;
    }
}

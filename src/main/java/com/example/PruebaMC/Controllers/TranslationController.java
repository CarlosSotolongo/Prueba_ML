package com.example.PruebaMC.Controllers;

import com.example.PruebaMC.Entities.DtoServiceResponse;
import com.example.PruebaMC.Utils.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/translate")
public class TranslationController {
    
    @Autowired
    private Translator translator;

    @GetMapping(path="/morse2Human", produces = "application/json")
    public ResponseEntity<DtoServiceResponse> morse2Human(@RequestParam(value="morseCode") String morseCode) {
        DtoServiceResponse response = new DtoServiceResponse();
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Responded", "TranslationController");
            DtoServiceResponse dto = new DtoServiceResponse();
            dto.setTranslatedText(translator.translate2Human(morseCode));
            dto.defaultSuccess();
            return ResponseEntity.ok().headers(headers).body(dto);
        } catch (Exception e){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Responded", "TranslationController");
            DtoServiceResponse dto = new DtoServiceResponse();
            dto.setFailure("Error decoding message. Error: " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body(dto);
        }
    }

    @GetMapping(path="/human2Morse", produces = "application/json")
    public ResponseEntity<DtoServiceResponse> human2Morse(@RequestParam(value="humanText") String text) {
        DtoServiceResponse response = new DtoServiceResponse();
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Responded", "TranslationController");
            DtoServiceResponse dto = new DtoServiceResponse();
            dto.setTranslatedText(translator.humanToMorse(text.toLowerCase()));
            dto.defaultSuccess();
            return ResponseEntity.ok().headers(headers).body(dto);
        } catch (Exception e){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Responded", "TranslationController");
            DtoServiceResponse dto = new DtoServiceResponse();
            dto.setFailure("Error decoding message. Error: " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body(dto);
        }
    }
    
    @GetMapping(path="/bynary2Morse", produces = "application/json")
    public ResponseEntity<DtoServiceResponse> bynary2Morse(@RequestParam(value="bynaryCode") String binaryText) {
        DtoServiceResponse response = new DtoServiceResponse();
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Responded", "TranslationController");
            DtoServiceResponse dto = new DtoServiceResponse();
            dto.setTranslatedText(translator.decodeBits2Morse(binaryText));
            dto.defaultSuccess();
            return ResponseEntity.ok().headers(headers).body(dto);
        } catch (Exception e){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Responded", "TranslationController");
            DtoServiceResponse dto = new DtoServiceResponse();
            dto.setFailure("Error decoding message. Error: " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body(dto);
        }
    }
}

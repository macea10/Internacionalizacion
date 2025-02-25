package com.henrry.Idioma.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import java.util.Locale;

@RestController
public class InternacionalizacionController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/saludo")
    public ResponseEntity<String> obtenerSaludo(@RequestHeader(name = "Accept-Language", required =
            false) String locale) {
        Locale parsedLocale  = parseLocale(locale);
        String mensaje = messageSource.getMessage("welcome.message", null, parsedLocale);

        return ResponseEntity.ok().header(MediaType.TEXT_PLAIN_VALUE).body(mensaje);
    }

    private Locale parseLocale(String acceptLanguage) {
        if (acceptLanguage == null || acceptLanguage.isEmpty()) {
            return Locale.getDefault();
        }
        // Tomar solo la primera parte antes de la coma
        String[] locales = acceptLanguage.split(",");
        String[] primaryLocale = locales[0].split("-");

        if (primaryLocale.length > 1) {
            return new Locale(primaryLocale[0], primaryLocale[1]);
        } else {
            return new Locale(primaryLocale[0]);
        }
    }
}

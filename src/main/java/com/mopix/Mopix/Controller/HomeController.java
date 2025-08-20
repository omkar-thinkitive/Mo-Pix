package com.mopix.Mopix.Controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController{

    @GetMapping(value = "/home", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> home() {
        String html = """
                <html>
                  <head>
                    <title>Mopix</title>
                  </head>
                  <body>
                    <h1>Welcome to Mopix!</h1>
                    <p>This is the official landing page for the Mopix application.</p>
                  </body>
                </html>
                """;
        return ResponseEntity.ok(html);
    }
}

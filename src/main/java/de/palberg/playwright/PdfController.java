package de.palberg.playwright;

import com.microsoft.playwright.Browser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pdf")
class PdfController {

    private final Browser browser;

    @PostMapping
    public ResponseEntity<Resource> createPdf(@RequestBody CreatePdfOptions options) throws IOException {

        Resource resource;

        try (var page = browser.newPage()) {
            page.navigate(options.url());
            var pdf = page.pdf();
            resource = new ByteArrayResource(pdf);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(UUID.randomUUID().toString() + ".pdf")
                                .build()
                                .toString())
                .body(resource);
    }

    @GetMapping("/info")
    public String info() {
        return browser.browserType().name() + " " + browser.version();
    }

    record CreatePdfOptions(String url) {
    }
}

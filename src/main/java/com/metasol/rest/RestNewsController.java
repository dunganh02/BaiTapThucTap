package com.metasol.rest;

import com.metasol.dto.request.NewsRequestDto;
import com.metasol.dto.response.NewResponseDto;
import com.metasol.services.Imp.NewsServiceImp;
import com.metasol.utils.EOResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/news")
@AllArgsConstructor
public class RestNewsController {
    private final NewsServiceImp newsService;

    @GetMapping("/get-all")
    EOResponse<List<NewResponseDto>> getAll() {
        return EOResponse.build(newsService.getAll());

    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<NewResponseDto> create(@RequestBody NewsRequestDto dto,
                                          @RequestParam("files") MultipartFile[] files) {
        NewResponseDto responseDto = newsService.createNews(dto, files);
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping(value = "/create1", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<NewResponseDto> create1(@ModelAttribute NewsRequestDto dto,
                                           @RequestParam(name = "files", required = false) MultipartFile[] files) {
        NewResponseDto responseDto = newsService.createNews(dto, files);
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping(value = "/create3", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public EOResponse<NewResponseDto> create3(@ModelAttribute NewsRequestDto dto,
                                              @RequestParam(name = "files", required = false) MultipartFile[] files) {
        NewResponseDto responseDto = newsService.createNews(dto, files);
        return EOResponse.build(responseDto);
    }

    @GetMapping(value = "/news/{image_name}")
    public ResponseEntity<?> getImageById(@PathVariable(name = "image_name") String imageName) {
        try {
            java.nio.file.Path imagePath = Paths.get("uploads/" + imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.jpg").toUri()));
                //return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{id}")
    EOResponse<NewResponseDto> update(@PathVariable(name = "id") Long id, @ModelAttribute NewsRequestDto dto) {
        NewResponseDto responseDto = newsService.updateNews(id, dto);
        return EOResponse.build(responseDto);
    }

    @DeleteMapping("/{id}")
    EOResponse<?> delete(@PathVariable(name = "id") Long id) {
        newsService.deleteNews(id);
        return EOResponse.build("Da xoa id: " + id);
    }
}

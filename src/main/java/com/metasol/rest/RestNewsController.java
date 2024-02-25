package com.metasol.rest;

import com.metasol.dto.request.NewsRequestDto;
import com.metasol.dto.response.NewResponseDto;
import com.metasol.services.Imp.NewsServiceImp;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/news")
@AllArgsConstructor
public class RestNewsController {
    private final NewsServiceImp newsService;

    @GetMapping("/get-all")
    ResponseEntity<List<NewResponseDto>> getAll() {
        return ResponseEntity.ok().body(newsService.getAll());

    }


    @PostMapping(name = "create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<NewResponseDto> create(@RequestBody NewsRequestDto dto,
                                          @ModelAttribute MultipartFile[] files) {
        NewResponseDto responseDto = newsService.createNews(dto, files);
        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/{id}")
    ResponseEntity<NewResponseDto> update(@PathVariable(name = "id") Long id, @RequestBody NewsRequestDto dto) {
        NewResponseDto responseDto = newsService.updateNews(id, dto);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.ok().body("Da xoa id: " + id);
    }
}

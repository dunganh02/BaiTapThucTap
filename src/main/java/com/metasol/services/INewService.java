package com.metasol.services;

import com.metasol.dto.request.NewsRequestDto;
import com.metasol.dto.request.TypeRequestDto;
import com.metasol.dto.response.NewResponseDto;
import com.metasol.dto.response.TypeResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface INewService {
    NewResponseDto createNews(NewsRequestDto requestDto, MultipartFile[] file);
    List<NewResponseDto> getAll();
    NewResponseDto updateNews(Long id, NewsRequestDto requestDto);
    void deleteNews(Long id);
}

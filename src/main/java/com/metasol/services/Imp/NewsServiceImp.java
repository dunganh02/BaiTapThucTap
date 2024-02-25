package com.metasol.services.Imp;

import com.metasol.constant.ErrorCode;
import com.metasol.constant.MessageCode;
import com.metasol.dto.request.NewsRequestDto;
import com.metasol.dto.response.NewResponseDto;
import com.metasol.entity.NewsEntity;
import com.metasol.exception.EOException;
import com.metasol.repositories.INewsEntityRepository;
import com.metasol.services.INewService;
import com.metasol.services.mapper.NewsResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NewsServiceImp implements INewService {
    private final INewsEntityRepository newRepo;
    private final NewsResponseMapper newsResponseMapper;

    @Override
    public NewResponseDto createNews(NewsRequestDto requestDto, MultipartFile[] files) {
        NewsEntity entity = new NewsEntity();
        this.value(entity, requestDto);

        // Kiểm tra và xử lý từng file ảnh được gửi kèm
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                try {
                    // Lưu file ảnh và nhận đường dẫn đã được lưu
                    String imagePath = storeFile(file);

                    // Lưu thông tin đường dẫn của ảnh vào entity
                    entity.setImage(imagePath);  // Gán đường dẫn vào một danh sách ảnh trong entity (tùy thuộc vào thiết kế của bạn)
                } catch (IOException e) {
                    // Xử lý nếu có lỗi khi lưu file
                    e.printStackTrace();
                }
            }
        }
        // Cập nhật entity sau khi đã thêm thông tin ảnh
        newRepo.save(entity);
        // Chuyển đổi entity thành DTO để trả về cho client
        return newsResponseMapper.entityToResponse(entity);
    }

    @Override
    public List<NewResponseDto> getAll() {
        List<NewsEntity> entityList = newRepo.findAll();
        List<NewResponseDto> responseDtoList = new LinkedList<>();

        for (NewsEntity entity : entityList) {
            NewResponseDto responseDto = newsResponseMapper.entityToResponse(entity);
            responseDtoList.add(responseDto);

        }
        return responseDtoList;
    }

    @Override
    public NewResponseDto updateNews(Long id, NewsRequestDto requestDto) {
        NewsEntity existEntity = newRepo.findById(id)
                .orElseThrow(() -> new EOException(ErrorCode.ENTITY_NOT_FOUND, MessageCode.ENTITY_NOT_FOUND));

        this.value(existEntity, requestDto);
        newRepo.save(existEntity);
        return newsResponseMapper.entityToResponse(existEntity);
    }

    @Override
    public void deleteNews(Long id) {
                newRepo.deleteById(id);
    }

    private void value(NewsEntity entity, NewsRequestDto requestDto) {
        entity.setTitle(requestDto.getTitle());
        entity.setContent(requestDto.getContent());
//        entity.setImage(requestDto.getImage());
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Không đúng định dạng");
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // thêm UUID vào trực tiếp tên file để đảm bảo tên file là duy nhaats
        String uniqueFilename = UUID.randomUUID().toString() + "_" + fileName;
        // Đường dẫn đến thư mục m bạn muốn lưu
        Path uploatDir = Paths.get("uploads");
        // kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploatDir)) {
            Files.createDirectories(uploatDir);
        }
        // đường dẫn đầy đủ đến file
        Path destination = Paths.get(uploatDir.toString(), uniqueFilename);
        // sao chép file vào thư mục -- StandardCopyOption.REPLACE_EXISTING: nếu có th thay thé
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}

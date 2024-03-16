package com.metasol.services.mapper;

import com.metasol.dto.response.ProductImageResponseDto;
import com.metasol.entity.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductImageResponseMapper {
////    @Mapping(source = "imageUrl", target = "imageUrl", qualifiedByName = "mapImageUrlListToString")
//    ProductImageResponseDto entityToResponse(ProductImage entity);
//
////    @Named("mapImageUrlListToString")
//    default List<String> mapImageUrlListToString(List<String> imageUrl) {
//        // Thực hiện chuyển đổi từ List<String> sang List<String> nếu cần
//        // (Ví dụ: thêm logic kiểm tra, sửa đổi, ...)
//        return new ArrayList<>(imageUrl);
//    }
}

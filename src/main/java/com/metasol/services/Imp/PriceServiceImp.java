package com.metasol.services.Imp;

import com.metasol.constant.ErrorCode;
import com.metasol.constant.MessageCode;
import com.metasol.dto.request.PriceRequestDto;
import com.metasol.dto.response.PriceResponseDto;
import com.metasol.entity.PriceEntity;
import com.metasol.exception.EOException;
import com.metasol.repositories.IPriceRepository;
import com.metasol.services.IPriceService;
import com.metasol.services.mapper.PriceResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class PriceServiceImp implements IPriceService {
    private final IPriceRepository priceRepo;
    private final PriceResponseMapper responseMapper;

    @Override
    public PriceResponseDto createPrice(PriceRequestDto dto) {
        PriceEntity entity = new PriceEntity();
        this.value(entity, dto);
        priceRepo.save(entity);
        return responseMapper.entityToResponse(entity);
    }

    @Override
    public PriceResponseDto getPriceById(Long id) {
        return null;
    }

    @Override
    public List<PriceResponseDto> getAllPrice() {
        List<PriceEntity> entities = priceRepo.findAll();
        List<PriceResponseDto> responseDtoList = new LinkedList<>();
        for (PriceEntity entity : entities) {
            PriceResponseDto responseDto = responseMapper.entityToResponse(entity);
            responseDtoList.add(responseDto);

        }
        return responseDtoList;
    }

    @Override
    public PriceResponseDto updatePrice(Long id, PriceRequestDto dto) {

        PriceEntity entity = priceRepo.findById(id)
                .orElseThrow(() -> new EOException(ErrorCode.ENTITY_NOT_FOUND, MessageCode.ENTITY_NOT_FOUND));

        this.value(entity, dto);
        priceRepo.save(entity);
        return responseMapper.entityToResponse(entity);
    }

    @Override
    public void deletePrice(long id) {
        priceRepo.deleteById(id);

    }

    private void value(PriceEntity entity, PriceRequestDto dto) {
        entity.setType(dto.getType());
        entity.setProduct(dto.getProduct());
        entity.setPrice(dto.getPrice());

    }

}

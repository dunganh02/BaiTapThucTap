package com.metasol.services.Imp;

import com.metasol.constant.ErrorCode;
import com.metasol.constant.MessageCode;
import com.metasol.dto.request.CustomerRequestDto;
import com.metasol.dto.response.CustomerResponseDto;
import com.metasol.entity.CustomerEntity;
import com.metasol.exception.EOException;
import com.metasol.repositories.ICustomerRepository;
import com.metasol.services.ICustomerService;
import com.metasol.services.mapper.CustomerResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImp implements ICustomerService {

    private final ICustomerRepository customerRepo;
    private final CustomerResponseMapper customerResponseMapper;

    @Override
    public CustomerResponseDto getById(Long id) {

        CustomerEntity customerEntity = customerRepo.findById(id)
                .orElseThrow(() -> new EOException(ErrorCode.ENTITY_NOT_FOUND, MessageCode.ENTITY_NOT_FOUND));
        return customerResponseMapper.entityToResponse(customerEntity);
    }

    @Override
    public CustomerResponseDto create(CustomerRequestDto requestDto) {
        String phoneNumber = requestDto.getPhoneNumber();
        if (customerRepo.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("PHone number already exists");
        }

        CustomerEntity entity = new CustomerEntity();
        this.value(entity, requestDto);
        customerRepo.save(entity);
        return customerResponseMapper.entityToResponse(entity);
    }

    @Override
    public CustomerResponseDto update(Long id, CustomerRequestDto requestDto) {
        CustomerEntity entity = customerRepo.findById(id)
                .orElseThrow(() -> new EOException(ErrorCode.ENTITY_NOT_FOUND, MessageCode.ENTITY_NOT_FOUND));
        this.value(entity, requestDto);
        customerRepo.save(entity);
        return customerResponseMapper.entityToResponse(entity);
    }

    @Override
    public void delete(Long id) {
        CustomerEntity entity = customerRepo.findById(id)
                .orElseThrow(() -> new EOException(ErrorCode.ENTITY_NOT_FOUND, MessageCode.ENTITY_NOT_FOUND));
        entity.setActive(false);
        customerRepo.save(entity);
    }

    @Override
    public Page<CustomerResponseDto> getAll(PageRequest pageRequest) {
        if (pageRequest == null) {
            // Xử lý trường hợp pageRequest là null (tùy thuộc vào yêu cầu cụ thể của bạn)
            return Page.empty();
        }
        Page<CustomerEntity> listCustomer = customerRepo.findAll(pageRequest);
        /**
         Page<CustomerResponseDto> pageCustomer = listCustomer.map(this::convertToResponseDto);

         return pageCustomer;
         */

        return listCustomer.map(this::convertToResponseDto);
    }

    @Override
    public List<CustomerResponseDto> getAll2() {
        List<CustomerEntity> entityList = customerRepo.findAll();
        List<CustomerResponseDto> responseDtoList = new LinkedList<>();

        for (CustomerEntity entity : entityList) {
            CustomerResponseDto responseDto = customerResponseMapper.entityToResponse(entity);
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    /**
     * @param entity
     * @param dto    :
     *               Set value từ dto -> entity
     */
    private void value(CustomerEntity entity, CustomerRequestDto dto) {
        entity.setName(dto.getName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setActive(dto.isActive());
        entity.setType(dto.getTypeId());
    }

    /**
     * @param entity
     * @return : trả ra dto
     * Thực hiện chuyển đổi dữ liệu từ CustomerEntity sang CustomerResponseDto
     */
    private CustomerResponseDto convertToResponseDto(CustomerEntity entity) {

        CustomerResponseDto dto = new CustomerResponseDto();
        dto.setName(entity.getName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setActive(entity.isActive());
        dto.setType(entity.getType());

        return dto;
    }
}

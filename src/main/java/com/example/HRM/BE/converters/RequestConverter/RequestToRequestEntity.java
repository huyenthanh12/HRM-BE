package com.example.HRM.BE.converters.RequestConverter;

import com.example.HRM.BE.DTO.Request;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.converters.RequestTypeConverter.RequestTypeToRequestTypeEntity;
import com.example.HRM.BE.entities.RequestEntity;
import com.example.HRM.BE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestToRequestEntity extends Converter<Request, RequestEntity> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RequestTypeToRequestTypeEntity requestTypeToRequestTypeEntity;

    @Override
    public RequestEntity convert(Request source) {

        RequestEntity requestEntity = new RequestEntity();

        requestEntity.setId(source.getId());
        requestEntity.setAddress(source.getAddress());
        requestEntity.setCreateAt(source.getCreateAt());
        requestEntity.setDateRequest(source.getDayRequest());
        requestEntity.setReason(source.getReason());
        requestEntity.setRequestTypeEntity(requestTypeToRequestTypeEntity.convert(source.getRequestType()));
        requestEntity.setStatus(source.getStatus());
        requestEntity.setUserEntity(userRepository.findById(source.getUser().getId()).get());

        return requestEntity;
    }
}

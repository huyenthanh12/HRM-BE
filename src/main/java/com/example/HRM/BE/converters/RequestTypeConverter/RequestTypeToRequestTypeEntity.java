package com.example.HRM.BE.converters.RequestTypeConverter;

import com.example.HRM.BE.DTO.RequestType;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.RequestTypeEntity;
import com.example.HRM.BE.repositories.RequestTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestTypeToRequestTypeEntity extends Converter<RequestType, RequestTypeEntity> {

    @Autowired
    private RequestTypeRepository requestTypeRepository;

    @Override
    public RequestTypeEntity convert(RequestType requestType) {

        if (requestType.getName() == null || requestType.getName().equals(""))
            return requestTypeRepository.findById(requestType.getId()).get();
        else {
            RequestTypeEntity requestTypeEntity = new RequestTypeEntity();

            requestTypeEntity.setId(requestType.getId());
            requestTypeEntity.setName(requestType.getName());

            return requestTypeEntity;
        }
    }
}

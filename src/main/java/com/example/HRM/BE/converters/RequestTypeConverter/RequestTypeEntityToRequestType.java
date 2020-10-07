package com.example.HRM.BE.converters.RequestTypeConverter;

import com.example.HRM.BE.DTO.RequestType;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.RequestTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class RequestTypeEntityToRequestType extends Converter<RequestTypeEntity, RequestType> {

    @Override
    public RequestType convert(RequestTypeEntity requestTypeEntity) {

        RequestType requestType = new RequestType();

        requestType.setId(requestTypeEntity.getId());
        requestType.setName(requestTypeEntity.getName());

        return requestType;
    }
}

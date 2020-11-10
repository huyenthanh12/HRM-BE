package com.example.HRM.BE.converters.RequestConverter;

import com.example.HRM.BE.DTO.Request;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.converters.ProfileConverter.UserEntityToProfile;
import com.example.HRM.BE.converters.RequestTypeConverter.RequestTypeEntityToRequestType;
import com.example.HRM.BE.entities.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestEntityToRequest extends Converter<RequestEntity, Request> {

    @Autowired
    private UserEntityToProfile userEntityToProfile;

    @Autowired
    private RequestTypeEntityToRequestType requestTypeEntityToRequestType;


    @Override
    public Request convert(RequestEntity source) {

        Request request = new Request();

        request.setId(source.getId());
        request.setAddress(source.getAddress());
        request.setCreateAt(source.getCreateAt());
        request.setDayRequest(source.getDateRequest());
        request.setReason(source.getReason());
        request.setRequestType(requestTypeEntityToRequestType.convert(source.getRequestTypeEntity()));
        request.setStatus(source.getStatus());
        request.setUser(userEntityToProfile.convert(source.getUserEntity()));

        return request;

    }
}

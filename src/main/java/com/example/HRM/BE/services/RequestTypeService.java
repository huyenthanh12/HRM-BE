package com.example.HRM.BE.services;

import com.example.HRM.BE.DTO.RequestType;
import com.example.HRM.BE.converters.RequestTypeConverter.RequestTypeEntityToRequestType;
import com.example.HRM.BE.converters.RequestTypeConverter.RequestTypeToRequestTypeEntity;
import com.example.HRM.BE.entities.RequestTypeEntity;
import com.example.HRM.BE.exceptions.RequestTypeException.RequestTypeHasExisted;
import com.example.HRM.BE.exceptions.RequestTypeException.RequestTypeNotFound;
import com.example.HRM.BE.repositories.RequestTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RequestTypeService {

    @Autowired
    private RequestTypeRepository requestTypeRepository;

    @Autowired
    private RequestTypeToRequestTypeEntity requestTypeToRequestTypeEntity;

    @Autowired
    private RequestTypeEntityToRequestType requestTypeEntityToRequestType;

    public List<RequestType> getAllTypesOfRequest() {

        List<RequestType> requestTypeList = new ArrayList<>();
        List<RequestTypeEntity> requestTypeEntities = requestTypeRepository.findByOrderByNameAsc();

        for (RequestTypeEntity requestTypeEntity : requestTypeEntities) {
            requestTypeList.add(requestTypeEntityToRequestType.convert(requestTypeEntity));
        }
        return requestTypeList;
    }

    public RequestType getRequestTypeFollowID(int id) {

        if (!requestTypeRepository.findById(id).isPresent()) {
            throw new RequestTypeNotFound();
        } else {
            return requestTypeEntityToRequestType.convert(requestTypeRepository.findById(id).get());
        }
    }

    public List<RequestType> getRequestTypeFollowName(String key) {

        List<RequestType> requestTypeList = new ArrayList<>();

        for (RequestTypeEntity requestTypeEntity : requestTypeRepository.findByKeyword(key)) {
            requestTypeList.add(requestTypeEntityToRequestType.convert(requestTypeEntity));
        }
        return requestTypeList;

    }

    public RequestType addRequestType(String name) {

        if (requestTypeRepository.findByName(name).isPresent()) {
            throw new RequestTypeHasExisted();
        } else {
            return requestTypeEntityToRequestType.convert(requestTypeRepository.save(new RequestTypeEntity(name)));
        }
    }
}

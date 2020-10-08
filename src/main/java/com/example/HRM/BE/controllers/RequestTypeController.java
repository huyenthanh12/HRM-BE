package com.example.HRM.BE.controllers;

import com.example.HRM.BE.DTO.RequestType;
import com.example.HRM.BE.services.RequestTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/request-types")
public class RequestTypeController {

    @Autowired
    private RequestTypeService requestTypeService;

    @GetMapping
    public List<RequestType> getAllTypesOfRequest() {
        return requestTypeService.getAllTypesOfRequest();
    }

    @GetMapping("/{id}")
    public RequestType getRequestTypeFollowID(@PathVariable int id) {
        return requestTypeService.getRequestTypeFollowID(id);
    }

    @GetMapping("/search")
    public List<RequestType> getRequestTypeFollowName(@RequestParam(name = "key") String key) {
        return requestTypeService.getRequestTypeFollowName(key);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public RequestType addNewRequestType(@RequestBody RequestType requestType) {
        return requestTypeService.addRequestType(requestType.getName());
    }
}

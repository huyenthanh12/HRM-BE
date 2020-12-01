package com.example.HRM.BE.controllers;

import com.example.HRM.BE.DTO.Request;
import com.example.HRM.BE.entities.RequestEntity;
import com.example.HRM.BE.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping
    public List<Request> getAllRequests() {
        return requestService.getAllRequests();
    }

    @GetMapping("/me")
    public List<Request> getMyRequests() {
        return requestService.getMyRequests();
    }

    @PostMapping("/email/{emailUser}")
    public Request addNewRequest(@RequestBody @Validated Request request, @PathVariable("emailUser") String emailUser) {
        return requestService.addNewRequestFollowEmailUser(request, emailUser);
    }

    @GetMapping("/search")
    public List<Request> searchRequestsFollowKeyword(@RequestParam(name = "key") String key) {
        return requestService.searchRequestsFollowKeyword(key);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    public Request editRequest(@RequestBody @Valid Request request,
                               @PathParam("id") int id) {
        request.setId(id);
        return requestService.editRequest(request);
    }

    @DeleteMapping("/{id}")
    public void deleteRequest(@PathVariable int id) {
        requestService.deleteRequest(id);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/approve/{id}/{keyAdmin}")
    public Request approvedRequest(@PathVariable int id, @PathVariable String keyAdmin){
         return requestService.approvedOrRejectRequest(id, keyAdmin, "APPROVED");
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("reject/{id}/{keyAdmin}")
    public Request rejectRequest(@PathVariable int id, @PathVariable String keyAdmin){
        return requestService.approvedOrRejectRequest(id, keyAdmin, "REJECTED");
    }
}

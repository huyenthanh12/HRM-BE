package com.example.HRM.BE.services;

import com.example.HRM.BE.DTO.Request;
import com.example.HRM.BE.common.Email;
import com.example.HRM.BE.controllers.EmailController;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.converters.RequestConverter.RequestEntityToRequest;
import com.example.HRM.BE.converters.RequestConverter.RequestToRequestEntity;
import com.example.HRM.BE.converters.RequestTypeConverter.RequestTypeToRequestTypeEntity;
import com.example.HRM.BE.entities.RequestEntity;
import com.example.HRM.BE.entities.RoleEntity;
import com.example.HRM.BE.entities.UserEntity;
import com.example.HRM.BE.exceptions.RequestException.RequestNotFound;
import com.example.HRM.BE.repositories.RequestRepository;
import com.example.HRM.BE.repositories.RequestTypeRepository;
import com.example.HRM.BE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.HRM.BE.common.Constants.PENDING;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestTypeRepository requestTypeRepository;

    @Autowired
    private RequestEntityToRequest requestEntityToRequest;

    @Autowired
    private RequestToRequestEntity requestToRequestEntity;

    @Autowired
    private RequestTypeToRequestTypeEntity requestTypeToRequestTypeEntity;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileService profileService;

    @Value("#{'${emailAdmins}'.split(',')}")
    private List<String> emailAdmins;

    @Autowired
    private EmailController emailController;

    public List<Request> getAllRequests() {
        return requestEntityToRequest.convert(requestRepository.findAll());
    }

    public Request addNewRequest(Request request) {

        request.setCreateAt(new Date());
        request.setDayRequest(new Date());
        request.setStatus(PENDING);

        RequestEntity requestEntity = requestToRequestEntity.convert(request);
        requestRepository.save(requestEntity);

        sendRequestToAdmin(requestEntity.getUserEntity().getEmail(), requestEntity);

        return requestEntityToRequest.convert(requestEntity);
    }

    public void sendRequestToAdmin(String emailUserRequest, RequestEntity requestEntity) {
        for(String emailAdmin : emailAdmins) {
            String html =
                    "<table>\n" +
                            "    <col width=\"350\">\n" +
                            "    <col width=\"350\">\n" +
                            "    <col width=\"500\">\n" +
                            "    <col width=\"450\">\n" +
                            "    <tr>\n" +
                            "        <th><span style=\"float: left;\">Request by email</span></th>\n" +
                            "        <th><span style=\"float: left;\">Request type</span></th>\n" +
                            "        <th><span style=\"float: left;\">Day request</span</th>\n" +
                            "        <th><span style=\"float: left;\">Description</span></th>\n" +
                            "    </tr>\n" +
                            "    <tr>\n" +
                            "        <td><span>" + requestEntity.getUserEntity().getEmail()+"</span></td>\n" +
                            "        <td><span>"+requestEntity.getRequestTypeEntity().getName()+"</span></td>\n" +
                            "        <td><span>"+requestEntity.getDateRequest()+"</span></td>\n" +
                            "        <td><span>"+requestEntity.getReason()+"</span></td>\n" +
                            "    </tr>\n" +
                            "</table>";
//                            "<form method=\"post\" action=\"https://helpdesk-kunlez-novahub.herokuapp.com/api/requests/approveRequest/" + requestEntity.getId() + "/" + tokenProvider.genTokenAdmin(emailAdmin) + "\">" +
//                            "   <button type=\"submit\">APPROVE</button>" +
//                            "</form>"+
//                            "<form method=\"post\" action=\"https://helpdesk-kunlez-novahub.herokuapp.com/api/requests/rejectRequest/" + requestEntity.getId() + "/" + tokenProvider.genTokenAdmin(emailAdmin) + "\">" +
//                            "   <button type=\"submit\">REJECT</button>" +
//                            "</form>";
            Email email = new Email();
            List<String> emails = new ArrayList<>();
            emails.add(emailAdmin);
            email.setSendToEmail(emails);
            email.setSubject(emailUserRequest);
            email.setText(html);
            emailController.sendEmail(email);
        }
    }

    public List<Request> getMyRequests() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(authentication.getName());

        return requestEntityToRequest.convert(requestRepository.findByUserEntityId(userEntityOptional.get().getId()));
    }

    public List<Request> searchRequestsFollowKeyword(String key) {

        List<Request> requestEntityList = new ArrayList<>();

        for (RequestEntity requestEntity : requestRepository.findAllRequestsByKeyword(key)) {
            requestEntityList.add(requestEntityToRequest.convert(requestEntity));
        }
        return requestEntityList;
    }

    public Request editRequest(Request request) {

        Optional<RequestEntity> requestEntityOptional = requestRepository.findById(request.getId());

        if (!requestEntityOptional.isPresent()) {
            throw new RequestNotFound();
        } else {
            RequestEntity requestEntity = requestEntityOptional.get();

            requestEntity.setStatus(request.getStatus());
            requestEntity.setReason(request.getReason());
            requestEntity.setRequestTypeEntity(requestTypeToRequestTypeEntity.convert(request.getRequestType()));
            requestEntity.setDateRequest(new Date());
            requestEntity.setAddress(request.getAddress());

            requestRepository.save(requestEntity);
            return requestEntityToRequest.convert(requestEntity);
        }
    }

    public void deleteRequest(int id) {

        Optional<RequestEntity> requestEntityOptional = requestRepository.findById(id);

        if (!requestEntityOptional.isPresent()) {
            throw new RequestNotFound();
        } else {
            requestRepository.deleteById(id);
        }
    }
}

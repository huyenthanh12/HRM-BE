package com.example.HRM.BE.services;

import com.example.HRM.BE.DTO.Profile;
import com.example.HRM.BE.DTO.Request;
import com.example.HRM.BE.DTO.User;
import com.example.HRM.BE.common.Email;
import com.example.HRM.BE.configurations.TokenProvider;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.HRM.BE.common.Constants.APPROVED;
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
    private TokenProvider tokenProvider;

    @Autowired
    private EmailController emailController;

    public List<Request> getAllRequests() {
        return requestEntityToRequest.convert(requestRepository.findAll());
    }

    public Request addNewRequestFollowEmailUser(Request request, String email) {

        request.setCreateAt(new Date());
        request.setDayRequest(new Date());
        request.setStatus(PENDING);
        request.setUser(Profile.builder().id(
                this.userRepository.findByEmail(email).orElseThrow(
                        () -> new UsernameNotFoundException(email)
                ).getId()
        ).build());

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

    public Request approvedOrRejectRequest(int id, String keyAdmin, String status) {

        UserEntity userEntity = userRepository.findByEmail(tokenProvider.decodeJWTAdmin(keyAdmin).get("sub").toString()).get();

        boolean isAdmin = false;

        for(RoleEntity roleEntity : userEntity.getRoleEntities()){
            if(roleEntity.getName().equals("ROLE_ADMIN")){
                isAdmin = true;
                break;
            }
        }
        RequestEntity requestEntity = requestRepository.findById(id).get();

        String html = status + " BY ADMIN: " + userEntity.getEmail() +
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

        if(isAdmin) {
            if(requestEntity.getStatus().equals("PENDING")) {

                requestEntity.setStatus(status);
                requestEntity = requestRepository.save(requestEntity);

                Email email = new Email();

                List<String> sendToEmail = new ArrayList<>();
                sendToEmail.add(requestEntity.getUserEntity().getEmail());
                sendToEmail.addAll(emailAdmins);

                email.setSendToEmail(sendToEmail);
                email.setSubject("[" + status + " Request]");
                email.setText(html);
                emailController.sendEmail(email);

            }else{
                Email email = new Email();

                List<String> emails = new ArrayList<>();
                emails.add(userEntity.getEmail());
                email.setSendToEmail(emails);
                email.setSubject("["+status + " request of "+ requestEntity.getUserEntity().getEmail() +" FAILED]");
                email.setText(html + "Request was not PENDING, please click <a href=\"https://localhost:8080/admin/requests\">here</a> to edit this request!!!"+
                        "This request is: " +
                        "<table>\n" +
                        "    <col width=\"350\">\n" +
                        "    <col width=\"350\">\n" +
                        "    <col width=\"500\">\n" +
                        "    <col width=\"450\">\n" +
                        "    <tr>\n" +
                        "        <th><span style=\"float: left;\">Request by email</span></th>\n" +
                        "        <th><span style=\"float: left;\">Request type</span></th>\n" +
                        "        <th><span style=\"float: left;\">Day request</span</th>\n" +
                        "        <th><span style=\"float: left;\">Reason</span></th>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><span>" + requestEntity.getUserEntity().getEmail()+"</span></td>\n" +
                        "        <td><span>"+requestEntity.getRequestTypeEntity().getName()+"</span></td>\n" +
                        "        <td><span>"+requestEntity.getDateRequest()+"</span></td>\n" +
                        "        <td><span>"+requestEntity.getReason()+"</span></td>\n" +
                        "    </tr>\n" +
                        "</table>");
                emailController.sendEmail(email);

            }
        }
        return requestEntityToRequest.convert(requestEntity);
    }
}

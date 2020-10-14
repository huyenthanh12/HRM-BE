package com.example.HRM.BE.configurations;

import com.example.HRM.BE.entities.RoleEntity;
import com.example.HRM.BE.entities.UserEntity;
import com.example.HRM.BE.repositories.RoleRepository;
import com.example.HRM.BE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@Configuration
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private void addRoleIfMissing(String name){
        if(!roleRepository.findByName(name).isPresent()){
            roleRepository.save(new RoleEntity(name));
        }
    }

    private void addUserIfMissing(String username, String password, String ...roles){
        if(!userRepository.findByEmail(username).isPresent()){
            UserEntity user = new UserEntity(username,new BCryptPasswordEncoder().encode(password),"firstName","lastName");
            user.setRoleEntities(new HashSet<>());

            for (String role: roles) {
                user.getRoleEntities().add(roleRepository.findByName(role).get());
            }

            userRepository.save(user);
        }
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        addRoleIfMissing("ROLE_ADMIN");
        addRoleIfMissing("ROLE_MEMBER");

        addUserIfMissing("cctrang123@gmail.com", "abc123", "ROLE_ADMIN", "ROLE_MEMBER");

    }
}

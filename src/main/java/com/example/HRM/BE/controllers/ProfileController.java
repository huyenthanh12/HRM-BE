package com.example.HRM.BE.controllers;

import com.example.HRM.BE.DTO.Profile;
import com.example.HRM.BE.DTO.Skill;
import com.example.HRM.BE.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @Secured("ROLE_MEMBER")
    @GetMapping
    public Profile getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return profileService.getProfile(authentication.getName());
    }

    @Secured("ROLE_MEMBER")
    @PutMapping
    public void editProfile(@RequestBody Profile profile) {
        profileService.editProfile(profile);
    }

    @Secured("ROLE_MEMBER")
    @PutMapping("/avatars/{id}")
    public void editAvatar(@RequestParam MultipartFile avatarBase, @PathVariable("id") int id) throws IOException {
        if (avatarBase.isEmpty()) {
            throw new FileNotFoundException();
        }
        profileService.uploadAvatar(avatarBase.getBytes(), id);
    }

    @GetMapping("/search")
    public List<Profile> searchAllProfileByKeyword(String keyword) {
        return profileService.searchAllUserFollowKeyword(keyword);
    }

}

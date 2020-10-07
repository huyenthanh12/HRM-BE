package com.example.HRM.BE.controllers;

import com.example.HRM.BE.DTO.Skill;
import com.example.HRM.BE.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired
    SkillService skillService;

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }

    @GetMapping("/{id}")
    public Skill getSkillFollowID(@PathVariable("id") int id) {
        return skillService.getSkillFollowID(id);
    }

    @GetMapping("/user")
    public List<Skill> getSkillsFollowUser(@RequestParam int id) {
        return skillService.getSkillsFollowUser(id);
    }

    @GetMapping("/category")
    public List<Skill> getSkillsFollowCategory(@RequestParam int id) {
        return skillService.getSkillsFollowCategory(id);
    }

    @PostMapping
    public void addNewSkill(@RequestBody @Validated Skill skill) {
        skillService.addNewSkill(skill);
    }

    @PutMapping
    public void editSkill(@RequestBody @Validated Skill skill,
                          @PathParam("id") int id) {
        skill.setId(id);
        skillService.editSkill(id, skill);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void deleteSkill(@PathVariable int id) {
        skillService.deleteSkill(id);
    }
}

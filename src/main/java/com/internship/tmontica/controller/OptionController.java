package com.internship.tmontica.controller;

import com.internship.tmontica.dto.Option;
import com.internship.tmontica.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/options")
public class OptionController {
    @Autowired
    private OptionService optionService;

    @GetMapping("/{optionId}")
    public Option getOption(@PathVariable("optionId")int id){
        Option option = optionService.getOptionById(id);
        return option;
    }

    @GetMapping
    public List<Option> getOptions(){
        return optionService.getAllOptions();
    }

    @PostMapping
    public void addOption(@RequestBody @Valid Option option){
        optionService.addOption(option);
    }

    @PutMapping
    public void updateOption(@RequestBody @Valid Option option){
        optionService.updateOption(option);
    }

    @DeleteMapping("/{optionId}")
    public void deleteOption(@PathVariable("optionId")int id){
        optionService.deleteOption(id);
    }

}

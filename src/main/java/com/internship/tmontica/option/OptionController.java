package com.internship.tmontica.option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/options")
public class OptionController {
    @Autowired
    private OptionService optionService;

    @GetMapping("/{optionId}")
    public ResponseEntity<Option> getOption(@PathVariable("optionId")int id){
        Option option = optionService.getOptionById(id);
        return new ResponseEntity<>(option, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Option>> getOptions(){
        return new ResponseEntity<>(optionService.getAllOptions(), HttpStatus.OK);
    }

}

package com.internship.tmontica.option;

import com.internship.tmontica.option.Option;
import com.internship.tmontica.option.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

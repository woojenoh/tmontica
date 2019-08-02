package com.internship.tmontica.point;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointController {

      private final PointService pointService;

      @PostMapping
      public ResponseEntity<String> test(@RequestBody Point point){
          pointService.updateUserPoint(point);
          return new ResponseEntity<>("dd", HttpStatus.OK);
      }
}

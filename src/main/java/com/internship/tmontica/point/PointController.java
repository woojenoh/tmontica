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

      @GetMapping
      public ResponseEntity<Integer> getUserPoint(){

          return new ResponseEntity<>(pointService.getUserPoint(), HttpStatus.OK);
      }
}

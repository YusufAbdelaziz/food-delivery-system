package com.joe.abdelaziz.food_delivery_system.region;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/regions")
@AllArgsConstructor
public class RegionController {

  private final RegionService regionService;

  @PostMapping("")
  public ResponseEntity<Region> addRegion(@RequestBody Region region) {
    Region insertedRegion = regionService.insertRegion(region);
    return ResponseEntity.ok().body(insertedRegion);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Region> getRegionById(@PathVariable Long id) {
    return ResponseEntity.ok().body(regionService.findRegionById(id));
  }

  @GetMapping("")
  public ResponseEntity<List<Region>> getAllRegions() {
    return ResponseEntity.ok().body(regionService.findAll());
  }

}

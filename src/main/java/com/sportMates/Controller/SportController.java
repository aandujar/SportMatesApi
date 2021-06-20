package com.sportMates.Controller;

import com.sportMates.Entities.FilterEvent;
import com.sportMates.Service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sport")
public class SportController {

    @Autowired
    SportService sportService;

    @GetMapping
    public Object getAllSports() {
        return sportService.getAllSports();
    }
}

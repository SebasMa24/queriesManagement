package com.quantumdev.integraservicios.queriesManagement.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantumdev.integraservicios.database.model.ReservedHardware;
import com.quantumdev.integraservicios.database.model.ReservedSpace;
import com.quantumdev.integraservicios.database.model.Space;
import com.quantumdev.integraservicios.database.model.StoredHardware;

import java.util.List;
import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/query")
public interface QueryController {

    @GetMapping("hardware")
    public List<StoredHardware> getAvaiableItems(
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam Short building,
            @RequestParam Instant start,
            @RequestParam Instant end,
            @RequestParam Boolean getReserved,
            @RequestParam Integer qSize,
            @RequestParam Integer qPage,
            @RequestParam String orderBy
        );

    @GetMapping("space")
    public List<Space> getAvaiableSpaces(
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam Integer capacity,
            @RequestParam Short building,
            @RequestParam Instant start,
            @RequestParam Instant end,
            @RequestParam Boolean getReserved,
            @RequestParam Integer qSize,
            @RequestParam Integer qPage,
            @RequestParam String orderBy
        );

    @GetMapping("hardwareHistory")
    public List<ReservedHardware> getItemHistory(
            @RequestParam String email,
            @RequestParam Integer qSize,
            @RequestParam Integer qPage,
            @RequestParam String orderBy
        );

    @GetMapping("spaceHistory")
    public List<ReservedSpace> getSpaceHistory(
            @RequestParam String email,
            @RequestParam Integer qSize,
            @RequestParam Integer qPage,
            @RequestParam String orderBy
        );

}

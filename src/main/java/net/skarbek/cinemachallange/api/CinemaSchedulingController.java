package net.skarbek.cinemachallange.api;


import net.skarbek.cinemachallange.api.model.AddSeansRequest;
import net.skarbek.cinemachallange.api.model.WeekScheduleView;
import net.skarbek.cinemachallange.domain.application.AddSeansToScheduleService;
import net.skarbek.cinemachallange.domain.application.FindScheduleService;
import net.skarbek.cinemachallange.domain.model.WeekOfYear;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static net.skarbek.cinemachallange.api.model.ActionResult.success;
import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/schedule")
public class CinemaSchedulingController {

    private final AddSeansToScheduleService addSeansToScheduleService;
    private final FindScheduleService findScheduleService;

    public CinemaSchedulingController(AddSeansToScheduleService addSeansToScheduleService, FindScheduleService findScheduleService) {
        this.addSeansToScheduleService = addSeansToScheduleService;
        this.findScheduleService = findScheduleService;
    }

    @PostMapping("/year/{year}/week/{week}/seans")
    private ResponseEntity<?> addSeansToSchedule(
            @PathVariable int year,
            @PathVariable int week,
            @RequestBody AddSeansRequest addSeansRequest
    ) {
        addSeansToScheduleService.addSeans(addSeansRequest, new WeekOfYear(year, week));
        return accepted().body(success());
    }

    @GetMapping("/year/{year}/week/{week}/seans")
    private ResponseEntity<WeekScheduleView> getSchedule(
            @PathVariable int year,
            @PathVariable int week
    ) {
        return ok(findScheduleService.getSchedule(new WeekOfYear(year, week)));
    }


}

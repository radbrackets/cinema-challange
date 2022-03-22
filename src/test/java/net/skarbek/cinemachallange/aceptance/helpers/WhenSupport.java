package net.skarbek.cinemachallange.aceptance.helpers;

import net.skarbek.cinemachallange.builder.AddSeansRequestBuilder;
import net.skarbek.cinemachallange.domain.application.AddSeansToScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WhenSupport {

    @Autowired
    AddSeansToScheduleService addSeansToScheduleService;

    public AddSeansRequestBuilder addSeansRequest() {
        return new AddSeansRequestBuilder((request, weekOfYear) -> addSeansToScheduleService.addSeans(request, weekOfYear));
    }
}

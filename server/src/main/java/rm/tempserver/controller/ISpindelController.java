package rm.tempserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rm.tempserver.json.ISpindelData;
import rm.tempserver.service.ISpindelService;

/**
 * Rest controller for receiving ISpindel readings
 */
@Validated
@RequestMapping(path = "/v1/ispindel")
@RestController
@Slf4j
public class ISpindelController {

    @Autowired
    private ISpindelService service;

    @PostMapping
    void receiveData(@RequestBody ISpindelData data) {
        log.trace("receiveData({})", data);
        this.service.readingReceived(data);
    }
}

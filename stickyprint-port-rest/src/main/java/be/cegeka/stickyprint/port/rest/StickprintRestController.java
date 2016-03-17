package be.cegeka.stickyprint.port.rest;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping(
        value = "/public",
        produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
public class StickprintRestController {

    @RequestMapping(value = "/print")
    public ResponseEntity<String> print(
            @RequestParam(value = "line1", required = true) String line1,
            @RequestParam(value = "line2", required = false) String line2,
            HttpServletResponse response) {


        System.out.println("received request for printing " + line1 + ", " + line2);

        return new ResponseEntity<>( HttpStatus.CREATED);
    }
}

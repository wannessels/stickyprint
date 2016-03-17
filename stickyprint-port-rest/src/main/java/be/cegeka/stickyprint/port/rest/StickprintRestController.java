package be.cegeka.stickyprint.port.rest;


import be.cegeka.stickyprint.core.api.PrintLine;
import be.cegeka.stickyprint.core.api.PrintTask;
import be.cegeka.stickyprint.core.api.PrintingApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(
        value = "/public",
        produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
public class StickprintRestController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private PrintingApplicationService printingApplicationService;

    @RequestMapping(value = "/print")
    public ResponseEntity<String> print(
            @RequestParam(value = "line1", required = true) String line1,
            @RequestParam(value = "line2", required = false) String line2) {

        PrintLine printLine1 = PrintLine.aPrintLine(line1);
        PrintLine printLine2 = null;
        if (StringUtils.isNotBlank(line2)) {
            printLine2 = PrintLine.aPrintLine(line2);
        }

        PrintTask printTask = PrintTask.aPrintTask(printLine1, printLine2);

        printingApplicationService.print(printTask);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}

package be.cegeka.stickyprint.webapp.admin;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @RequestMapping(value = "/admin/status", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
    public String status() {
        return "status:top";
    }


}

package be.cegeka.stickyprint.port.rest;


import be.cegeka.stickyprint.core.api.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@RestController
@Slf4j
@RequestMapping(
        value = "/public",
        produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
public class StickprintRestController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private PrintingApplicationService printingApplicationService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ImageRenderService imageRenderService;

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

    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    @SneakyThrows
    public ResponseEntity<InputStreamResource> preview(
            @RequestParam(name = "html", required = true) String htmlToPreviewAsStickyCard,
            @RequestParam(name="css") String css) {

        ImageRenderResult imageRenderResult = imageRenderService.renderImage(new HtmlSnippet(htmlToPreviewAsStickyCard,css));

        BufferedImage bufferedImage = imageRenderResult.getResult();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(is));

    }

    @RequestMapping(value = "/printpreview", method = RequestMethod.GET)
    @SneakyThrows
    public ResponseEntity<InputStreamResource> printpreview(
            @RequestParam(name = "html", required = true) String htmlToPreviewAsStickyCard) {

        ImageRenderResult imageRenderResult = printingApplicationService.print(new HtmlSnippet(htmlToPreviewAsStickyCard, ""));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}

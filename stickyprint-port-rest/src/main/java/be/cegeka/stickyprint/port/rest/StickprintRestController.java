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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Currency;

import static be.cegeka.stickyprint.core.api.PaperHeight.HEIGHT_80MM;

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

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(PaperWidth.class, new PaperWidthConverter());
        dataBinder.registerCustomEditor(PaperHeight.class, new PaperHeightConverter());
    }


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
            @RequestParam(name="css") String css,
            @RequestParam(name="height") PaperHeight paperHeight,
            @RequestParam(name="width") PaperWidth paperWidth) {

        ImageRenderResult imageRenderResult = imageRenderService.renderImage(new HtmlSnippet(htmlToPreviewAsStickyCard,css), paperHeight, paperWidth);

        BufferedImage bufferedImage = imageRenderResult.getResult();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(is));

    }

    @RequestMapping(value = "/previewstory", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE )
    @SneakyThrows
    public ResponseEntity<String> previewStory(
        @RequestBody StoryRequestData storyRequestData) {

        ImageRenderResult imageRenderResult = imageRenderService.renderImage(new HtmlSnippet(storyRequestData.getHtml(),storyRequestData.getCss()), storyRequestData.getPaperHeight(), storyRequestData.getPaperWidth());

        BufferedImage bufferedImage = imageRenderResult.getResult();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", os);

        String imgBase64 = new String(Base64.getEncoder().encode(os.toByteArray()));
        System.out.println(imgBase64);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(imgBase64);

        //return ResponseEntity.ok().body("bla");
    }

    @RequestMapping(value = "/printpreview", method = RequestMethod.POST)
    @SneakyThrows
    public ResponseEntity<InputStreamResource> printpreview(
            @RequestParam(name = "html", required = true) String htmlToPreviewAsStickyCard,
            @RequestParam(name="css") String css,
            @RequestParam(name="height") PaperHeight paperHeight,
            @RequestParam(name="width") PaperWidth paperWidth) {


        ImageRenderResult imageRenderResult = printingApplicationService.print(new HtmlSnippet(htmlToPreviewAsStickyCard, css),paperHeight, paperWidth);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}

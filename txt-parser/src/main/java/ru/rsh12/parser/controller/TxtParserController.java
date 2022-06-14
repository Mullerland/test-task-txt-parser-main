package ru.rsh12.parser.controller;
/*
 * Date: 08.02.2022
 * Time: 11:04 PM
 * */

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.rsh12.parser.service.TxtParserService;

@RestController
@RequestMapping("/api")
public class TxtParserController {

    private final static Logger log = LoggerFactory.getLogger(TxtParserController.class);

    private final TxtParserService txtParserService;

    @Autowired
    public TxtParserController(TxtParserService txtParserService) {
        this.txtParserService = txtParserService;
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<Map<String, Object>> uploadAndGetAsMap(
            @RequestParam("file") MultipartFile file) {

        log.debug("uploadAndGetAsMap: accepts the file and passes it to the service, "
                + "returns Map");

        return ResponseEntity.ok(txtParserService.saveAndReturnAsMap(file));
    }

    @PostMapping(value = "/upload/tree")
    public ResponseEntity<Map<String, Object>> uploadAndGetAsBinaryTree(
            @RequestParam("file") MultipartFile file) {

        log.debug("uploadAndGetAsBinaryTree: accepts the file and passes it to the service, "
                + "returns BinaryTree");

        return ResponseEntity.ok(txtParserService.saveAndReturnAsBinaryTree(file));
    }

    @GetMapping(value = "/find")
    public ResponseEntity<Map<String, Object> > findByFileNameAndGetAsMap(
            @RequestParam("name") String fileName) {

        log.debug("findByFileNameAndGetAsMap: returns data as a Map");

        Map<String, Object> byNameAsMap = txtParserService.findByNameAsMap(fileName);
        return ResponseEntity.ok(byNameAsMap);
    }

    @GetMapping(value = "/find/tree")
    public ResponseEntity<Map<String, Object>> findByFileNameAsBinaryTree(
            @RequestParam("name") String fileName) {

        log.debug("findByFileNameAsBinaryTree: returns data as a BinaryTree");

        return ResponseEntity.ok(txtParserService.findByNameAsBinaryTree(fileName));
    }


}

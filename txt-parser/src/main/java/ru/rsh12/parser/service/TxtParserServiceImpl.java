package ru.rsh12.parser.service;
/*
 * Date: 08.02.2022
 * Time: 11:07 PM
 * */

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.rsh12.parser.entity.ParsedTxt;
import ru.rsh12.parser.exception.DuplicateKeyException;
import ru.rsh12.parser.repository.ParsedTxtRepository;
import ru.rsh12.parser.util.BinaryTree;
import ru.rsh12.parser.util.UtilService;

@Service
public class TxtParserServiceImpl implements TxtParserService {

    private final static Logger log = LoggerFactory.getLogger(TxtParserServiceImpl.class);

    private final UtilService utilService;
    private final ParsedTxtRepository repository;

    @Autowired
    public TxtParserServiceImpl(UtilService utilService,
            ParsedTxtRepository repository) {
        this.utilService = utilService;
        this.repository = repository;
    }

    @Override
    public Map<String, Object> saveAndReturnAsMap(MultipartFile file) {
        log.debug("saveAndReturnAsMap: returns data as a Map");

        try {
            ParsedTxt parsedTxt = parseAndSave(file);
            return setResponseValues(findAggregatedText(getFileNameWithoutExt(file)),
                    parsedTxt.getStructuredText());

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    @Override
    public Map<String, Object> saveAndReturnAsBinaryTree(MultipartFile file) {
        log.debug("saveAndReturnAsBinaryTree: converts data to BinaryTree");

        try {
            ParsedTxt parsedTxt = parseAndSave(file);
            return setResponseValues(findAggregatedText(getFileNameWithoutExt(file)),
                    utilService.getAsTree(parsedTxt.getStructuredText()));

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    @Override
    public Map<String, Object> findByNameAsMap(String fileName) {
        log.debug("findByNameAsMap: searches for the processed text file and returns it as a Map");

        Map<Long, String> map = repository.findByFileName(fileName)
                .map(ParsedTxt::getStructuredText)
                .orElse(Collections.emptyMap());

        return setResponseValues(findAggregatedText(fileName), map);
    }

    @Override
    public Map<String, Object> findByNameAsBinaryTree(String fileName) {
        log.debug("findByNameAsBinaryTree: searches for the processed text file "
                + "and returns it as a BinaryTree");

        BinaryTree binaryTree = repository.findByFileName(fileName)
                .map(ParsedTxt::getStructuredText)
                .map(utilService::getAsTree)
                .orElse(new BinaryTree());

        return setResponseValues(findAggregatedText(fileName), binaryTree);
    }

    private String findAggregatedText(String fileName) {
        return repository.findAggreagtedText(fileName);
    }

    private ParsedTxt parseAndSave(MultipartFile file) throws IOException {
        log.debug("parseAndSave: processes the text file and saves it to the DB");

        ParsedTxt parsedTxt = utilService.parse(file);
        // CompletableFuture.runAsync(() -> repository.save(parsedTxt));
        try {
            // waiting for the completion
            return repository.save(parsedTxt);
        } catch (Exception e) {
            throw new DuplicateKeyException("A file with this name already exists",
                    HttpStatus.BAD_REQUEST);
        }
    }

    private Map<String, Object> setResponseValues(String raw, Object structered) {
        if (raw == null || structered == null) {
            return Collections.emptyMap();
        }

        return Map.of("raw", raw,
                "structrued", structered);
    }

    private String getFileNameWithoutExt(MultipartFile file) {
        return Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[0];
    }

}

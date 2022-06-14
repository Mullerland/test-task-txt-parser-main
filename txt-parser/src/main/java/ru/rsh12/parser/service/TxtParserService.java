package ru.rsh12.parser.service;

import java.util.Map;
import org.springframework.web.multipart.MultipartFile;
import ru.rsh12.parser.entity.ParsedTxt;

public interface TxtParserService {

    /**
     * Processes the text file, saves it to the DB.
     *
     * @param file the txt file
     * @return Map<Long, String>
     * @see ParsedTxt#getStructuredText()
     */
    Map<String, Object> saveAndReturnAsMap(MultipartFile file);

    /**
     * Processes the text file, saves it to the DB.
     *
     * @param file the txt file
     * @return an instance of class BinaryTree
     * @see ru.rsh12.parser.util.BinaryTree
     */
    Map<String, Object> saveAndReturnAsBinaryTree(MultipartFile file);

    /**
     * Searches for the processed text by file name in the DB.
     *
     * @param fileName the txt file name
     * @return the optional of Map<Long, String>
     */
    Map<String, Object> findByNameAsMap(String fileName);

    /**
     * Searches for the processed text by file name in the DB.
     *
     * @param fileName the txt file name
     * @return the optional of BinaryTree
     * @see ru.rsh12.parser.util.BinaryTree
     */
    Map<String, Object> findByNameAsBinaryTree(String fileName);
}

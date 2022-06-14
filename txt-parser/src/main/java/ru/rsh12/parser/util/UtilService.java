package ru.rsh12.parser.util;
/*
 * Date: 08.02.2022
 * Time: 5:13 PM
 * */

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.rsh12.parser.entity.ParsedTxt;

@Component
public class UtilService {

    private final static Logger log = LoggerFactory.getLogger(UtilService.class);


    // '#' - идентификатор главы (раздела)
    private static final Pattern SECTION = Pattern.compile("#");

    private static final String RAW_LINE_BREAKS = "(\\\\r\\\\n)|(\\\\r)|(\\\\n)";
    private static final String DELIMITER = "\n";

    private static final ObjectMapper mapper = new ObjectMapper();

    /*
     * Принимаем MultipartFile и конвертируем в File.
     * Заменяем raw спец. символы.
     * Группируем по кол-ву '#', собирая в список, элементы которого позже конкатенируем,
     * превращая в String
     * */
    public ParsedTxt parse(MultipartFile multipartFile) throws IOException {
        log.debug("parse: processes the text file and returns an instance of the ParsedTxt class");

        File file = convertToFile(multipartFile);
        String rawTxt = Files.lines(file.toPath())
                .map(line -> line.replaceAll(RAW_LINE_BREAKS, System.lineSeparator()))
                .collect(joining(DELIMITER));

        /*
         * Изначально считаем, что глава обязательно должна существовать,
         * потому инициализируем lastCount (кол-во '#') как 1
         * */
        AtomicLong lastCount = new AtomicLong(1);

        String[] array = rawTxt.split(System.lineSeparator());
        Map<Long, String> result = Arrays.stream(array)
                .collect(groupingBy(handleSections(lastCount),
                        collectingAndThen(joining(DELIMITER),
                                line -> line.replaceAll("#", ""))));

        return new ParsedTxt(multipartFile.getName(), result);
    }

    /*
     * Возвращаем данные в виде бинарного дерева
     * */
    public BinaryTree getAsTree(Map<Long, String> map) {
        log.debug("getAsTree: converts a Map to an instance of the BinaryTree class");

        BinaryTree tree = new BinaryTree();
        map.forEach(tree::add);

        return tree;
    }

    /*
     * Строчки без '#' интерпретируются как тело заголовка,
     * то есть мы данные строчки добавляем в предудыщую главу (строка с '#')
     * */
    private static Function<String, Long> handleSections(AtomicLong lastCount) {
        return line -> {
            long count = SECTION.matcher(line).results().count();
            if (count > 0) {
                lastCount.set(count);
                return count;
            }
            return lastCount.get();
        };
    }

    private File convertToFile(MultipartFile file) {
        log.debug("convertToFile: converts a MultipartFile to a File");

        File convertedFile = new File(
                System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try {
            file.transferTo(convertedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertedFile;
    }

}

package ru.rsh12.parser.repository;
/*
 * Date: 08.02.2022
 * Time: 11:29 PM
 * */

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.rsh12.parser.entity.ParsedTxt;

public interface ParsedTxtRepository extends CrudRepository<ParsedTxt, Long> {

    Optional<ParsedTxt> findByFileName(String fileName);

    @Query(value = """
            SELECT STRING_AGG(txt, ' ')
            FROM structered_txt
                     JOIN parsed_txt pt ON pt.id = structered_txt.parsed_txt_id
            WHERE file_name = ?1
            GROUP BY file_name;

            """, nativeQuery = true)
    String findAggreagtedText(String fileaNe);

}

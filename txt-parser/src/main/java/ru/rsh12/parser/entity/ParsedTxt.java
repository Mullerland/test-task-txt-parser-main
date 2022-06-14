package ru.rsh12.parser.entity;
/*
 * Date: 08.02.2022
 * Time: 11:22 PM
 * */

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.validation.constraints.NotNull;

/**
 * An entity for working with parsed text
 */
@Entity
public class ParsedTxt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String fileName;

    @ElementCollection
    @MapKeyColumn(columnDefinition = "TEXT", name = "section")
    @Column(name = "txt", columnDefinition = "TEXT")
    @CollectionTable(name = "structered_txt", joinColumns = @JoinColumn(name = "parsed_txt_id"))
    private Map<Long, String> structuredText = new HashMap<>();

    public ParsedTxt() {
    }

    public ParsedTxt(String fileName,
            Map<Long, String> structuredText) {
        this.fileName = fileName;
        this.structuredText = structuredText;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Map<Long, String> getStructuredText() {
        return structuredText;
    }

    public void setStructuredText(Map<Long, String> structuredText) {
        this.structuredText = structuredText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParsedTxt parsedTxt = (ParsedTxt) o;

        if (!Objects.equals(id, parsedTxt.id)) {
            return false;
        }
        return fileName.equals(parsedTxt.fileName);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + fileName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ParsedTxt{" +
                "fileName='" + fileName + '\'' +
                '}';
    }

}

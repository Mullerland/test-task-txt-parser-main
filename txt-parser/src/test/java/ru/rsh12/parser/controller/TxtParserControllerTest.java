package ru.rsh12.parser.controller;
/*
 * Date: 08.02.2022
 * Time: 11:39 PM
 * */

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.rsh12.parser.service.TxtParserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TxtParserController.class)
public class TxtParserControllerTest {

    private final String API_UPLOAD = "/api/upload/";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TxtParserService service;

    @Test
    public void testFileMaxSize() throws Exception {
        byte[] size = new byte[1024 * 1024 * 100];
        MockMultipartFile file = new MockMultipartFile("file", "sample.txt",
                MediaType.TEXT_PLAIN_VALUE, size);

        given(service.saveAndReturnAsMap(file)).willReturn(Collections.emptyMap());

        mvc.perform(multipart(API_UPLOAD)
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

}

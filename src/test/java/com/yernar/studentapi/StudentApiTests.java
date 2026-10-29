
package com.yernar.studentapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void infoShowsStudentName() throws Exception {
        mockMvc.perform(get("/api/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student").value("Yernar Temirkhan"));
    }

    @Test
    void unknownStudentReturns404() throws Exception {
        mockMvc.perform(get("/api/students/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void createStudentReturns201() throws Exception {
        String json = "{\"firstName\":\"Test\",\"lastName\":\"Temirkhan\","
                + "\"email\":\"junit.temirkhan@example.com\",\"groupName\":\"IT2-2314\"}";
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void searchByLastNameFindsStudentIgnoringCase() throws Exception {
        String json = "{\"firstName\":\"Search\",\"lastName\":\"Searchable\","
                + "\"email\":\"junit.search@example.com\",\"groupName\":\"IT2-2314\"}";
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/students/search").param("lastName", "searchable"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName").value("Searchable"));
    }
}


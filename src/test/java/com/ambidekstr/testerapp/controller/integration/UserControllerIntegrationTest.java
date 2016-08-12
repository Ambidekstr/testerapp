package com.ambidekstr.testerapp.controller.integration;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.ambidekstr.testerapp.TesterappApplication;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;


/**
 * Created by anatolii on 09.08.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesterappApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerIntegrationTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void addUserTest() throws Exception{
        String addUserUrl = "/user";
        String content = "{\n" +
                "  \"lastName\": \"Voloshyn\",\n" +
                "  \"firstName\": \"Anatolii\",\n" +
                "  \"email\": \"aavolosh@gmail.com\",\n" +
                "  \"password\": \"password\"\n" +
                "}";

        mockMvc.perform(post(addUserUrl).contentType(MediaType.APPLICATION_JSON).content(content.getBytes()))
                .andExpect(status().isOk())
                .andDo(document("adding-user"))
                .andReturn();

    }

    @Test
    public void bLoginUserTest() throws Exception{
        String loginUserUrl = "/user/login";
        mockMvc.perform(post(loginUserUrl).param("email","aavolosh@gmail.com").param("password","password"))
                .andExpect(status().isOk())
                .andDo(document("login",requestParameters(
                        parameterWithName("email").description("The user's email"),
                        parameterWithName("password").description("The user's password"))))
                .andReturn();
    }

    @Test
    public void cFindUserByEmailTest() throws Exception{
        mockMvc.perform(get("/user/aavolosh@gmail.com"))
                .andExpect(content().string("First name: Anatolii Last name: Voloshyn"))
                .andExpect(status().isOk())
                .andDo(document("find-user-by-email"))
                .andReturn();
    }

    @Test
    public void dFileUploadTest() throws Exception{
        String fileUploadUrl = "/user/550e8400-e29b-41d4-a716-446655440000/receipts";
        String originalFileName = "t375x200_ArticlePreviewImage_19549.png";
        File file = new File("E:\\Innovations\\"+originalFileName);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", originalFileName, "multipart/form-data", new FileInputStream(file));
        mockMvc.perform(fileUpload(fileUploadUrl)
                .file(mockMultipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andDo(document("file-upload", requestParts(
                        partWithName("file").description("File was successfully uploaded"))))
                .andReturn();

    }



}

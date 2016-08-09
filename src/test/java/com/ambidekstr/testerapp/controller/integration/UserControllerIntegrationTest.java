package com.ambidekstr.testerapp.controller.integration;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ambidekstr.testerapp.TesterappApplication;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anatolii on 09.08.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesterappApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Order(value = 1)
    public void atest() throws Exception{
        String addUserUrl = "/user";
        String content = "{\n" +
                "  \"lastName\": \"Voloshyn\",\n" +
                "  \"firstName\": \"Anatolii\",\n" +
                "  \"email\": \"aavolosh@gmail.com\",\n" +
                "  \"password\": \"password\"\n" +
                "}";

        mockMvc.perform(post(addUserUrl).contentType(MediaType.APPLICATION_JSON).content(content.getBytes()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

    }

    @Test
    public void bLoginUserTest() throws Exception{
        String loginUserUrl = "/user/login";
        mockMvc.perform(post(loginUserUrl).param("email","aavolosh@gmail.com").param("password","password"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void cFindUserByEmailTest() throws Exception{
        String findUserByEmailUrl = "/user/aavolosh@gmail.com/";
        mockMvc.perform(get(findUserByEmailUrl))
                .andExpect(content().string("First name: Anatolii Last name: Voloshyn"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void dFileUploadTest() throws Exception{
        String fileUploadUrl = "/user/0b7dffe9-41bb-43e7-8642-d7aedf5fd031/receipts";
        String pathName = "E:\\Ambidekstr\\t375x200_ArticlePreviewImage_19433.png";
        File file = new File(pathName);
        InputStream is = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file",is);
        mockMvc.perform(fileUpload(fileUploadUrl)
                .file(mockMultipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(content().string("File was successfully uploaded"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        is.close();
    }



}

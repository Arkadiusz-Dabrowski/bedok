//package com.startup.bedok.advertisement.controller;
//
//import com.startup.bedok.advertisment.controller.AdvertisementController;
//import com.startup.bedok.advertisment.model.AdvertisementDTO;
//import com.startup.bedok.advertisment.model.AdvertisementRequest;
//import com.startup.bedok.advertisment.model.PriceDTO;
//import com.startup.bedok.advertisment.model.PriceListDTO;
//import com.startup.bedok.host.model.HostResponse;
//import org.bson.types.Binary;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@WebMvcTest(AdvertisementController.class)
//public class AdvertisementControllerTest {
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void greetingShouldReturnDefaultMessage() throws Exception {
//        File file = new File("src/test/resources/OIP.jfif");
//        FileInputStream input = new FileInputStream(file);
//        MultipartFile photo = new MockMultipartFile("file", input);
//        PriceDTO price = new PriceDTO(1, 10, 100.0);
//        List<String> equipment = Arrays.asList("IRON");
//        UUID uuid = new UUID(1L, 1L);
//
//        AdvertisementRequest advertisementRequest = new AdvertisementRequest(uuid, "00-000",
//                "warszawska", "test", 25.0,
//                10, 0,
//                new PriceListDTO(Arrays.asList(price)), false, "Polski", equipment, equipment,
//                Arrays.asList("CACHE"), Arrays.asList("cisza nocna")
//        );
//
//        mockMvc.perform(post("/advertisement"));
//        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/advertisement",
//                advertisementRequest,
//                ResponseEntity.class));
//    }
//
//}

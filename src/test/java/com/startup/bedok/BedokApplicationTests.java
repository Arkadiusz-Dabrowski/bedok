//package com.startup.bedok;
//
//import com.startup.bedok.advertisment.model.request.AdvertisementRequest;
//import com.startup.bedok.advertisment.model.response.PriceDTO;
//import com.startup.bedok.advertisment.model.PriceListDTO;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class BedokApplicationTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void shouldReturnDefaultMessage() throws Exception {
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
//        this.mockMvc.perform(post("/advertisement").content(String.valueOf(advertisementRequest)))
//                .andDo(print()).andExpect(status().isOk());
//    }
//
//}

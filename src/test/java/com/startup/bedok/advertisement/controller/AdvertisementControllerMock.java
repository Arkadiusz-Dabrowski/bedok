//package com.startup.bedok.advertisement.controller;
//
//import com.startup.bedok.advertisment.controller.AdvertisementController;
//import com.startup.bedok.advertisment.model.request.AdvertisementRequest;
//import com.startup.bedok.advertisment.model.response.PriceDTO;
//import com.startup.bedok.advertisment.model.PriceListDTO;
//import com.startup.bedok.advertisment.services.AdvertisementService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AdvertisementControllerMock {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private final AdvertisementService advertisementService;
//
//    public AdvertisementControllerMock(MockMvc mockMvc, AdvertisementService advertisementService) {
//        this.mockMvc = mockMvc;
//        this.advertisementService = advertisementService;
//    }
//
//    @Test
//    public void greetingShouldReturnMessageFromService() throws Exception {
//
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
//        when(advertisementService.createAdvertisement(advertisementRequest)).thenReturn(UUID.randomUUID());
//        this.mockMvc.perform(get("/advertisement")).andDo(print()).andExpect(status().isOk());
//    }
//}

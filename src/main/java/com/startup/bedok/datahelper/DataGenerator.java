package com.startup.bedok.datahelper;

import com.github.javafaker.Faker;
import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.entity.Price;
import com.startup.bedok.advertisment.model.entity.RoomPhoto;
import com.startup.bedok.advertisment.model.enumerated.DistrictEnum;
import com.startup.bedok.advertisment.model.enumerated.GenderRoomEnum;
import com.startup.bedok.advertisment.repository.AdvertisementRepository;
import com.startup.bedok.advertisment.repository.PriceRepository;
import com.startup.bedok.advertisment.repository.RoomPhotosRepository;
import com.startup.bedok.advertisment.services.AdvertisementPhotoService;
import com.startup.bedok.host.model.Host;
import com.startup.bedok.host.repository.HostPhotoRepository;
import com.startup.bedok.host.repository.HostRepository;
import com.startup.bedok.host.service.HostPhotoService;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DataGenerator {

    private final HostRepository hostRepository;
    private final AdvertisementRepository advertisementRepository;
    private final HostPhotoRepository hostPhotoRepository;
    private final RoomPhotosRepository roomPhotosRepository;
    private final PriceRepository priceRepository;
    private final HostPhotoService hostPhotoService;

    private final AdvertisementPhotoService advertisementPhotoService;
    private Faker faker = new Faker();

    @Transactional
    public void createSomeHostData(){
        List<Host> hosts = IntStream.rangeClosed(1, 20)
                .mapToObj(i -> new Host(
                        faker.name().name(),
                        faker.internet().password(),
                        faker.internet().emailAddress(),
                        faker.phoneNumber().phoneNumber(),
                        null
                )).toList();
        hostRepository.saveAll(hosts);
    }

    public void createSomeHostPhotos() throws IOException {
        listFilesUsingFilesList("C:\\Users\\arkad\\IdeaProjects\\bedok\\src\\main\\resources\\host").stream().map(x -> {
            File file = new File(x);
            try {
                FileInputStream in = new FileInputStream(file);
                hostPhotoService.savePhoto(in.readAllBytes(), faker.rickAndMorty().character());
                return new Binary(in.readAllBytes());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public String createSomeAdvertisementPhotos() throws IOException {
        List<Advertisement> advertisementsWithoutPhoto =
                advertisementRepository.findAll().
                        stream().filter(x -> x.getRoomPhotos().isEmpty()).toList();
        Set<String>  files = listFilesUsingFilesList(("c:/users/arkad/IdeaProjects/bedok/src/main/resources/room"));
        return files.stream().map(x -> {
            File file = new File(x);
            try {
                FileInputStream in = new FileInputStream(file);
                System.out.println(file.getName());
                List<RoomPhoto> roomPhotos = advertisementPhotoService.saveAdvertisementPhotosFromBinary(Arrays.asList(in.readAllBytes()));
                advertisementsWithoutPhoto.forEach(y -> {
                    addPhotosToAdvertisement(roomPhotos ,y.getId());
                });
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }).collect(Collectors.toSet()).stream().findFirst().get().toString();
    }

    public Set<String> listFilesUsingFilesList(String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

    @Transactional
    public void createSomeAdvertisementData(){
        List<Advertisement> advertisements = IntStream.rangeClosed(1, 3)
                .mapToObj(i -> new Advertisement(
                        getHostUUID(),
                        faker.ancient().hero(),
                        faker.options().option(DistrictEnum.class),
                        faker.options().option(GenderRoomEnum.class),
                        Collections.nCopies(2, faker.rickAndMorty().character()),
                        faker.address().zipCode(),
                        faker.address().streetName(),
                        null,
                        faker.rickAndMorty().quote(),
                        faker.number().randomDouble(2,20,40),
                        faker.number().numberBetween(1,5),
                        faker.number().numberBetween(0,1),
                        createSomePrice(),
                        faker.bool().bool(),
                        faker.programmingLanguage().name(),
                        faker.bool().bool(),
                        faker.bool().bool(),
                        faker.bool().bool(),
                        faker.bool().bool(),
                        faker.bool().bool(),
                        faker.bool().bool(),
                        faker.bool().bool(),
                        faker.bool().bool(),
                        faker.bool().bool(),
                        faker.bool().bool(),
                        faker.bool().bool(),
                        faker.bool().bool(),
                        faker.leagueOfLegends().quote()
                )).toList();
        advertisementRepository.saveAll(advertisements);
    }

    private UUID getHostUUID(){
        return hostRepository.findAll().get(faker.number().numberBetween(0,5)).getId();
    }

    @Transactional
    public List<Price> createSomePrice(){
        List<Price> prices = List.of(new Price(
                        faker.number().numberBetween(0, 5),
                        faker.number().numberBetween(10, 20),
                        faker.number().randomDouble(3, 100, 200)
                ));
        return priceRepository.saveAll(prices);
    }
    private String getRandomHostPhoto(){
        return hostPhotoRepository.findAll().stream().findAny().get().getId();
    }
    private List<RoomPhoto> getRoomPhoto(){
        return new ArrayList<>(roomPhotosRepository.findAll()).subList(0, faker.number().numberBetween(1,3));
    }


    private String addPhotosToAdvertisement(List<RoomPhoto> roomPhotos, UUID advertisementId) {
        roomPhotosRepository.saveAll(roomPhotos);
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new RuntimeException(String.format("there is no Advertisement with uuid %s", advertisementId)));
        advertisement.getRoomPhotos().addAll(roomPhotos);
        advertisementRepository.save(advertisement);
        if (advertisementRepository.getById(advertisementId).getRoomPhotos().size() > 0)
            return " added";
        return "fail";
    }
}

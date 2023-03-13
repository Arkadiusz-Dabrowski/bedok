package com.startup.bedok.datahelper;

import com.github.javafaker.Faker;
import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.entity.AdvertisementPhoto;
import com.startup.bedok.advertisment.model.entity.RoomPhoto;
import com.startup.bedok.advertisment.model.enumerated.DistrictEnum;
import com.startup.bedok.advertisment.model.enumerated.GenderRoomEnum;
import com.startup.bedok.advertisment.repository.AdvertisementRepository;
import com.startup.bedok.advertisment.repository.RoomPhotosRepository;
import com.startup.bedok.advertisment.services.AdvertisementPhotoService;
import com.startup.bedok.user.entity.TypeOfUser;
import com.startup.bedok.user.model.ApplicationUser;
import com.startup.bedok.user.repository.UserPhotoRepository;
import com.startup.bedok.user.repository.UserRepository;
import com.startup.bedok.user.service.UserPhotoService;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;
    private final UserPhotoRepository userPhotoRepository;
    private final RoomPhotosRepository roomPhotosRepository;
    private final UserPhotoService userPhotoService;

    private final AdvertisementPhotoService advertisementPhotoService;
    private Faker faker = new Faker();

    @Transactional
    public void createSomeHostData(){
        List<ApplicationUser> users = IntStream.rangeClosed(1, 20)
                .mapToObj(i -> new ApplicationUser(
                        TypeOfUser.TENANT,
                        faker.name().name(),
                        faker.internet().password(),
                        faker.internet().emailAddress(),
                        faker.phoneNumber().phoneNumber(),
                        null
                )).toList();
        userRepository.saveAll(users);
    }

    public void createSomeHostPhotos() throws IOException {
        listFilesUsingFilesList("C:\\Users\\arkad\\IdeaProjects\\bedok\\src\\main\\resources\\host").stream().map(x -> {
            File file = new File(x);
            try {
                FileInputStream in = new FileInputStream(file);
                userPhotoService.savePhoto(in.readAllBytes(), faker.rickAndMorty().character());
                return new Binary(in.readAllBytes());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    @Transactional
    public List<RoomPhoto> createSomeAdvertisementPhotos() throws IOException {
        String directory = "./src/main/resources/room/";
        Set<String>  files = listFilesUsingFilesList(directory);
        List<Advertisement> advertisementWithPhoto = roomPhotosRepository.findAll().stream().map(RoomPhoto::getAdvertisement).toList();
        List<Advertisement> advertisementsWithoutPhoto =
                advertisementRepository.findAll().
                        stream().filter(x -> !advertisementWithPhoto.contains(x)).toList();
        List<AdvertisementPhoto> advertisementPhotos = files.stream().map(x ->  new File(directory + x))
                .toList()
                .stream()
                .map(file -> {
                    try {
                        return new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).map(in -> {
                    try {
                        return new AdvertisementPhoto(new Binary(BsonBinarySubType.BINARY, in.readAllBytes()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
        List<String> advertisementPhotosId = advertisementPhotos.stream().map(advertisementPhotoService::saveAdvertisementPhoto).toList();
        advertisementsWithoutPhoto.forEach(advertisement -> {
              advertisementPhotosId.forEach(id -> roomPhotosRepository.save(new RoomPhoto(id, advertisement)));
        });
        return roomPhotosRepository.findAll();
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
                        faker.address().city(),
                        faker.options().option(DistrictEnum.class),
                        faker.options().option(GenderRoomEnum.class),
                        Collections.nCopies(2, faker.rickAndMorty().character()),
                        faker.address().zipCode(),
                        faker.address().streetName(),
                        faker.rickAndMorty().quote(),
                        faker.number().randomDouble(2,20,40),
                        faker.number().randomDigit(),
                        faker.number().randomDigit(),
                        faker.number().randomDouble(2,50,80),
                        faker.number().randomDouble(2,0,5),
                        faker.number().randomDouble(2, 5, 8),
                        faker.number().numberBetween(8,10),
                        faker.number().numberBetween(10,15),
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
        return userRepository.findAll().get(faker.number().numberBetween(0,5)).getId();
    }

    private String getRandomHostPhoto(){
        return userPhotoRepository.findAll().stream().findAny().get().getId();
    }
    private List<RoomPhoto> getRoomPhoto(){
        return new ArrayList<>(roomPhotosRepository.findAll()).subList(0, faker.number().numberBetween(1,3));
    }
}

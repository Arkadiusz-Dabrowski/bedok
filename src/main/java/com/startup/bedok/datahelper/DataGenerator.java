package com.startup.bedok.datahelper;

import com.github.javafaker.Faker;
import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.enumerated.RoomGender;
import com.startup.bedok.advertisment.repository.AdvertisementRepository;
import com.startup.bedok.advertisment.repository.RoomPhotosRepository;
import com.startup.bedok.advertisment.services.AdvertisementPhotoService;
import com.startup.bedok.user.model.ApplicationUser;
import com.startup.bedok.user.model.GenderEnum;
import com.startup.bedok.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DataGenerator {

    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;
    private final RoomPhotosRepository roomPhotosRepository;
    private final AdvertisementPhotoService advertisementPhotoService;
    private Faker faker = new Faker();

    @Transactional
    public void createSomeHostData(){
        List<ApplicationUser> users = IntStream.rangeClosed(1, 20)
                .mapToObj(i -> new ApplicationUser(
                        faker.name().name(),
                        GenderEnum.MALE,
                        faker.internet().password(),
                        faker.internet().emailAddress(),
                        faker.phoneNumber().phoneNumber(),
                        null,
                        LocalDate.now().minusYears(faker.number().numberBetween(20,40))
                                .minusMonths(faker.number().numberBetween(2,8)),
                        "polski",
                        faker.bool().bool(),
                        faker.bool().bool(),
                        faker.bool().bool(),
                        faker.bool().bool()
                )).toList();
        userRepository.saveAll(users);
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
    public List<Advertisement> createSomeAdvertisementData(){
        List<Advertisement> advertisements = IntStream.rangeClosed(1, 3)
                .mapToObj(i -> new Advertisement(
                        getHostUUID(),
                        faker.ancient().hero(),
                        faker.address().city(),
                        faker.address().state(),
                        faker.options().option(RoomGender.class),
                        faker.address().zipCode(),
                        faker.address().streetName(),
                        faker.superhero().descriptor(),
                        faker.number().randomDigit(),
                        faker.number().randomDigit(),
                        faker.number().randomDouble(2,50,80),
                        faker.number().randomDouble(2,0,5),
                        faker.number().randomDouble(2, 5, 8),
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
                        faker.bool().bool(),
                        faker.leagueOfLegends().quote()
                )).toList();
        return advertisementRepository.saveAll(advertisements);
    }

    private UUID getHostUUID(){
        return userRepository.findAll().get(faker.number().numberBetween(0,5)).getId();
    }

}

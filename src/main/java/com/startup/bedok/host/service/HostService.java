package com.startup.bedok.host.service;

import com.startup.bedok.datahelper.DataGenerator;
import com.startup.bedok.host.model.Host;
import com.startup.bedok.host.model.HostDTO;
import com.startup.bedok.host.model.HostResponse;
import com.startup.bedok.host.repository.HostRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.startup.bedok.host.mapper.HostMapperImpl.hostDTOtoHost;
import static com.startup.bedok.host.mapper.HostMapperImpl.hostToHostResponse;


@Service
@RequiredArgsConstructor
public class HostService {

    private final HostRepository hostRepository;
    private final HostPhotoService hostPhotoService;
    private final DataGenerator dataGenerator;

    @Transactional
    public UUID createHost(HostDTO hostDTO) throws IOException {
        try {
            String photoId = null;
            if(hostDTO.getHostPhoto() != null) {
                photoId = hostPhotoService.savePhoto(hostDTO.getHostPhoto().getBytes(),
                        hostDTO.getHostName());
            }
            Host host = hostDTOtoHost(hostDTO, photoId);
            return hostRepository.save(host).getId();
        } catch (IOException ioException) {
            throw new IOException("Error during host creation", ioException.getCause());
        }
    }


    public HostResponse getHostByID(UUID id) {
        Host host = hostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("there is no host with uuid: '%s'", id)));
        Binary hostPhoto = null;
        String hostPhotoId = host.getHostPhotoId();
        if(hostPhotoId !=null)
        hostPhoto = hostPhotoService.getHostPhoto(hostPhotoId);

        return hostToHostResponse(host, hostPhoto);
    }

    public void checkIfHostExists(UUID id) {
        if(!hostRepository.existsById(id))
        throw new RuntimeException(String.format("there is no host with uuid: '%s'", id));
    }

    public void createSomeRandomHosts() {
        dataGenerator.createSomeHostData();
    }

    public void createSomeHostPhotos() throws IOException {
        dataGenerator.createSomeHostPhotos();
    }


}

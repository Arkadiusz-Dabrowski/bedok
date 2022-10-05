package com.startup.bedok.host.service;

import static com.startup.bedok.host.mapper.HostMapperImpl.hostDTOtoHost;
import static com.startup.bedok.host.mapper.HostMapperImpl.hostToHostResponse;
import com.startup.bedok.host.model.Host;
import com.startup.bedok.host.model.HostDTO;
import com.startup.bedok.host.model.HostResponse;
import com.startup.bedok.host.repository.HostRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class HostService {

    private final HostRepository hostRepository;
    private final HostPhotoService hostPhotoService;

    @Transactional
    public UUID createHost(HostDTO hostDTO) throws IOException {
        try {
            String photoId = hostPhotoService.savePhoto(hostDTO.getHostPhoto().getBytes(),
                    hostDTO.getHostName());
            Host host = hostDTOtoHost(hostDTO, photoId);
            return hostRepository.save(host).getId();
        } catch (IOException ioException) {
            throw new IOException("fail");
        }
    }


    public HostResponse getHostByID(UUID id) {
        Host host = hostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("there is no host with uuid: '%s'", id)));
        Binary hostPhoto = hostPhotoService.getHostPhoto(host.getHostPhotoId());
        return hostToHostResponse(host, hostPhoto);
    }

    public void checkIfHostExists(UUID id) {
        if(!hostRepository.existsById(id));
        throw new RuntimeException(String.format("there is no host with uuid: '%s'", id));
    }
}

package com.startup.bedok.host.service;

import static com.startup.bedok.host.mapper.HostMapperImpl.hostDTOtoHost;
import static com.startup.bedok.host.mapper.HostMapperImpl.hostToHostResponse;
import com.startup.bedok.host.model.Host;
import com.startup.bedok.host.model.HostDTO;
import com.startup.bedok.host.model.HostResponse;
import com.startup.bedok.host.repository.HostRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@Service
@Getter
@RequiredArgsConstructor
public class HostService {

    private final HostRepository hostRepository;
    private final HostPhotoService hostPhotoService;

    @Transactional
    public Long createHost(HostDTO hostDTO) throws IOException {
        try {
            String photoId = hostPhotoService.savePhoto(hostDTO.getHostPhoto().getBytes(),
                    hostDTO.getHostName());
            Host host = hostDTOtoHost(hostDTO, photoId);
            return hostRepository.save(host).getId();
        } catch (IOException ioException) {
            throw new IOException("fail");
        }
    }


    public HostResponse getHostByID(Long id) {
        Host host = hostRepository.getById(id);
        Binary hostPhoto = hostPhotoService.getHostPhoto(host.getHostPhotoId());
        return hostToHostResponse(host, hostPhoto.getData());
    }
}

package com.startup.bedok.host.service;

import com.startup.bedok.host.mapper.HostMapper;
import static com.startup.bedok.host.mapper.HostMapperImpl.hostDTOtoHost;
import static com.startup.bedok.host.mapper.HostMapperImpl.hostToHostDTO;
import com.startup.bedok.host.model.Host;
import com.startup.bedok.host.model.HostDTO;
import com.startup.bedok.host.repository.HostRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@Getter
@RequiredArgsConstructor
public class HostService {

    private HostRepository hostRepository;
    private HostPhotoService hostPhotoService;

    @Autowired
    public HostService(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }
    @Transactional
    public Long createHost(HostDTO hostDTO) {
        String photoId = hostPhotoService.savePhoto(hostDTO.getHostPhoto(),
                hostDTO.getHostName());

        Host host = hostDTOtoHost(hostDTO, photoId);
        return hostRepository.save(host).getId();
    }


    public HostDTO getHostByID(Long id) {
        Host host = hostRepository.getById(id);
        MultipartFile hostPhoto = hostPhotoService.getHostPhoto(host.getHostPhotoId());
        return hostToHostDTO(host, hostPhoto);
    }
}

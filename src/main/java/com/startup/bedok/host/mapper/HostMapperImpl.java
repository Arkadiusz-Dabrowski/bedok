package com.startup.bedok.host.mapper;

import com.startup.bedok.host.model.Host;
import com.startup.bedok.host.model.HostDTO;
import org.springframework.web.multipart.MultipartFile;

public class HostMapperImpl {

    static public Host hostDTOtoHost(HostDTO hostDTO, String photoId) {
        return new Host(
                hostDTO.getHostName(),
                hostDTO.getHostPassword(),
                hostDTO.getHostEmail(),
                hostDTO.getHostPhone(),
                photoId
        );
    }

    static public HostDTO hostToHostDTO(Host host, MultipartFile hostPhoto) {
        return new HostDTO(
                host.getHostName(),
                host.getHostEmail(),
                host.getHostPhone(),
                hostPhoto
        );
    }
}

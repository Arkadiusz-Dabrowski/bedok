package com.startup.bedok.host.mapper;

import com.startup.bedok.host.model.Host;
import com.startup.bedok.host.model.HostDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface HostMapper {


    Host hostDTOtoHost(HostDTO hostDTO, String photoId);

    HostDTO hostToHostDTO(Host host, MultipartFile hostPhoto);
}

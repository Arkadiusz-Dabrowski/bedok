package com.startup.bedok.host.mapper;

import com.startup.bedok.host.model.Host;
import com.startup.bedok.host.model.HostDTO;
import com.startup.bedok.host.model.HostResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

public class HostMapperImpl {

    static public Host hostDTOtoHost(HostDTO hostDTO, String photoId) {
        return new Host(
                hostDTO.getHostName(),
                encryptePassword(hostDTO.getHostPassword()),
                hostDTO.getHostEmail(),
                hostDTO.getHostPhone(),
                photoId
        );
    }

    static public HostResponse hostToHostResponse(Host host, byte[] hostPhoto) {
        return new HostResponse(
                host.getHostName(),
                host.getHostEmail(),
                host.getHostPhone(),
                hostPhoto
        );
    }

    private static String encryptePassword(String plainPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(10, new SecureRandom());
        return bCryptPasswordEncoder.encode(plainPassword);
    }
}

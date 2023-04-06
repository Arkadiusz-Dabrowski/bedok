package com.startup.bedok.user.mapper;

import com.startup.bedok.user.model.ApplicationUser;
import com.startup.bedok.user.model.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface HostMapper {


    ApplicationUser hostDTOtoHost(UserDTO userDTO, String photoId);

    UserDTO hostToHostDTO(ApplicationUser user, MultipartFile hostPhoto);
}

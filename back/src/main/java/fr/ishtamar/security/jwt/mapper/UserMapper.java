package fr.ishtamar.security.jwt.mapper;

import fr.ishtamar.security.jwt.dto.UserDto;
import fr.ishtamar.security.jwt.entity.UserInfo;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, UserInfo> {
}

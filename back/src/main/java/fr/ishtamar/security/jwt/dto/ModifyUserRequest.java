package fr.ishtamar.security.jwt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModifyUserRequest {
    @Size(max=30)
    private String name;

    @Size(max=60)
    private String password;

    @Size(max=63)
    @Email
    private String email;
}

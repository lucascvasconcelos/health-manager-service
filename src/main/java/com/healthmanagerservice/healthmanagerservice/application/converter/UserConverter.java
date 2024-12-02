package com.healthmanagerservice.healthmanagerservice.application.converter;

import com.healthmanagerservice.healthmanagerservice.domain.entities.Doctor;
import com.healthmanagerservice.healthmanagerservice.domain.entities.Nurse;
import com.healthmanagerservice.healthmanagerservice.domain.entities.User;
import com.healthmanagerservice.healthmanagerservice.domain.enums.Role;
import com.healthmanagerservice.healthmanagerservice.presentation.dto.request.UserRequestDTO;
import com.healthmanagerservice.healthmanagerservice.presentation.dto.response.UserResponseDTO;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.function.Supplier;

@Component
public class UserConverter {

    public UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getCpf(),
                user.getRole()
        );
    }

    public User toEntity(UserRequestDTO userRequestDTO) {
        User user = createUserByRole(userRequestDTO.role());

        user.setName(userRequestDTO.name());
        user.setCpf(userRequestDTO.cpf());
        user.setPassword(userRequestDTO.password());
        user.setRole(userRequestDTO.role());

        return user;
    }

    private User createUserByRole(Role role) {
        EnumMap<Role, Supplier<User>> roleMap = new EnumMap<>(Role.class);
        roleMap.put(Role.ROLE_DOCTOR, Doctor::new);
        roleMap.put(Role.ROLE_NURSE, Nurse::new);

        Supplier<User> userSupplier = roleMap.get(role);
        if (userSupplier != null) {
            return userSupplier.get();
        } else {
            throw new IllegalArgumentException("Role not found: " + role);
        }
    }
}

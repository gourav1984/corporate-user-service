package co.user.api.service;

import co.user.api.data.Users;
import co.user.api.models.UserModel;
import co.user.api.shared.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService{
    UserDTO addUser(UserDTO user);

    List<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO updateUser(Long id, UserDTO userDTO);

    UserDTO deleteUserById(Long id);
}

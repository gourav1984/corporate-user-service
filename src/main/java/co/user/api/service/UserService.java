package co.user.api.service;

import co.user.api.shared.UserDTO;

import java.util.List;

public interface UserService{

    List<UserDTO> findAll();
    UserDTO findById(Long id);
    UserDTO findByCivilId(String cid);
    UserDTO findByName(String name);
    List<UserDTO>findByStatus(String status);
    UserDTO deleteUserById(Long id);
    UserDTO deleteUserByCivilId(String cid);

    UserDTO addUser(UserDTO user);
    UserDTO updateUser(Long id, UserDTO userDTO);


}

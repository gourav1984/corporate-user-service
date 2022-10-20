package co.user.api.service.impl;

import co.user.api.data.File;
import co.user.api.data.Users;
import co.user.api.models.UserModel;
import co.user.api.repo.FileRepository;
import co.user.api.repo.UserRepository;
import co.user.api.service.UserService;
import co.user.api.shared.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDTO addUser(UserDTO user) {
        Users u = modelMapper.map(user,Users.class);
        return modelMapper.map(userRepository.save(u),UserDTO.class);
    }

    @Override
    public List<UserDTO> findAll() {
        List<Users> users =userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user,UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<Users> users = userRepository.findById(id);
        return modelMapper.map(users,UserDTO.class);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<Users> users = userRepository.findById(id);
        if (!users.isPresent()) {
            return null;
        }
        Users user = modelMapper.map(userDTO,Users.class);
        user.setId(users.get().getId());
        return modelMapper.map(userRepository.save(user),UserDTO.class);
    }

    @Override
    public UserDTO deleteUserById(Long id) {
        Optional<Users> users = userRepository.findById(id);
        if (!users.isPresent()) {
            return null;
        }
        userRepository.deleteById(users.get().getId());
        return modelMapper.map(users,UserDTO.class);
    }
}

package co.user.api.controller;

import co.user.api.data.Users;
import co.user.api.models.UserModel;
import co.user.api.service.UserService;
import co.user.api.shared.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
@Tag(name = "User", description = "The User API. Contain all the opertions that can be performed on User")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Create Users for corporate request", summary = "Create Users for corporate request")
    public ResponseEntity<UserModel> createUsers(@RequestBody final UserModel userModel){

        if(!userModel.validateFiles(userModel.getAttachments())){
            return ResponseEntity.unprocessableEntity().build();
        }
        UserDTO user = userService.addUser(modelMapper.map(userModel,UserDTO.class));

        return new ResponseEntity<UserModel>(modelMapper.map(user,UserModel.class), HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Get all Users for corporate request", summary = "Get all Users for corporate request")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll().stream().map(userDTO -> modelMapper.map(userDTO,UserModel.class)).collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @Valid @RequestBody UserModel userModel) {

        UserDTO userDTO = userService.updateUser(id,modelMapper.map(userModel,UserDTO.class));
        if (null == userDTO) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserModel> deleteUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.deleteUserById(id);
        if (null == userDTO) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getById(@PathVariable Long id) {
        UserDTO userDTO = userService.findById(id);
        if (null == userDTO) {
            return ResponseEntity.unprocessableEntity().build();
        }
        LocalDate currentDate = LocalDate.now();
        UserModel model = modelMapper.map(userDTO,UserModel.class);
        if(userDTO.getExpiryDate().isBefore(currentDate)){
            model.setWarning("Civil Id is Expired");
        }

        return ResponseEntity.ok(model);
    }

}

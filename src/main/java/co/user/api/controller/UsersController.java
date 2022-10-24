package co.user.api.controller;

import co.user.api.models.UserModel;
import co.user.api.service.UserService;
import co.user.api.shared.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/v1/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Retrieves a list of all user records")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "Found Users",
                    content = { @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = UserModel.class))) }),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> userModels = userService.findAll().stream().map(userDTO -> modelMapper.map(userDTO,UserModel.class)).collect(Collectors.toList());
        return userModels.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(userModels);
    }

    @Operation(summary = "Retrieves user by Id")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "Found User",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserModel.class))) }),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> getUserById(@Parameter(description = "id of user to be searched") @PathVariable Long id) {
        UserDTO userDTO = userService.findById(id);
        if (null == userDTO) {
            return ResponseEntity.notFound().build();
        }
        LocalDate currentDate = LocalDate.now();
        UserModel model = modelMapper.map(userDTO,UserModel.class);
        if(userDTO.getExpiryDate().isBefore(currentDate)){
            model.setWarning("Civil Id is Expired");
        }
        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Retrieves user by Civil Id")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "Found User",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserModel.class))) }),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            })
    @GetMapping(value = "/civilId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> getUserById(@Parameter(description = "civil Id of user to be searched") @RequestParam String cid) {
        UserDTO userDTO = userService.findByCivilId(cid);
        if (null == userDTO) {
            return ResponseEntity.notFound().build();
        }
        LocalDate currentDate = LocalDate.now();
        UserModel model = modelMapper.map(userDTO,UserModel.class);
        if(userDTO.getExpiryDate().isBefore(currentDate)){
            model.setWarning("Civil Id is Expired");
        }
        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Retrieves user by User Name")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "Found User",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserModel.class))) }),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            })
    @GetMapping(value = "/name", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> getUserByName(@Parameter(description = "Name of user to be searched") @RequestParam String name) {
        UserDTO userDTO = userService.findByName(name);
        if (null == userDTO) {
            return ResponseEntity.notFound().build();
        }
        LocalDate currentDate = LocalDate.now();
        UserModel model = modelMapper.map(userDTO,UserModel.class);
        if(userDTO.getExpiryDate().isBefore(currentDate)){
            model.setWarning("Civil Id is Expired");
        }
        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Retrieves user by status")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "Found User",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserModel.class))) }),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
                    @ApiResponse(responseCode = "400", description = "status input is not valid",content = @Content)
            })
    @GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserModel>> getUserByStatus(@Parameter(example = "Active",description = "Status of user to be searched") @RequestParam String status) {
        if(!UserModel.validateStatus(status)){
            return ResponseEntity.badRequest().build();
        }
        List<UserDTO> userDTOs = userService.findByStatus(status);
        if (null == userDTOs || userDTOs.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "status input is not valid");
            //return ResponseEntity.notFound().build();
        }
        LocalDate currentDate = LocalDate.now();

        List<UserModel> users = userDTOs
                .stream()
                .map(userDTO -> modelMapper.map(userDTO, UserModel.class)).toList();

        List<UserModel> one =users.stream().filter(userModel -> userModel.getExpiryDate().isBefore(currentDate)).collect(Collectors.toList());
        one.forEach(u -> u.setWarning("Civil Id expired"));
        List<UserModel> two =users.stream().filter(userModel -> !userModel.getExpiryDate().isBefore(currentDate)).collect(Collectors.toList());
        List<UserModel> userModels = Stream.of(one,two)
                                .flatMap(Collection::stream)
                                .collect(Collectors.toList());

        return ResponseEntity.ok(userModels);
    }

    @Operation(summary = "Delete existing user records using Id")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "204", description = "Users Deleted", content = @Content),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            })
    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long id) {
        UserModel userModel = modelMapper.map(userService.deleteUserById(id),UserModel.class);
        return userModel==null ? ResponseEntity.notFound().build() : ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete existing user records using civil Id")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "204", description = "Users Deleted", content = @Content),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            })
    @DeleteMapping(value = "/{cid}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "cid") String cid) {
        UserModel userModel = modelMapper.map(userService.deleteUserByCivilId(cid),UserModel.class);
        return userModel==null ? ResponseEntity.notFound().build() : ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create new user records. JSON payload will be validated")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "201", description = "User Created",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserModel.class))) }),
                    @ApiResponse(responseCode = "422", description = "JSON payload not validated", content = @Content)
            })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> createUsers(@RequestBody final UserModel userModel){

        if(userModel.validateFiles(userModel.getAttachments())){
            return ResponseEntity.unprocessableEntity().build();
        }
        UserDTO user = userService.addUser(modelMapper.map(userModel,UserDTO.class));

        return new ResponseEntity<>(modelMapper.map(user, UserModel.class), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing user record. Will not create new record if user does not already exist")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "202", description = "User Updated",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserModel.class))) }),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            })
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> updateVehicle(@PathVariable Long id, @Valid @RequestBody UserModel userModel) {
        if(userModel.getAttachments()!=null || userModel.validateFiles(userModel.getAttachments())){
            return ResponseEntity.unprocessableEntity().build();
        }
        UserDTO userDTO=userService.updateUser(id,modelMapper.map(userModel,UserDTO.class));
        return userDTO==null ? ResponseEntity.notFound().build() : new ResponseEntity<>(modelMapper.map(userDTO, UserModel.class), HttpStatus.ACCEPTED);
    }
}

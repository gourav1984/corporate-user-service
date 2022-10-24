package co.user.api.controller;

import co.user.api.service.UserService;
import co.user.api.shared.FileDTO;
import co.user.api.shared.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void createUsersTest() throws Exception {

        Set<FileDTO> attachments =new HashSet<>();
        attachments.add(FileDTO.builder().file("/path/civil.pdf").build());
        attachments.add(FileDTO.builder().file("/path/civil.csv").build());

        UserDTO userModel = UserDTO.builder()
                .name("Gourav Tanpure")
                .civilId("F5480795")
                .expiryDate(LocalDate.now())
                .attachments(attachments)
                .build();

        UserDTO userDTO = modelMapper.map(userModel,UserDTO.class);

        Mockito.when(userService.addUser(Mockito.any(UserDTO.class))).thenReturn(userDTO);

         MockMvcRequestBuilders.post("http://localhost:8080/corporate/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(userDTO));
    }

    @Test
    public void getAllUsersTest() throws Exception{

        Set<FileDTO> att1 =new HashSet<>();
        att1.add(FileDTO.builder().file("/path/civil.pdf").build());
        att1.add(FileDTO.builder().file("/path/civil.csv").build());

        List<UserDTO> userModels = new ArrayList<>();
        UserDTO userModel = UserDTO.builder()
                .name("Gourav Tanpure")
                .civilId("F5480795")
                .expiryDate(LocalDate.now())
                .attachments(att1)
                .build();

        userModels.add(userModel);

        List<UserDTO> userDTO = userModels.stream().map(userDTOs -> modelMapper.map(userModel,UserDTO.class)).collect(Collectors.toList());

        Mockito.when(userService.findAll()).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/corporate/api/v1/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void getUserByIdTest() throws Exception {

        Mockito.when(userService.findById(1L)).thenReturn(UserDTO.builder().build());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/corporate/api/v1/users/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void getUserByCivilIdTest() throws Exception {

        Mockito.when(userService.findByCivilId("F5480795")).thenReturn(UserDTO.builder().build());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/corporate/api/v1/users/civilId/cid?cid=F5480795")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
    @Test
    public void getUserByNameTest() throws Exception {

        Mockito.when(userService.findByName("Gourav Tanpure")).thenReturn(UserDTO.builder().build());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/corporate/api/v1/users/name?name=Gourav Tanpure")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}

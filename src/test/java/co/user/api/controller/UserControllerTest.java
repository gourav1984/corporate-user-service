package co.user.api.controller;

import co.user.api.service.UserService;
import co.user.api.shared.FileDTO;
import co.user.api.shared.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    void createUsers() throws JsonProcessingException {

        Set<FileDTO> attchements =new HashSet<>();
        attchements.add(FileDTO.builder().file("/path/civil.pdf").build());
        attchements.add(FileDTO.builder().file("/path/civil.csv").build());

        UserDTO userModel = UserDTO.builder()
                .name("Gourav Tanpure")
                .civilId("F5480795")
                .expiryDate(LocalDate.now())
                .attachments(attchements)
                .build();

        UserDTO userDTO = modelMapper.map(userModel,UserDTO.class);

        Mockito.when(userService.addUser(Mockito.any(UserDTO.class))).thenReturn(userDTO);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("http://localhost:8080/corporate/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(userDTO));
    }

    @Test
    public void getAllUsers() throws Exception{

        Set<FileDTO> att1 =new HashSet<>();
        att1.add(FileDTO.builder().file("/path/civil.pdf").build());
        att1.add(FileDTO.builder().file("/path/civil.csv").build());

        Set<FileDTO> att2 =new HashSet<>();
        att2.add(FileDTO.builder().file("/path/ref.pdf").build());
        att2.add(FileDTO.builder().file("/path/ref.csv").build());
        att2.add(FileDTO.builder().file("/path/info.csv").build());

        List<UserDTO> users = new ArrayList<>();
        UserDTO user1 = UserDTO.builder()
                .name("Gourav Tanpure")
                .civilId("F5480795")
                .expiryDate(LocalDate.now())
                .attachments(att1)
                .build();

        UserDTO user2 = UserDTO.builder()
                .name("Sam")
                .civilId("F4736800")
                .expiryDate(LocalDate.now().plusDays(12))
                .attachments(att2)
                .build();

        users.add(user1);
        users.add(user2);

        Mockito.when(userService.findAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/corporate/api/v1/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}

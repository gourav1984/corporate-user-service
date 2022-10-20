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
import java.util.HashSet;
import java.util.Set;


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
}

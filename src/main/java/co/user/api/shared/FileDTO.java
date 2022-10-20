package co.user.api.shared;

import co.user.api.data.File;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


public class FileDTO implements Serializable {

    private String file;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserDTO userDTO;


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}

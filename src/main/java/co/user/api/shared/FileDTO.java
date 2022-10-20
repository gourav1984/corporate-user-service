package co.user.api.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
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

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}

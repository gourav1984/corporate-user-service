package co.user.api.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class FileModel implements Serializable {

    private String file;
}

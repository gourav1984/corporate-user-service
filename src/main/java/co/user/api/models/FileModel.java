package co.user.api.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Builder
@Getter
@Setter
public class FileModel implements Serializable {

    private String file;
}

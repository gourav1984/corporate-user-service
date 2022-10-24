package co.user.api.models;

import lombok.*;

import java.io.Serializable;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileModel implements Serializable {

    private String file;
}

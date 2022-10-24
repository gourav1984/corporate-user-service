package co.user.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractModel implements Serializable {

    public abstract boolean validateFiles(Set<FileModel> files);

    private String warning;


}

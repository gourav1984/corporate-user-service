package co.user.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
@Setter
public class UserModel extends AbstractModel {

    private String name;

    private String civilId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate expiryDate;

    @Min(2)
    @Max(4)
    private Set<FileModel> attachments;

    @Override
    public boolean validateFiles(Set<FileModel> files) {
        for (FileModel f:files) {
            for (FileModel f2: files){
                if(f.equals(f2)){
                    return true;
                }
            }
        }
        return false;
    }


}

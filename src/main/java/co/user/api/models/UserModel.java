package co.user.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel extends AbstractModel implements Serializable {

    @NotNull
    private String name;

    @NotNull
    private String civilId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate expiryDate;

    @Min(2)
    @Max(4)
    private Set<FileModel> attachments;

    public static boolean validateStatus(String status) {
        return status.equalsIgnoreCase(Status.ACTIVE.value) && status.equalsIgnoreCase(Status.INACTIVE.value);
    }


    @Override
    public boolean validateFiles(Set<FileModel> files) {
        for (FileModel f:files) {
            for (FileModel f2: files){
                if(f.equals(f2)){
                    return false;
                }
            }
        }
        return true;
    }

    public enum Status{

        ACTIVE("Active"),
        INACTIVE("Inactive"),
        ;
        private final String value;

        Status(final String value) {
            this.value= value;
        }

    }


}

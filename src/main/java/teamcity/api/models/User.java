package teamcity.api.models;

import teamcity.api.annotations.Random;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseModel {
    private int id;
    @Random
    private String username;
    @Random
    private String password;
    private Roles roles;
}
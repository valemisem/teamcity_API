package teamcity.api.models;

import teamcity.api.annotations.Optional;
import teamcity.api.annotations.Parameterizable;
import teamcity.api.annotations.Random;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildType extends BaseModel{
    @Random
    @Parameterizable
    private String id;
    @Random
    @Parameterizable
    private String name;
    @Parameterizable
    private Project project;
    @Optional
    private Steps steps;
}
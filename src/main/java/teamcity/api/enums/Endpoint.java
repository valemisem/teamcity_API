package teamcity.api.enums;

import teamcity.api.models.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Endpoint {
    BUILD_TYPES("/app/rest/buildTypes", BuildType.class),
    PROJECTS("/app/rest/projects", Project.class),
    USERS("/app/rest/users", User.class),
    AUTH_SETTINGS("/app/rest/server/authSettings", AuthSettings.class);

    private final String url;
    private final Class<? extends BaseModel> modelClass;


}
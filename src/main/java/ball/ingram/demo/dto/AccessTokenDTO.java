package ball.ingram.demo.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {
    private String client_id;
    private String redirect_uri;
    private String client_secret;
    private String code;
    private String state;

    public AccessTokenDTO(String client_id, String redirect_uri, String client_secret, String code, String state) {
        this.client_id = client_id;
        this.redirect_uri = redirect_uri;
        this.client_secret = client_secret;
        this.code = code;
        this.state = state;
    }
}

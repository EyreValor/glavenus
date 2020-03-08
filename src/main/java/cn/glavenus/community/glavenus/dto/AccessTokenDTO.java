package cn.glavenus.community.glavenus.dto;

import lombok.Data;

/**
 * Creaked by EyreValor on 2020/2/25
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_url;
    private String state;
}

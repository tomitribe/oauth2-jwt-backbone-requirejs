package org.superbiz.filter;


import org.apache.johnzon.mapper.JohnzonIgnore;
import org.apache.johnzon.mapper.JohnzonProperty;
import org.apache.johnzon.mapper.Mapper;
import org.apache.johnzon.mapper.MapperBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getDecoder;

/**
 * Bean representation of a Access Token response. See <a
 * href="https://tools.ietf.org/html/draft-ietf-oauth-v2-30#section-4.1.3">this
 * section</a> of the spec for more info.
 */
@XmlRootElement
public class AccessTokenResponse {

    @JohnzonIgnore
    public static final String AUTHORIZATION = "authorization";

    @JohnzonIgnore
    private static final Mapper MAPPER = new MapperBuilder().build();

    @JohnzonProperty("access_token")
    private String accessToken;

    @JohnzonProperty("token_type")
    private String tokenType;

    @JohnzonProperty("expires_in")
    private long expiresIn;

    @JohnzonProperty("refresh_token")
    private String refreshToken;

    @JohnzonProperty("id_token")
    private String idToken;

    private String scope;

    AccessTokenResponse() {
    }

    public AccessTokenResponse(String accessToken, String tokenType, long expiresIn, String refreshToken, String scope) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.scope = scope;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccessTokenResponse{");
        sb.append("accessToken='").append(accessToken).append('\'');
        sb.append(", tokenType='").append(tokenType).append('\'');
        sb.append(", expiresIn=").append(expiresIn);
        sb.append(", refreshToken='").append(refreshToken).append('\'');
        sb.append(", idToken='").append(idToken).append('\'');
        sb.append(", scope='").append(scope).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @JohnzonIgnore
    public static AccessTokenResponse parse(String value) {
        Objects.requireNonNull(value, "value is required");
        byte[] decode = getDecoder().decode(value);
        return MAPPER.readObject(new String(decode, UTF_8), AccessTokenResponse.class);
    }

}
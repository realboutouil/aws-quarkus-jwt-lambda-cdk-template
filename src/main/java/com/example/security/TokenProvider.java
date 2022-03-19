package com.example.security;

import com.example.entities.UserEntity;
import com.example.model.LoginResponse;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import io.smallrye.jwt.util.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;

import javax.enterprise.context.ApplicationScoped;
import java.security.PrivateKey;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@ApplicationScoped
public class TokenProvider {
    public static final String JWT_USER_ID_CLAIM = "userId";
    public static final String JWT_ISSUER_CLAIM = Claims.iss.name();

    private final String sigKeyId;
    private final String issuer;
    private final PrivateKey privateKey;
    private final long defaultExpiration;
    private final long extendedExpiration;
    private final boolean extendedTimeout;

    public TokenProvider(
            @ConfigProperty(name = "mp.jwt.verify.privatekey.location")
                    String privateKeyLocation,
            @ConfigProperty(name = "mp.jwt.expiration.default")
                    long defaultExpiration,
            @ConfigProperty(name = "mp.jwt.expiration.extended")
                    long extendedExpiration,
            @ConfigProperty(name = "mp.jwt.expiration.extended-timeout")
                    boolean extendedTimeout,
            @ConfigProperty(name = "mp.jwt.verify.issuer")
                    String issuer
    ) throws Exception {
        this.issuer = issuer;
        this.sigKeyId = privateKeyLocation;
        this.defaultExpiration = defaultExpiration;
        this.extendedExpiration = extendedExpiration;
        this.extendedTimeout = extendedTimeout;
        log.info("Private key location: {}", privateKeyLocation);
        this.privateKey = KeyUtils.readPrivateKey(privateKeyLocation);
        // KeyUtils.readPrivateKey(privateKeyLocation);
        // StaticUtils.resourceAsUrl(privateKeyLocation).toString());
    }

    /**
     * Gets a user's jwt. Meant to be used during auth, returns the object meant to return to the user.
     *
     * @param user The user to get the jwt for
     * @return The response to give back to the user.
     */
    public LoginResponse getUserToken(UserEntity user) {
        Instant expiration = Instant.now()
                .plusSeconds((
                        extendedTimeout
                                ? this.extendedExpiration
                                : this.defaultExpiration
                ));

        return LoginResponse.builder()
                .expires(expiration)
                .access_token(this.generateTokenString(user, expiration))
                .build();
    }

    /**
     * Generates a jwt for use by the user.
     *
     * @param user    The user to get the jwt for
     * @param expires When the jwt should expire
     * @return The jwt for the user
     */
    public String generateTokenString(UserEntity user, Instant expires) {
        // info on what claims are: https://auth0.com/docs/security/tokens/json-web-tokens/json-web-token-claims
        Map<String, Object> rawClaims = this.getUserClaims(user);
        JwtClaimsBuilder claims = Jwt.claims(rawClaims);
        claims.expiresAt(expires);
        return claims.jws().keyId(this.sigKeyId).sign(this.privateKey);
    }

    private Map<String, Object> getUserClaims(UserEntity user) {
        Map<String, Object> output = this.getBaseClaims();
        var identification = user.getId() + ";" + user.getUsername();
        output.put(Claims.jti.name(), user.getId() + "-" + UUID.randomUUID());
        output.put(Claims.sub.name(), user.getId());
        output.put(Claims.aud.name(), identification);
        output.put(Claims.upn.name(), user.getUsername());
        output.put(Claims.email.name(), user.getUsername());
        output.put(Claims.given_name.name(), user.getFirstName());
        output.put(Claims.family_name.name(), user.getLastName());
        output.put(Claims.groups.name(), user.getGrantedRoles());
        output.put(JWT_USER_ID_CLAIM, user.getId());
        return output;
    }

    private Map<String, Object> getBaseClaims() {
        Map<String, Object> output = new HashMap<>();
        output.put(JWT_ISSUER_CLAIM, this.issuer); // serverInfo.getOrganization() + " - Task Timekeeper Server");
        output.put(Claims.auth_time.name(), Instant.now().getEpochSecond());
        return output;
    }
}
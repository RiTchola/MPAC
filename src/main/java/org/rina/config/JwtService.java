package org.rina.config;

import org.rina.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des JSON Web Tokens (JWT) pour l'authentification et l'autorisation.
 */
@Service
public class JwtService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;
  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;
  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  /**
   * Extrait le nom d'utilisateur du token JWT.
   *
   * @param token Le token JWT.
   * @return Le nom d'utilisateur.
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extrait une revendication spécifique du token JWT.
   *
   * @param <T> Le type de la revendication.
   * @param token Le token JWT.
   * @param claimsResolver La fonction pour résoudre la revendication.
   * @return La revendication extraite.
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Génère un token JWT à partir des détails de l'utilisateur.
   *
   * @param user Les détails de l'utilisateur.
   * @return Le token JWT généré.
   */
  public String generateToken(User user) {
    return buildToken(user, jwtExpiration);
  }

  /**
   * Génère un token de rafraîchissement JWT à partir des détails de l'utilisateur.
   *
   * @param user Les détails de l'utilisateur.
   * @return Le token de rafraîchissement JWT généré.
   */
  public String generateRefreshToken(
      User user
  ) {
    return buildToken(user, refreshExpiration);
  }

  /**
   * Construit un token JWT avec des revendications spécifiques et une expiration.
   *
   * @param user Les détails de l'utilisateur.
   * @param expiration La durée d'expiration du token.
   * @return Le token JWT construit.
   */
  private String buildToken(
          User user,
          long expiration
  ) {
    HashMap<String, Object> claims = new HashMap<>();
    List<String> roles = user.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    claims.put("authorities", roles);
    claims.put("role", user.getRole().name());

    return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(user.getUsername())
            .setAudience("MPAC2223-API")
            .setIssuer("https://rina.org")
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  /**
   * Vérifie si le token JWT est valide pour un utilisateur donné.
   *
   * @param token Le token JWT.
   * @param userDetails Les détails de l'utilisateur.
   * @return true si le token est valide, sinon false.
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  /**
   * Vérifie si un token JWT est expiré.
   *
   * @param token Le token JWT à vérifier.
   * @return true si le token est expiré, sinon false.
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Extrait la date d'expiration d'un token JWT.
   *
   * @param token Le token JWT dont on veut extraire la date d'expiration.
   * @return La date d'expiration du token JWT.
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }


  /**
   * Extrait toutes les réclamations (claims) d'un token JWT.
   * Les réclamations sont les assertions faites par le fournisseur d'identité et contiennent des informations sur l'utilisateur.
   *
   * @param token Le token JWT dont on veut extraire les réclamations.
   * @return L'ensemble des réclamations contenues dans le token JWT.
   */
  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * Récupère la clé utilisée pour signer les tokens JWT.
   * La clé est décodée à partir d'une chaîne encodée en BASE64.
   *
   * @return La clé utilisée pour signer les tokens JWT.
   */
  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}

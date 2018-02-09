package pl.szotaa.snippr.security;

public class SecurityConstants {

    public static final String SECRET = "Secret"; // TODO: change this
    public static final long EXPIRATION_TIME = 60 * 60 * 24;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user/sign-up";
}

package com.example.SecureJwTeaTime.util.routes;

/** Contains constants holding endpoints for security configuration */
public class SecurityRoutes {
  private static final String BASE = "/api/v1";
  private static final String AUTH = BASE + "/auth";
  private static final String LOGIN_USERS = AUTH + "/login";
  private static final String REGISTER_CUSTOMERS = AUTH + "/customers/register";
  private static final String PASSWORD_RESET_REQUEST = AUTH + "/request-password-reset";
  private static final String LOGOUT = AUTH + "/logout";
  private static final String REFRESH_TOKEN = AUTH + "/refresh-token";
  private static final String SWAGGER_UI = "/swagger-ui/**";
  private static final String SWAGGER_DOCS = "/v3/api-docs*/**";

  /**
   * Method that returns endpoints that do not require authentication
   *
   * @return String[] containing endpoints
   */
  public static String[] getOpenRoutes() {
    return new String[] {
      REFRESH_TOKEN,
      LOGOUT,
      REGISTER_CUSTOMERS,
      LOGIN_USERS,
      PASSWORD_RESET_REQUEST,
      SWAGGER_UI,
      SWAGGER_DOCS
    };
  }
}

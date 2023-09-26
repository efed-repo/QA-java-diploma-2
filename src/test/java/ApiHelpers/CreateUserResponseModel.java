package ApiHelpers;

public class CreateUserResponseModel {
    private String success;
    private User user;
    private String accessToken;
    private String refreshToken;

    public String getSuccess() {
        return success;
    }

    public User getUser() {
        return user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public static class User {
        private String email;
        private String name;

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }
    }

}

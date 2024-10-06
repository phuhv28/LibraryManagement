package UserAuth;
public class Account {
    private String userName;
    private String password;

    public Account(String input_name, String input_password) {
        userName = input_name;
        password = input_password;
    }

    public String getName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}
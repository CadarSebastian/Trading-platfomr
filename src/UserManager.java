import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static UserManager instance;
    private Map<String, User> users;  

    private UserManager() {
        this.users = new HashMap<>();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void registerUser(String username, String password) {
        if (!users.containsKey(username)) {
            User newUser = new User(username, password);
            users.put(username, newUser);
            System.out.println("User " + username + " registered successfully.");
        } else {
            System.out.println("Username " + username + " is already taken. Please choose another one.");
        }
    }

    public User loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("User " + username + " logged in successfully.");
            return user;
        } else {
            System.out.println("Invalid username or password.");
            return null;
        }
    }

    
}

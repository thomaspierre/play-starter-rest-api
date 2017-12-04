package microDon;

import microDon.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersProvider {

    private List<User> users;

    public UsersProvider() {
        this.users = new ArrayList<>();
        User user1 = new User();
        user1.setUuid("1");
        user1.setEmail("user1@mail.com");
        user1.setPassword("a!Strongp#assword1");
        this.users.add(user1);

        User user2 = new User();
        user2.setUuid("2");
        user2.setEmail("user2@mail.com");
        user2.setPassword("a!Strongp#assword2");
        this.users.add(user2);

        User user3 = new User();
        user3.setUuid("3");
        user3.setEmail("user3@mail.com");
        user3.setPassword("a!Strongp#assword3");
        this.users.add(user3);
    }

    public Optional<User> getUserById(String id) {
        if (id == null) {
           return Optional.empty();
        }
        return users.stream().filter(u -> id.equals(u.getUuid())).findFirst();
    }

    public List<User> list() {
        return users;
    }
}

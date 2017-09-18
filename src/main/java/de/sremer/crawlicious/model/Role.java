package de.sremer.crawlicious.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_role")
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private int id;

    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
        user.addRole(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.removeRole(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        return id == ((Role) o).id;
    }
}

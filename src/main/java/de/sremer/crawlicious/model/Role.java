package de.sremer.crawlicious.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    @Getter
    @Setter
    private long id;

    @Column(name = "role")
    @Getter
    @Setter
    private String role;

    @ManyToMany(mappedBy = "roles")
    @Getter
    @Setter
    private Set<User> users;

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.removeRole(this);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof Role && id == ((Role) o).id;
    }
}

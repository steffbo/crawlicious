package de.sremer.crawlicious.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.Transient;

import jakarta.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User implements Comparable<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    private String name;

    @NaturalId
    private String email;

    @Transient
    private String password;

    private boolean enabled;

    private long registeredOn;

    private boolean privateProfile;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Getter(AccessLevel.NONE)
    private Set<Posting> postings;

    public Set<Posting> getPostings() {
        TreeSet<Posting> treeSet = new TreeSet(postings);
        return treeSet;
    }

    public String getRegistrationDate(String dateformat) {
        return new SimpleDateFormat(dateformat).format(new Date(this.getRegisteredOn()));
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.addUser(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.removeUser(this);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof User && id == ((User) o).id;
    }

    @Override
    public int compareTo(User o) {
        return Long.compare(o.registeredOn, this.registeredOn);
    }
}
package de.sremer.crawlicious.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "user")
public class User implements Comparable<User> {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    @Getter
    @Setter
    private long id;

    @NotEmpty(message = "*Please provide your name")
    @Getter
    @Setter
    private String name;

    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    @NaturalId
    @Getter
    @Setter
    private String email;

    @NotEmpty(message = "*Please provide your password")
    @Transient
    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private boolean enabled;

    @Getter
    @Setter
    private long registeredOn;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Getter
    @Setter
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    @Getter
    @Setter
    private Set<Posting> postings;

    public Set<Posting> getPostings() {
        TreeSet treeSet = new TreeSet(postings);
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
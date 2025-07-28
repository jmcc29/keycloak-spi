
package main.java.org.keycloak.quickstart.storage.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;


@Entity
@Table(name = "users", schema = "public")
@NamedQueries({
    @NamedQuery(name = "getUserByCi", query = "SELECT u FROM UserEntity u WHERE u.ci = :ci"),
    @NamedQuery(name = "getUserById", query = "SELECT u FROM UserEntity u WHERE u.id = :id")
})
public class UserEntity {

    @Id
    private UUID id;

    private String ci;
    private String first_name;
    private String second_name;
    private String lastname;
    private String mother_lastname;
    private Date birthdate;
    private String cellphone;
    private Boolean is_active;

    @Column(columnDefinition = "text[]")
    private String[] roles;

    // Generar Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getSecondName() {
        return second_name;
    }

    public void setSecondName(String second_name) {
        this.second_name = second_name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMotherLastname() {
        return mother_lastname;
    }

    public void setMotherLastname(String mother_lastname) {
        this.mother_lastname = mother_lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Boolean getIsActive() {
        return is_active;
    }

    public void setIsActive(Boolean is_active) {
        this.is_active = is_active;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}

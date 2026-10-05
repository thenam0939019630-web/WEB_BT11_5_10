package vn.iotstar.entity;


import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "UserRoles")
public class UserRole {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;


    @Column(name = "roleName")
    private String roleName;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer value) { roleId = value; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String value) { roleName = value; }
    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }


}
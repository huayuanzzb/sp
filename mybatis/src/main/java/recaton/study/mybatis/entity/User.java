package recaton.study.mybatis.entity;

import java.util.List;

public class User {

    private Integer id;
    private Integer companyId;
    private String name;
    private List<Address> addresses;

    public User() {
    }

    public User(Integer companyId, String name) {
        this.companyId = companyId;
        this.name = name;
    }

    public User(Integer id, Integer companyId, String name) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", name='" + name + '\'' +
                '}';
    }
}

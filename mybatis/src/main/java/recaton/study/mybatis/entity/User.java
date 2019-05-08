package recaton.study.mybatis.entity;

public class User {

    private Integer id;
    private Integer companyId;
    private String name;

    public User() {
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


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", name='" + name + '\'' +
                '}';
    }
}

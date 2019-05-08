package recaton.study.mybatis.entity;

public class Address {

    private Integer id;
    private Integer userId;
    private String type;
    private String province;
    private String city;

    public Address() {
    }

    public Address(Integer id, Integer userId, String type, String province, String city) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.province = province;
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

package recaton.study.mybatis.mapper;

import recaton.study.mybatis.entity.Company;

import java.util.List;

public interface CompanyMapper {

    int deleteAll();

    List<Company> getCompany(String name);

    int insertCompanyMap(Company company);
}

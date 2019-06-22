package recaton.study.mybatis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import recaton.study.mybatis.entity.Company;
import recaton.study.mybatis.mapper.CompanyMapper;

import java.util.List;

public class CompanyService {

    private Logger logger = LoggerFactory.getLogger(CompanyService.class);

    private CompanyMapper companyMapper;

    public CompanyService(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }

    public int saveCompany(Company company){
        return companyMapper.insertCompanyMap(company);
    }

    public List<Company> getCompanys(String name) {
        return companyMapper.getCompany(name);
    }

    public int deleteCompanys() {
        return companyMapper.deleteAll();
    }

}

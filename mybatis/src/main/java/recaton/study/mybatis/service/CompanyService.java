package recaton.study.mybatis.service;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import recaton.study.mybatis.entity.Company;

import java.util.List;

public class CompanyService {

    private Logger logger = LoggerFactory.getLogger(CompanyService.class);

    private SqlSession session;

    public CompanyService(SqlSession session) {
        this.session = session;
    }

    public int saveCompany(Company company){
        return session.insert("insertCompanyMap", company);
    }

    public List<Company> getCompanys(String name) {
        return session.selectList("getCompany", name);
    }

    public int deleteCompanys() {
        return session.delete("deleteCompanys");
    }

}

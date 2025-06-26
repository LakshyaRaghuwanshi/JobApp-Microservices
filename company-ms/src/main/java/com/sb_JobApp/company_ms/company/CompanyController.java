package com.sb_JobApp.company_ms.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        if(company != null) {
            return new ResponseEntity<>(company, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @PostMapping
    public ResponseEntity<String> addCompany(@RequestBody Company company) {
        companyService.createCompany(company);
        return new ResponseEntity<>("Company created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id,
                                                @RequestBody Company company) {
        companyService.updateCompany(id, company);
        return new ResponseEntity<>("Company updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        boolean isDeleted = companyService.deleteCompanyById(id);
        if (isDeleted) {
            return new ResponseEntity<>("Company Deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
        }
    }
}

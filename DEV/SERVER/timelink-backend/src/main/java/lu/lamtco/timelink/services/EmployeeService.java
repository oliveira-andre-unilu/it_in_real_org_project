package lu.lamtco.timelink.services;

import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.dto.EmployeeDTO;
import lu.lamtco.timelink.persister.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Employee createEmployee(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setSurname(dto.getSurName());
        employee.setEmail(dto.getEmail());
        employee.setPassword(dto.getPassword());
        employee.setRole(dto.getRole());
        employee.setHourlyRate(dto.getHourlyRate());

        return employeeRepository.save(employee);
    }
}

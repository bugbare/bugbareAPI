/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.greglturnquist.payroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Component
public class DatabaseLoader implements CommandLineRunner {

	private final EmployeeRepository employees;
	private final ManagerRepository managers;

	@Autowired
	public DatabaseLoader(EmployeeRepository employeeRepository,
						  ManagerRepository managerRepository) {

		this.employees = employeeRepository;
		this.managers = managerRepository;
	}

	@Override
	public void run(String... strings) throws Exception {

		Manager bugbare = this.managers.save(new Manager("bugbare", "bugbare",
							"ROLE_MANAGER"));
		Manager lionel = this.managers.save(new Manager("lionel", "lionelrishi",
							"ROLE_MANAGER"));

		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken("bugbare", "doesn't matter",
				AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

		this.employees.save(new Employee("Frodo", "Baggins", "ring bearer", bugbare));
		this.employees.save(new Employee("Bilbo", "Baggins", "burglar", bugbare));
		this.employees.save(new Employee("Gandalf", "the Grey", "wizard", bugbare));

		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken("lionel", "doesn't matter",
				AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

		this.employees.save(new Employee("Samwise", "Gamgee", "gardener", lionel));
		this.employees.save(new Employee("Merry", "Brandybuck", "pony rider", lionel));
		this.employees.save(new Employee("Peregrin", "Took", "pipe smoker", lionel));

		SecurityContextHolder.clearContext();
	}
}
// end::code[]
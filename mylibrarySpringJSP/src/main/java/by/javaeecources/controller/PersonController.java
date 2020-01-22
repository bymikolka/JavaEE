package by.javaeecources.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import by.javaeecources.model.Person;
import by.javaeecources.model.PersonDto;
import by.javaeecources.service.PersonService;
@Controller
public class PersonController {

	@Autowired
	PersonService personService;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = { 5, 10, 25, 50 };
	
	Map<Long, String> mapRoles = null;
	public Map<Long, String> getRoles() {
		if(mapRoles!=null) {
			return mapRoles;
		}
		mapRoles = new HashMap<>();
	    mapRoles.put(1L, "Teacher");
	    mapRoles.put(2L, "Student");
	    return mapRoles;
	}
	

	@GetMapping("/")
    public String home(@RequestParam("recordsPerPage") Optional<Integer> pageSize, 
    		@RequestParam("currentPage") Optional<Integer> page, 
            @RequestParam(defaultValue = "id") String sortBy,
    		Model model) {
		
		int recordsPerPage = pageSize.orElse(INITIAL_PAGE_SIZE);
		int tempPageNumber = page.isPresent()?page.get()-1:1;
        int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : tempPageNumber;

        List<Person> personsList = personService.findAll(currentPage, recordsPerPage, sortBy);
        long rows = personService.count();
        
		long nOfPages = rows / recordsPerPage;
		if (nOfPages % recordsPerPage > 0 && (nOfPages * recordsPerPage != rows)) {
			nOfPages++;
		}
		
		model.addAttribute("noOfPages", nOfPages);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("recordsPerPage", recordsPerPage);
		model.addAttribute("pageSizes", PAGE_SIZES);
        
        
        model.addAttribute("personsList",personsList);
        model.addAttribute("mapRoles", getRoles());

        return "index";
    	
    }

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		personService.delete(id);
		return "redirect:/";
	}
	
	
	@Transactional
    @PostMapping("/savePerson")
    public String savePerson(@ModelAttribute("person") PersonDto personDto) {
		Person person = new Person();
		BeanUtils.copyProperties(personDto, person);
		personService.createOrUpdatePerson(person);
        return "redirect:/";
    }
    @GetMapping("/search")
    public String search(@ModelAttribute("person") PersonDto personDto, Model model) {
		Person person = new Person();
		BeanUtils.copyProperties(personDto, person);
		List<Person> personsList = personService.findByFirstnameOrLastnameOrderById(person.getFirstname(), person.getFirstname());
		model.addAttribute("personsList",personsList);
		model.addAttribute("mapRoles", mapRoles);
        return "index";
    }
	
	@PostMapping(value = "/create")
	public String create(Model model) {
		model.addAttribute("mapRoles", mapRoles);
		return "newPerson";
	}


	@GetMapping(path = { "edit", "/edit/?id={id}" })
	public String update(Model model, @RequestParam("id") Optional<Long> id) {
	    model.addAttribute("mapRoles", mapRoles);
		if (id.isPresent()) {
			Optional<Person> personOptional = personService.findById(id.get());
			if (personOptional.isPresent()) {
				model.addAttribute("person", personOptional.get());
			}
		}

		return "newPerson";
	}
}

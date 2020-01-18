package by.javaeecources.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import by.javaeecources.model.PagerModel;
import by.javaeecources.model.Person;
import by.javaeecources.model.PersonDto;
import by.javaeecources.service.PersonService;
@Controller
public class PersonController {
	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = { 5, 10, 25, 50 };

	@Autowired
	PersonService personService;

	@GetMapping(value = {"/", "/search/persons"})
    public ModelAndView home(@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page, HttpServletRequest request, Optional<PersonDto> dto) {
    	ModelAndView modelAndView = new ModelAndView("index");
    	String attr = HandlerMapping.LOOKUP_PATH;
		String uriVars = (String) request.getAttribute(attr);
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int tempPageNumber = page.isPresent()?page.get()-1:1;
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : tempPageNumber;
        Page<Person> personsList = null;
        
        String name = dto.isPresent()?dto.get().getFirstname():"";
        
        if(uriVars!=null && uriVars.contains("/search/persons")&& !name.isEmpty()){
        	personsList = personService.findAllPersonWithPagination(PageRequest.of(evalPage, evalPageSize), name);
        }else {
        	personsList = personService.findAll(PageRequest.of(evalPage, evalPageSize));
        }
        PagerModel pager = new PagerModel(personsList.getTotalPages(),personsList.getNumber(),BUTTONS_TO_SHOW);

        modelAndView.addObject("personsList",personsList);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("person", new Person()); 
        return modelAndView;
    	
    }

	@GetMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		personService.delete(id);
		return "redirect:/";
	}
	@Transactional
	@PostMapping(path = "/create")
	public String create(@ModelAttribute("person") PersonDto personDto, BindingResult bindingResult, Model model) {
		Person person = new Person();
		BeanUtils.copyProperties(personDto, person);

		Optional<Person> optional = Optional.of(personService.createOrUpdatePerson(person));
		if(optional.isPresent() && optional.get().getId()!=0) {
			return "redirect:/";
		}else {
			return "add-edit-person";
		}
		
	}


	@GetMapping(path = { "edit", "/edit/{id}" })
	public String update(Model model, @PathVariable("id") Optional<Long> id) {
		if (id.isPresent()) {
			Optional<Person> personOptional = personService.findById(id.get());
			if (personOptional.isPresent()) {
				model.addAttribute("person", personOptional.get());
			}
		}

		return "add-edit-person";
	}
}

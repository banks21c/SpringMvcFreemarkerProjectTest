package com.zetcode.web;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zetcode.service.VersionService;

@Controller
public class MyController {
	private static final Logger logger = LoggerFactory.getLogger(MyController.class);

    @Autowired
    private VersionService versionService;

    @RequestMapping("/")
    public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
        return "home";
    }

    @RequestMapping("/index")
    public String index(Model model) {
    	return "index";
    }
    
    @RequestMapping (value="/index2", method = RequestMethod.GET)
    public String index2(){       
        return "index2";//Tiles definition name        
    }  
    
    @RequestMapping (value="/index3", method = RequestMethod.GET)
    public ModelAndView index3(){       
        ModelAndView modelAndView = new ModelAndView("index3");        
        return modelAndView;
    }

    @RequestMapping (value="/index4", method = RequestMethod.GET)
    public String index4(Model model){       
    	model.addAttribute("<name of the attribute>","<value as object>");
        return "index4";//Tiles definition name        
    }    

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public ModelAndView version() {

        String version = versionService.getVersion();

        ModelAndView model = new ModelAndView("version");
        model.addObject("version", version);

        return model;
    }
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ModelAndView hello() {
    	
    	ModelAndView model = new ModelAndView("hello");
    	model.addObject("name", "kdhong");
    	
    	return model;
    }
}
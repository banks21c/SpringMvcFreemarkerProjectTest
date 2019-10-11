package com.zetcode.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zetcode.bean.Product;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;
import freemarker.template.Version;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/ftl/struts", method = RequestMethod.GET)
	public String struts(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		model.addAttribute("user", "홍길동");

		List<Product> lst = new ArrayList<Product>();
		Product prdt = new Product();
		prdt.setName("computer");
		prdt.setPrice(1000000);
		lst.add(prdt);

		prdt = new Product();
		prdt.setName("mouse");
		prdt.setPrice(10000);
		lst.add(prdt);

		model.addAttribute("latestProducts", lst);

		return "ftl/struts";
	}

	@RequestMapping(value = "/ftl/latestProducts", method = RequestMethod.GET)
	public String latestProducts(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		// 1. Configure FreeMarker
		//
		// You should do this ONLY ONCE, when your application starts,
		// then reuse the same Configuration object elsewhere.

		// Configuration cfg = new Configuration();
		Configuration cfg = new Configuration(new Version("2.3.23"));

		// Where do we load the templates from:
//        cfg.setClassForTemplateLoading(MainTest.class, "templates");
//        cfg.setClassForTemplateLoading(MainTest.class, "./templates");
//
//The name was interpreted by this TemplateLoader: LegacyDefaultFileTemplateLoader(baseDir="C:\sts-4.4.0.RELEASE", canonicalBasePath="C:\sts-4.4.0.RELEASE\").
//Warning: The "template_loader" FreeMarker setting wasn't set (Configuration.setTemplateLoader), and using the default value is most certainly not intended and dangerous, and can be the cause of this error.
//		cfg.setClassForTemplateLoading(HomeController.class, "/templates");
//		cfg.setClassForTemplateLoading(HomeController.class, "/templates/ftl/");
		cfg.setClassForTemplateLoading(HomeController.class, "/WEB-INF/views/ftl/");

		// Some other recommended settings:
		cfg.setIncompatibleImprovements(new Version(2, 3, 20));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setLocale(Locale.US);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		// 2. Proccess template(s)
		//
		// You will do this for several times in typical applications.

		// 2.1. Prepare the template input:

		model.addAttribute("user", "홍길동");

		List<Product> lst = new ArrayList<Product>();
		Product prdt = new Product();
		prdt.setName("computer");
		prdt.setPrice(1000000);
		lst.add(prdt);

		prdt = new Product();
		prdt.setName("mouse");
		prdt.setPrice(10000);
		lst.add(prdt);

		model.addAttribute("latestProducts", lst);

		// 2.2. Get the template

		Template template;
		try {
			template = cfg.getTemplate("latestProducts.ftl");
//	        input.put("msg", "Today is a beautiful day");

			// 2.3. Generate the output

			// Write output to the console
			Writer consoleWriter = new OutputStreamWriter(System.out);
			template.process(model, consoleWriter);

			// For the sake of example, also write output into a file:
			Writer fileWriter = new FileWriter(new File("output.html"));
			try {
				template.process(model, fileWriter);
			} finally {
				fileWriter.flush();
				fileWriter.close();
			}
		} catch (TemplateNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedTemplateNameException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}

		return "ftl/latestProducts";
	}

}

package com.zjy.controller;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.zjy.service.UserService;
import com.zjy.entity.Aritcle;
import com.zjy.entity.User;
import com.zjy.util.SpringContextHolder;

@Controller
@RequestMapping("/hello")
public class Hello {
	@Autowired 
    private UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {		
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {

			@Override
			public String getAsText() {
				return getValue().toString();
			}

			@Override
			public void setAsText(String text)  {
				SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
				System.out.println("ddd"+text);
				try {
					setValue(simpleDateFormat.parse(text));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					IllegalArgumentException t = new IllegalArgumentException();
					t.initCause(e);
					throw t;
				}
			}

		});
	}
	
	@ExceptionHandler
    public ModelAndView exceptionHandler(Exception ex){
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("exception", ex);
        System.out.println("in testExceptionHandler");
        return mv;
    }
	
	@RequestMapping("/mapping/path")
	public @ResponseBody String byPath() {
	        return "Mapped by path!";
	}
	
	@RequestMapping(value="/mapping/path/*", method=RequestMethod.GET)
	public @ResponseBody String byPathPattern(HttpServletRequest request) {
	        return "Mapped by path pattern ('" + request.getRequestURI() + "')";
	    }
	
	@RequestMapping(value="/mapping/parameter", method=RequestMethod.GET, params="foo")
	public @ResponseBody String byParameter() {
	    return "Mapped by path + method + presence of query parameter!";
	}

	@RequestMapping(value="/mapping/parameter", method=RequestMethod.GET, params={"foo","t"})
	public @ResponseBody String byParameter2() {
	    return "Mapped by path + method + presence of query parameter!";
	}

	@RequestMapping("/test1/{param}")
	public String Test1(@PathVariable("param") int ownerId, Model model) {

		model.addAttribute("message", ownerId);
		return "test1";
	}
	
	@RequestMapping(value="/mapping/header", method=RequestMethod.GET, headers="FooHeader=foo")
	public @ResponseBody String byHeader() {
	    return "Mapped by path + method + presence of header!";
	}
	@RequestMapping(value="/mapping/consumes", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String byConsumes(/*@RequestBody JavaBean javaBean*/) {
	    return "Mapped by path + method + consumable media type (javaBean )";
	}
	/*
	@RequestMapping(value="/mapping/produces", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JavaBean byProducesJson() {
	        return new JavaBean();
	}*/

	@RequestMapping(value="param", method=RequestMethod.GET)
	public @ResponseBody String withParam(@RequestParam String foo) {
	        return "Obtained 'foo' query parameter value '" + foo + "'";
	}
	
	@RequestMapping(value="path/{var}", method=RequestMethod.GET)
	public @ResponseBody String withPathVariable(@PathVariable String var) {
	    return "Obtained 'var' path variable value '" + var + "'";
	}
	
	@RequestMapping(value="{path}/simple", method=RequestMethod.GET)
	public @ResponseBody String withMatrixVariable(@PathVariable String path, @MatrixVariable String foo) {
	    return "Obtained matrix variable 'foo=" + foo + "' from path segment '" + path + "'";
	}
	
	@RequestMapping(value="{path1}/{path2}", method=RequestMethod.GET)// matrixvars;foo=bar1/multiple;foo=bar2
	public @ResponseBody String withMatrixVariablesMultiple (
	            @PathVariable String path1, @MatrixVariable(value="foo", pathVar="path1") String foo1,
	            @PathVariable String path2, @MatrixVariable(value="foo", pathVar="path2") String foo2) {

	    return "Obtained matrix variable foo=" + foo1 + " from path segment '" + path1
	                + "' and variable 'foo=" + foo2 + " from path segment '" + path2 + "'";
	    }

	@RequestMapping(value="header", method=RequestMethod.GET)
	public @ResponseBody String withHeader(@RequestHeader String Accept) {
	    return "Obtained 'Accept' header '" + Accept + "'";
	}

	@RequestMapping(value="cookie", method=RequestMethod.GET)
	public @ResponseBody String withCookie(@CookieValue String openid_provider,@SessionAttribute(value = "sessionStr") String sessionStr) {
	    return "Obtained 'openid_provider' cookie '" + openid_provider + "'";
	    }

	@RequestMapping(value="entity", method=RequestMethod.POST)
    public @ResponseBody String withEntity(HttpEntity<String> entity) {
    return "Posted request body '" + entity.getBody() + "'; headers = " + entity.getHeaders();
}
	@PostMapping("/post")
	@ResponseBody
	public String testPost(Aritcle aritcle,BindingResult result) {
		System.out.println( result.getAllErrors());
		for (ObjectError err : result.getAllErrors()) {
			System.out.println(err.getDefaultMessage());
		}
		return "testPost";
	}
	
	@GetMapping("/test2/{name}/{age}")
	public String test2(@Valid User user, BindingResult result) {
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors().get(0).getDefaultMessage());
		}
		User user1 = userService.selectUserById(1); 
		System.out.println(user1.getName());
		return "test1";
	}

	@GetMapping(value = "/test3", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String test3(@RequestParam Collection<Integer> values ,Model model) {
		return "{\"test1\":12}";
	}


}

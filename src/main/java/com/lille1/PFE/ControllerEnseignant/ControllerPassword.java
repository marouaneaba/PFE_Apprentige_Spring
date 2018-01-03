package com.lille1.PFE.ControllerEnseignant;

import java.security.Principal;

import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/forgetPassword")
public class ControllerPassword {

	@RequestMapping(method = RequestMethod.GET)
	public String GetLogin(ModelMap pModel) {

		return "ForgetPassword";

	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String POSTLogin(ModelMap pModel,@RequestParam("email") String email,@RequestParam("name") String name) {

		return "ForgetPassword";

	}
}

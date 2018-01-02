package com.lille1.PFE.ControllerEnseignant;

import java.security.Principal;

import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/forgetPassword")
public class ControllerPassword {

	@RequestMapping(method = RequestMethod.GET)
	public String GetLogin(ModelMap pModel) {

		return "login";

	}
}

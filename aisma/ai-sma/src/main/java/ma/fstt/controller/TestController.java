package ma.fstt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import jade.wrapper.StaleProxyException;
import ma.fstt.service.JadeService;

/**
 * This controller does nothing!
 * @author Nouhaila
 *
 */
@RestController
public class TestController {

	@Autowired
	JadeService service;
	
	@RequestMapping(value = "/return", method = RequestMethod.GET)
	public void fun() {
		try {
			service.getContainerController().createNewAgent("TestAgent", "ma.fstt.agent.Test2Agent", null).start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}
	
}

package ma.fstt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import ma.fstt.agent.ScrapingAgent;
import ma.fstt.model.ScrapRequest;
import ma.fstt.service.JadeService;

/**
 * this is the controller that handles the client requests for starting a new scraping job
 * @author abdelbari
 *
 */
@RestController
@CrossOrigin("*")
public class ScrapingController {
	@Autowired
	private JadeService jade;
	
	@RequestMapping(value = "/scrap", method = RequestMethod.POST)
	public ResponseEntity<String> scrap_start(@RequestBody ScrapRequest request) {
		try {
			jade.getContainerController().createNewAgent(ScrapingAgent.TYPE + "-" + request.getTag(), "ma.fstt.agent.ScrapingAgent", new String[] {request.getTag()}).start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("cant start new ScrapingAgent!");
		}
		return ResponseEntity.ok("Job Started!");
	}
	
	@RequestMapping(value = "/scrap", method = RequestMethod.DELETE)
	public ResponseEntity<String> scrap_stop(@RequestBody ScrapRequest request) {
		try {
			jade.getContainerController().getAgent(ScrapingAgent.TYPE + "-" + request.getTag()).kill();
		} catch (StaleProxyException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("cant stop the ScrapingAgent!");
		} catch (ControllerException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("cant stop the ScrapingAgent!");
		}
		return ResponseEntity.ok("Job Stoped!");
	}
}

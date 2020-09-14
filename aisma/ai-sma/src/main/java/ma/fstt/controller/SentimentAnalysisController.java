package ma.fstt.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jade.wrapper.StaleProxyException;
import ma.fstt.model.ScrapRequest;
import ma.fstt.model.SentimentAnalysisResponse;
import ma.fstt.service.JadeService;
import ma.fstt.util.AppVars;

@RestController
@CrossOrigin("*")
public class SentimentAnalysisController {
	
	@Autowired
	private JadeService jade;
	
	@RequestMapping(value = "/sentiment", method = RequestMethod.POST)
	public ResponseEntity<String> scrape(@RequestBody ScrapRequest request) {
		try {
			jade.getContainerController().createNewAgent("sentiment-"+request.getTag(), "ma.fstt.agent.SentimentAnalysisAgent", new String[] {request.getTag()}).start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("cant start new SentimentAnalysisAgent!");
		}
		return ResponseEntity.ok("Job Started!");
	}
	
	

	@RequestMapping(value = "/sentiment", method = RequestMethod.PUT)
	public ResponseEntity<SentimentAnalysisResponse> result(@RequestBody(required = false) ScrapRequest request) {
		SentimentAnalysisResponse resp = new SentimentAnalysisResponse();
		resp.setTag((request!=null)?request.getTag():"");
		String cmd = String.format("python main.py %s", resp.getTag());
		try {
			Process process = Runtime.getRuntime().exec(cmd, null, new File(AppVars.COUNT_APP));
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			resp.getDatasets().get(0).getData().clear();
			resp.getDatasets().get(0).getData().add(Integer.parseInt(br.readLine()));
			resp.getDatasets().get(0).getData().add(Integer.parseInt(br.readLine()));
			// resp.setPositive(Integer.parseInt(br.readLine()));
			// resp.setNegative(Integer.parseInt(br.readLine()));
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(resp);
		}
		return ResponseEntity.ok(resp);
	}
}

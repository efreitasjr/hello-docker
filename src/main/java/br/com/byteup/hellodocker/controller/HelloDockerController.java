package br.com.byteup.hellodocker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.byteup.hellodocker.service.SqsEventProducerService;

@RequestMapping("docker")
@RestController
public class HelloDockerController {
	
	@Autowired
    private AmazonS3 s3client;
	
	@Autowired
    private SqsEventProducerService sqsEventProducerService;

    @GetMapping("batata")
    public List<Bucket> helloDockerBatat() {
        return s3client.listBuckets();
    }

	@GetMapping
	public String helloDocker() {
		return "Hello world! ByteUpAcademy tutorial";
	}
	
	@PostMapping("")
    public ResponseEntity helloDocker(@RequestBody JsonNode jsonNode) {
        sqsEventProducerService.publishEvent(jsonNode);
        return ResponseEntity.ok().build();
    }
}

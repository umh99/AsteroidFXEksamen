package dk.sdu.mmmi.cbse.scoring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@RestController
public class ScoringServiceApplication {

	private final AtomicInteger score = new AtomicInteger(0);


	public static void main(String[] args) {
		SpringApplication.run(ScoringServiceApplication.class, args);
	}


	@PostMapping("/score")
	public void add(@RequestBody ScoreEvent evt) {
		score.addAndGet(evt.getValue());
	}


	@GetMapping("/score")
	public int current() {
		return score.get();
	}
}

class ScoreEvent {
	private int value;

	public int getValue() { return value; }


}
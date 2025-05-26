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

	/** Game sends POST /score  with JSON: {"value":100} */
	@PostMapping("/score")
	public void add(@RequestBody ScoreEvent evt) {
		score.addAndGet(evt.getValue());
	}

	/** UI polls GET /score  -> current total */
	@GetMapping("/score")
	public int current() {
		return score.get();
	}
}

/** Simple DTO for JSON deserialisation */
class ScoreEvent {
	private int value;

	public ScoreEvent() {}              // default ctor needed by Jackson
	public ScoreEvent(int value) { this.value = value; }

	public int getValue() { return value; }
	public void setValue(int value) { this.value = value; }

}
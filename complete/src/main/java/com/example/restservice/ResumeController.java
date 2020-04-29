package com.example.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ResumeController {

	private static final String proficiency = "Skill level: %s";

	@GetMapping("/resume")
	public Experience experience(
			@RequestParam(value = "experience", defaultValue = "Data Scientist") String skill) {
		skill = getString(skill);
		return new Experience(String.format(proficiency, skill));}

	private String getString(@RequestParam(value = "experience", defaultValue = "Data Scientist") String skill) {
		switch (skill) {
			case "Python":
				skill = skill + " 8/10"; break;
			case "Java":
				skill = skill + " 7/10"; break;
			case "pandas":
				skill = skill + " 8/10"; break;
			case "numpy":
				skill = skill + " 8/10"; break;
			case "VBA":
				skill = skill + " 10/10"; break;
			case "API":
				skill = skill + " 4/10"; break;
			case "Spring":
				skill = skill + " 4/10"; break;
			case "JIRA":
				skill = skill + " 10/10"; break;
			case "Data Scientist":
				break;
			default:
				throw new IllegalArgumentException("Skill not mapped, please choose from Python, Java," +
						"pandas, numpy, VBA, Spring, JIRA, API ");
		}
		return skill;
	}
}

//package com.example.restservice;
//
//import com.example.restservice.api.model.DataToken;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class DataController {
//    private static final String access_token = "access_token";
//    private static final String refresh_token = "refresh_token";
//    @GetMapping("/data")
//    public DataToken data(
//            @RequestParam(value = "refresh_token", defaultValue = )
//                    String skill) {
//        skill = getString(skill);
//        return new DataToken(String.format(refresh_token));}
//
//    private String getString(@RequestParam(value = "experience", defaultValue = "Data Scientist") String skill) {
//        switch (skill) {
//            case "Python":
//                skill = skill + " 8/10"; break;
//            case "Java":
//                skill = skill + " 7/10"; break;
//            case "pandas":
//                skill = skill + " 8/10"; break;
//            case "numpy":
//                skill = skill + " 8/10"; break;
//            case "VBA":
//                skill = skill + " 10/10"; break;
//            case "API":
//                skill = skill + " 4/10"; break;
//            case "Spring":
//                skill = skill + " 4/10"; break;
//            case "JIRA":
//                skill = skill + " 10/10"; break;
//            case "Data Scientist":
//                break;
//            default:
//                throw new IllegalArgumentException("Skill not mapped, please choose from Python, Java," +
//                        "pandas, numpy, VBA, Spring, JIRA, API ");
//        }
//        return skill;
//    }
//}

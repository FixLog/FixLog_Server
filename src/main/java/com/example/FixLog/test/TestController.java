package com.example.FixLog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestMemberRepository testMemberRepository;

    @GetMapping("test/api")
    public String test() {
        return "this is test.";
    }

    @PostMapping("test/rds")
    public String testRds() {
        TestMember member = new TestMember(1L, "test", "1234");
        testMemberRepository.save(member);
        return "ok";
    }

}
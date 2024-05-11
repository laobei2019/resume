package com.bussiness.problem.controller;

import com.base.controller.BaseController;
import com.bussiness.problem.service.ProblemService;
import com.bussiness.problem.entity.Problem;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("problem")
@CrossOrigin
public class ProblemController extends BaseController<ProblemService, Problem> {
}

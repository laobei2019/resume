package com.bussiness.problem.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bussiness.problem.entity.Problem;
import com.bussiness.problem.mapper.ProblemMapper;
import org.springframework.stereotype.Service;

@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {
}

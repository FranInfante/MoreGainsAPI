package com.example.MoreGains.service.impl;

import com.example.MoreGains.model.dtos.PlanDTO;
import com.example.MoreGains.model.entities.Plan;
import com.example.MoreGains.model.entities.Users;
import com.example.MoreGains.repository.PlanRepository;
import com.example.MoreGains.repository.UsersRepository;
import com.example.MoreGains.service.PlanService;
import com.example.MoreGains.util.PlanMapper;
import com.example.MoreGains.util.messages.MessageConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final UsersRepository usersRepository;

    private static final String UPLOAD_DIR = "plan_uploads";

    @Override
    public List<PlanDTO> getAllPlans() {
        List<Plan> plans = planRepository.findAll();
        return PlanMapper.listPlanEntityToDTO(plans);
    }

    @Override
    public PlanDTO getPlanById(Integer id) {
        Plan plan = planRepository.findById(id).orElse(null);
        return PlanMapper.planEntityToDTO(plan);
    }

    @Override
    public PlanDTO savePlan(PlanDTO planDTO) {
        Users user = usersRepository.findById(planDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.USER_NOT_FOUND));
        Plan plan = PlanMapper.planDTOToEntity(planDTO);
        plan.setUser(user);
        Plan savedPlan = planRepository.save(plan);
        return PlanMapper.planEntityToDTO(savedPlan);
    }

    @Override
    public List<PlanDTO> getPlansByUserId(Integer userId) {
        List<Plan> plans = planRepository.findByUserId(userId);
        return PlanMapper.listPlanEntityToDTO(plans);
    }

    @Override
    public Optional<PlanDTO> updatePlan(Integer id, PlanDTO updatePlan) throws Exception {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.PLAN_NOT_FOUND));

        if (updatePlan.getName() != null) {
            plan.setName(updatePlan.getName());
        }

        Plan savedPlan = planRepository.save(plan);
        return Optional.of(PlanMapper.planEntityToDTO(savedPlan));
    }

    @Override
    public void deletePlan(Integer planId) {
        Users user = usersRepository.findById(planId).orElseThrow(() -> new EntityNotFoundException(MessageConstants.PLAN_NOT_FOUND));
        user.setIsAvailable(false);
        usersRepository.save(user);
    }

}

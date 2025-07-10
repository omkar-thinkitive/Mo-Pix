package com.mopix.Mopix.Services.ServiceImpl;

import com.mopix.Mopix.Dtos.Request.ReportPostRequest;
import com.mopix.Mopix.Dtos.Response.ReportPostResponse;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Entity.PostEntity;
import com.mopix.Mopix.Entity.ReportPostEntity;
import com.mopix.Mopix.Entity.UserEntity;
import com.mopix.Mopix.Repository.PostRepo;
import com.mopix.Mopix.Repository.ReportPostRepo;
import com.mopix.Mopix.Repository.UserRepo;
import com.mopix.Mopix.Services.ReportPostService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReportPostServiceImpl implements ReportPostService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ReportPostRepo reportPostRepo;

    @Override
    public String reportPost(ReportPostRequest reportPostRequest) throws MopixExpection {

        UserEntity user = userRepo.findByUserName(reportPostRequest.getUserName());
        if(user == null){
            throw new MopixExpection(ResponseCode.BAD_REQUEST,"User Not Found !");
        }

        PostEntity postEntity = postRepo.getPostByUUID(reportPostRequest.getPostUUID());
        if(postEntity == null){
            throw new MopixExpection(ResponseCode.BAD_REQUEST,"Post Not Found !");
        }

        try {
            ReportPostEntity reportPost = new ReportPostEntity();
            reportPost.setReportUUID(UUID.randomUUID());
            reportPost.setUserEntity(user);
            reportPost.setPostEntity(postEntity);
            reportPost.setReportedUser(postEntity.getUserEntity());
            reportPost.setComment(reportPost.getComment());
            reportPost.setCreatedBy(String.valueOf(user.getUuid()));
            reportPost.setCreatedAt(Instant.now());
            reportPost.setUpdatedAt(Instant.now());
            reportPost.setInformUSer(false);
            reportPost.setTakenAction(false);
            reportPost.setResolve(false);
            reportPostRepo.save(reportPost);
        }catch (Exception e){
            throw new MopixExpection(ResponseCode.INTERNAL_ERROR,"report Not Add !");
        }
        return "Report Created Successfully";
    }

    @Override
    public Page<ReportPostResponse> getAllReports(String userName, Pageable pageable) throws MopixExpection {
        UserEntity user = userRepo.findByUserName(userName);

        Page<ReportPostEntity> reportPostEntities = reportPostRepo.findAllReportsByUser(user,pageable);
        return reportPostEntities.map(report ->{
            ReportPostResponse reportPostResponse = ReportPostResponse.builder()
                    .reportUUID(report.getReportUUID())
                    .user(report.getReportedUser())
                    .comment(report.getComment())
                    .postEntity(report.getPostEntity())
                    .isInformUSer(report.isInformUSer())
                    .isResolve(report.isResolve())
                    .isTakenAction(report.isTakenAction())
                    .build();
            return reportPostResponse;
        });
    }

    @Override
    public ReportPostResponse getReportById(Long id) throws MopixExpection {

        Optional<ReportPostEntity> reportPost = reportPostRepo.findById(id);
        ReportPostEntity report = reportPost.get();

        ReportPostResponse reportPostResponse = ReportPostResponse.builder()
                .user(report.getUserEntity())
                .reportUUID(report.getReportUUID())
                .build();
        return null;
    }

    //    @Override
    public ReportPostResponse getReportByUUID(UUID reportUUID) throws MopixExpection {
        ReportPostEntity report = reportPostRepo.findByReportUUIDAndDeletedFalse(reportUUID);
//                .orElseThrow(() -> new MopixExpection(ResponseCode.BAD_REQUEST, "Report Not Found!"));

        return ReportPostResponse.builder()
                .reportUUID(report.getReportUUID())
                .user(report.getReportedUser())
                .comment(report.getComment())
                .postEntity(report.getPostEntity())
                .isInformUSer(report.isInformUSer())
                .isResolve(report.isResolve())
                .isTakenAction(report.isTakenAction())
                .build();
    }

//    @Override
    public String editReport(UUID reportUUID, String newComment) throws MopixExpection {
        ReportPostEntity report = reportPostRepo.findByReportUUIDAndDeletedFalse(reportUUID);
//                .orElseThrow(() -> new MopixExpection(ResponseCode.BAD_REQUEST, "Report Not Found!"));

        report.setComment(newComment);
        report.setUpdatedAt(Instant.now());

        reportPostRepo.save(report);
        return "Report updated successfully";
    }

//    @Override
    public String deleteReport(UUID reportUUID) throws MopixExpection {
        ReportPostEntity report = reportPostRepo.findByReportUUIDAndDeletedFalse(reportUUID);
//                .orElseThrow(() -> new MopixExpection(ResponseCode.BAD_REQUEST, "Report Not Found!"));

        report.setDelete(true);
        report.setUpdatedAt(Instant.now());
        reportPostRepo.save(report);

        return "Report soft-deleted successfully";
    }



}

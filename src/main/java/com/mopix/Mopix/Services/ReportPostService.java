package com.mopix.Mopix.Services;

import com.mopix.Mopix.Dtos.Request.ReportPostRequest;
import com.mopix.Mopix.Dtos.Response.ReportPostResponse;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ReportPostService {
    String reportPost(ReportPostRequest reportPostRequest)throws MopixExpection;

    Page<ReportPostResponse> getAllReports(String userName, Pageable pageable) throws MopixExpection;

    ReportPostResponse getReportById(Long id) throws MopixExpection;
}

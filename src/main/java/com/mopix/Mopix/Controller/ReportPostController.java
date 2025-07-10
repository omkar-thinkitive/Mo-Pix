package com.mopix.Mopix.Controller;

import com.mopix.Mopix.Dtos.Request.ReportPostRequest;
import com.mopix.Mopix.Dtos.Response.ReportPostResponse;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Services.ReportPostService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import com.mopix.Mopix.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/master/v1/report")
public class ReportPostController extends AppController{

    @Autowired
    private ReportPostService reportPostService;

    @PostMapping("/post")
    public ResponseEntity<Response> reportPost(@RequestBody ReportPostRequest reportPostRequest) throws MopixExpection, IOException {
        String response =  reportPostService.reportPost(reportPostRequest);
        return success(ResponseCode.SUCCESS,response);
    }

    @GetMapping("/all/report")
    public ResponseEntity<Response> getAllReport(@RequestParam String userName,@org.springdoc.core.annotations.ParameterObject Pageable pageable) throws MopixExpection, IOException {
        Page<ReportPostResponse> response =  reportPostService.getAllReports(userName,pageable);
        return success(ResponseCode.SUCCESS,"Fetch All Reports Successfully", response);
    } // editReport, deleteReport

    @GetMapping("/id")
    public ResponseEntity<Response> getReportByUUID(@RequestParam Long Id) throws MopixExpection, IOException {
        ReportPostResponse response =  reportPostService.getReportById(Id);
        return success(ResponseCode.SUCCESS,"Fetch Reports Successfully", response);
    }
}

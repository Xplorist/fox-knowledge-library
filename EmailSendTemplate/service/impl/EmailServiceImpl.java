package com.foxconn.bidding.service.impl;

import com.foxconn.bidding.mapper.BillMapper;
import com.foxconn.bidding.mapper.UserMapper;
import com.foxconn.bidding.model.*;
import com.foxconn.bidding.service.EmailService;
import com.foxconn.bidding.util.EmailUtil;
import com.foxconn.bidding.util.MoneyNumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private UserMapper userMapper;

    // 忘記密碼，發送驗證碼郵件
    @Override
    public ResultParam send_verification_code_mail(USER_INFO_bean user_bean, VERIFICATION_CODE_bean code_bean) {
        String code = code_bean.getCode();

        // 郵件地址
        String address = user_bean.getEmail();

        // 郵件主題
        String subject = "【模治檢具訂單信息服務平台】忘記密碼-驗證碼";

        // 郵件內容
        String content = "【模治檢具訂單信息服務平台】忘記密碼-驗證碼<br>";
        content += "驗證碼為：<b style='color: red;'>" + code + "</b>";

		// 發送郵件
        Boolean f_sendEmail = EmailUtil.sendEmail(EmailContent.of(address, subject, content));
        if(!f_sendEmail) {
            return new ResultParam("0", "發送驗證碼郵件失敗", null);
        }

        return new ResultParam("1", "發送驗證碼郵件成功", null);
    }
}
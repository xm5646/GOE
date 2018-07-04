package com.project.goe.projectgeodbserver.controller;

import com.project.goe.projectgeodbserver.entity.Notice;
import com.project.goe.projectgeodbserver.service.NoticeService;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping(value = "/notice")
@CrossOrigin
public class NoticeController {

    @Autowired
    private NoticeService noticeService;


    /**
     * 添加新的通知
     * @param title
     * @param content
     * @param showStatus
     * @return
     */
    @PostMapping("/addNotice")
    public RetMsg saveNotice(String title, String content,boolean showStatus) {
        Notice notice = new Notice();
        notice.setShowStatus(showStatus);
        notice.setTitle(title);
        notice.setContent(content);
        noticeService.save(notice);
        RetMsg msg = new RetMsg();
        msg.setCode(200);
        msg.setSuccess(true);
        msg.setMessage("添加成功");
        return  msg;
    }


    /**
     * 更新通知
     * @param noticeId
     * @param title
     * @param content
     * @param showStatus
     * @return 返回更新消息
     */
    @PostMapping("/updateNotice")
    public RetMsg updateNotice(Long noticeId,String title, String content, boolean showStatus) {
        Notice notice = new Notice();
        notice.setNoticeId(noticeId);
        notice.setShowStatus(showStatus);
        notice.setTitle(title);
        notice.setContent(content);
        try{
            noticeService.save(notice);
            RetMsg msg = new RetMsg();
            msg.setCode(200);
            msg.setSuccess(true);
            msg.setMessage("更新成功");
            return  msg;
        } catch (Exception e) {
            RetMsg msg = new RetMsg();
            msg.setCode(400);
            msg.setSuccess(true);
            msg.setMessage("更新失败");
            return  msg;
        }

    }

    /**
     * 分页查询所有通知
     * @param pageNum
     * @return
     */
    @GetMapping("/getAllNotices")
    public RetMsg getAllNoticeByPage(@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum){

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest( pageNum, 10, sort);
        RetMsg retMsg = new RetMsg();
        retMsg.setSuccess(true);
        retMsg.setCode(200);
        retMsg.setData(noticeService.getAll(pageable));
        return  retMsg;
    }

    /**
     * 查询所有设置为显示状态的通知
     * @return
     */
    @GetMapping("/getShownNotices")
    public RetMsg getAllShownNotice(){
        RetMsg retMsg = new RetMsg();
        retMsg.setSuccess(true);
        retMsg.setCode(200);
        retMsg.setData(noticeService.findAllShownNotices());
        return  retMsg;
    }
}

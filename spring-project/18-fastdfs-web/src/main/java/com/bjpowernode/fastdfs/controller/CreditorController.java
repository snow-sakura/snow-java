package com.bjpowernode.fastdfs.controller;

import com.bjpowernode.fastdfs.model.Creditor;
import com.bjpowernode.fastdfs.service.CreditorService;
import com.bjpowernode.fastdfs.util.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ClassName:CreditorController
 * Package:com.bjpowernode.fastdfs.controller
 * Description:
 *
 * @date:2018/10/13 10:26
 * @author:bjpowernode.com
 */
@Controller
public class CreditorController {
    @Autowired
    private CreditorService creditorService;
    @RequestMapping("/")
    public String  index(Model model){

        List<Creditor>list=creditorService.selectAll();
        model.addAttribute("creditorList",list);
        return "creditors";
    }

    @RequestMapping("/goUpdate/{id}")
    public String goUpdate(@PathVariable Integer id,Model model){
        Creditor creditor= creditorService.selectCreditorById(id);
        model.addAttribute("creditor",creditor);
        return "upload";
    }
    @RequestMapping("/upload")
    public String upload(Integer id, MultipartFile uploadFile ){
        try {
            if(uploadFile.isEmpty()){
                System.out.println("返回上传页面给用户提示提示没有选择要上传的文件");
                return "";
            }
            byte[] bytes = uploadFile.getBytes();//获取文件的字节数组
            String fileName=uploadFile.getOriginalFilename();//获取文件名 例如xx/dsfsd/dsfs.xxx
            String fileType= uploadFile.getContentType();//获取文件类型
            long fileSize=uploadFile.getSize();//获取文件大小
            String extFileName=fileName.substring(fileName.lastIndexOf(".")+1);//有可能会出错因为有些文件可以没有扩展名
            String[]results= FastDFSUtil.upload(bytes,extFileName);
            Creditor creditor=new Creditor();
            creditor.setId(id);
            creditor.setFileSize(fileSize);
            creditor.setOldFileName(fileName);
            creditor.setFileType(fileType);
            creditor.setGroupName(results[0]);//可能出现错，例如文件上传失败
            creditor.setRemoteFileName(results[1]);
            creditorService.updateFile(creditor);

        }catch (Exception e){

        }

        return "redirect:/";
    }
    @RequestMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Integer id){
        Creditor creditor= creditorService.selectCreditorById(id);
        byte[]bytes=  FastDFSUtil.download(creditor.getGroupName(),creditor.getRemoteFileName());
        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + creditor.getOldFileName());
        headers.add("Content-Type",creditor.getFileType());//这是文件下载的类型，否则文件将以html格式完成下载
        headers.add("Content-Length",creditor.getFileSize()+"");//设置文件的大小，用于显示下载时的进度条
        ResponseEntity responseEntity=new ResponseEntity(bytes,headers, HttpStatus.OK);
        return responseEntity;
    }
    @RequestMapping("/delete/{id}")
    public String  delete(@PathVariable Integer id){

        creditorService.deleteFile(id);
        return "redirect:/";
    }
}

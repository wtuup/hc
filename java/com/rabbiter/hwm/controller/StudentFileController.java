package com.rabbiter.hwm.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.hwm.common.Constants;
import com.rabbiter.hwm.common.Result;
import com.rabbiter.hwm.controller.dto.StudentDTO;
import com.rabbiter.hwm.entity.StudentFile;
import com.rabbiter.hwm.mapper.StudentFileMapper;
import com.rabbiter.hwm.service.IStudentFileService;
import com.rabbiter.hwm.utils.PathUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2024-03-17
 */
@RestController
@RequestMapping("/student-file")
public class StudentFileController {

    @Resource
    private IStudentFileService studentFileService;
    @Resource
    private StudentFileMapper studentFileMapper;

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam Boolean lookFlag
    ) {
        QueryWrapper<StudentFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("title", name);
        }
        if(!lookFlag) {
            queryWrapper.eq("flag", 0);
        }
        return Result.success(studentFileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @PostMapping("/upload")
    public Result upload(@RequestParam(defaultValue = "") MultipartFile file , @RequestParam String dwc, @RequestParam Integer userFileId, @RequestParam String title, @RequestParam String an1,@RequestParam String student,@RequestParam Integer studentId,@RequestParam String content) throws IOException {
        if(studentFileMapper.selectCount(new QueryWrapper<StudentFile>().in("flag", 0, 1).eq("user_file_id", userFileId)) > 0) {
            return Result.error(Constants.CODE_400, "您已经提交过该作业等待审核中 或 已完成该作业，无法重复提交");
        }
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 定义一个文件唯一的标识码
        String fileUUID = IdUtil.fastSimpleUUID() + StrUtil.DOT + type;

        File uploadFile = new File(PathUtils.getClassLoadRootPath() + "/files/" + fileUUID);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }

        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        StudentFile dbFiles = getFileByMd5(md5);
        if (dbFiles != null) {
            url = dbFiles.getUrl();
        } else {
            // 上传文件到磁盘
            file.transferTo(uploadFile);
            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url = "/files/" + fileUUID;
        }


        // 存储数据库
        StudentFile saveFile = new StudentFile();
        saveFile.setName(originalFilename);
        saveFile.setDwc(dwc);

        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        saveFile.setUserFileId(userFileId);
        saveFile.setTitle(title);
        saveFile.setContent(content);
        saveFile.setAn1(an1);
        saveFile.setStudent(student);
        saveFile.setStudentId(studentId);
        studentFileMapper.insert(saveFile);
        System.out.println(url);

        return Result.success();
    }
    private StudentFile getFileByMd5(String md5) {
        // 查询文件的md5是否存在
        QueryWrapper<StudentFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<StudentFile> studentFileList = studentFileMapper.selectList(queryWrapper);
        return studentFileList.size() == 0 ? null : studentFileList.get(0);
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        StudentFile studentFile = studentFileMapper.selectById(id);
        studentFileService.removeById(studentFile);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        studentFileService.removeByIds(ids);
        return Result.success();
    }
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(PathUtils.getClassLoadRootPath() + "/files/" + fileUUID);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        try {
            os.write(FileUtil.readBytes(uploadFile));
        } catch (Exception e) {
            System.err.println("文件下载失败，文件不存在");
        }
        os.flush();
        os.close();
    }
    @PostMapping
    public Result save(@RequestBody StudentDTO studentDTO){
        QueryWrapper<StudentFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student", studentDTO.getStudent());
        queryWrapper.eq("dwc",studentDTO.getDwc());
        queryWrapper.eq("user_file_id",studentDTO.getUserFileId());
        queryWrapper.eq("title",studentDTO.getTitle());
        List<StudentFile> list=studentFileService.list(queryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            StudentFile studentFile = list.get(0);
            studentFile.setFlag(studentDTO.getFlag());
            studentFile.setScore(studentDTO.getScore());
            studentFileService.updateById(studentFile);
        }
        return Result.success();
    }
}


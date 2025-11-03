package com.rabbiter.hwm.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.hwm.common.Result;
import com.rabbiter.hwm.entity.UserFile;
import com.rabbiter.hwm.mapper.UserFileMapper;
import com.rabbiter.hwm.service.IUserFileService;
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
 * @since 2024-03-15
 */
@RestController
@RequestMapping("/user-file")
public class UserFileController {

    @Resource
    private IUserFileService userFileService;
    @Resource
    private UserFileMapper userFileMapper;
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody UserFile userFile){
        return Result.success(userFileService.saveOrUpdate(userFile));
    }


    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name
                           ) {

        QueryWrapper<UserFile> queryWrapper = new QueryWrapper<>();
        // 查询未删除的记录

        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("title", name);
        }
        return Result.success(userFileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @PostMapping("/upload")
    public Result upload(@RequestParam(defaultValue = "") MultipartFile file ,@RequestParam String dwc,@RequestParam String title,@RequestParam String content) throws IOException {
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
        UserFile dbFiles = getFileByMd5(md5);
        if (dbFiles != null) {
            url = dbFiles.getUrl();
        } else {
            // 上传文件到磁盘
            file.transferTo(uploadFile);
            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url = "/files/" + fileUUID;
        }


        // 存储数据库
        UserFile saveFile = new UserFile();
        saveFile.setName(originalFilename);
        saveFile.setDwc(dwc);
        saveFile.setType(type);
        saveFile.setSize(size/1024); // 单位 kb
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        saveFile.setTitle(title);
        saveFile.setContent(content);
        userFileMapper.insert(saveFile);
        System.out.println(url);

        return Result.success();
    }
    private UserFile getFileByMd5(String md5) {
        // 查询文件的md5是否存在
        QueryWrapper<UserFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<UserFile> userFileList = userFileMapper.selectList(queryWrapper);
        return userFileList.size() == 0 ? null : userFileList.get(0);
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        UserFile userFile = userFileMapper.selectById(id);
        userFileService.removeById(userFile);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
            userFileService.removeByIds(ids);
        return Result.success();
    }
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(PathUtils.getClassLoadRootPath() + fileUUID);
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

}


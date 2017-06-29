/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传组件控制器
 *
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-6-27 19:08
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @ResponseBody
    @RequestMapping(value = "upload")
    public JSONObject upload(HttpServletRequest request) {
        Part part ;
        JSONObject jsonObject = new JSONObject();
        try {
            part = request.getPart("uploadFile");
            String root = request.getServletContext().getRealPath("/attachment");

            // 判断文件夹是否存在，如果不存在创建一个文件夹
            File file = new File(root);
            if(!file.exists()){
               file.mkdirs();
            }

            String headName = part.getHeader("content-disposition");
            String ext = headName.substring(headName.lastIndexOf("."), headName.length() - 1);
            String ranString = UUID.randomUUID().toString();
            String filename = root + "/" + ranString + ext;
            part.write(filename);
            filename = "/attachment/"+ranString + ext;
            jsonObject.put("filename", filename);
        } catch (IOException | ServletException e) {
            jsonObject.put("error", "invalid_request");
        }
        return jsonObject;
    }

}

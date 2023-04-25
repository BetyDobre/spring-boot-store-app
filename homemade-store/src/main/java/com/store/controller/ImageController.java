package com.store.controller;

import com.store.domain.Decoration;
import com.store.service.DecorationService;
import com.store.service.ImageService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final DecorationService decorationService;

    public ImageController(@Autowired DecorationService decorationService) {
        this.decorationService = decorationService;
    }

    @GetMapping("/decorations/getImage/{id}")
    public void downloadImage(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Decoration decoration = decorationService.findDecorationByDecorationId(id);

        if (decoration.getImage() != null) {
            byte[] byteArray = new byte[decoration.getImage().length];
            int i = 0;
            for (Byte wrappedByte : decoration.getImage()) {
                byteArray[i++] = wrappedByte;
            }
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            try {
                IOUtils.copy(is, response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
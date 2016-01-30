package net.realtoner.web.spring.file;

import net.realtoner.file.FileContext;
import net.realtoner.file.FileManager;
import net.realtoner.file.FileNameDecider;
import net.realtoner.file.exception.DuplicateFileException;
import net.realtoner.web.servlet.HttpHeaders;
import net.realtoner.web.servlet.ServletHandleProxy;
import net.realtoner.web.servlet.ServletHandlerProxyImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * provide operations for using {@link FileManager}.
 *
 * @author RyuIkHan
 * @see FileManager
 * @see FileNameDecider
 */
public class FileManagerServiceBean implements InitializingBean {

    private FileManager fileManager = null;

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    private ServletHandleProxy servletHandlerProxy = null;

    public void setServletHandlerProxy(ServletHandleProxy servletHandlerProxy) {
        this.servletHandlerProxy = servletHandlerProxy;
    }

    private FileTypeDecider fileTypeDecider = null;

    public void setFileTypeDecider(FileTypeDecider fileTypeDecider) {
        this.fileTypeDecider = fileTypeDecider;
    }

    public static FileContext createFileContextFromMultipartFile(String path, MultipartFile multipartFile) {

        return FileContext.createFileContext(path, multipartFile.getOriginalFilename(),
                multipartFile.getOriginalFilename(), multipartFile.getSize());
    }

    /**
     * @param path
     * @param multipartFile
     */
    public void store(String path, MultipartFile multipartFile) throws IOException, DuplicateFileException {

        FileContext ctx = FileContext.createFileContext(path, multipartFile.getOriginalFilename(),
                multipartFile.getOriginalFilename(), multipartFile.getSize());

        fileManager.storeFile(ctx, multipartFile.getInputStream());
    }

    /**
     * @param path
     * @param multipartFile
     */
    public void storeIgnoreDuplicate(String path, MultipartFile multipartFile) throws IOException {

        FileContext ctx = FileContext.createFileContext(path, multipartFile.getOriginalFilename(),
                multipartFile.getOriginalFilename(), multipartFile.getSize());

        fileManager.storeFileIgnoreDuplicate(ctx, multipartFile.getInputStream());
    }

    /**
     * @param path
     * @param multipartFile
     * @param fileNameDecider
     */
    public void store(String path, MultipartFile multipartFile, FileNameDecider fileNameDecider)
            throws IOException, DuplicateFileException {

        FileContext ctx = FileContext.createFileContext(path, multipartFile.getOriginalFilename(),
                multipartFile.getOriginalFilename(), multipartFile.getSize());

        String fileName = fileNameDecider.decideFileName(ctx);
        String fileExtension = fileNameDecider.decideFileExtension(ctx);

        ctx.setFileName(fileName);
        ctx.setFileExtension(fileExtension);

        fileManager.storeFile(ctx, multipartFile.getInputStream());
    }

    /**
     * @param path
     * @param multipartFile
     * @param fileNameDecider
     */
    public void storeIgnoreDuplication(String path, MultipartFile multipartFile, FileNameDecider fileNameDecider)
            throws IOException {

        FileContext ctx = FileContext.createFileContext(path, multipartFile.getOriginalFilename(),
                multipartFile.getOriginalFilename(), multipartFile.getSize());

        String fileName = fileNameDecider.decideFileName(ctx);
        String fileExtension = fileNameDecider.decideFileExtension(ctx);

        ctx.setFileName(fileName);
        ctx.setFileExtension(fileExtension);

        fileManager.storeFileIgnoreDuplicate(ctx, multipartFile.getInputStream());
    }

    /**
     *
     * @param path
     * @param fileName
     * @param httpServletResponse
     */
    public void retrieve(String path, String fileName, HttpServletResponse httpServletResponse) throws IOException {

        FileContext ctx = FileContext.createFileContext(path, fileName);

        InputStream inputStream = fileManager.retrieveFile(ctx);

        HttpHeaders httpHeaders = new HttpHeaders(HttpHeaders.STATUS_OK);

        servletHandlerProxy.handle(httpHeaders, httpServletResponse, inputStream);
    }

    /**
     *
     * @param path
     * @param fileName
     * @param httpServletResponse
     */
    public void retrieveForDownload(String path, String fileName, HttpServletResponse httpServletResponse)
            throws IOException {

        FileContext ctx = FileContext.createFileContext(path, fileName);

        InputStream inputStream = fileManager.retrieveFile(ctx);

        HttpHeaders httpHeaders = new HttpHeaders(HttpHeaders.STATUS_OK);

        httpHeaders.setContentType(fileTypeDecider.decideType(ctx));
        httpHeaders.setContentLength(ctx.getSize());

        //set file name
        httpHeaders.putHeader("Content-disposition", "attachment; filename=" + ctx.getFullFileName());

        servletHandlerProxy.handle(httpHeaders, httpServletResponse, inputStream);
    }

    /**
     *
     * @param path
     * @param fileName
     * @param httpServletResponse
     * */
    public void retrieveForDownload(String path, String fileName, HttpServletResponse httpServletResponse,
                         FileNameDecider fileNameDecider) throws IOException {

        FileContext ctx = FileContext.createFileContext(path , fileName);

        InputStream inputStream = fileManager.retrieveFile(ctx);

        String resultFileName = fileNameDecider.decideFileName(ctx) + "." + fileNameDecider.decideFileExtension(ctx);
        HttpHeaders httpHeaders = new HttpHeaders(HttpHeaders.STATUS_OK);

        httpHeaders.setContentType(fileTypeDecider.decideType(ctx));
        httpHeaders.setContentLength(ctx.getSize());

        //set file name
        httpHeaders.putHeader("Content-disposition" , "attachment; filename=" + resultFileName);

        servletHandlerProxy.handle(httpHeaders , httpServletResponse , inputStream);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if (fileManager == null)
            throw new NullPointerException("For using " + getClass().getSimpleName() + " , FileManager must be set.");

        if (fileTypeDecider == null)
            fileTypeDecider = new DefaultFileTypeDecider();

        if (servletHandlerProxy == null)
            servletHandlerProxy = new ServletHandlerProxyImpl();
    }
}
